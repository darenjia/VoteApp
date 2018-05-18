package com.bokun.bkjcb.voteapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.client.result.ParsedResultType;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.encode.QREncode;

public class MainActivity extends AppCompatActivity {

    private String[] strdate = {"aa", "bb", "cc", "dd"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //扫一扫
        //mScannerView = findViewById(R.id.scanner_view);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
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
                    ScannerActivity.gotoActivity(MainActivity.this,null);
//                    TestMainActivity.gotoActivity(MainActivity.this);
                }
            }
        });


        //给listview赋值
        ArrayAdapter<String> array = new ArrayAdapter<String>(
                MainActivity.this, R.layout.support_simple_spinner_dropdown_item, strdate
        );
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(array);



    }





}
