package com.axmor.service;

import com.axmor.dao.UserDao;
import com.axmor.dao.impl.UserDaoImpl;
import com.axmor.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class UserDaoTest {

    private EmbeddedDatabase db;
    UserDao userDao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql/create.sql")
                .build();
    }

    @Test
    public void testAddAndFindByLogin() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
        UserDaoImpl userDao = new UserDaoImpl(db);
        User newUser=new User();
        newUser.setLogin("anton");
        newUser.setPassword("1234");
        userDao.registerUser(newUser);

        User user = userDao.getUserByLogin("anton");

        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("anton", user.getLogin());
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}
