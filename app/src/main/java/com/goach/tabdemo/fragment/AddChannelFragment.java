package com.goach.tabdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.goach.tabdemo.R;
import com.goach.tabdemo.adapter.ChannelAdapter;
import com.goach.tabdemo.adapter.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 钟光新 on 2016/9/10 0010.
 */
public class AddChannelFragment extends DialogFragment implements View.OnClickListener{
    private View mContainerView;
    private RecyclerView mRecyclerView;
    private ChannelAdapter mRecyclerAdapter;
    private List<String> mDataList;
    private ItemTouchHelper mItemTouchHelper;
    private ImageView mCloseIv;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainerView = inflater.inflate(R.layout.fragment_channel_layout,container,false);
        return mContainerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) mContainerView.findViewById(R.id.id_channel_recycler_view);
        mCloseIv = (ImageView)mContainerView.findViewById(R.id.id_channel_close_iv);
        GridLayoutManager gridLayout = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(gridLayout);
        mRecyclerView.setHasFixedSize(true);
        mDataList = new ArrayList<>();
        for (int i = 0; i <2; i++) {
            mDataList.add("频道"+i);
        }
        mRecyclerAdapter = new ChannelAdapter(getContext(),mDataList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRecyclerAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mCloseIv.setOnClickListener(this);
    }
    public List<String> getChannelDataList(){
        return mRecyclerAdapter.getDataList();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_channel_close_iv:
              //  if(this.context instanceof MainActivity)
               //  ((MainActivity)this.context).notifyTabData();
                dismiss();
                break;
        }
    }
}
