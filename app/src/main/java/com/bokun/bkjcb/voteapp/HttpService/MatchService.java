package com.bokun.bkjcb.voteapp.HttpService;

import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.MatchList;
import com.bokun.bkjcb.voteapp.Model.MatchResult;
import com.bokun.bkjcb.voteapp.Model.PersonResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DengShuai on 2018/6/22.
 * Description :
 */
public interface MatchService {
    @POST("Activity")
    Observable<MatchResult> getMatch(@Query("id") String id);

    @POST("Score/submit")
    Observable<HttpResult> submitScore(@Query("actid") String matchId, @Query("jid") String judgeId, @Query("score") String score);

    @GET("Score/get")
    Observable<PersonResult> getResult(@Query("id") String id);

    @GET("Activity/get")
    Observable<MatchList> getMatchList(@Query("userid") String id);
}
