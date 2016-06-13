package com.mozhuowen.rxandroidframework.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mozhuowen.rxandroid.adapter.BaseListAdapter;
import com.mozhuowen.rxandroid.model.EveListHttpModel;
import com.mozhuowen.rxandroid.presenter.BaseListPresenter;
import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.rxandroid.ui.ListView;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.http.ARetrofitClient;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;
import com.mozhuowen.rxandroidframework.ui.activity.ViewCellEve;
import com.mozhuowen.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Awen on 16/5/12.
 * Email:mozhuowen@gmail.com
 */
public class TestPresenter extends BaseListPresenter{

    public int page = 1;

    BaseListAdapter<ViewCellEve,MovieItem> adapter;
//    BaseListAdapter<ViewCellEve,MovieItem> adapter;
    Context mContext;
    LMRecyclerView recyclerView;
    LMRecyclerView.LoadMoreListener loadMoreListener;

    public TestPresenter(Context context, ListView<MovieItem> iView) {
        super(context, iView);
        this.mContext = context;

//        List<MovieItem> list = new ArrayList<>();
        List<MovieItem> list = new ArrayList<>();
        adapter = new BaseListAdapter<>(mContext, R.layout.item_ganhuo,list,ViewCellEve.class);
        adapter.setAutoLoadMoreEnable(true);
    }

    @Override
    public void release() {

    }

//    @Override
//    public void fetchData() {
//        ARetrofitClient.getRetrofitInstance().getMeiziData(page++)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<MeiziData>() {
//                            @Override
//                            public void call(MeiziData meiziData) {
//                                mView.showList((meiziData.results));
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                mView.showErrorView();
//                            }
//                        });
//    }
    @Override
    public void fetchData() {
        ARetrofitClient.getRetrofitInstance().getMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<EveListHttpModel<MovieItem>>() {
                    @Override
                    public void call(EveListHttpModel<MovieItem> meiziData) {
                        mView.showList((meiziData._items));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showErrorView();
                        LogUtil.d(throwable.getMessage());
                    }
                });
    }

    public void setLoadMoreListener(LMRecyclerView.LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        if (this.loadMoreListener != null && this.recyclerView != null)
            this.recyclerView.setLoadMoreListener(this.loadMoreListener);

    }

    public BaseListAdapter<ViewCellEve, MovieItem> getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseListAdapter<ViewCellEve, MovieItem> adapter) {
        this.adapter = adapter;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(LMRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(adapter);
    }
}
