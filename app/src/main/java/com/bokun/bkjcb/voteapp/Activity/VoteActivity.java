package com.bokun.bkjcb.voteapp.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

import com.bokun.bkjcb.voteapp.Fragment.VoteFragment;
import com.bokun.bkjcb.voteapp.HttpService.MatchService;
import com.bokun.bkjcb.voteapp.Interface.TextChanged;
import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.MatchResult;
import com.bokun.bkjcb.voteapp.Model.PersonModel;
import com.bokun.bkjcb.voteapp.Model.PersonResult;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Sql.DBUtil;
import com.bokun.bkjcb.voteapp.Utils.CacheUtil;
import com.bokun.bkjcb.voteapp.Utils.Constants;
import com.bokun.bkjcb.voteapp.Utils.NetworkUtils;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;
import com.bokun.bkjcb.voteapp.Utils.SystemBarTintManager;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bokun.bkjcb.voteapp.View.ConfirmScoreView;
import com.bokun.bkjcb.voteapp.View.RankView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VoteActivity extends BaseActivity implements TextChanged {

    private View toolbar;

    public static final String VOTE_ACTIVITY_KEY = "key";
    private HeaderViewPager headerViewPager;
    private List<PersonModel> personInfos;
    private ViewPager pager;
    private ArrayList<VoteFragment> fragments;
    private View titleBar_Bg;
    private View status_bar_fix;
    private ImageView pic;
    private MatchResult.Data match;
    private View view;
    private TextView title;
    private MagicIndicator indicator;
    private HashMap<String, String> scoreResult;
    private Button submit;
    private boolean isFinished = false;
    private int finishedCount = 0;
    private RequestOptions options;
    private MatchService matchService;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView content;
    private TextView progress;
    private Button look_result;
    private LinearLayout op_view;
    private int proCount;
    private ProgressDialog progressDialog;
    private String userid;

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
        //tintManager.setStatusBarTintResource(android.R.color.transparent);  //设置上方状态栏透明
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);  //设置上方状态栏透明
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
        DBUtil.addActivity(match, this);
        isFinished = match.getIscompelete().equals("1");
        String[] scores = null;
//        for (int i = 0; i < match.getJudges().size(); i++) {
//            if (userid.equals(match.getJudges().get(i).getJudges_id())) {
//                if (match.getJudges().get(i).getScore() != "") {
//                    scores = match.getJudges().get(i).getScore().split(",");
//                    //look_result.setVisibility(View.VISIBLE);
//                    // op_view.setVisibility(View.GONE);
//                }
//            }
//        }

        title.setText(match.getTitle());
        SPUtils.put(this, "MatchTitle", match.getTitle());
        scoreResult = new HashMap<>();
        personInfos = match.getPerson();


        if (!isFinished) {
            CacheUtil cacheUtil = new CacheUtil(this).getCache();
            String scoreStr = cacheUtil != null ? cacheUtil.getData(match.getId()) : null;
            if (!TextUtils.isEmpty(scoreStr)) {
                String[] users = scoreStr.split(",");
                for (int i = 0; i < personInfos.size(); i++) {
                    if (personInfos.get(i).getIscompleted().equals("true")) {
                        continue;
                    }
                    for (String user : users) {
                        String[] info = user.split(":");
                        if (info.length > 1) {
                            if (personInfos.get(i).getId().equals(info[0])) {
                                personInfos.get(i).setScore(info[1]);
                                scoreResult.put(info[0], info[1]);
                            }
                        }
                    }
                }
//                    finishedCount = users.length;
//                    setProgress();
            }
        }

        fragments = new ArrayList<>();
        for (int i = 0; i < personInfos.size(); i++) {
            VoteFragment fragment = VoteFragment.newInstance(personInfos.get(i));
            fragment.setTextChanged(this);
            fragments.add(fragment);
            if (personInfos.get(i).getIscompleted().equals("true")) {
                finishedCount++;
            }
        }
        setProgress();
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        headerViewPager.setCurrentScrollableContainer(fragments.get(0));
        isCompleted(0);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                headerViewPager.setCurrentScrollableContainer(fragments.get(position));
                isCompleted(position);
            }
        });
        options = new RequestOptions().placeholder(R.drawable.blue_bg).error(R.drawable.blue_bg);
        Glide.with(this).load(Utils.getImageUrl(match.getFilerurl())).apply(options).into(pic);//图片加载出来前，显示的图片
        SPUtils.put(this, "MatchUrl", match.getFilerurl());
        //  Glide.with(this).load().into(pic);
        content.setText(match.getContent());
        initMagicIndicator();
        if (match.getIscompelete().equals("1")) {
            isFinished = true;
            op_view.setVisibility(View.GONE);
            look_result.setVisibility(View.VISIBLE);
        }
        //進度條消失
        view.setVisibility(View.GONE);
    }

    private void isCompleted(int position) {
        if (personInfos.get(position).getIscompleted().equals("true")) {
            changeButtonStyle(true);
        } else {
            changeButtonStyle(false);
        }
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
        progress = findViewById(R.id.score_progress);
        content = findViewById(R.id.match_info);
        look_result = findViewById(R.id.look_result);
        op_view = findViewById(R.id.op_progress);
        ImageView back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        pic = (ImageView) findViewById(R.id.image_content);
        titleBar_Bg = toolbar.findViewById(R.id.bg);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        status_bar_fix = toolbar.findViewById(R.id.status_bar_fix);
        status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getStatusHeight(this)));
        titleBar_Bg.setAlpha(1);
        status_bar_fix.setAlpha(0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content.getVisibility() == View.GONE) {
                    content.setVisibility(View.VISIBLE);
                } else {
                    content.setVisibility(View.GONE);
                }
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
//                float alpha = 1.0f * currentY / maxY;
//                titleBar_Bg.setAlpha(alpha);
//                //注意头部局的颜色也需要改变
//                status_bar_fix.setAlpha(alpha);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (match.getType().equals("0") && Constants.user.getType() == 1) {
                    Toast.makeText(VoteActivity.this, "您没有打分权限！", Toast.LENGTH_SHORT).show();
                } else {
                    int count = pager.getCurrentItem();
                    showConfirmDialog(personInfos.get(count));
                }
            }
        });
        look_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResult();
            }
        });
    }

    private void getResult() {
        if (!NetworkUtils.isEnable(this)) {
            Toast.makeText(this, "网络不可用！", Toast.LENGTH_SHORT).show();
            return;
        }
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(VoteActivity.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showConfirmDialog(final PersonModel person) {
        //校验打分是否合格
        int score = 0;
        boolean isOk = false;
        try {
            score = Integer.valueOf(person.getScore());
            if (score >= 0 && score <= 100) {
                isOk = true;
            }
        } catch (NumberFormatException e) {
            isOk = false;
        }
        if (!isOk) {
            Toast.makeText(this, "请输入正确的评分(0~100)！", Toast.LENGTH_SHORT).show();
            return;
        }
        builder = new AlertDialog.Builder(this);
        builder.setView(new ConfirmScoreView().builder(this, person));
        dialog = builder.create();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                submitResult(person.getScore(), person.getId());
                dialogInterface.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    private void showResult(List<PersonModel> persons) {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
        }
        builder.setView(new RankView().builder(this, persons, 0));
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * 提交评分结果
     */
    public void submitResult(String score, String personId) {
        disposable = matchService.submitScore(match.getId(), Constants.user.getGuid(), score, personId, Constants.user.getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<HttpResult>() {
                    @Override
                    public void accept(HttpResult httpResult) throws Exception {
                        showProgressDialog();
                    }
                })
                .subscribe(new Consumer<HttpResult>() {
                    @Override
                    public void accept(HttpResult result) throws Exception {
                        if (result.isSuccess()) {
                            Toast.makeText(VoteActivity.this, "成功提交评分！", Toast.LENGTH_SHORT).show();
                            personInfos.get(pager.getCurrentItem()).setIscompleted("true");
                            finishedCount++;
                            isCompleted(pager.getCurrentItem());
                            setProgress();
                            fragments.get(pager.getCurrentItem()).hasCompleted();
                        } else {
                            Toast.makeText(VoteActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(VoteActivity.this, "未知错误，请稍后再试！", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(VoteActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //当前窗口获取焦点时，才能正真拿到titlebar的高度，此时将需要固定的偏移量设置给scrollableLayout即可
        //headerViewPager.setTopOffset(toolbar.getHeight());
        headerViewPager.setTopOffset(Utils.getStatusHeight(this));
    }

    public static void gotoVoteActivity(Activity activity, String key) {
        Intent intent = new Intent(activity, VoteActivity.class);
        intent.putExtra(VoteActivity.VOTE_ACTIVITY_KEY, key);
        activity.startActivity(intent);
    }


    @Override
    public void onTextChange(PersonModel person, String score) {
        person.setScore(score);
        if (scoreResult.get(person.getId()) == null) {
            scoreResult.put(person.getId(), score);
//            if (score.equals("")) {
//                finishedCount = finishedCount > 0 ? finishedCount-- : 0;
//            } else {
//                finishedCount++;
//            }
//            setProgress();
        } else {
            scoreResult.put(person.getId(), score);
        }
    }

    private void setProgress() {
        proCount = finishedCount * 100 / personInfos.size();
        progress.setText(proCount + "%");
        if (proCount == 100) {
            isFinished = true;
            op_view.setVisibility(View.GONE);
            look_result.setVisibility(View.VISIBLE);
        } else {
            changeButtonStyle(true);
            look_result.setVisibility(View.GONE);
        }
    }

    private void changeButtonStyle(boolean isCompleted) {
        if (isCompleted) {
            submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            submit.setEnabled(false);
            submit.setText("已提交");
        } else {
            submit.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            submit.setEnabled(true);
            submit.setText("提交");
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
        userid = Constants.user.getGuid();
        Log.i("dengshuai", id + ":::" + userid);
        matchService = retrofit.create(MatchService.class);
        disposable = matchService.getMatch(id, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MatchResult>() {
                    @Override
                    public void accept(MatchResult result) throws Exception {
                        if (result.isSuccess()) {
                            if(result.getData().getPerson()==null||result.getData().getPerson().size()==0){
                                Toast.makeText(VoteActivity.this, "参赛人员信息暂未公布！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                initData(result);
                            }
                        } else {
                            Toast.makeText(VoteActivity.this, "获取活动信息失败，请重试！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(VoteActivity.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        showTips();
    }

    private void showTips() {
        if (isFinished) {
            finish();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("评分尚未完成,是否退出？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveData();
                            finish();
                        }
                    })
                    .create();
            dialog.show();
        }
    }

    private void saveData() {
        StringBuilder builder = new StringBuilder();
        CacheUtil cacheUtil = new CacheUtil(this);
        for (Map.Entry<String, String> entry : scoreResult.entrySet()) {
            if (!entry.getValue().equals("")) {
                builder.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            }
        }
        String cacheStr = builder.toString();
        if (cacheStr.length() > 0) {
            cacheStr = cacheStr.substring(0, cacheStr.length() - 1);
        }
        if (cacheUtil.getCache() != null) {
            cacheUtil.saveData(match.getId(), cacheStr);
        }
    }
}
