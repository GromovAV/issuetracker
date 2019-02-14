package com.axmor.model;

import spark.utils.StringUtils;

public class User {
    private int id;
    private String login;
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String validate() {
        String error = null;

        if(StringUtils.isEmpty(login)) {
            error = "You have to enter a login";
        }else if(StringUtils.isEmpty(password)) {
            error = "You have to enter a password";
        }
        return error;
    }

}
