package com.example.dell.week3_2.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.week3_2.R;
import com.example.dell.week3_2.bean.DetailsGoods;
import com.example.dell.week3_2.presenter.IPresenterImpl;
import com.example.dell.week3_2.util.MyView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DatailActivity extends AppCompatActivity implements View.OnClickListener,IView {
     private String url="http://www.zhaoapi.cn/product/getProductDetail";
     private IPresenterImpl miPresenter;
     private Banner banner;
     private TextView shoptitle,shopprice;
     private MyView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);
        miPresenter = new IPresenterImpl(this);
        banner = findViewById(R.id.baner);
        shoptitle = findViewById(R.id.shoptitle);
        shopprice = findViewById(R.id.shopprice);
        banner.isAutoPlay(false);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                image = new MyView(context);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                return image;
            }
        });

        init();
    }

    private void init() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        HashMap<String,String> map = new HashMap<>();
        map.put("pid",id+"");
        miPresenter.requestpData(url,map,DetailsGoods.class);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void success(Object data) {
       DetailsGoods bean = (DetailsGoods) data;
        DetailsGoods.DataBean data1 = bean.getData();
        String images = data1.getImage();
        String[] split = images.split("\\|");
        List<String> list = new ArrayList<>();
        Log.i("rr",split+"");
        for (int i=0;i<split.length;i++){
            list.add(split[i]);
        }
        banner.setImages(list);
        banner.start();
        shoptitle.setText(data1.getTitle());
        shopprice.setText(data1.getPrice()+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        miPresenter.del();
    }
}
