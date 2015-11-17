package com.example.administrator.androidjsoup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 作者：Administrator on 2015/11/17 16:19
 */
public class TestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread) //在ui线程执行
    public void onEvent(MessageEvent event){
        Log.e("BBBBBBBBBBBBBBBBBBBBB", event.message);
    }
}
