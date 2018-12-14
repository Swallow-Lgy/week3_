package com.example.dell.week3_2.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.week3_2.bean.GoodsNew;
import com.example.dell.week3_2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GoodsNew.DataBean> mData;
    private Context mContext;

    public MyAdpter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setmData(List<GoodsNew.DataBean> data) {
       mData.clear();
       if (data!=null){
           mData.addAll(data);
       }
       notifyDataSetChanged();
    }
    public void addmData(List<GoodsNew.DataBean> data) {

        if (data!=null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String images = mData.get(i).getImages();
        String[] split = images.split("\\|");
        List<String> list = Arrays.asList(split);
        Log.i("LGY",list+"");
        Glide.with(mContext).load(list.get(0)).into(holder.icon);
        holder.title.setText(mData.get(i).getTitle());
        holder.price.setText(mData.get(i).getPrice()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monClick!=null){
                    monClick.onClickLister(i);

                }
            }
        });

    }
    //传过去值
    public int setData(int postion){
        int pid = mData.get(postion).getPid();
        Log.i("qq",pid+"");
        return pid;
    }
    //记住
    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView title,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }
    }
    //定义全局变量
    onClick monClick;
    public void setOnClickLisenrter(onClick onClick){
        monClick=onClick;
    }
    // 定义接口
    public interface   onClick{
        void onClickLister(int position);
    }
}
