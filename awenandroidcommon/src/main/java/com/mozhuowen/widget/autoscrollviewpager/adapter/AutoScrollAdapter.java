package com.mozhuowen.widget.autoscrollviewpager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mozhuowen.R;
import com.mozhuowen.util.ImageLoader;
import com.mozhuowen.widget.autoscrollviewpager.InfinitePagerAdapter;
import com.mozhuowen.widget.autoscrollviewpager.indicator.AdClickDelegate;

import java.util.List;

/**
 * Created by Awen on 16/3/31.
 * Email:mozhuowen@gmail.com
 */
public class AutoScrollAdapter<T> extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private List<PagerItem> mList;
    private AdClickDelegate<T> adClickDelegate;

    public void setDataList(List<PagerItem> list) {
        if (list == null || list.size() == 0)
            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public AutoScrollAdapter(Context context,AdClickDelegate<T> adClickDelegate) {
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
        this.adClickDelegate = adClickDelegate;
    }


    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.awen_autosrollview_viewpager, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        PagerItem item = mList.get(position);
        holder.position = position;
//        holder.name.setText(item.getName());
//        holder.description.setText(item.getDesc()+"position:"+position);
//        Picasso.with(mContext).load(item.getImageUrl()).placeholder(R.mipmap.bg_loding_horizontal).into(holder.image);
        ImageLoader.getInstance(mContext).loadImage(holder.image,item.getImage());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adClickDelegate.onClick(mList.get(position).getType(),(T)mList.get(position));
            }
        });
        return view;
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    private static class ViewHolder {
        public int position;
//        TextView name;
//        TextView description;
        ImageView image;
//        Button downloadButton;

        public ViewHolder(View view) {
//            name = (TextView) view.findViewById(R.id.item_name);
//            description = (TextView) view.findViewById(R.id.item_desc);
            image = (ImageView) view.findViewById(R.id.item_image);
//            downloadButton = (Button) view.findViewById(R.id.item_button);
        }
    }

}
