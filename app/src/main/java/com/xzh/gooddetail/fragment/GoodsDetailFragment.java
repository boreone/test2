package com.xzh.gooddetail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.xzh.gooddetail.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * item页ViewPager里的详情Fragment
 */
public class GoodsDetailFragment extends Fragment {


    @BindView(R.id.goods_detail)
    TextView goodsDetail;
    @BindView(R.id.goods_config)
    TextView goodsConfig;
    @BindView(R.id.tab_cursor)
    View tabCursor;

    private List<TextView> tabTextList = new ArrayList<>();
    private GoodsConfigFragment goodsConfigFragment;
    private GoodsDetailWebFragment goodsDetailWebFragment;
    private Fragment currFragment;
    private int currIndex;
    private  int fromX=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goods_detail, null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        tabTextList.add(goodsDetail);
        tabTextList.add(goodsConfig);
    }

    private void initView() {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsDetailWebFragment = new GoodsDetailWebFragment();

        currFragment = goodsDetailWebFragment;
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_content, currFragment).commitAllowingStateLoss();
    }

    @OnClick({R.id.goods_detail, R.id.goods_config})
    public void onClick(View view) {
        switch (view.getId()) {
            //商品详情tab
            case R.id.goods_detail:
                switchFragment(currFragment, goodsDetailWebFragment);
                currIndex = 0;
                currFragment = goodsDetailWebFragment;
                scrollCursor();
                break;
            case R.id.goods_config:
                //规格参数tab
                switchFragment(currFragment, goodsConfigFragment);
                currIndex = 1;
                currFragment = goodsConfigFragment;
                scrollCursor();
                break;
        }
    }

    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, currIndex * tabCursor.getWidth(), 0, 0);
        anim.setFillAfter(true);
        anim.setDuration(50);
        fromX = currIndex * tabCursor.getWidth();
        tabCursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == currIndex ? getResources().getColor(R.color.text_red) : getResources().getColor(R.color.text_black));
        }
    }

    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (currFragment != toFragment) {
            if (!toFragment.isAdded()) {
                getChildFragmentManager().beginTransaction().hide(fromFragment).add(R.id.frameLayout_content, toFragment).commitAllowingStateLoss();
            } else {
                getChildFragmentManager().beginTransaction().hide(fromFragment).show(toFragment).commitAllowingStateLoss();
            }
        }
    }
}
