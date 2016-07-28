package com.mozhuowen.rxandroid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mozhuowen.R;

/**
 * Created by Awen on 16/5/17.
 * Email:mozhuowen@gmail.com
 */
public class ToolbarDefault extends Toolbar {

    protected LayoutInflater inflater;
    protected Context mContext;
    protected View customview;

    public ToolbarDefault(Context context) {
        this(context, null);
    }

    public ToolbarDefault(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarDefault(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mdl_toolbar_default, this, true);

        mToolbar = (android.support.v7.widget.Toolbar) getChildAt(0);

//        ImageView vi = new ImageView(context);
//        vi.setImageResource(R.mipmap.iconfont_qrcode);

        // Calculate ActionBar height
        float actionBarHeight = 0;
        TypedValue tva = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tva, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tva.data,getResources().getDisplayMetrics());
        }

        if (getCustomView() != null) {
            View customview = getCustomView();
            FrameLayout.LayoutParams lp
                    = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    (int) actionBarHeight);

            lp.gravity = Gravity.RIGHT | Gravity.TOP;
            addView(customview, lp);
        }
    }

    public android.support.v7.widget.Toolbar getToolbar() {
        return mToolbar;
    }

    protected View getCustomView(){
        return null;
    }

    public void setCustomview(View view) {
        customview = view;
    }
}
