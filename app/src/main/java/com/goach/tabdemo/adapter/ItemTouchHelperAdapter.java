package com.goach.tabdemo.adapter;

/**
 * Created by 钟光新 on 2016/9/10 0010.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
