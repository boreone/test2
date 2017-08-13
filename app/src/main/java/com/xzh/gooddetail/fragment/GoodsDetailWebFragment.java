package com.xzh.gooddetail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xzh.gooddetail.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 图文详情webview的Fragment
 */
public class GoodsDetailWebFragment extends Fragment {
    
    @BindView(R.id.webView)
    WebView webView;
    
    private WebSettings webSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail_web, null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        initWebView();
        initView();
    }

    public void initView() {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        webView.setFocusable(false);
        webView.loadUrl(url);
    }

    private void initWebView() {
        webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
