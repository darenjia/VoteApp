package com.bokun.bkjcb.voteapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bokun.bkjcb.voteapp.Adapter.MatchAdapter;
import com.bokun.bkjcb.voteapp.HttpService.MatchService;
import com.bokun.bkjcb.voteapp.Model.MatchList;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.SPUtils;
import com.mylhyl.zxing.scanner.common.Scanner;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private long time;
    private SwipeRefreshLayout loading;
    private ListView listView;
    private MatchAdapter adapter;
    private MatchList matchs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("活动");
        setSupportActionBar(toolbar);

        loading = findViewById(R.id.refreshView);
        listView = findViewById(R.id.match_listView);
        initData();
        initListener();

    }

    private void initListener() {
        loading.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                loading.setEnabled(enable);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = matchs.getData().get(i).getPipeliningID();
                VoteActivity.gotoVoteActivity(MainActivity.this, s);
            }
        });
    }

    private void initData() {

        loading.setRefreshing(true);
        MatchService service = retrofit.create(MatchService.class);
        String userID = (String) SPUtils.get(this, "UserID", "");
        disposable = service.getMatchList(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MatchList>() {
                    @Override
                    public void accept(MatchList matchList) throws Exception {
                        matchs = matchList;
                        initList(matchList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loading.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "获取数据错误,请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initList(MatchList list) {
        if (adapter == null) {
            adapter = new MatchAdapter(this, list);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(list);
        }
        loading.setRefreshing(false);
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
    public void onBackPressed() {
        if (System.currentTimeMillis() - time > 1000) {
            time = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.scanner) {
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
        return true;
    }


}
