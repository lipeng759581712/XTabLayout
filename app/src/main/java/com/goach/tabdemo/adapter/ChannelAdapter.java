package com.goach.tabdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.goach.tabdemo.R;
import com.goach.tabdemo.util.AppUtils;

import java.util.List;

/**
 * Created by 钟光新 on 2016/9/10 0010.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>
        implements ItemTouchHelperAdapter {
    private Context mContext;
    private List<String> mDataList;
    public ChannelAdapter(Context context ,List<String> dataList){
        this.mContext = context ;
        this.mDataList = dataList ;
    }
    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChannelViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_channel_layout_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.mChannelTxtTv.setText(mDataList.get(position));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.mChannelTxtTv.getLayoutParams();
        params.width = (AppUtils.getScreenWidth(mContext)-5*AppUtils.dp2px(5))/4;
        holder.mChannelTxtTv.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.d("zgx","onItemMove==============="+fromPosition);
        if(fromPosition==0)
            return;
        String prev = mDataList.remove(fromPosition);
        mDataList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }
    public List<String> getDataList(){
        return mDataList ;
    }
    public class ChannelViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        private TextView mChannelTxtTv;
        public ChannelViewHolder(View itemView) {
            super(itemView);
            mChannelTxtTv = (TextView) itemView.findViewById(R.id.id_channel_txt_tv);
        }
        @Override
        public void onItemSelected() {
            mChannelTxtTv.setBackgroundResource(R.drawable.chanel_move_bg);
        }
        @Override
        public void onItemClear() {
            mChannelTxtTv.setBackgroundResource(R.drawable.chanel_item_bg);
        }
    }
}
