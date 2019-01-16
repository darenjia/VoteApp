package com.bokun.bkjcb.voteapp.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bokun.bkjcb.voteapp.Model.User;

/**
 * Created by DengShuai on 2018/10/8.
 * Description :
 */
public class UserDao {
    public static UserDao useDao;

    public static UserDao newInstance(Context context) {
        if(useDao==null){
            useDao=new UserDao();
        }
        return useDao;
    }
    public boolean addUser(User user, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("guid",user.getGuid());
        values.put("name",user.getGuid());
        values.put("type",user.getType());
        long flag = db.insert("user","id",values);
        return  flag>0;
    }

}
