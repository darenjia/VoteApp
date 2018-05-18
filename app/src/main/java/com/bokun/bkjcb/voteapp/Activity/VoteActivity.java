package com.bokun.bkjcb.voteapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bokun.bkjcb.voteapp.NetWork.RequestListener;
import com.bokun.bkjcb.voteapp.R;

import java.lang.ref.WeakReference;

public class VoteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private static class MyHandler extends Handler {
        private final WeakReference<VoteActivity> mActivity;

        public MyHandler(VoteActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final VoteActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case RequestListener.EVENT_NOT_NETWORD:
                        Snackbar.make(activity.toolbar, "无网络连接，请检查网络", Snackbar.LENGTH_LONG).setAction("设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                activity.startActivity(intent);
                            }
                        }).show();
                        break;
                    case RequestListener.EVENT_CLOSE_SOCKET:
                        Snackbar.make(activity.toolbar, "网络错误！", Snackbar.LENGTH_LONG).show();
                        break;
                    case RequestListener.EVENT_NETWORD_EEEOR:
                        Snackbar.make(activity.toolbar, "请确认网络是否可用！", Snackbar.LENGTH_LONG).show();
                        break;
                    case RequestListener.EVENT_GET_DATA_EEEOR:
                        Snackbar.make(activity.toolbar, "服务器错误，请稍后再试！", Snackbar.LENGTH_LONG).show();
                        break;
                    case RequestListener.EVENT_GET_DATA_SUCCESS:

                        break;

                }
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);
    public static final String VOTE_ACTIVITY_KEY="key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
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
