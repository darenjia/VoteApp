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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.Fragment.VoteFragment;
import com.bokun.bkjcb.voteapp.HttpService.MatchService;
import com.bokun.bkjcb.voteapp.Interface.TextChanged;
import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.MatchResult;
import com.bokun.bkjcb.voteapp.Model.PersonResult;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.Constants;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;
import com.bokun.bkjcb.voteapp.Utils.SystemBarTintManager;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bokun.bkjcb.voteapp.View.RankView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.lzy.widget.HeaderViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VoteActivity extends BaseActivity implements TextChanged {

    private View toolbar;

    public static final String VOTE_ACTIVITY_KEY = "key";
    private HeaderViewPager headerViewPager;
    private List<MatchResult.Data.Person> personInfos;
    private ViewPager pager;
    private ArrayList<VoteFragment> fragments;
    private View titleBar_Bg;
    private View status_bar_fix;
    private ImageView pic;
    private MatchResult.Data match;
    private View view;
    private TextView title;
    private MagicIndicator indicator;
    private NumberProgressBar progressBar;
    private HashMap<String, String> scoreResult;
    private Button submit;
    private boolean isFinished = false;
    private int finishedCount = 0;
    private RequestOptions options;
    private MatchService matchService;

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

    private void initData(MatchResult result) {
        match = result.getData();
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
        options = new RequestOptions().placeholder(R.drawable.green).error(R.drawable.green);
        Glide.with(this).load(Constants.imgurl + match.getFilerurl()).apply(options).into(pic);//图片加载出来前，显示的图片
        SPUtils.put(this, "MatchUrl", match.getFilerurl());
        //  Glide.with(this).load().into(pic);
        initMagicIndicator();
        //進度條消失
        view.setVisibility(View.GONE);
    }

    private void initMagicIndicator() {

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(personInfos.get(index).getPerson());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, pager);

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
        disposable.dispose();
        disposable = matchService.getResult(match.getPipeliningID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PersonResult>() {
                    @Override
                    public void accept(PersonResult result) throws Exception {
                        if (result.isSuccess()) {
                            showResult(result.getData());
                        } else {
                            Toast.makeText(VoteActivity.this, "结果尚未出来，请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showResult(List<PersonResult.Person> persons) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(RankView.builder(this, persons));
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * 提交评分结果
     */
    private void submitResult() {
        StringBuilder strScore = new StringBuilder();
        for (int i = 0; i < match.getPerson().size(); i++) {
            String sc = scoreResult.get(match.getPerson().get(i).getId());
            if (sc == null || sc.equals("")) {
                pager.setCurrentItem(i);
                Toast.makeText(this, "请先完成评分！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (i == 0) {
                strScore.append(sc);
            } else {
                strScore.append(",").append(sc);
            }
        }
        disposable = matchService.submitScore( match.getId(),(String) SPUtils.get(this, "UserID", ""), strScore.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult>() {
                    @Override
                    public void accept(HttpResult result) throws Exception {
                        if (result.isSuccess()) {
                            Toast.makeText(VoteActivity.this, "成功提交评分！", Toast.LENGTH_SHORT).show();
                            isFinished = true;
                            submit.setText("查看结果");
                            submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            Toast.makeText(VoteActivity.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
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


    @Override
    public void onTextChange(MatchResult.Data.Person person, String score) {
        if (scoreResult.get(person.getId()) == null) {
            scoreResult.put(person.getId(), score);
            if (score == "") {
                finishedCount = finishedCount > 0 ? finishedCount-- : 0;
            } else {
                finishedCount++;
            }
            progressBar.setProgress(finishedCount * 100 / personInfos.size());
            if (progressBar.getProgress() == 100) {
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
            }
        } else {
            scoreResult.put(person.getId(), score);
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
        matchService = retrofit.create(MatchService.class);
        disposable = matchService.getMatch(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MatchResult>() {
                    @Override
                    public void accept(MatchResult result) throws Exception {
                        if (result != null && result.isSuccess()) {
                            initData(result);
                        } else {
                            Toast.makeText(VoteActivity.this, "获取活动信息失败，请重试！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

}
