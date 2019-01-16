package com.bokun.bkjcb.voteapp.Model;

/**
 * Created by DengShuai on 2018/10/8.
 * Description :
 */
public class User {
    private String id;
    private String name;
    private String guid;
    private int type=1;//1：游客
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
