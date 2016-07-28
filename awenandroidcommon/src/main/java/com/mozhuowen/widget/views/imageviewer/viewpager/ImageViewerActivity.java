package com.mozhuowen.widget.views.imageviewer.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mozhuowen.R;
import com.mozhuowen.rxandroid.activity.BaseActivity;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.widget.views.imageviewer.pinchimageview.PinchImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ImageViewerActivity extends BaseActivity {
    private static int statusbar_height;
    private static int actionBarHeight;
    private static int currPosition;
    static List<String> imageUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.imageviewer_pager_activity);
        getSupportActionBar().setTitle("");

//        imageUris.add("http://attach.bbs.miui.com/forum/201206/06/2226336d6nxnnfxldyxhed.jpg");
//        imageUris.add("http://img540.ph.126.net/5PyPfeSlM3ZWt_npImBosw==/1348265138445286339.jpg");
//        imageUris.add("http://cdn.duitang.com/uploads/item/201112/27/20111227143751_TtLkL.jpg");
//        imageUris.add("http://imgsrc.baidu.com/forum/pic/item/89f4051f95cad1c87ccacc6b7e3e6709c83d5147.jpg");

        final LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();

        final PinchImageViewPager pager = (PinchImageViewPager) findViewById(R.id.pager);
        if (pager == null)
            return;

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageUris.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PinchImageView piv;
                if (viewCache.size() > 0) {
                    piv = viewCache.remove();
                    piv.reset();
                } else {
                    piv = new PinchImageView(ImageViewerActivity.this);
                }

                Glide.with(ImageViewerActivity.this)
                        .load(imageUris.get(position))
                        .crossFade()
                        .into(piv);

                container.addView(piv);

                piv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                return piv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                container.removeView(piv);
                viewCache.add(piv);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
//                Glide.with(ImageViewerActivity.this)
//                        .load(imageUris.get(position))
//                        .crossFade()
//                        .into(piv);
                pager.setMainPinchImageView(piv);
            }
        });

        pager.setCurrentItem(currPosition);
    }

    @Override
    public void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    public static void startAction(Context context,List<String> list,int Position){
        imageUris = list;
        currPosition = Position;
        Intent intent = new Intent(context,ImageViewerActivity.class);
        context.startActivity(intent);
    }

    public static void startAction( Context context,List<String> list ) {
        imageUris = list;
        currPosition = 0;
        Intent intent = new Intent(context,ImageViewerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return null;
    }

    @Override
    protected boolean enableBackArrow() {
        return false;
    }

    @Override
    protected boolean enableLayoutFullScreen()
    {
        return true;
    }
}