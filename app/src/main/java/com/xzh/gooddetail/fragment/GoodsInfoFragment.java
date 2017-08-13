package com.xzh.gooddetail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.xzh.gooddetail.R;
import com.xzh.gooddetail.activity.MainActivity;
import com.xzh.gooddetail.adapter.BannerHolderView;
import com.xzh.gooddetail.adapter.ItemRecommendAdapter;
import com.xzh.gooddetail.bean.RecommendGoodsBean;
import com.xzh.gooddetail.controller.GoodsController;
import com.xzh.gooddetail.view.SlideDetailsLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * item页ViewPager里的商品Fragment
 */
public class GoodsInfoFragment extends Fragment implements SlideDetailsLayout.OnSlideDetailsListener {


    @BindView(R.id.banner)
    ConvenientBanner banner;
    @BindView(R.id.tv_current_goods)
    TextView tvCurrentGoods;
    @BindView(R.id.recommend_banner)
    ConvenientBanner recommendBanner;
    @BindView(R.id.goods_detail)
    TextView goodsDetail;
    @BindView(R.id.goods_config)
    TextView goodsConfig;
    @BindView(R.id.tab_cursor)
    View tabCursor;
    @BindView(R.id.frameLayout_content)
    FrameLayout frameLayoutContent;
    @BindView(R.id.slideDetailsLayout)
    SlideDetailsLayout slideDetailsLayout;
    @BindView(R.id.fab_up)
    FloatingActionButton fabUp;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private GoodsConfigFragment goodsConfigFragment;
    private GoodsInfoWebFragment goodsInfoWebFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<TextView> tabTextList=new ArrayList<>();
    private Fragment currFragment;
    private int currIndex=0;
    public MainActivity activity;
    private float fromX;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goods_info, null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    //本数据的代码可以再优化，写到另一个Controller处理
    private void init() {
        initView();
        initTabView();
        initSlide();
        initBannerView();
        initRecommendView();
        initIndicator();
        initDetailView();
    }

    private void initTabView() {
        tabTextList.add(goodsDetail);
        tabTextList.add(goodsConfig);
    }

    private void initSlide() {
        slideDetailsLayout.setOnSlideDetailsListener(this);
    }


    private void initRecommendView() {
        List<RecommendGoodsBean> data = new ArrayList<>();
        data.add(new RecommendGoodsBean("Letv/乐视 LETV体感-超级枪王 乐视TV超级电视产品玩具 体感游戏枪 电玩道具 黑色",
                "http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg", new BigDecimal(599), "799"));
        data.add(new RecommendGoodsBean("IPEGA/艾派格 幽灵之子 无线蓝牙游戏枪 游戏体感枪 苹果安卓智能游戏手柄 标配",
                "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg", new BigDecimal(299), "399"));
        data.add(new RecommendGoodsBean("Letv/乐视 LETV体感-超级枪王 乐视TV超级电视产品玩具 体感游戏枪 电玩道具 黑色",
                "http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg", new BigDecimal(599), "799"));
        data.add(new RecommendGoodsBean("IPEGA/艾派格 幽灵之子 无线蓝牙游戏枪 游戏体感枪 苹果安卓智能游戏手柄 标配",
                "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg", new BigDecimal(299), "399"));
        List<List<RecommendGoodsBean>> handledData = GoodsController.handleRecommendGoods(data);
        //设置如果只有一组数据时不能滑动
        recommendBanner.setManualPageable(handledData.size() == 1 ? false : true);
        recommendBanner.setCanLoop(handledData.size() == 1 ? false : true);
        recommendBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new ItemRecommendAdapter();
            }
        }, handledData);

    }

    //可以自定义个工具类，专门做指示器（让adapter自己去计算）
    private void initIndicator() {
        recommendBanner.setPageIndicator(new int[]{R.mipmap.index_white, R.mipmap.index_red});
        recommendBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        recommendBanner.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        recommendBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    private void initView() {
        fabUp.hide();
        goodsConfigFragment = new GoodsConfigFragment();
        goodsInfoWebFragment = new GoodsInfoWebFragment();
        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);

        currFragment = goodsInfoWebFragment;
        //默认显示商品详情tab
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_content, currFragment).commitAllowingStateLoss();
    }

    private void initBannerView() {
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg");
        imgUrls.add("http://img14.hqbcdn.com/product/77/6c/776c63e6098f05fdc5639adc96d8d6ea.jpg");
        imgUrls.add("http://img13.hqbcdn.com/product/41/ca/41cad5139371e4eb1ce095e5f6224f4d.jpg");
        imgUrls.add("http://img10.hqbcdn.com/product/fa/ab/faab98caca326949b87b770c8080e6cf.jpg");
        imgUrls.add("http://img2.hqbcdn.com/product/6b/b8/6bb86086397a8cd0525c449f29abfaff.jpg");
        //初始化商品图片轮播
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();
            }
        }, imgUrls);
    }

    private void initDetailView() {

    }

    @OnClick(R.id.fab_up)
    public void topUpClick() {
        scrollView.smoothScrollTo(0, 0);
        slideDetailsLayout.smoothClose(true);
    }

    @OnClick(R.id.pull_up_view)
    public void pullClick() {
        slideDetailsLayout.smoothOpen(true);
    }

    @OnClick({R.id.goods_detail, R.id.goods_config})
    public void onClick(View view) {
        switch (view.getId()) {
            //商品详情tab
            case R.id.goods_detail:
                currIndex = 0;
                scrollCursor();
                switchFragment(currFragment, goodsInfoWebFragment);
                currFragment = goodsInfoWebFragment;
                break;
            //商品规格tab
            case R.id.goods_config:
                currIndex = 1;
                scrollCursor();
                switchFragment(currFragment, goodsConfigFragment);
                currFragment = goodsConfigFragment;
                break;
        }
    }


    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, currIndex * tabCursor.getWidth(), 0, 0);
        anim.setFillAfter(true);
        anim.setDuration(50);
        fromX = currIndex * tabCursor.getWidth();
        tabCursor.startAnimation(anim);

        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == currIndex ? getResources().getColor(R.color.text_red) : getResources().getColor(R.color.text_black));
        }
    }

    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (currFragment != toFragment) {
            if (!toFragment.isAdded()) {
                getFragmentManager().beginTransaction().hide(fromFragment).add(R.id.frameLayout_content, toFragment).commitAllowingStateLoss();
            } else {
                getFragmentManager().beginTransaction().hide(fromFragment).show(toFragment).commitAllowingStateLoss();
            }
        }
    }

    //可以继续优化
    @Override
    public void onStatusChanged(SlideDetailsLayout.Status status) {
        //当前为图文详情页
        if (status == SlideDetailsLayout.Status.OPEN) {
            fabUp.show();
            activity.operaTitleBar(true, true, false);
        } else {
            //当前为商品详情页
            fabUp.hide();
            activity.operaTitleBar(false, false, true);
        }
    }


}
