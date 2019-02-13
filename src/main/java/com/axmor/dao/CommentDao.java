package com.axmor.dao;

import com.axmor.model.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> getAllCommentsByIssueId(int id);
    void addComment(Comment com);
}
