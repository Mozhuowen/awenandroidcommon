package com.mozhuowen.widget.autoscrollviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mozhuowen.R;
import com.mozhuowen.widget.autoscrollviewpager.adapter.AutoScrollAdapter;
import com.mozhuowen.widget.autoscrollviewpager.adapter.PagerItem;
import com.mozhuowen.widget.autoscrollviewpager.indicator.AdClickDelegate;
import com.mozhuowen.widget.autoscrollviewpager.indicator.LinePageIndicator;

import java.util.List;

/**
 * Created by Awen on 16/3/31.
 * Email:mozhuowen@gmail.com
 */
public class AdView {

    Context mContext;
    LayoutInflater inflater;
    InfiniteViewPager mViewPager;
    LinePageIndicator mLineIndicator;
    int selectcolor;
    AdClickDelegate adClickDelegate;

    List<PagerItem> itemList;

    public AdView (Context context,List<PagerItem> ils,int selectcolor,AdClickDelegate adClickDelegate) {
        this.mContext = context;
        this.selectcolor = selectcolor;
        this.adClickDelegate = adClickDelegate;
        itemList = ils;
        inflater = LayoutInflater.from(mContext);
    }

    public View getView() {
        View view = inflater.inflate(R.layout.awen_autoscrollview,null);
        mViewPager = (InfiniteViewPager) view.findViewById(R.id.viewpager);
        mLineIndicator = (LinePageIndicator) view.findViewById(R.id.indicator);
        mViewPager.setPageMargin(0);

        AutoScrollAdapter adapter = new AutoScrollAdapter(mContext,adClickDelegate);
        adapter.setDataList(itemList);
        mViewPager.setAdapter(adapter);
        mViewPager.setAutoScrollTime(5000);
        mViewPager.startAutoScroll();
        mLineIndicator.setViewPager(mViewPager);
        mLineIndicator.setSelectedColor(selectcolor);

        return view;
    }
}
