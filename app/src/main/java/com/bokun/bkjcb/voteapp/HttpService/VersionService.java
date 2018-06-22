package com.bokun.bkjcb.voteapp.HttpService;

import com.bokun.bkjcb.voteapp.Model.HttpResult;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DengShuai on 2018/6/22.
 * Description :
 */
public interface VersionService {
    @GET("Version")
    Call<HttpResult> checkVersion();
}
