package com.example.administrator.androidjsoup;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends Activity {

    private String mInfo = null;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        new getHtml().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MainThread) //在ui线程执行
    public void onEvent(MessageEvent event){
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        Log.e("AAAAAAAAAAAAAAAAAAAAA",event.message);

        if( s ){
            Intent intent = new Intent(MainActivity.this,TestActivity.class);
            startActivity(intent);
            s = false;
        }
    }

    boolean s = true;






    private class getHtml extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {

            //目标页面
            String url = "http://www.zhihu.com/people/18sui-47-9";
            //使用Jsoup连接目标页面,并执行请求,获取服务器响应内容
            String html = null;
            try{
                html = Jsoup.connect(url).execute().body();
                Document doc = Jsoup.parse(html);
                Elements links = doc.getElementsByClass("zm-profile-section-item zm-item clearfix");
                for(int loop=0;loop<links.size();loop++){
                   if( mInfo == null ){
                       mInfo = links.get(loop).getAllElements() + "";
                       Log.e("AAAAAAAAAAAAAAAA",links.get(loop).getAllElements()+"");
                   }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            boolean b = true;
            while(b){
                SystemClock.sleep(1000);
                EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
            }

            //打印页面内容
            System.out.println(html);
            return html;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            text_view.setText(mInfo);
        }
    }




}
