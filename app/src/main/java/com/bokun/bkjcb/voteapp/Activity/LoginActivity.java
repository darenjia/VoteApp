package com.bokun.bkjcb.voteapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.HttpService.UserService;
import com.bokun.bkjcb.voteapp.Model.UserResult;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.CheckUpUtil;
import com.bokun.bkjcb.voteapp.Utils.NetworkUtils;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseActivity {


    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //页面初始化加载方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        new CheckUpUtil(LoginActivity.this).checkUpadte(true, false);
        mEmailView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);

        //给按钮添加点击事件
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        //显示上次记录的用户名
        String name = (String) SPUtils.get(this, "UserName", "");
        if (name != "") {
            mEmailView.setText(name);
        }
    }


    //判断登陆方法
    private void attemptLogin() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mLoginFormView.getWindowToken(), 0);
        }
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            login(email, password);

        }
    }

    private void login(String email, String password) {
        if (!NetworkUtils.isEnable(this)) {
            Toast.makeText(this, "网络不可用！", Toast.LENGTH_SHORT).show();
            return;
        }
        UserService userService = retrofit.create(UserService.class);
        disposable = userService.login(email, password)
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Function<Throwable, UserResult>() {
                    @Override
                    public UserResult apply(Throwable throwable) throws Exception {
                        return new UserResult();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserResult>() {
                    @Override
                    public void accept(UserResult result) throws Exception {
                        showProgress(false);
                        if (result.getData() == null) {
                            Toast.makeText(LoginActivity.this, "网络错误请稍后再试！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SPUtils.put(LoginActivity.this, "UserID", result.getData().getId());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //保存用户名
                        SPUtils.put(LoginActivity.this, "UserName", result.getData().getUsername());
                        startActivity(intent);
                        finish();
                    }
                });
    }

    //进度条是否显示

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.trim().length() > 0;
    }

}
