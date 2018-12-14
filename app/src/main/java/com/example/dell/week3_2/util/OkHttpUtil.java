package com.example.dell.week3_2.util;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {
    public static volatile OkHttpUtil mIstance;
    public OkHttpClient mClient;
    public Handler handler = new Handler(Looper.myLooper());
    public static OkHttpUtil getmIstance(){
        if (mIstance==null){
            synchronized (OkHttpUtil.class){
                mIstance = new OkHttpUtil();
            }
        }
        return mIstance;
    }
    //完成个构造
    private OkHttpUtil(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient  = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }
    public void postEnqueue(String url, Map<String,String>pramas, final ICall iCall, final Class clazz){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String>entry:pramas.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCall.fila(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(result, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCall.success(o);
                    }
                });
            }
        });
    }

}
