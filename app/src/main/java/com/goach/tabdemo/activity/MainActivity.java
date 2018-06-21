package com.goach.tabdemo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.goach.tabdemo.R;
import com.goach.tabdemo.fragment.AddChannelFragment;
import com.goach.tabdemo.fragment.LazyFragment;
import com.goach.tabdemo.fragment.OneFragment;
import com.goach.tabdemo.fragment.TwoFragment;
import com.goach.tabdemo.view.ZTabLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ZTabLayout tabLayout;
    private ViewPager mViewPager;
    private MyViewPager myViewPagerAdapter;
    private List<String> mDataList = Arrays.asList("推荐","热点","军事","图片","社会","娱乐","科技","体育","深圳","财经");
    private ImageView mAddChannelEntryIv;
   // private AddChannelFragment mAddChannelFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab);
        initView();
        initData();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_add_channel_entry_iv:
                /*if(!mAddChannelFragment.isAdded()){
                    mAddChannelFragment.show(getSupportFragmentManager(),"addChannel");
                }*/
                break;
        }
    }
    private void initView(){
        tabLayout = (ZTabLayout) findViewById(R.id.id_tab_pager_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_view_Pager);
        mAddChannelEntryIv = (ImageView)findViewById(R.id.id_add_channel_entry_iv);
        mAddChannelEntryIv.setOnClickListener(this);
    }
    private void initData(){
        myViewPagerAdapter = new MyViewPager(getSupportFragmentManager(),mDataList);
        tabLayout.setDataList(mDataList);
        mViewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        //mAddChannelFragment = new AddChannelFragment();

        tabLayout.showMsg(3,100,0);
        tabLayout.showMsg(2,20,0);
    }



    public class MyViewPager extends FragmentStatePagerAdapter {
        private List<String> mDataList;
        private boolean[] mInit ;
        private Map<Integer,LazyFragment> baseFragmentMap = new HashMap<>();
        public MyViewPager(FragmentManager fm, List<String> list) {
            super(fm);
            mDataList = list ;
            mInit = new boolean[mDataList.size()];
        }

        @Override
        public Fragment getItem(int position) {
            LazyFragment fragment = baseFragmentMap.get(position);
            if(fragment==null){
                if(position%2==0)
                    fragment =  OneFragment.newInstance();
                else
                    fragment =  TwoFragment.newInstance(mDataList.get(position));
                baseFragmentMap.put(position,fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (!mInit[position]) {
                LazyFragment lazyFragment = (LazyFragment) object;
                if (lazyFragment.getTargetView() != null) {
                    mInit[position] = true;
                    lazyFragment.initNet();
                }
            }
        }
    }
}
