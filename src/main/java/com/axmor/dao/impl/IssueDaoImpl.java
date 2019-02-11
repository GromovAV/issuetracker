package com.axmor.dao.impl;

import com.axmor.dao.IssueDao;
import com.axmor.model.Issue;
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

        String sql = "insert into issue (userId,name,author, publicationDate,description,status)" +
                " values (:userId, :name,:author, :publicationDate,:description,0)";
        template.update(sql, params);
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
        params.put("status", ism.getStatus());

        String sql = "update issue  set status=:status where id=:id ";
        template.update(sql,params);
    }

    public  List<Issue> getAllIssues()
    {
        Map<String, Object> params = new HashMap<String, Object>();

        String sql = "select *  from issue;";
        List<Issue> result = template.query(sql, params, issueMapper);

        return result;
    };
    @Override
    public Issue getIssueByID(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM issue WHERE id=:id";

        List<Issue> list = template.query(
                sql,
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
        m.setStatus(rs.getInt("status"));
        return m;
    };
}
