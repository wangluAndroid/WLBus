package com.serenity.wlbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.serenity.wlbus.bus.WLBus;

/**
 * Created by serenitynanian on 2018/6/14.
 */

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WLBus.getDefault().post("1","SecondActivity","SecondActivity");

    }
}
