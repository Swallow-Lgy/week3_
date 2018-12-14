package com.example.dell.week3_2.model;

import com.example.dell.week3_2.callback.MyCallBack;
import com.example.dell.week3_2.util.ICall;
import com.example.dell.week3_2.util.OkHttpUtil;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void requestmData(String url, Map<String, String> pramas, Class clazz, final MyCallBack callBack) {
        OkHttpUtil.getmIstance().postEnqueue(url, pramas, new ICall() {
            @Override
            public void fila(Exception e) {
                callBack.setData(e);
            }

            @Override
            public void success(Object data) {
               callBack.setData(data);
            }
        },clazz);
    }
}
