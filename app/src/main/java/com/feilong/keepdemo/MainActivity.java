package com.feilong.keepdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1、前台服务保活
//        startService(new Intent(this,ForegroundService.class));

        //双进程守护拉活
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
    }
}
