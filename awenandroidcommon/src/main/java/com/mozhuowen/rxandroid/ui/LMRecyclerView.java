package com.mozhuowen.rxandroid.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.mozhuowen.rxandroid.adapter.BaseListAdapter;
import com.mozhuowen.rxandroid.adapter.ListLoadType;

import java.util.Arrays;

/**
 *
 * 添加加载更多功能LoadMoreRecyclerView
 * Created by xybcoder on 2016/3/1.
 */
public class LMRecyclerView extends RecyclerView {

    private boolean isScrollingToBottom = true;
    private FloatingActionButton floatingActionButton;
    private LoadMoreListener listener;
    private BaseListAdapter baseListAdapter;

    public LMRecyclerView(Context context) {
        super(context);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void applyFloatingActionButton(FloatingActionButton floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.listener = loadMoreListener;
    }

    public void setBaseListAdapter(BaseListAdapter baseListAdapter) {
        super.setAdapter(baseListAdapter);
        this.baseListAdapter = baseListAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof BaseListAdapter)
            this.baseListAdapter = (BaseListAdapter) adapter;
        super.setAdapter(adapter);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        isScrollingToBottom = dy > 0;
        if (floatingActionButton != null) {
            if (isScrollingToBottom) {
                if (floatingActionButton.isShown())
                    floatingActionButton.hide();
            } else {
                if (!floatingActionButton.isShown())
                    floatingActionButton.show();
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
       /* LinearLayoutManager mLayoutManager = (LinearLayoutManager) getLayoutManager();
        int lastVisibleItemPosition = 0;
        int totalItemCount = 0;
        if (mLayoutManager instanceof LinearLayoutManager) {
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItemPosition == (totalItemCount - 1) && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }
            }

        } else if (mLayoutManager instanceof GridLayoutManager) {
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItemPosition == (totalItemCount - 1) && isScrollingToBottom) {
                    if (listener != null)
                        listener.loadMore();
                }
            }
        }
*/
        if (getLayoutManager() instanceof  LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                if (baseListAdapter.isEnableFooter() && !baseListAdapter.isLoadingMore()) {
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = baseListAdapter.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && baseListAdapter.getLoadType() == ListLoadType.AUTO_LOAD) {
                        baseListAdapter.setLoadingMore(true);
                        baseListAdapter.setLoastLoadMorePosition(lastVisibleItem);
                        if (listener != null)
                            listener.onLoadMore();
                    }
                }
            }
        } else if( getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            if (state == RecyclerView.SCROLL_STATE_IDLE) {
                if (baseListAdapter.isEnableFooter() && !baseListAdapter.isLoadingMore()) {
                    int[] lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPositions(null);
                    Arrays.sort(lastVisibleItem);

                    int totalItemCount = baseListAdapter.getItemCount();
                    if ( lastVisibleItem[1] == (totalItemCount - 1) && baseListAdapter.getLoadType() == ListLoadType.AUTO_LOAD) {
                        baseListAdapter.setLoadingMore(true);
                        baseListAdapter.setLoastLoadMorePosition(lastVisibleItem[1]);
                        if (listener != null)
                            listener.onLoadMore();
                    }
                }
            }
        }
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }

    public static class DividerItemDecoration extends RecyclerView.ItemDecoration{

        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        private int mOrientation;

        public DividerItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {

            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }

        }


        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }
}
