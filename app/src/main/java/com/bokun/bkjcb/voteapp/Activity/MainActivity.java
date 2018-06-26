package com.bokun.bkjcb.voteapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.Constants;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;
import com.bokun.bkjcb.voteapp.View.ScanView;
import com.bumptech.glide.Glide;
import com.mylhyl.zxing.scanner.common.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView line;
    private ScanView scanView;
    private ImageView pic;
    private TextView title;
    private CardView history;
    private long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanView = findViewById(R.id.scan_view);
        line = findViewById(R.id.line);
        pic = findViewById(R.id.vote_history_background);
        title = findViewById(R.id.vote_history_title);
        history = findViewById(R.id.history);

        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 60);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    ScannerActivity.gotoActivity(MainActivity.this, true, ScannerActivity.EXTRA_LASER_LINE_MODE_0, ScannerActivity.EXTRA_SCAN_MODE_1, false, false, false);
//                    TestMainActivity.gotoActivity(MainActivity.this);
                }
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = (String) SPUtils.get(MainActivity.this, "MatchKey", "");
                VoteActivity.gotoVoteActivity(MainActivity.this, s);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == BasicScannerActivity.REQUEST_CODE_SCANNER) {
            String s = data.getStringExtra(Scanner.Scan.RESULT);
            SPUtils.put(this, "MatchKey", s);
            VoteActivity.gotoVoteActivity(this, s);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 60) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ScannerActivity.gotoActivity(MainActivity.this, true, ScannerActivity.EXTRA_LASER_LINE_MODE_0, ScannerActivity.EXTRA_SCAN_MODE_0, false, false, false);
            } else {
                Toast.makeText(this, "无法打开相机", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String url = (String) SPUtils.get(this, "MatchUrl", "");
        String strTitle = (String) SPUtils.get(this, "MatchTitle", "");
        if (!url.equals("")) {
            Glide.with(this).load(Constants.imgurl+url).into(pic);
            title.setText(strTitle);
        } else {
            history.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int y = scanView.getWidth() / 2;
        Animation animation = new TranslateAnimation(0, 0, -y / 2, y / 2);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(2000);
        animation.setInterpolator(new LinearInterpolator());
        line.setAnimation(animation);
        animation.start();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time > 1000) {
            time = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
