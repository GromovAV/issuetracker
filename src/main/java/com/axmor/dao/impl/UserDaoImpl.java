package com.axmor.dao.impl;

import com.axmor.dao.UserDao;
import com.axmor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    private NamedParameterJdbcTemplate template;

    @Autowired
    public UserDaoImpl(DataSource ds) {
        template = new NamedParameterJdbcTemplate(ds);
    }
    @Override
    public void registerUser(User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("login", user.getLogin());
        params.put("password", user.getPassword());

        String sql = "insert into user (login,password) values (:login,:password)";

        template.update(sql,  params);
    }

    @Override
    public User getUserByLogin(String login) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", login);

        String sql = "SELECT * FROM user WHERE login=:name";

        List<User> list = template.query(
                sql,
                params,
                userMapper);

        User result = null;
        if(list != null && !list.isEmpty()) {
            result = list.get(0);
        }
        return result;
    }
    @Override
    public User getUserByID(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM user WHERE id=:id";

        List<User> list = template.query(
                sql,
                params,
                userMapper);

        User result = null;
        if(list != null && !list.isEmpty()) {
            result = list.get(0);
        }
    return result;
    }

        private RowMapper<User> userMapper = (rs, rowNum) -> {
        User us = new User();

        us.setId(rs.getInt("id"));
        us.setLogin(rs.getString("login"));
        us.setPassword(rs.getString("password"));

        return us;
    };
}