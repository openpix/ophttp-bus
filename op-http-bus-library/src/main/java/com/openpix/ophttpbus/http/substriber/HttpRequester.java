package com.openpix.ophttpbus.http.substriber;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
public class HttpRequester {
    public static final int RESULT_JSON_TYPE = 1;
    public static final int RESULT_STRING_TYPE = 2;
    public static final int DEFAULT_RETRY_NUMBER = 0;
    public static final int DEFAULT_TIMEOUT = 60000;
    private static volatile int syncID = 1000;
    public INetwork.Method mMethod;
    public int requestID;
    public String mUrl;
    public Map<String, String> params;
    public Map<String, String> mHeaders;
    public int resultType;
    public int timeOut;
    public int retryNumber;
    public Object tag;//请求时不需要的信息，但是回调会用到的信息。

    public HttpRequester() {
        mMethod = INetwork.Method.GET;
        init();
    }

    private static synchronized int crateID() {
        if (syncID == Integer.MAX_VALUE) {
            syncID = 0;
        }
        return syncID++;
    }

    public static HttpRequester newInstance() {
        return new HttpRequester();
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getRetryNumber() {
        return retryNumber;
    }

    public void setRetryNumber(int retryNumber) {
        this.retryNumber = retryNumber;
    }

    private void init() {
        requestID = crateID();
        resultType = RESULT_JSON_TYPE;
        timeOut = DEFAULT_TIMEOUT;
        retryNumber = DEFAULT_RETRY_NUMBER;
    }

    public int getRequestID() {
        return requestID;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setMethod(INetwork.Method method) {
        mMethod = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public void addHeader(String key, String value) {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        mHeaders.put(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        mHeaders.putAll(headers);
    }

    @Override
    public String toString() {
        return "HttpRequester{" +
                "params=" + params +
                ", mMethod=" + mMethod +
                ", requestID=" + requestID +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
