package com.bokun.bkjcb.voteapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.Model.User;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Sql.DBUtil;
import com.bokun.bkjcb.voteapp.Utils.AppManager;
import com.bokun.bkjcb.voteapp.Utils.Constants;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;

import java.util.UUID;

public class VisitorLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    /**
     * ID
     */
    private TextView mUserId;
    private EditText mId;
    /**
     * 用户名
     */
    private TextView mTvUsername;
    /**
     * 请输入您的称呼
     */
    private EditText mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_login);
        initView();


    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mUserId = (TextView) findViewById(R.id.user_id);
        mId = (EditText) findViewById(R.id.id);
        mTvUsername = (TextView) findViewById(R.id.tv_username);
        mUsername = (EditText) findViewById(R.id.username);
        Button mLogin = (Button) findViewById(R.id.login);
        mToolbar.setTitle("大众评委登录");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.reback);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserId.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String id = UUID.randomUUID().toString().replace("-", "");
                mId.setText(id);
                return true;
            }
        });
        mLogin.setOnClickListener(this);
        initData();
    }

    private void initData() {
        if (Constants.user == null) {
            String id = UUID.randomUUID().toString().replace("-", "");
            mId.setText(id);
            mId.setEnabled(false);
        } else {
            mId.setText(Constants.user.getGuid());
            mUsername.setText(Constants.user.getName());
            mId.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login:
                String name = mUsername.getText().toString();
                if (isNameValid(name)) {
                    Constants.user = new User();
                    String guid = mId.getText().toString();
                    Constants.user.setGuid(guid);
                    Constants.user.setName(name);
                    Constants.user.setType(1);
                    DBUtil.addUser(Constants.user, VisitorLoginActivity.this);
                    Intent intent = new Intent(VisitorLoginActivity.this, MainActivity.class);
                    SPUtils.put(VisitorLoginActivity.this, "UserID", guid);
                    SPUtils.put(VisitorLoginActivity.this, "UserName", name);
                    SPUtils.put(VisitorLoginActivity.this, "Type", 1);

                    startActivity(intent);
                    finish();
                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                } else {
                    Toast.makeText(this, "请输入您的称呼！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //登录保存用户名，放全局
    //判断是否是多人投票
    private boolean isNameValid(String name) {

        return name.trim().length() > 0;
    }
}
