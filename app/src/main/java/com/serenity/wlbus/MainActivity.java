package com.serenity.wlbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.serenity.wlbus.bus.Subscribe;
import com.serenity.wlbus.bus.WLBus;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WLBus.getDefault().register(this);


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
}
