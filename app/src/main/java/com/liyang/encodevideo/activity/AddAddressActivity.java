package com.liyang.encodevideo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liyang.encodevideo.R;
import com.liyang.encodevideo.data.AddressUrl;
import com.liyang.encodevideo.util.DBCRUDUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AddAddressActivity extends AppCompatActivity {
    private Button addAddress;
    private Button cancleAddress;
    private EditText addrEdit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        addrEdit = (EditText) findViewById(R.id.add_text);
        addAddress = (Button) findViewById(R.id.add_addr);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=addrEdit.getText().toString();
                String[] urls = content.split("\n");
                List<AddressUrl> urlList = new ArrayList<>();
                for(int i=0;i<urls.length;i++){
                    AddressUrl url = new AddressUrl();
                    url.setUrl(urls[i]);
                    urlList.add(url);
                }
                DBCRUDUtils.addAddress(urlList);
                finish();
            }
        });
        cancleAddress = (Button) findViewById(R.id.cancle_btn);
        cancleAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
