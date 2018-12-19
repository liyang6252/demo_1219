package com.liyang.encodevideo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.liyang.encodevideo.R;

public class VideoPlayActivity extends AppCompatActivity {
    private WebView playWeb;
    private Button close;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        playWeb = (WebView) findViewById(R.id.play_web);
        playWeb.getSettings().setJavaScriptEnabled(true);
        playWeb.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("url");
        if(!TextUtils.isEmpty(url)){
            playWeb.loadUrl(url);
        }else{
            Toast.makeText(this,"链接无效",Toast.LENGTH_SHORT).show();
        }
        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
