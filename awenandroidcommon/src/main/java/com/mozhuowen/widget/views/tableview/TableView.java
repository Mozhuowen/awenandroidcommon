/*
 * Create by Awen 16-1-11 下午5:10
 * 重构net.datafans.android.common.widget.table.TableView
 */

package com.mozhuowen.widget.views.tableview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mozhuowen.R;

import net.datafans.android.common.widget.table.GroupTableViewDataSource;
import net.datafans.android.common.widget.table.TableViewDataSource;
import net.datafans.android.common.widget.table.TableViewStyle;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.common.widget.table.refresh.RefreshTableViewAdapter;
import net.datafans.android.common.widget.table.refresh.RefreshTableViewListener;
import net.datafans.android.common.widget.table.refresh.adapter.BGARefreshTableViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.BGARefreshTableViewAdapter.RefreshType;
import net.datafans.android.common.widget.table.refresh.adapter.DropDownRefreshTableViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.OriginRefreshTableViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.PullDownRefreshTableViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.UltraPullToRefreshRefreshTableViewAdapter;

import java.util.HashMap;
import java.util.Map;

public class TableView<T> implements RefreshTableViewListener {

    private Map<RefreshControlType, RefreshTableViewAdapter> adapterMap = new HashMap<>();

    private RefreshControlType refreshType;

    private TableViewAdapter tableViewAdapter;
    private TableViewDataSource<T> dataSource;
    private TableViewDelegate delegate;

    private Map<Integer, Integer> sectionMap = new HashMap<>();
    private Map<Integer, Integer> rowMap = new HashMap<>();

    private Context context;
    private boolean enableRefresh = false;
    private boolean enableLoadMore = false;
    private boolean enableAutoLoadMore = false;

    private TableViewStyle style;

    private View headerView;
    private View footerView;


    protected TableView(Context context, RefreshControlType type,
                        boolean enableRefresh, boolean enableLoadMore,
                        boolean enableAutoLoadMore, TableViewStyle style, TableViewDataSource<T> dataSource, TableViewDelegate delegate,View headerView) {
        this.context = context;
        this.enableRefresh = enableRefresh;
        this.enableLoadMore = enableLoadMore;
        this.enableAutoLoadMore = enableAutoLoadMore;
        this.style = style;
        refreshType = type;
        this.dataSource = dataSource;
        this.delegate = delegate;
        this.headerView = headerView;
        init();
    }


    private void init() {
        tableViewAdapter = new TableViewAdapter();
        initView();
    }

    private RefreshTableViewAdapter getAdapter(RefreshControlType type) {
        RefreshTableViewAdapter adapter = adapterMap.get(type);
        if (adapter == null) {
            switch (type) {
                case None:
                    adapter = new OriginRefreshTableViewAdapter(context, tableViewAdapter);
                    break;
                case PullDown:
                    adapter = new PullDownRefreshTableViewAdapter(context, tableViewAdapter);
                    break;
                case SwipeRefresh:
                    adapter = new SwipeRefreshTableViewAdapter(context,
                            tableViewAdapter);
                    break;
                case UltraPullToRefresh:
                    adapter = new UltraPullToRefreshRefreshTableViewAdapter(context,
                            tableViewAdapter);
                    break;
                case DropDown:
                    adapter = new DropDownRefreshTableViewAdapter(context, tableViewAdapter);
                    break;
                case BGANormal:
                    adapter = new BGARefreshTableViewAdapter(context, tableViewAdapter,
                            RefreshType.Normal);
                    break;
                case BGAMooc:
                    adapter = new BGARefreshTableViewAdapter(context, tableViewAdapter,
                            RefreshType.MoocStyle);
                    break;
                case BGAStickiness:
                    adapter = new BGARefreshTableViewAdapter(context, tableViewAdapter,
                            RefreshType.Stickiness);
                    break;
                default:
                    break;
            }

            adapter.enableRefresh(enableRefresh);
            adapter.enableLoadMore(enableLoadMore);
            adapter.enableAutoLoadMore(enableAutoLoadMore);

            ListView listView = adapter.getListView();
            if (headerView != null) {
                listView.addHeaderView(headerView);
            }

            if (footerView != null) {
                listView.addHeaderView(footerView);
            }

            adapter.getListView().setAdapter(getTableViewAdapter());

            adapterMap.put(type, adapter);
        }
        return adapter;
    }

    public Context getContext() {
        return context;
    }

    public RefreshTableViewAdapter getAdapter() {
        return getAdapter(refreshType);
    }

    public View getView() {
        return getAdapter().getRootView();
    }

    public BaseAdapter getTableViewAdapter() {
        return tableViewAdapter;
    }

    public void reloadData() {
        tableViewAdapter.notifyDataSetChanged();
//        init();
    }

    private void initView() {

        RefreshTableViewAdapter adapter = getAdapter();
        adapter.setListener(this);

        final ListView listView = adapter.getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (TableView.this.headerView != null || getAdapter().getListView().getHeaderViewsCount() > 0) position = position - 1;
                if (delegate == null) return;
                int section = getSection(position);
                int row = getRow(position);

                if (style == TableViewStyle.GROUP)
                    row = row - 1;
                if (style == TableViewStyle.PLAIN) {
//                    if (TableView.this.headerView != null)
//                        row--;
                }
                Log.d("TableView", "row click: " + row + " position: " + position);
                delegate.onClickRow(section, row);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (TableView.this.headerView != null || getAdapter().getListView().getHeaderViewsCount() > 0) position = position - 1;
                if (delegate == null) return false;
                int section = getSection(position);
                int row = getRow(position);

                if (style == TableViewStyle.GROUP)
                    row = row - 1;
                if (style == TableViewStyle.PLAIN) {
                    if (TableView.this.headerView != null)
                        row--;
                }
//                Log.d("TableView", "row click: " + row);
                delegate.onLongClickRow(section,row);
                return true;
            }
        });
    }

    private class TableViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (dataSource == null) {
                return 0;
            }
            return getTotalCellCount();
        }


        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getItemViewType(int position) {

            int section = getSection(position);
            int row = getRow(position);

            int type = dataSource.getItemViewType(section, row);

            if (style == TableViewStyle.GROUP) {
                if (row == 0) type = 0;
                else if (row == dataSource.getRows(section) + 1) type = 0;
                else type = dataSource.getItemViewType(section, row - 1) + 1;
            }
//            Log.d("CELL", "获取TYPE:" + type + " POSITION: " + position);
            return type;
        }

        @Override
        public int getViewTypeCount() {
            int count = dataSource.getItemViewTypeCount();
            return style == TableViewStyle.GROUP ? count + 2 : count;
        }


        private ViewHolder getViewHolder() {

            View rootView = LayoutInflater.from(context).inflate(R.layout.table_view_header_footer, null);

            View topline = rootView.findViewById(R.id.topline);
            View bottomline = rootView.findViewById(R.id.bottomline);
            TextView titleView = (TextView) rootView.findViewById(R.id.title);

            ViewHolder holder = new ViewHolder();
            holder.rootView = rootView;
            holder.topline = topline;
            holder.bottomline = bottomline;
            holder.titleView = titleView;

            return holder;
        }

        public class ViewHolder {
            View rootView;
            View topline;
            View bottomline;
            TextView titleView;
        }


        @SuppressWarnings("unchecked")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            int section = getSection(position);
            int row = getRow(position);

            int type = getItemViewType(position);
            int typeCount = getViewTypeCount();

            if (style == TableViewStyle.GROUP) {


                TableViewCell<T> cell = null;
                ViewHolder holder = null;

                if (convertView == null) {
                    if (type == 0) {
                        holder = getViewHolder();
                        convertView = holder.rootView;
                        convertView.setTag(holder);

//                        Log.d("CELL", "GROUP: 创建HEADER OR FOOTER");
                    } else {
                        cell =(TableViewCell) dataSource.getTableViewCell(section, row - 1);
                        convertView = cell.getView();
                        convertView.setTag(cell);
//                        Log.d("CELL", "GROUP: 创建CELL");
                    }

                } else {

                    if (type == 0) {
//                        Log.d("CELL", "GROUP: 重用HEADER OR FOOTER");
                        holder = (ViewHolder) convertView.getTag();
                    } else {
//                        Log.d("CELL", "GROUP: 重用CELL");
                        cell = (TableViewCell<T>) convertView.getTag();
                    }
                }


                if (row == 0 && holder != null) {
                    holder.topline.setVisibility(View.GONE);
                    holder.bottomline.setVisibility(View.VISIBLE);

                    if (dataSource instanceof GroupTableViewDataSource) {
                        GroupTableViewDataSource source = (GroupTableViewDataSource) dataSource;

                        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, source.getSectionHeaderHeight(section));
                        holder.rootView.setLayoutParams(layoutParams);

                        holder.titleView.setText(source.getSectionHeaderTitle(section));
                    }

                }

                if (row == dataSource.getRows(section) + 1 && holder != null) {
                    holder.topline.setVisibility(View.VISIBLE);
                    holder.bottomline.setVisibility(View.GONE);
                    if (dataSource instanceof GroupTableViewDataSource) {
                        GroupTableViewDataSource source = (GroupTableViewDataSource) dataSource;
                        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, source.getSectionFooterHeight(section));
                        holder.rootView.setLayoutParams(layoutParams);
                        holder.titleView.setText(source.getSectionFooterTitle(section));
                    }
                }


                if (type != 0) {

                    if (row == dataSource.getRows(section)) {
                        cell.divider.setVisibility(View.GONE);
                    } else {
                        cell.divider.setVisibility(View.VISIBLE);
                    }
                    T t = dataSource.getEntity(section, row - 1);
                    cell.refresh(t);
                }

                return convertView;
            } else {


                TableViewCell<T> cell = null;

                if (convertView == null) {
                    for (int i = 0; i < typeCount; i++) {
                        if (type == i) {
                            cell = (TableViewCell) dataSource.getTableViewCell(section, row);
                            convertView = cell.getView();
                            convertView.setTag(cell);

//                            Log.d("CELL", "PLAIN: 创建CELL  POSTION: " + position + "  TYPE:" + type + "   " + cell);
                        }
                    }

                } else {
                    for (int i = 0; i < typeCount; i++) {
                        if (type == i) {

                            cell = (TableViewCell<T>) convertView.getTag();
//                            Log.d("CELL", "PLAIN: 重用CELL   POSTION:" + position + "  TYPE:" + type + "   " + cell);
                        }
                    }
                }

                T t = dataSource.getEntity(section, row);

                if (cell != null) {
                    cell.divider.setVisibility(View.GONE);

                    cell.refresh(t);
                }

                return convertView;

            }

        }
    }

    private int getTotalCellCount() {
        if (dataSource == null)
            return 0;

        if (dataSource.getItemViewTypeCount() == 1 && sectionMap.get(0) != null)
            return dataSource.getRows(0);

        int section = 1;
        if (dataSource instanceof GroupTableViewDataSource) {
            GroupTableViewDataSource source = (GroupTableViewDataSource) dataSource;
            section = source.getSections();
        }

        if (style == TableViewStyle.PLAIN)
            section = 1;
        int totalCount = 0;
        for (int i = 0; i < section; i++) {
            int row = dataSource.getRows(i);
            if (style == TableViewStyle.GROUP)
                row = row + 2; //加上header和footer两个特殊的row
            for (int j = 0; j < row; j++) {
                sectionMap.put(totalCount + j, i);
                rowMap.put(totalCount + j, j);
            }
            totalCount += row;
        }
        return totalCount;
    }

    private int getSection(int postion) {
        if (dataSource.getItemViewTypeCount() > 1)
            return sectionMap.get(postion);
        else
            return 0;
    }

    private int getRow(int position) {
//        Log.e("TableView","getRow position->"+position);
        if (dataSource.getItemViewTypeCount()>1) {
            return rowMap.get(position);
        }else
            return position;
    }


    public TableViewDataSource<T> getDataSource() {
        return dataSource;
    }

    public void setDataSource(TableViewDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    public TableViewDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(TableViewDelegate delegate) {
        this.delegate = delegate;
    }

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    @Override
    public void onRefresh() {

        if (delegate == null) {
            return;
        }

        delegate.onRefresh();
    }

    @Override
    public void onLoadMore() {
        if (delegate == null) {
            return;
        }

        delegate.onLoadMore();
    }

    public void endRefresh() {
        getAdapter().endRefresh();
    }

    public void endLoadMore() {
        getAdapter().endLoadMore();
    }

    public void loadOver(boolean over) {
        getAdapter().loadOver(over);
    }

    public void hideDivider() {
        getAdapter().getListView().setDivider(null);
    }


    public static class Builder<T> {

        private RefreshControlType refreshType = RefreshControlType.None;
        private TableViewDataSource<T> dataSource;
        private TableViewDelegate delegate;
        private Context context;
        private boolean enableRefresh = false;
        private boolean enableLoadMore = false;
        private boolean enableAutoLoadMore = false;
        private TableViewStyle style;
        private View headerView;

        public Builder<T> setHeaderView(View headerView) {
            this.headerView = headerView;
            return this;
        }


        public Builder<T> setRefreshType(RefreshControlType refreshType) {
            this.refreshType = refreshType;
            return this;
        }

        public Builder<T> setDelegate(TableViewDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public Builder<T> setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder<T> setEnableRefresh(boolean enableRefresh) {
            this.enableRefresh = enableRefresh;
            return this;
        }

        public Builder<T> setEnableLoadMore(boolean enableLoadMore) {
            this.enableLoadMore = enableLoadMore;
            return this;
        }

        public Builder<T> setEnableAutoLoadMore(boolean enableAutoLoadMore) {
            this.enableAutoLoadMore = enableAutoLoadMore;
            return this;
        }

        public Builder<T> setDataSource(TableViewDataSource<T> dataSource) {
            this.dataSource = dataSource;
            return this;
        }


        public void setStyle(TableViewStyle style) {
            this.style = style;
        }

        public RefreshControlType getRefreshType() {
            return refreshType;
        }

        public TableViewDelegate getDelegate() {
            return delegate;
        }

        public Context getContext() {
            return context;
        }

        public boolean isEnableRefresh() {
            return enableRefresh;
        }

        public boolean isEnableLoadMore() {
            return enableLoadMore;
        }

        public boolean isEnableAutoLoadMore() {
            return enableAutoLoadMore;
        }

        public TableViewStyle getStyle() {
            return style;
        }

        public TableViewDataSource<T> getDataSource() {
            return dataSource;
        }

        public View getHeaderView() {return headerView;}

        public TableView<T> build() {
            return new TableView<>(context, refreshType, enableRefresh, enableLoadMore, enableAutoLoadMore, style, dataSource, delegate,headerView);
        }

    }

}
