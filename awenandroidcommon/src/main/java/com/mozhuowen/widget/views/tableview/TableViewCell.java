package com.mozhuowen.widget.views.tableview;

import android.content.Context;
import android.view.ViewGroup;

public class TableViewCell<T> extends net.datafans.android.common.widget.table.TableViewCell<T>
{


    public TableViewCell(int layout, Context context) {
        super(layout, context);
        container.removeView(divider);
    }

    public ViewGroup getView() {
        return container;
    }
    @Override
    public void refresh(T o) {

    }


}
