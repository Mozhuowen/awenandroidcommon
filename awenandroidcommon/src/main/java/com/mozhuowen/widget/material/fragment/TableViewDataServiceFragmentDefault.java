package com.mozhuowen.widget.material.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mozhuowen.R;
import com.mozhuowen.widget.views.tableview.TableViewDelegate;
import com.umeng.analytics.MobclickAgent;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;
import net.datafans.android.common.widget.controller.DataServiceFragment;
import net.datafans.android.common.widget.table.TableViewDataSource;

public abstract class TableViewDataServiceFragmentDefault<T> extends DataServiceFragment implements TableViewDataSource<T>,TableViewDelegate
{
    protected View loadingview;
    protected View customview;
    protected TextView networkfailview;

    protected boolean needShowloagingView = true;
    protected boolean needShowNothingView = false;

    //友盟SESSION统计
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dataservice_fragment,container,false);
        loadingview = view.findViewById(R.id.default_loading);
        networkfailview = (TextView) view.findViewById(R.id.networkfail_view);
        customview = initViews(inflater,container,savedInstanceState);
        if (needShowloagingView) {
            customview.setVisibility(View.GONE);
        } else {
            loadingview.setVisibility(View.GONE);
            networkfailview.setVisibility(View.GONE);
            customview.setVisibility(View.VISIBLE);
        }
        if(needShowNothingView) {
            loadingview.setVisibility(View.GONE);
            customview.setVisibility(View.GONE);
            networkfailview.setVisibility(View.VISIBLE);
            networkfailview.setText(getNothingViewText());
        }

        view.addView(customview);
        initDataService();
        return view;
    }

    public abstract View initViews(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState);

    public abstract void initDataService();

    @Override
    public int getItemViewType(int section, int row) {
        return 0;
    }

    @Override
    public int getItemViewTypeCount() {
        return 1;
    }

    @Override
    public void onStatusOk(BaseResponse response, DataService service) {
        if (needShowloagingView) {
            loadingview.setVisibility(View.GONE);
            customview.setVisibility(View.VISIBLE);
        }
    }

    public void showLoadingView() {
        loadingview.setVisibility(View.VISIBLE);
        customview.setVisibility(View.GONE);
        networkfailview.setVisibility(View.GONE);
    }

    @Override
    public void onStatusError(BaseResponse response, DataService service) {
        super.onStatusError(response,service);
        Toast.makeText(getActivity(),response.getErrorMsg(),Toast.LENGTH_SHORT).show();
        loadingview.setVisibility(View.GONE);
        networkfailview.setVisibility(View.VISIBLE);
        networkfailview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                initDataService();
            }
        });
    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable, DataService service) {
        super.onRequestError(errorCode, errorResponse, throwable, service);
        if (errorResponse != null)
            Toast.makeText(getActivity(), new String(errorResponse), Toast.LENGTH_SHORT).show();

        loadingview.setVisibility(View.GONE);
        networkfailview.setVisibility(View.VISIBLE);
        networkfailview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                initDataService();
            }
        });
    }

    protected String getNothingViewText() {
        return "";
    }
}