package com.axmor.dao.impl;

import com.axmor.dao.CommentDao;
import com.axmor.model.Comment;
import com.axmor.model.Issue;
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
public class CommentDaoImpl implements CommentDao {

    private NamedParameterJdbcTemplate template;
    @Autowired
    public CommentDaoImpl(DataSource ds) {
        template = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public List<Comment> getAllCommentsByIssueId(int id)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "select *  from comment  WHERE issueId=:id";
        List<Comment> result = template.query(sql, params, commentMapper);

        return result;
    };

    @Override
    public void addComment(Comment com)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("issueId",com.getIssueId());
        params.put("text", com.getText());
        params.put("commentDate", com.getCommentDate());
        params.put("author", com.getAuthor());
        params.put("changeStatus", com.isChangeStatus());

        String sql = "insert into comment (issueId,author,commentDate,text,changeStatus)" +
                " values (:issueId,:author,:commentDate,:text,:changeStatus)";
        template.update(sql, params);
    };
    private RowMapper<Comment> commentMapper = (rs, rowNum) -> {
        Comment m = new Comment();
        m.setIssueId(rs.getInt("issueId"));
        m.setText(rs.getString("text"));
        m.setAuthor(rs.getString("author"));
        m.setCommentDate(rs.getTimestamp("commentDate"));
        m.setChangeStatus(rs.getBoolean("changeStatus"));
        return m;
    };
}
