package com.serenity.wlbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.serenity.wlbus.bus.Subscribe;
import com.serenity.wlbus.bus.WLBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //注册WLBus
        WLBus.getDefault().register(this);

        //发送消息
        WLBus.getDefault().post("2","wanglu",2);
    }



    @Subscribe({"1","2"})
    public void test1(String name ,String type) {
        Log.e(TAG, "---------test1-------name-->"+name+"----type---->"+type);
    }


    @Subscribe({"1"})
    public void test2(String name ,String type) {
        Log.e(TAG, "---------test2-------name-->"+name+"----type---->"+type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //进行反注册
        WLBus.getDefault().unRegister(this);

        //退出app的时候，进行资源释放
        WLBus.getDefault().release();
    }

    public void clickTextView(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }
}
