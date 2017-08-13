package com.xzh.gooddetail.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.xzh.gooddetail.R;
import com.xzh.gooddetail.adapter.ItemTitlePagerAdapter;
import com.xzh.gooddetail.fragment.GoodsCommentFragment;
import com.xzh.gooddetail.fragment.GoodsDetailFragment;
import com.xzh.gooddetail.fragment.GoodsInfoFragment;
import com.xzh.gooddetail.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_tabs)
    PagerSlidingTabStrip titleTabs;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment=new GoodsInfoFragment();
    private GoodsDetailFragment goodsDetailFragment=new GoodsDetailFragment();
    private GoodsCommentFragment goodsCommentFragment=new GoodsCommentFragment();
    private String[] titles= new String[]{"商品", "详情", "评价"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentList.add(goodsInfoFragment);
        fragmentList.add(goodsDetailFragment );
        fragmentList.add(goodsCommentFragment);
        viewPager.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, titles));
        viewPager.setOffscreenPageLimit(3);
        titleTabs.setViewPager(viewPager);
    }

    public void operaTitleBar(boolean scroAble,boolean titleVisiable,boolean tanVisiable){
        viewPager.setNoScroll(scroAble);
        title.setVisibility(titleVisiable? View.VISIBLE:View.GONE);
        titleTabs.setVisibility(tanVisiable? View.VISIBLE:View.GONE);
    }
}
