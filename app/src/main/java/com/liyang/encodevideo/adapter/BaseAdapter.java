package com.liyang.encodevideo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liyang.encodevideo.R;

import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private List<String> strList;

    public BaseAdapter(List<String> strList){
        this.strList = strList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView addrText;
        public ViewHolder(View itemView) {
            super(itemView);
            addrText = (TextView) itemView.findViewById(R.id.text);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.base_str_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.addrText.setText(strList.get(position));
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }
}
