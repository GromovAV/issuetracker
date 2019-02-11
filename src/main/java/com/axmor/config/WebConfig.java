package com.axmor.config;

import com.axmor.model.Comment;
import com.axmor.model.Issue;
import com.axmor.model.LoginResult;
import com.axmor.model.User;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.axmor.service.IssueService;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;
import spark.utils.StringUtils;

public class WebConfig {

    private static final String USER_SESSION_ID = "user";
    private IssueService service;

    public WebConfig(IssueService service) {
        this.service = service;
        staticFileLocation("/public");
       setupRoutes();
    }

    private void setupRoutes() {
        /*
         * Shows all issues  if no user is logged in,
          */
        get("/", (Request req, Response res) -> {
            User user = getAuthenticatedUser(req);
            Map<String, Object> map = new HashMap<>();
            map.put("pageTitle", "IssueTracker");
            map.put("user", user);
            List<Issue> issues = service.getAllIssues();
            map.put("issue", issues);
            return new ModelAndView(map, "index.ftl");
        }, new FreeMarkerEngine());
        /*
         * Create issue if user is logged.
         */
        get("/create", (req, res) -> {
            User user = getAuthenticatedUser(req);
            Map<String, Object> map = new HashMap<>();
            map.put("pageTitle", "Create Issue");
            map.put("user", user);
            if (user==null) {
                res.redirect("/");
                halt();
            }
            return new ModelAndView(map, "create.ftl");
        }, new FreeMarkerEngine());
        /*
         * Presents the login form or redirect the user to
         * her main page if it's already logged in
         */
        get("/login", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            if(req.queryParams("r") != null) {
                map.put("message", "You were successfully registered and can login now");
            }
            return new ModelAndView(map, "login.ftl");
        }, new FreeMarkerEngine());
        /*
         * Logs the user in.
         */
        post("/login", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            User user = new User();
            try {
                MultiMap<String> params = new MultiMap<String>();
                UrlEncoded.decodeTo(req.body(), params, "UTF-8");
                BeanUtils.populate(user, params);
            } catch (Exception e) {
                halt(501);
                return null;
            }
            LoginResult result = service.checkUser(user);
            if(result.getUser() != null) {
                addAuthenticatedUser(req, result.getUser());
                res.redirect("/");
                halt();
            } else {
                map.put("error", result.getError());
            }
            map.put("login", user.getLogin());
            return new ModelAndView(map, "login.ftl");
        }, new FreeMarkerEngine());
        /*
         * Checks if the user is already authenticated
         */
        before("/login", (req, res) -> {
            User authUser = getAuthenticatedUser(req);
            if(authUser != null) {
                res.redirect("/");
                halt();
            }
        });
        get("/issue/:issueId", (req, res) -> {
            String issueId = req.params(":issueId");
            Issue issue = service.getIssueById(Integer.parseInt(issueId));
            User profileUser = service.getUserById(issue.getUserId());

            User authUser = getAuthenticatedUser(req);
            Map<String, Object> map = new HashMap<>();
            map.put("pageTitle", "View and Comment");
            map.put("user", authUser);
            map.put("profileUser", profileUser);
            map.put("issue",issue);
            List<Comment> comments=service.getAllCommentsByIssueId(issue.getId());
            map.put("comments",comments);
            return new ModelAndView(map, "viewAndComment.ftl");
        }, new FreeMarkerEngine());
        /*
         * Checks if the issue has user id
         */
        before("/issue/:issueId", (req, res) -> {
            String issueId = req.params(":issueId");
            Issue iss = service.getIssueById(Integer.parseInt(issueId));
            User profileUser = service.getUserById(iss.getUserId());
            if(profileUser == null) {
                halt(404, "User not Found");
            }
        });
        /*
         * Presents the register form or redirect the user to
         * main page if it's already logged in
         */
        get("/register", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "register.ftl");
        }, new FreeMarkerEngine());
        /*
         * Registers the user.
         */
        post("/register", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            User user = new User();
            try {
                MultiMap<String> params = new MultiMap<String>();
                UrlEncoded.decodeTo(req.body(), params, "UTF-8");
                BeanUtils.populate(user, params);
            } catch (Exception e) {
                halt(501);
                return null;
            }
            String error = user.validate();
            if(StringUtils.isEmpty(error)) {
                User existingUser = service.getUserByLogin(user.getLogin());
                if(existingUser == null) {
                    service.registerUser(user);
                    res.redirect("/login?r=1");
                    halt();
                } else {
                    error = "Login is already taken";
                }
            }
            map.put("error", error);
            map.put("username", user.getLogin());
            return new ModelAndView(map, "register.ftl");
        }, new FreeMarkerEngine());
        /*
         * Checks if the user is already authenticated
         */
        before("/register", (req, res) -> {
            User authUser = getAuthenticatedUser(req);
            if(authUser != null) {
                res.redirect("/");
                halt();
            }
        });
        /*
         * create a new issue for the user.
         */
        post("/create", (req, res) -> {
            User user = getAuthenticatedUser(req);
            MultiMap<String> params = new MultiMap<String>();
            UrlEncoded.decodeTo(req.body(), params, "UTF-8");
            Issue m = new Issue();
            m.setUserId(user.getId());
            m.setPublicationDate(new Date());
            BeanUtils.populate(m, params);
            service.addIssue(m);
            res.redirect("/");
            return null;
        });
        /*
         * Comments an issue for the user.
         */
        post("/comment/:issueId", (req, res) -> {
            String issueId = req.params(":issueId");
            MultiMap<String> params = new MultiMap<String>();
            UrlEncoded.decodeTo(req.body(), params, "UTF-8");
            Comment m = new Comment();
            m.setIssueId(Integer.parseInt(issueId));
            m.setCommentDate(new Date());
            String sts=params.getString("status");
            int status=Integer.parseInt(sts);
            Issue issue= service.getIssueById(Integer.parseInt(issueId));
            if (issue.getStatus()!=status)
            {
                issue.setStatus(status);
                service.modifyIssue(issue);
                m.setChangeStatus(true);
            }
            params.remove("status");
            BeanUtils.populate(m, params);
            service.addComment(m);
            String loc = "/issue/" + issueId;
            res.redirect(loc);
            return null;
        });
        /*
         * Logs the user out and redirects to the main page
         */
        get("/logout", (req, res) -> {
            removeAuthenticatedUser(req);
            res.redirect("/");
            return null;
        });

    }

    private void removeAuthenticatedUser(Request request) {
        request.session().removeAttribute(USER_SESSION_ID);

    }

    private void addAuthenticatedUser(Request request, User user) {
        request.session().attribute(USER_SESSION_ID, user);

    }
    private User getAuthenticatedUser(Request request) {
        return request.session().attribute(USER_SESSION_ID);
    }
}
