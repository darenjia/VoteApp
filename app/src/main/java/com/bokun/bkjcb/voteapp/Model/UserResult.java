package com.bokun.bkjcb.voteapp.Model;

/**
 * Created by DengShuai on 2018/6/22.
 * Description :
 */
public class UserResult {


    /**
     * success : true
     * message : 操作成功
     * data : {"id":"1","username":"libai","pass":"123"}
     */

    private boolean success;
    private String message;
    private User data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public static class User {
        /**
         * id : 1
         * username : libai
         * pass : 123
         */

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
}
