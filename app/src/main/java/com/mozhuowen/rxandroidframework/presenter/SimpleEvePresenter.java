package com.mozhuowen.rxandroidframework.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mozhuowen.rxandroid.adapter.BaseListAdapter;
import com.mozhuowen.rxandroid.model.BaseEveHttpModel;
import com.mozhuowen.rxandroid.model.EveListHttpModel;
import com.mozhuowen.rxandroid.presenter.BaseEvePresenter;
import com.mozhuowen.rxandroid.ui.LMRecyclerView;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.context.App;
import com.mozhuowen.rxandroidframework.http.ARetrofitClient;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;
import com.mozhuowen.rxandroidframework.ui.activity.ViewCellEve;
import com.mozhuowen.rxandroidframework.ui.iView.TestEveView;
import com.mozhuowen.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Awen on 16/6/14.
 * Email:mozhuowen@gmail.com
 */
public class SimpleEvePresenter extends BaseEvePresenter<TestEveView> {

    protected int page = -1;
    public BaseEveHttpModel currentHttpModel = null;

    BaseListAdapter<ViewCellEve,MovieItem> adapter;
    //    BaseListAdapter<ViewCellEve,MovieItem> adapter;
    Context mContext;
    LMRecyclerView recyclerView;
    LMRecyclerView.LoadMoreListener loadMoreListener;

    public SimpleEvePresenter(Context context, TestEveView view) {
        super(context, view);

        List<MovieItem> list = new ArrayList<>();
        adapter = new BaseListAdapter<>(mContext, R.layout.item_ganhuo,list,ViewCellEve.class);
        adapter.setAutoLoadMoreEnable(true);
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

    @Override
    public void release() {

    }

    @Override
    public void addOneItem(BaseEveHttpModel item) {
        item._id = null;
        ARetrofitClient.getRetrofitInstance().addOneData((MovieItem) item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<BaseEveHttpModel>() {
                            @Override
                            public void call(BaseEveHttpModel data) {
                                if (data._status.equals("OK")) {
                                    Toast.makeText(context, "新增成功", Toast.LENGTH_SHORT).show();
                                    updateLocalStorage((MovieItem) data);
                                }
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
    public void updateOneItem(final BaseEveHttpModel item) {
        ARetrofitClient.getRetrofitInstance().updateOneMovie(item._id,item._etag,(MovieItem) item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<BaseEveHttpModel>() {
                    @Override
                    public void call(BaseEveHttpModel data) {
                        if (data._status.equals("OK")) {
                            Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                            updateLocalStorage((MovieItem) item);
                        }else
                            Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                    }
                },new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d(throwable.getMessage());
                    }
                });
    }

    @Override
    public void deleteOneItem(BaseEveHttpModel item) {

    }

    @Override
    public void getOneItem(String id) {
        ARetrofitClient.getRetrofitInstance().getOneMovie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<MovieItem>() {
                    @Override
                    public void call(MovieItem data) {
                        mView.onGetData(data);
                        updateLocalStorage(data);
                    }
                },new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.d(throwable.getMessage());
                    }
                });
    }

    @Override
    public void fetchNextPage() {
        String nextPath = null;
        if (currentHttpModel != null)
            nextPath = currentHttpModel._links.next.href == null ? null:currentHttpModel._links.next.href;
        else
            nextPath = "movies";

        if (nextPath == null)
            return;

        ARetrofitClient.getRetrofitInstance().getMovie(nextPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<EveListHttpModel<MovieItem>>() {
                    @Override
                    public void call(EveListHttpModel<MovieItem> meiziData) {
                        LogUtil.d("test object to string ->"+meiziData._links.next.href);
                        mView.showList(meiziData._items);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showErrorView();
                        LogUtil.d(throwable.getMessage());
                    }
                });
    }

    public void updateLocalStorage(MovieItem item) {
        App.getDbHash().put(item._id,item);
    }

    public void updateLocalstorage(List<MovieItem> items) {
        for(BaseEveHttpModel object:items) {
            App.getDbHash().put(object._id,object);
        }
    }

    public BaseEveHttpModel getItemFromLocalStorage(String etag) {
        return null;
    }
}
