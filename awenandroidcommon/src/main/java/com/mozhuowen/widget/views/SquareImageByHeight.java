package com.mozhuowen.widget.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageByHeight extends ImageView
{
    public SquareImageByHeight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public SquareImageByHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SquareImageByHeight(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}