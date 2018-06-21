package com.goach.tabdemo.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 钟光新 on 2016/9/11 0011.
 */
public abstract class LazyFragment extends Fragment{

    public abstract void initNet();
    public View getTargetView(){
        return getView() ;
    }

}
