package com.mozhuowen.widget.material.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Awen on 16/5/23.
 * Email:mozhuowen@gmail.com
 */
public class BlankFragment extends Fragment
{
    private Context mContext;

    public static BlankFragment newInstance(String title) {
        BlankFragment fragment = new BlankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        String content = getArguments().getString("content");
        TextView textView = new TextView(mContext);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setText("测试页面\n\n"+content);
        textView.setBackgroundColor(0xFFececec);
        return textView;
    }
}
