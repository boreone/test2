package com.xzh.gooddetail;

/**
 * Created by zhihong on 2017/1/21.
 */

public class YmtApplication {

    private static YmtApplication mInstance = null;

    public static synchronized YmtApplication getInstance() {
        if (mInstance == null) {
            mInstance = new YmtApplication();
        }
        return mInstance;
    }
}
