package com.bokun.bkjcb.voteapp.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.Event.MessageEvent;
import com.bokun.bkjcb.voteapp.Fragment.VoteFragment;
import com.bokun.bkjcb.voteapp.Interface.TextChanged;
import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.Match;
import com.bokun.bkjcb.voteapp.Model.PersonResult;
import com.bokun.bkjcb.voteapp.NetWork.HttpManager;
import com.bokun.bkjcb.voteapp.NetWork.HttpRequestVo;
import com.bokun.bkjcb.voteapp.NetWork.JsonParser;
import com.bokun.bkjcb.voteapp.NetWork.RequestListener;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;
import com.bokun.bkjcb.voteapp.Utils.SystemBarTintManager;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bokun.bkjcb.voteapp.View.RankView;
import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.lzy.widget.HeaderViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteActivity extends BaseActivity implements RequestListener, TextChanged {

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
    private MagicIndicator indicator;
    private NumberProgressBar progressBar;
    private HashMap<String, String> scoreResult;
    private Button submit;
    private boolean isFinished = false;
    private int finishedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        toolbar = findViewById(R.id.titlebar);
        initView();
        initViewPage();

        view = findViewById(R.id.load_view);
        view.setVisibility(View.VISIBLE);
        String matchId = getIntent().getStringExtra(VOTE_ACTIVITY_KEY);
        if (matchId == null || matchId.equals("")) {
            Toast.makeText(this, "二维码扫描错误，请重试！", Toast.LENGTH_LONG).show();
            finish();
        }
        getList(matchId);
        //getList("1");
    }

    private void initView() {
        setTranslucentStatus(true);
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
        String userid = (String) SPUtils.get(this, "UserID", "");
        String[] scores = null;
        for (int i = 0; i < match.getJudges().size(); i++) {
            if (userid.equals(match.getJudges().get(i).getJudges_id())) {
                if (match.getJudges().get(i).getScore() != "") {
                    scores = match.getJudges().get(i).getScore().split(",");

                    submit.setVisibility(View.VISIBLE);
                    isFinished = true;
                    submit.setText("查看结果");
                    submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    progressBar.setVisibility(View.GONE);
                }
            }
        }


        title.setText(match.getTitle());
        SPUtils.put(this, "MatchTitle", match.getTitle());
        scoreResult = new HashMap<>();
        personInfos = match.getPerson();
        if (scores != null) {
            for (int i = 0; i < scores.length; i++) {
                personInfos.get(i).setScore(scores[i]);
            }
        }
        fragments = new ArrayList<>();
        for (int i = 0; i < personInfos.size(); i++) {
            VoteFragment fragment = VoteFragment.newInstance(personInfos.get(i));
            fragment.setTextChanged(this);
            fragments.add(fragment);
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
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(personInfos.get(index).getPerson());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, pager);
        match.setFilerurl("http://img.sccnn.com/bimg/339/04935.jpg");
        Glide.with(this).load(match.getFilerurl()).into(pic);
        SPUtils.put(this, "MatchUrl", match.getFilerurl());
        // Glide.with(this).load().into(pic);
        //進度條消失
        view.setVisibility(View.GONE);
    }

    private void initViewPage() {
        pager = findViewById(R.id.content);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress_bar);
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
        indicator = findViewById(R.id.magic_indicator);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFinished) {
                    getResult();
                } else {
                    submitResult();
                }
            }
        });
    }

    private void getResult() {
        HttpRequestVo requestVo = new HttpRequestVo();
        requestVo.requestDataMap.put("id", match.getPipeliningID());
        requestVo.methodName = "Getscore ";
        HttpManager manager = new HttpManager(this, this, requestVo);
        manager.postRequest();
    }

    /**
     * 提交评分结果
     */
    private void submitResult() {
      //  StringBuilder strScore = new StringBuilder();
        String strScore="";
        for (int i = 0; i < match.getPerson().size(); i++) {
            String sc = scoreResult.get(match.getPerson().get(i).getPerson());
            if (sc == null || sc.equals("")) {
                pager.setCurrentItem(i);
                Toast.makeText(this, "请先完成评分！", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.i("VoteActivity", sc);
           if(strScore=="")
           {
               strScore=sc;
           }else
           {
               strScore+=","+sc;
           }
            Log.i("VoteActivity", strScore);
          //  strScore.append(sc);
        }
        HttpRequestVo requestVo = new HttpRequestVo();
        requestVo.requestDataMap.put("jid", (String) SPUtils.get(this, "UserID", ""));
        requestVo.requestDataMap.put("actid", match.getId());
        requestVo.requestDataMap.put("score", strScore.toString());
        requestVo.methodName = "Addscore";
        HttpManager manager = new HttpManager(this, this, requestVo);
        manager.postRequest();
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
        if (event.getType() == RequestListener.EVENT_GET_DATA_SUCCESS) {
            if (match == null) {
                //类型转换 转成 modal
                match = JsonParser.getList(event.getResult().getData());
                initData();
            } else if (!isFinished) {
                Toast.makeText(this, "成功提交评分！", Toast.LENGTH_SHORT).show();
                isFinished = true;
                submit.setText("查看结果");
                submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                if (event.getResult().isSuccess()) {
                    List<PersonResult> results = JsonParser.getPersonResult(event.getResult().getData());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setView(RankView.builder(this, results));
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                } else {
                    Toast.makeText(VoteActivity.this, "结果尚未出来，请稍后再试！", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(VoteActivity.this, "获取活动信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onTextChange(Match.PersonBean person, String score) {
        if (scoreResult.get(person.getPerson()) == null) {
            scoreResult.put(person.getPerson(), score);
            if (score == "") {
                finishedCount = finishedCount > 0 ? finishedCount-- : 0;
            } else {
                finishedCount++;
            }
            progressBar.setProgress(finishedCount / personInfos.size()*100);
            if (progressBar.getProgress() == 100) {
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
            }
        } else {
            scoreResult.put(person.getPerson(), score);
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


    private void getList(String id) {
        HttpRequestVo requestVo = new HttpRequestVo();
        requestVo.requestDataMap.put("id", id);
        requestVo.methodName = "GetActivity";
        HttpManager manager = new HttpManager(VoteActivity.this, VoteActivity.this, requestVo);
        manager.postRequest();
    }

}
