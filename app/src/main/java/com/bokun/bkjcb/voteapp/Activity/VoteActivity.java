package com.bokun.bkjcb.voteapp.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.Event.MessageEvent;
import com.bokun.bkjcb.voteapp.Fragment.VoteFragment;
import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.Match;
import com.bokun.bkjcb.voteapp.Model.PersonInfo;
import com.bokun.bkjcb.voteapp.NetWork.HttpManager;
import com.bokun.bkjcb.voteapp.NetWork.HttpRequestVo;
import com.bokun.bkjcb.voteapp.NetWork.JsonParser;
import com.bokun.bkjcb.voteapp.NetWork.RequestListener;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.SystemBarTintManager;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.lzy.widget.HeaderViewPager;
import com.rd.PageIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends BaseActivity implements RequestListener {

    private View toolbar;

    public static final String VOTE_ACTIVITY_KEY = "key";
    private HeaderViewPager headerViewPager;
    private List<Match.PersonBean> personInfos;
    private ViewPager pager;
    private ArrayList<VoteFragment> fragments;
    private View titleBar_Bg;
    private View status_bar_fix;
    private ImageView pic;
    private Match match;
    private View view;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        toolbar = findViewById(R.id.titlebar);
        initView();
        initViewPage();

        view = findViewById(R.id.load_view);
        view.setVisibility(View.VISIBLE);
        getlist("1");
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);  //设置上方状态栏透明
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void initData() {
        title.setText(match.getTitle());


        personInfos = match.getPerson();
        fragments = new ArrayList<>();
        for (int i = 0; i < personInfos.size(); i++) {
            fragments.add(VoteFragment.newInstance(personInfos.get(i)));
        }
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        headerViewPager.setCurrentScrollableContainer(fragments.get(0));
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                headerViewPager.setCurrentScrollableContainer(fragments.get(position));
            }
        });
        //進度條消失
     view.setVisibility(View.GONE);
    }

    private void initViewPage() {
        pager = findViewById(R.id.content);
        ImageView back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        pic = findViewById(R.id.image_content);
        titleBar_Bg = toolbar.findViewById(R.id.bg);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        status_bar_fix = toolbar.findViewById(R.id.status_bar_fix);
        status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getStatusHeight(this)));
        titleBar_Bg.setAlpha(0);
        status_bar_fix.setAlpha(0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
        ImageView imageView = findViewById(R.id.image);
        headerViewPager = findViewById(R.id.scrollableLayout);
        headerViewPager.setOnScrollListener(new HeaderViewPager.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                //让头部具有差速动画,如果不需要,可以不用设置
                //pic.setTranslationY(currentY/3);
                //动态改变标题栏的透明度,注意转化为浮点型
                float alpha = 1.0f * currentY / maxY;
                titleBar_Bg.setAlpha(alpha);
                //注意头部局的颜色也需要改变
                status_bar_fix.setAlpha(alpha);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //当前窗口获取焦点时，才能正真拿到titlebar的高度，此时将需要固定的偏移量设置给scrollableLayout即可
        headerViewPager.setTopOffset(toolbar.getHeight());
    }

    public static void gotoVoteActivity(Activity activity, String key) {
        Intent intent = new Intent(activity, VoteActivity.class);
        intent.putExtra(VoteActivity.VOTE_ACTIVITY_KEY, key);
        activity.startActivity(intent);
    }

    //回调方法
    @Override
    public void action(int i, Object object) {
        HttpResult result = null;
        if (i == RequestListener.EVENT_GET_DATA_SUCCESS) {
            result = JsonParser.getData((SoapObject) object);
            // Log.i("11", result.getData());
        }
        EventBus.getDefault().post(new MessageEvent(i, result));

    }

    @Override
    protected void action(MessageEvent event) {
        if(event.getType()== RequestListener.EVENT_GET_DATA_SUCCESS) {
            //类型转换 转成 modal
            match = JsonParser.getList(event.getResult().getData());
            initData();
        }else
        {
            Toast.makeText(VoteActivity.this,"操作失敗",Toast.LENGTH_SHORT).show();
        }

    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    private void getlist(String id) {
        HttpRequestVo requestVo = new HttpRequestVo();
        requestVo.requestDataMap.put("id", id);
        requestVo.methodName = "GetActivity";
        HttpManager manager = new HttpManager(VoteActivity.this, VoteActivity.this, requestVo);
        manager.postRequest();
    }

}
