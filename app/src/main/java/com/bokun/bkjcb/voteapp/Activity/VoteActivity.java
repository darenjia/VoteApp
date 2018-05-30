package com.bokun.bkjcb.voteapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bokun.bkjcb.voteapp.R;

public class VoteActivity extends BaseActivity {

    private Toolbar toolbar;

    public static final String VOTE_ACTIVITY_KEY="key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("投票");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static void gotoVoteActivity(Activity activity,String key){
        Intent intent= new Intent(activity,VoteActivity.class);
        intent.putExtra(VoteActivity.VOTE_ACTIVITY_KEY,key);
        activity.startActivity(intent);
    }
}
