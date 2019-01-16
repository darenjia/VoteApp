package com.bokun.bkjcb.voteapp.Utils;

/**
 * Created by DengShuai on 2018/10/8.
 * Description :
 */
public class SqlContants {

    public static String CREATE_USER_TABLE="" +
            "create table user(" +
            "id integer primary key," +
            "name varchar(20)," +
            "guid varchar(50)," +
            "password varchar(30)," +
            "type int(1)" +
            ");";
    public  static String CREATE_ACTIVITY_TABLE="" +
            "create table activity(" +
            "id integer primary key," +
            "act_id varchar(20)," +
            "user_id varchar(50)," +
            "title varchar(50)," +
            "content varchar(100)," +
            "time varchar(10)," +
            "status int(1)," +
            "fileUrl varchar(100)"+
            ");";

}
