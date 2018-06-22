package com.bokun.bkjcb.voteapp.Interface;

import com.bokun.bkjcb.voteapp.Model.MatchResult;

/**
 * Created by DengShuai on 2018/6/6.
 * Description :
 */
public interface TextChanged {
    void onTextChange(MatchResult.Data.Person person, String score);
}
