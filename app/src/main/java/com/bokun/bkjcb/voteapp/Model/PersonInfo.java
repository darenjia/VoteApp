package com.bokun.bkjcb.voteapp.Model;

import java.io.Serializable;

/**
 * Created by DengShuai on 2018/6/4.
 * Description :
 */
public class PersonInfo implements Serializable {
    private String id;
    private String username;
    private String pass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
