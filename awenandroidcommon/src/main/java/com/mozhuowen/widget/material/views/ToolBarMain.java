package com.mozhuowen.widget.material.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blunderer.materialdesignlibrary.views.Toolbar;
import com.mozhuowen.R;

public class ToolBarMain extends Toolbar {

    public View statusView;
    protected LayoutInflater inflater;
    protected Context mContext;

    public ToolBarMain(Context context) {
        this(context, null);
    }

    public ToolBarMain(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBarMain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mdl_toolbar_default, this, true);

        mToolbar = (android.support.v7.widget.Toolbar) getChildAt(0);
        statusView = getChildAt(1);

//        ImageView vi = new ImageView(context);
//        vi.setImageResource(R.mipmap.iconfont_qrcode);

        // Calculate ActionBar height
        float actionBarHeight = 0;
        TypedValue tva = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tva, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tva.data,getResources().getDisplayMetrics());
        }

//        FrameLayout.LayoutParams lp2
//                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarHeight);
//        this.setLayoutParams(lp2);

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

    protected View getCustomView(){ return null;}

}