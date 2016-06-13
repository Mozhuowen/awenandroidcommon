package com.mozhuowen.rxandroidframework.presenter;

import android.content.Context;
import android.widget.Toast;

import com.mozhuowen.rxandroid.model.BaseEveHttpModel;
import com.mozhuowen.rxandroidframework.http.ARetrofitClient;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;
import com.mozhuowen.rxandroidframework.ui.iView.TestEveView;
import com.mozhuowen.util.LogUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Awen on 16/6/8.
 * Email:mozhuowen@gmail.com
 */
public class TestEvePresenter extends com.mozhuowen.rxandroid.presenter.BasePresenter<TestEveView> {
    public TestEvePresenter(Context context, TestEveView iView) {
        super(context, iView);
    }

    public void fetchOneData(String id) {
        ARetrofitClient.getRetrofitInstance().getOneMovie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<MovieItem>() {
                    @Override
                    public void call(MovieItem data) {
                        mView.onGetData(data);
                    }
                },new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtil.d(throwable.getMessage());
                        }
                    });
    }

    public void updateTitle(String id,String etag,MovieItem movieItem) {
        ARetrofitClient.getRetrofitInstance().updateOneMovie(id,etag,movieItem)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<BaseEveHttpModel>() {
                    @Override
                    public void call(BaseEveHttpModel data) {
                        if (data._status.equals("OK"))
                            Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                    }
                },new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d(throwable.getMessage());
                    }
                });
    }

    public void addOneData(MovieItem movieItem) {
        movieItem._id = null;
        ARetrofitClient.getRetrofitInstance().addOneData(movieItem)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Action1<BaseEveHttpModel>() {
                            @Override
                            public void call(BaseEveHttpModel data) {
                                if (data._status.equals("OK"))
                                    Toast.makeText(context, "新增成功", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "新增失败", Toast.LENGTH_SHORT).show();
                            }
                },new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtil.d(throwable.getMessage());
                            }
                        });
    }

    @Override
    public void release() {

    }
}
