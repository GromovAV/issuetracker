package com.axmor.dao.impl;

import com.axmor.dao.IssueDao;
import com.axmor.model.Issue;
import com.axmor.model.Status;
import com.axmor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IssueDaoImpl implements IssueDao {

    public static final String ADD_ISSUE ="insert into issue (userId,name,author, publicationDate,description,status)" +
            " values (:userId, :name,:author, :publicationDate,:description,:status)";
    public static final String MODIFY_ISSUE_BY_ID = "update issue  set status=:status where id=:id ";
    public static final String GET_ALL_ISSUES = "select *  from issue";
    public static final String GET_ISSUE_BY_ID = "SELECT * FROM issue WHERE id=:id";


    private NamedParameterJdbcTemplate template;

    @Autowired
    public IssueDaoImpl(DataSource ds) {
        template = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public void addIssue(Issue ism)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", ism.getUserId());
        params.put("name", ism.getName());
        params.put("publicationDate", ism.getPublicationDate());
        params.put("description", ism.getDescription());
        params.put("author", ism.getAuthor());
        params.put("status",ism.getStatus().name());

        template.update(ADD_ISSUE, params);
    }
    @Override
    public void deleteIssueById(int id){}
    @Override
    public  void modifyIssue(Issue ism)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", ism.getId());
       params.put("userId", ism.getUserId());
        params.put("name", ism.getName());
        params.put("publicationDate", ism.getPublicationDate());
        params.put("description", ism.getDescription());
        params.put("author", ism.getAuthor());
        params.put("status", ism.getStatus().name());

        template.update(MODIFY_ISSUE_BY_ID,params);
    }

    public  List<Issue> getAllIssues()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Issue> result = template.query(GET_ALL_ISSUES, params, issueMapper);

        return result;
    };
    @Override
    public Issue getIssueByID(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        List<Issue> list = template.query(
                GET_ISSUE_BY_ID,
                params,
                issueMapper);

        Issue result = null;
        if(list != null && !list.isEmpty()) {
            result = list.get(0);
        }
        return result;
    }
    private RowMapper<Issue> issueMapper = (rs, rowNum) -> {
        Issue m = new Issue();

        m.setId(rs.getInt("id"));
        m.setUserId(rs.getInt("userId"));
        m.setName(rs.getString("name"));
        m.setAuthor(rs.getString("author"));
        m.setPublicationDate(rs.getTimestamp("publicationDate"));
        m.setDescription(rs.getString("description"));
        m.setStatus(Status.valueOf(rs.getString("status")));
        return m;
    };
}
