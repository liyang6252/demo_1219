package com.liyang.encodevideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liyang.encodevideo.R;

import java.util.List;

public class EditAddressAdapter extends RecyclerView.Adapter<EditAddressAdapter.ViewHolder> {
    private List<String> address;
    private Context context;
    private OnDeleteBtnClick onDeleteBtnClick;
    public EditAddressAdapter(List<String> address,OnDeleteBtnClick onDeleteBtnClick){
        this.address = address;
        this.onDeleteBtnClick = onDeleteBtnClick;
    }

    public interface OnDeleteBtnClick{
        void onBtnClick(View v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView addrText;
        ImageButton deleBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            addrText = (TextView) itemView.findViewById(R.id.content);
            deleBtn = (ImageButton) itemView.findViewById(R.id.delete_btn);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_addr_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.addrText.setText(address.get(position));
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        holder.deleBtn.startAnimation(shake);
        holder.deleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteBtnClick.onBtnClick(holder.itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return address.size();
    }
}
