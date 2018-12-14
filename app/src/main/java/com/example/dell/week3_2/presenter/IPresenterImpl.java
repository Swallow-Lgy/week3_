package com.example.dell.week3_2.presenter;

import com.example.dell.week3_2.callback.MyCallBack;
import com.example.dell.week3_2.model.IModelImpl;
import com.example.dell.week3_2.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter{
    private IView mIView;
    private IModelImpl miModel;
    public IPresenterImpl(IView iView){
        mIView = iView;
        miModel = new IModelImpl();
    }
    @Override
    public void requestpData(String url, Map<String, String> pramse, Class calzz) {
        miModel.requestmData(url, pramse, calzz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.success(data);
            }
        });
    }
    public void del(){
        if (miModel!=null){
            miModel=null;
        }
        if (mIView==null){
            mIView=null;
        }
    }
}
