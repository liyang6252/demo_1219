package com.liyang.encodevideo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liyang.encodevideo.R;
import com.liyang.encodevideo.adapter.AddressAdapter;
import com.liyang.encodevideo.adapter.RecycleViewDivider;
import com.liyang.encodevideo.bean.Address;
import com.liyang.encodevideo.data.AddressUrl;
import com.liyang.encodevideo.fragment.AddressMsgFragment;
import com.liyang.encodevideo.util.DBCRUDUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button addressManager;
    private EditText editAddress;
    private Button encodeBtn;
    private ImageButton showMenu;
    private CircleImageView iconImage;
    private RecyclerView listEncode;
    private ProgressDialog dialog;
    private String videoUrl;
    private AddressAdapter adapter;
    private List<Address> addressList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout= (DrawerLayout) findViewById(R.id.draw_layout);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        showMenu = (ImageButton) findViewById(R.id.show_menu);
        showMenu.setOnClickListener(this);
        addressManager = (Button) findViewById(R.id.address_man);
        addressManager.setOnClickListener(this);
        editAddress = (EditText) findViewById(R.id.et_addr);
        encodeBtn= (Button) findViewById(R.id.encode_btn);
        encodeBtn.setOnClickListener(this);
        listEncode= (RecyclerView) findViewById(R.id.list_encode);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listEncode.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        listEncode.setLayoutManager(layoutManager);
        iconImage = (CircleImageView) findViewById(R.id.icon_image);
        iconImage.setOnClickListener(this);
        setAdapter();
    }

    public void setAdapter(){
        adapter = new AddressAdapter(addressList, new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                TextView result= (TextView) view.findViewById(R.id.result);
                String ifUseful=result.getText().toString().trim();
                if(TextUtils.equals(ifUseful,"成功")){
                    TextView content= (TextView) view.findViewById(R.id.content);
                    String url = content.getText().toString().trim();
                    AddressMsgFragment msgFragment = new AddressMsgFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
                    msgFragment.setArguments(bundle);
                    msgFragment.show(getFragmentManager(),null);
                }
            }
        });
        listEncode.setAdapter(adapter);
    }

    private void encodeAddress(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AddressUrl> addresses = DBCRUDUtils.queryAddress();
                for(AddressUrl url:addresses){
                    String visitUrl = url.getUrl() + videoUrl;
                    addressList.add(new Address(url.getUrl()+videoUrl, testIfUsefulUrl(visitUrl)));
                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }
    //判断放回的内容类型
    private boolean testIfUsefulUrl(String url){
        HttpURLConnection connection = null;
//        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
//            InputStream in=connection.getInputStream();
            String type = connection.getContentType();
            String sign = type.substring(0, type.indexOf("/"));
//            if(TextUtils.equals(sign,"audio")
//                    ||TextUtils.equals(sign,"video")){
//                return true;
//            }
            if(TextUtils.equals(type,"text/html")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
        return false;
    }
    private void showDialog(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在解析地址...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.address_man:
                Intent intent = new Intent(MainActivity.this,AddressManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.encode_btn:
                boolean isEmpty = TextUtils.isEmpty(editAddress.getText().toString().trim());
                videoUrl = isEmpty?"":editAddress.getText().toString().trim();
                showDialog();
                encodeAddress();
                break;
            case R.id.icon_image:
                Intent in = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);
                break;
        }
    }
}
