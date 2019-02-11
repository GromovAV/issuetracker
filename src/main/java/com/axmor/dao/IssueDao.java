package com.axmor.dao;

import com.axmor.model.Issue;
import com.axmor.model.User;

import java.util.List;

public interface IssueDao {
    void addIssue(Issue ism);
    void deleteIssueById(int id);
    void modifyIssue(Issue ism);
    List<Issue>  getAllIssues();
    Issue getIssueByID(int id);


}
