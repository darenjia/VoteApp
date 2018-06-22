package com.bokun.bkjcb.voteapp.HttpService;

import com.bokun.bkjcb.voteapp.Model.UserResult;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DengShuai on 2018/6/22.
 * Description :
 */
public interface UserService {
    //返回Rxjava观察者
    @POST("User")
    Observable<UserResult> login(@Query("name") String start, @Query("password") String end);
}
