package com.example.dell.week3_2.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dell.week3_2.bean.GoodsNew;
import com.example.dell.week3_2.R;
import com.example.dell.week3_2.adpter.MyAdpter;
import com.example.dell.week3_2.presenter.IPresenterImpl;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private IPresenterImpl miPresenter;
    private XRecyclerView xRecyclerView,xRecyclerView1;
    private String url="http://www.zhaoapi.cn/product/searchProducts";
    private int page=1;
    private MyAdpter adpter;
    private TextView sales,price1;
    private int i=0;
    private int mSpance=2;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xRecyclerView  = findViewById(R.id.xrecyc);
        xRecyclerView1 = findViewById(R.id.xrecyc1);
        findViewById(R.id.qh).setOnClickListener(this);
        findViewById(R.id.sales).setOnClickListener(this);
        findViewById(R.id.price1).setOnClickListener(this);
        miPresenter = new IPresenterImpl(this);
        adpter = new MyAdpter(this);
        adpter.setOnClickLisenrter(new MyAdpter.onClick() {
            @Override
            public void onClickLister(int position) {
                int i = adpter.setData(position);
                Intent intent = new Intent(MainActivity.this,DatailActivity.class);
                intent.putExtra("id",i);
                startActivity(intent);
            }
        });
        init();
        initG();
    }

    private void initData() {
        HashMap<String,String>  map = new HashMap<>();
        map.put("keywords","手机");
        map.put("page",page+"");
        map.put("sort",0+"");
        miPresenter.requestpData(url,map,GoodsNew.class);
    }

    private void init() {
        //流式
         linearLayoutManager = new LinearLayoutManager(this);
         linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
         xRecyclerView.setLayoutManager(linearLayoutManager);

        xRecyclerView.setAdapter(adpter);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();
                xRecyclerView.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                initData();
                xRecyclerView.loadMoreComplete();
            }
        });
        initData();
    }
    public void initG(){
        //网格
        gridLayoutManager = new GridLayoutManager(this,mSpance) ;
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView1.setLayoutManager(gridLayoutManager);
        xRecyclerView1.setAdapter(adpter);
        xRecyclerView1.setLoadingMoreEnabled(true);
        xRecyclerView1.setPullRefreshEnabled(true);
        xRecyclerView1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();

                xRecyclerView1.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                initData();

                xRecyclerView1.loadMoreComplete();
            }
        });
        initData();
    }

    @Override
    public void onClick(View v) {
        page=1;
        int id = v.getId();
        switch (id){
            case R.id.sales:
                HashMap<String,String>  map = new HashMap<>();
                map.put("keywords","手机");
                map.put("page",page+"");
                map.put("sort",1+"");
                miPresenter.requestpData(url,map,GoodsNew.class);
                break;
            case R.id.price1:
                HashMap<String,String>  map1 = new HashMap<>();
                map1.put("keywords","手机");
                map1.put("page",page+"");
                map1.put("sort",2+"");
                miPresenter.requestpData(url,map1,GoodsNew.class);
                break;
            case R.id.qh:
                i++;
                if (i%2==0){
                   xRecyclerView.setVisibility(View.VISIBLE);
                   xRecyclerView1.setVisibility(View.INVISIBLE);
                } else {
                    xRecyclerView1.setVisibility(View.VISIBLE);
                    xRecyclerView.setVisibility(View.INVISIBLE);
                }
                break;
                default:
                    break;
        }

    }
    //获取数据
    @Override
    public void success(Object data) {
        GoodsNew goodsNew = (GoodsNew) data;
        if (page==1){
            adpter.setmData(goodsNew.getData());
        }
        else {
            adpter.addmData(goodsNew.getData());
        }
        page++;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        miPresenter.del();
    }
}
