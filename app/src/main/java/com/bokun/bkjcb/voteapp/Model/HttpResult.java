package com.bokun.bkjcb.voteapp.Model;

/**
 * Created by DengShuai on 2018/5/30.
 * Description :
 */
public class HttpResult {
    private boolean success;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
