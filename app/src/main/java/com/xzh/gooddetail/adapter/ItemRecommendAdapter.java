package com.xzh.gooddetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.xzh.gooddetail.R;
import com.xzh.gooddetail.bean.RecommendGoodsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * item页底部的推荐商品适配器
 */
public class ItemRecommendAdapter implements Holder<List<RecommendGoodsBean>> {

    @BindView(R.id.recommend_gridView)
    GridView gridView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, List<RecommendGoodsBean> data) {
        gridView.setAdapter(new ItemRecommendGoodsAdapter(context, data));
    }
}
