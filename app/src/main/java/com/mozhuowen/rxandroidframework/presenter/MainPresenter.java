package com.mozhuowen.rxandroidframework.presenter;

import android.content.Context;

import com.mozhuowen.rxandroidframework.http.ARetrofitClient;
import com.mozhuowen.rxandroidframework.model.FunnyData;
import com.mozhuowen.rxandroidframework.model.MeiziData;
import com.mozhuowen.rxandroidframework.ui.iView.IMainView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    public void fetchMeiziData(int page) {
        iView.showProgress();
        subscription = Observable.zip(ARetrofitClient.getRetrofitInstance().getMeiziData(page),
                ARetrofitClient.getRetrofitInstance().getFunnyData(page),
                new Func2<MeiziData, FunnyData, MeiziData>() {
                    @Override
                    public MeiziData call(MeiziData meiziData, FunnyData funnyData) {
                        return createMeiziDataWith休息视频Desc(meiziData, funnyData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        if (meiziData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showMeiziList(meiziData.results);
                        }
                        iView.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.showErrorView();
                        iView.hideProgress();
                    }
                });
    }

    private MeiziData createMeiziDataWith休息视频Desc(MeiziData meiziData,FunnyData funnyData) {
        int size = Math.min(meiziData.results.size(),funnyData.results.size());
        for (int i = 0; i < size; i++) {
            meiziData.results.get(i).desc = meiziData.results.get(i).desc + "，" + funnyData.results.get(i).desc;
            meiziData.results.get(i).who = funnyData.results.get(i).who;
        }
        return meiziData;
    }


}
