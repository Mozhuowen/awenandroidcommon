package com.mozhuowen.widget.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView
{
    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SquareImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}