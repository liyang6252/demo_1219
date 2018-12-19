package com.liyang.encodevideo.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liyang.encodevideo.R;
import com.liyang.encodevideo.adapter.BaseAdapter;
import com.liyang.encodevideo.adapter.EditAddressAdapter;
import com.liyang.encodevideo.adapter.RecycleViewDivider;
import com.liyang.encodevideo.data.AddressUrl;
import com.liyang.encodevideo.fragment.EditAddressFragment;
import com.liyang.encodevideo.util.DBCRUDUtils;

import java.util.ArrayList;
import java.util.List;

public class AddressManagerActivity extends AppCompatActivity {
    private Button back;
    private Button editAddr;
    private FloatingActionButton fabBtn;
    private RecyclerView addrRecycle;
    private BaseAdapter baseAdapter;
    private List<String> stringList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_manager);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editAddr= (Button) findViewById(R.id.edit_addr);
        addrRecycle = (RecyclerView) findViewById(R.id.list_addr);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addrRecycle.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        addrRecycle.setLayoutManager(layoutManager);

        fabBtn = (FloatingActionButton) findViewById(R.id.add_fab);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressManagerActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
        getAddressList();
        baseAdapter = new BaseAdapter(stringList);
        addrRecycle.setAdapter(baseAdapter);
        editAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressFragment fragment = new EditAddressFragment();
                FragmentTransaction traction = getFragmentManager().beginTransaction();
                traction.replace(R.id.man_layout,fragment).addToBackStack(null).commit();
            }
        });
    }

    private void getAddressList(){
        stringList.clear();
        List<AddressUrl> addresses = DBCRUDUtils.queryAddress();
        for(AddressUrl url:addresses){
            stringList.add(url.getUrl());
        }
    }

    public void notifyDataChanged(){
        getAddressList();
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifyDataChanged();
    }
}
