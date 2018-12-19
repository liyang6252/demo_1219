package com.liyang.encodevideo.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liyang.encodevideo.R;
import com.liyang.encodevideo.activity.VideoPlayActivity;

public class AddressMsgFragment extends DialogFragment implements View.OnClickListener{
    private TextView addrMsgTx;
    private Button playVideo;
    private Button copyUrl;
    private Button openWay;
    private String url;
    private ClipboardManager clipboardManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.address_msg,container);
        addrMsgTx= (TextView) view.findViewById(R.id.addr_msg);
        playVideo= (Button) view.findViewById(R.id.play_video);
        playVideo.setOnClickListener(this);
        copyUrl = (Button) view.findViewById(R.id.copy_url);
        copyUrl.setOnClickListener(this);
        openWay = (Button) view.findViewById(R.id.open_way);
        openWay.setOnClickListener(this);
        url = getArguments().getString("url");
        addrMsgTx.setText(url);
        clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_video:
                Intent intent = new Intent(getActivity(),VideoPlayActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                break;
            case R.id.copy_url:
                ClipData clipData = ClipData.newPlainText("text", url);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getActivity(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_way:
                Intent in = new Intent();
                in.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                in.setData(content_url);
                startActivity(in);
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),R.style.DialogAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.address_msg);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().windowAnimations = R.style.DialogAnim;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.height = height/3;
            lp.dimAmount = 0.35f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return dialog;
    }
}
