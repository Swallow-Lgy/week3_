package com.example.dell.week3_2.model;

import com.example.dell.week3_2.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestmData(String url, Map<String,String>pramas, Class clazz, MyCallBack callBack);
}
