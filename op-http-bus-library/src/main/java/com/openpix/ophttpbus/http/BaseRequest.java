package com.openpix.ophttpbus.http;


import android.util.Log;

import com.openpix.ophttpbus.callback.IRequestPreCallback;
import com.openpix.ophttpbus.http.substriber.CancleEvent;
import com.openpix.ophttpbus.http.substriber.HttpRequestMode;
import com.openpix.ophttpbus.http.substriber.HttpRequester;
import com.openpix.ophttpbus.http.substriber.INetwork;
import com.openpix.ophttpbus.http.substriber.ResultParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:29
 * Version: 1.0.0
 * Description: 需要请求的需要单独实例化一个对象，用于同一种类的签名的请求
 * History:
 * <author> <time> <version> <desc>
 */
public class BaseRequest {

    private Object object;

    /**
     * 签名的回调，用于给请求参数内增加sign选项
     */
    private IRequestPreCallback requestPreCallback;
    private HttpConfig mHttpConfig;
    public BaseRequest() {
    }

    /**
     * 设置请求前处理的回调
     * @param requestPreCallback
     */
    public void setRequestPreCallback(IRequestPreCallback requestPreCallback) {
        this.requestPreCallback = requestPreCallback;
    }

    /**
     * 设置通用请求头
     * @param httpConfig
     */
    public void setHttpConfig(HttpConfig httpConfig) {
        mHttpConfig = httpConfig;
    }

    protected  int request(String url, INetwork.Method method, Map<String, String> params, Map<String, String> headers, ResultParser iResultParser, int retryNumber) {
        HttpRequester httpRequester = HttpRequester.newInstance();
        httpRequester.setmUrl(url);
        httpRequester.setMethod(method);
        httpRequester.setHeaders(headers);
        HashMap<String, String> header = (null != mHttpConfig ? mHttpConfig.getHeader() : new HashMap<String, String>());
        httpRequester.addHeaders(header);
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("r", genRandomString());

        if (params != null) {
            if(null != requestPreCallback) {
                requestPreCallback.onRequestPre(params,header);
            } else {
                throw new IllegalStateException("http sign callback is null");
            }
//            SignHelper.addSignWithHeader(params, header);
        }
        httpRequester.setParams(params);
        postEvent(HttpRequestMode.build(httpRequester, iResultParser, retryNumber));
        return httpRequester.getRequestID();
    }

    protected  int request(String url, INetwork.Method method, Map<String, String> params, Map<String, String> headers, ResultParser iResultParser) {
        return request(url, method, params, headers, iResultParser, HttpRequestMode.DEFAULT_RETRY_NUMBER);
    }

    public  int get(String url, Map<String, String> params, Map<String, String> headers, ResultParser iResultParser) {
        return request(url, INetwork.Method.GET, params, headers, iResultParser);
    }

    public  int post(String url, Map<String, String> params, Map<String, String> headers, ResultParser iResultParser) {
        return request(url, INetwork.Method.POST, params, headers, iResultParser);
    }

    private static void postEvent(HttpRequestMode httpRequestMode) {
        HttpBusManager.getInstance().post(httpRequestMode);
    }

    //生成随机字符串
    public static String genRandomString() {
        Random random = new Random(System.currentTimeMillis());
        int rand;

        int len = random.nextInt(10);
        if (len < 4) {
            len = 4;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rand = random.nextInt(26);
            buf.append((char) (rand + 'a'));
        }
        return buf.toString();
    }

    protected int get(String url, Map<String, String> params, Map<String, String> headers, ResultParser iResultParser, int retryNumber) {
        return request(url, INetwork.Method.GET, params, headers, iResultParser, retryNumber);
    }

    protected int post(String url, Map<String, String> params, Map<String, String> headers, ResultParser iResultParser, int retryNumber) {
        return request(url, INetwork.Method.POST, params, headers, iResultParser, retryNumber);
    }

    public void cancleRequest(Object o) {
        if (o != null) {
            CancleEvent cancleEvent = new CancleEvent();
            cancleEvent.tag = o;
            HttpBusManager.getInstance().post(cancleEvent);
        }
    }

    public void cancleRequest() {
        if (object != null) {
            CancleEvent cancleEvent = new CancleEvent();
            cancleEvent.tag = object;
            HttpBusManager.getInstance().post(cancleEvent);
        }
    }
}
