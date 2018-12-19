package com.liyang.encodevideo.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liyang.encodevideo.R;
import com.liyang.encodevideo.bean.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private List<Address> addressList;
    private OnItemClickListener mOnItemClickListener;
    public AddressAdapter(List<Address> addressList,OnItemClickListener mOnItemClickListener){
        this.addressList = addressList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener{
         void onItemClick(View view);
    }

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView result;
        public ViewHolder(View view){
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            result = (TextView) view.findViewById(R.id.result);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Address address=addressList.get(position);
        holder.content.setText(address.getAddress());
        holder.result.setText(address.isUseful()?"成功":"无效");
        holder.result.setTextColor(address.isUseful()?Color.GREEN:Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
