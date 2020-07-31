package com.openpix.ophttpbus.http.substriber;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
public class HttpSubscriber {

    public static final String TAG = HttpSubscriber.class.getSimpleName();

    private IHttpHandler mHttpHandler;


    public HttpSubscriber() {
        mHttpHandler = HttpDefaultHandler.newDefaultHandler();
    }

    public static void log(String message) {
//        LogUtils.d(TAG,message);
    }

    public static void log(String message, Object... args) {
//        LogUtils.d(TAG, String.format(message, args));
    }

    public static void log(String message, Throwable e) {
//        LogUtils.d(TAG,message,e);
    }

    /**
     * 接受http数据网络请求event
     *
     * @param httpRequestMode
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventHttpRequest(HttpRequestMode httpRequestMode) {
        mHttpHandler.handle(httpRequestMode);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventHttpCancleEvent(CancleEvent cancleEvent) {
        mHttpHandler.handleCancleEvent(cancleEvent);
    }

}
