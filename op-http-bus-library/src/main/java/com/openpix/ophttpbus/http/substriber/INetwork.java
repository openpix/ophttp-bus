package com.openpix.ophttpbus.http.substriber;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description: 网络层抽象
 * History:
 * <author> <time> <version> <desc>
 */
public interface INetwork {
    public void performRequest(HttpRequester httpRequester, OnResponseListener onResponseListener);

    /**
     * http request method
     */
    public enum Method {
        GET, POST
    }

    public static interface OnResponseListener {
        public void onResponseSuccess(HttpRequester httpRequester, HttpResponse httpResponse);

        public void onResponseError(HttpRequester httpRequester, HttpResponse httpResponse);
    }

}
