package com.bokun.bkjcb.voteapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.Event.MessageEvent;
import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.NetWork.HttpManager;
import com.bokun.bkjcb.voteapp.NetWork.HttpRequestVo;
import com.bokun.bkjcb.voteapp.NetWork.JsonParser;
import com.bokun.bkjcb.voteapp.NetWork.RequestListener;
import com.bokun.bkjcb.voteapp.R;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.serialization.SoapObject;


public class LoginActivity extends BaseActivity implements RequestListener {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //页面初始化加载方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //new CheckUpUtil(LoginActivity.this).checkUpadte(true,false);
        mEmailView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


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
    }


    //判断登陆方法
    private void attemptLogin() {

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

    @Override
    public void action(int i, Object object) {
        HttpResult result = JsonParser.parseSoap((SoapObject) object);
        if (result != null && i == RequestListener.EVENT_GET_DATA_SUCCESS) {
            EventBus.getDefault().post(new MessageEvent(1, result));
        } else {
            showProgress(false);
            Toast.makeText(this, "服务器错误!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void action(MessageEvent event) {
        showProgress(false);
        if (event.getResult().isSuccess()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }

    }

    private void login(String email, String password) {
        HttpRequestVo requestVo = new HttpRequestVo();
        requestVo.requestDataMap.put("user", email);
        requestVo.requestDataMap.put("password", password);
        requestVo.requestDataMap.put("type", "1");
        requestVo.methodName = "GetUser";
        HttpManager manager = new HttpManager(LoginActivity.this, LoginActivity.this, requestVo);
        manager.postRequest();
    }

    //进度条是否显示

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.trim().length() > 0;
    }


}
