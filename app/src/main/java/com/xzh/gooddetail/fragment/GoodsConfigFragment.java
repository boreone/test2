package com.xzh.gooddetail.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xzh.gooddetail.R;
import com.xzh.gooddetail.adapter.GoodsConfigAdapter;
import com.xzh.gooddetail.bean.GoodsConfigBean;
import com.xzh.gooddetail.view.ItemListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图文详情里的规格参数的Fragment
 */
public class GoodsConfigFragment extends Fragment {

    @BindView(R.id.listView)
    ItemListView listView;

    public Activity activity=null;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.activity = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        listView.setFocusable(false);
        List<GoodsConfigBean> data = new ArrayList<>();
        data.add(new GoodsConfigBean("品牌", "华为"));
        data.add(new GoodsConfigBean("型号", "华为-值得拥有"));
        listView.setAdapter(new GoodsConfigAdapter(activity, data));
    }
}
