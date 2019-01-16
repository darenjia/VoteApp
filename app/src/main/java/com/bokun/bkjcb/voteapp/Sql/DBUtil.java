package com.bokun.bkjcb.voteapp.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bokun.bkjcb.voteapp.Model.MatchList;
import com.bokun.bkjcb.voteapp.Model.MatchResult;
import com.bokun.bkjcb.voteapp.Model.User;
import com.bokun.bkjcb.voteapp.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengShuai on 2018/10/8.
 * Description :
 */
public class DBUtil {
    public static boolean addUser(User user, Context context) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("guid", user.getGuid());
        values.put("name", user.getGuid());
        values.put("type", user.getType());
        long flag = db.insert("user", "id", values);
        db.close();
        return flag > 0;
    }

    public static boolean addActivity(MatchResult.Data activity, Context context) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("act_id", activity.getId());
        values.put("user_id", Constants.user.getGuid());
        values.put("title", activity.getTitle());
        values.put("content", activity.getContent());
        values.put("time", activity.getHolddate());
        values.put("status", Integer.valueOf(activity.getIscompelete()));
        values.put("fileUrl", activity.getFilerurl());
        Cursor cursor = db.query("activity", null, "act_id=?", new String[]{activity.getId()}, null, null, null);
        long flag;
        if (cursor.moveToNext()) {
            flag = db.update("activity", values, "act_id=?", new String[]{activity.getId()});
        } else {
            flag = db.insert("activity", "id", values);
        }
        db.close();
        return flag > 0;
    }

    public static MatchList getActivity(User user, Context context) {
        MatchList list = new MatchList();
        List<MatchList.Data> dataList = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        Cursor cursor = db.query("activity", new String[]{"act_id", "user_id", "title", "content", "time", "status", "fileUrl"}, "user_id=?", new String[]{user.getGuid()}, null, null, null);
        while (cursor.moveToNext()) {
            MatchList.Data data = new MatchList.Data();
            data.setActivityid(cursor.getString(0));
            data.setUserid(cursor.getString(1));
            data.setTitle(cursor.getString(2));
            data.setContent(cursor.getString(3));
            data.setDate(cursor.getString(4));
            data.setIscomplete(cursor.getString(5));
            data.setFileurl(cursor.getString(6));
            dataList.add(data);
        }
        db.close();
        list.setData(dataList);
        return list;
    }
}
