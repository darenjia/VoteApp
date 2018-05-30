package com.bokun.bkjcb.voteapp.Event;

import com.bokun.bkjcb.voteapp.Model.HttpResult;

/**
 * Created by DengShuai on 2018/5/30.
 * Description :
 */
public class MessageEvent {
    private int type;
    private HttpResult result;

    public MessageEvent(int type, HttpResult result) {
        this.type = type;
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HttpResult getResult() {
        return result;
    }

    public void setResult(HttpResult result) {
        this.result = result;
    }
}
