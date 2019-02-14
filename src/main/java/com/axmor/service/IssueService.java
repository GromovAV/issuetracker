package com.axmor.service;

import com.axmor.dao.CommentDao;
import com.axmor.dao.IssueDao;
import com.axmor.dao.UserDao;
import com.axmor.model.Comment;
import com.axmor.model.Issue;
import com.axmor.model.LoginResult;
import com.axmor.model.User;

import com.axmor.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private CommentDao commentDao;

    public void registerUser(User user) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        userDao.registerUser(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setIssueDao(IssueDao issueDao) {
        this.issueDao = issueDao;
    }
    public LoginResult checkUser(User user) {
        LoginResult result = new LoginResult();
        User userFound = userDao.getUserByLogin(user.getLogin());
        if(userFound == null) {
            result.setError("Invalid login");
        } else if(!PasswordUtil.verifyPassword(user.getPassword(), userFound.getPassword())) {
            result.setError("Invalid password");
        } else {
            result.setUser(userFound);
        }
        return result;
    }

    public void modifyIssue(Issue issue) {
        issueDao.modifyIssue(issue);
    }
    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }
    public void addIssue(Issue issue) {
        issueDao.addIssue(issue);
    }
    public Issue getIssueById(int id) {
        return issueDao.getIssueByID(id);
    }
    public User getUserById(int id) {
        return userDao.getUserByID(id);
    }
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }
    public List<Issue> getAllIssues() {
        return issueDao.getAllIssues();
    }
    public List<Comment> getAllCommentsByIssueId(int id) {
        return commentDao.getAllCommentsByIssueId(id);
    }
}
