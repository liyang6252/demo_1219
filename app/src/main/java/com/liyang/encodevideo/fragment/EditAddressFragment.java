package com.liyang.encodevideo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.liyang.encodevideo.R;
import com.liyang.encodevideo.activity.AddressManagerActivity;
import com.liyang.encodevideo.adapter.EditAddressAdapter;
import com.liyang.encodevideo.adapter.RecycleViewDivider;
import com.liyang.encodevideo.data.AddressUrl;
import com.liyang.encodevideo.util.DBCRUDUtils;

import java.util.ArrayList;
import java.util.List;

public class EditAddressFragment extends Fragment implements View.OnClickListener{
    private Button yesEdit;
    private Button cancelEdit;
    private RecyclerView editRecycle;
    private EditAddressAdapter editAdapter;
    private List<AddressUrl> addresses;
    private List<AddressUrl> deleteList = new ArrayList<>();
    private AddressManagerActivity manActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.edit_fragment,container,false);
        yesEdit = (Button) view.findViewById(R.id.yes_edit);
        yesEdit.setOnClickListener(this);
        cancelEdit = (Button) view.findViewById(R.id.cancle_edit);
        cancelEdit.setOnClickListener(this);
        editRecycle = (RecyclerView) view.findViewById(R.id.edit_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        editRecycle.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        editRecycle.setLayoutManager(layoutManager);
        final List<String> stringList = new ArrayList<>();
        addresses = DBCRUDUtils.queryAddress();
        for(AddressUrl url:addresses){
            stringList.add(url.getUrl());
        }
        editAdapter = new EditAddressAdapter(stringList, new EditAddressAdapter.OnDeleteBtnClick() {
            @Override
            public void onBtnClick(View v) {
                TextView deleteAddr = (TextView) v.findViewById(R.id.content);
                String addr=deleteAddr.getText().toString().trim();
                for(AddressUrl url:addresses){
                    if(url.getUrl().equals(addr)){
                        deleteList.add(url);
                    }
                }
                stringList.remove(addr);
                editAdapter.notifyDataSetChanged();
            }
        });
        editRecycle.setAdapter(editAdapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        manActivity = (AddressManagerActivity) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yes_edit:
                DBCRUDUtils.deleteAddress(deleteList);
                manActivity.notifyDataChanged();
                getFragmentManager().popBackStack();
                break;
            case R.id.cancle_edit:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
