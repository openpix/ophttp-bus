package com.openpix.ophttpbus.okhttp;

import android.text.TextUtils;
import android.util.Log;

import com.openpix.ophttpbus.http.substriber.HttpRequester;
import com.openpix.ophttpbus.http.substriber.HttpResponse;
import com.openpix.ophttpbus.http.substriber.INetwork;
import com.openpix.ophttpbus.utils.BasicNameValuePair;
import com.openpix.ophttpbus.utils.UrlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
public class OKHttpNetwork implements INetwork {
    private static final String TAG = "OKHttpNetwork";

    @Override
    public void performRequest(final HttpRequester httpRequester, final OnResponseListener responseListener) {
        final HttpResponse<Object> httpResponse = new HttpResponse<>();
        // 请求参数名值对
        List<BasicNameValuePair> paramList = new ArrayList<>();
        // 取得请求体
        RequestBody requestBody = getRequestBody(httpRequester.getParams(), paramList);
        Log.d(TAG, "performRequest(),url:" + httpRequester.getmUrl());
        // 取得请求建造者
        Request.Builder requestBuilder = getRequestBuilder(httpRequester.mMethod, httpRequester.getmUrl(), requestBody, paramList, httpRequester.getHeaders());
        // 构建请求
        Request request = requestBuilder.build();
        Call call = OKHttpManager.getInstance().getOKHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure()");
                if (call.isCanceled()) {
                    Log.d(TAG, "onFailure(),取消请求");
                    return;
                }
                httpResponse.throwable = e.fillInStackTrace();
                httpResponse.status = HttpResponse.STATUS_ERROR_NET;
                responseListener.onResponseError(httpRequester, httpResponse);
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                Log.d(TAG, "onResponse()");
                if (response.isSuccessful()) {
                    httpResponse.status = HttpResponse.STATUS_OK;
                    String content = response.body().string();
                    httpResponse.setResult(content);
                    responseListener.onResponseSuccess(httpRequester, httpResponse);
                } else { // 失败
                    httpResponse.status = HttpResponse.STATUS_ERROR_SERVRE;
                    httpResponse.httpStatusCode = response.networkResponse().code();
                    Headers headers = response.headers();
                    Set<String> keys = headers.names();
                    Map<String, String> headerMap = new HashMap<String, String>();
                    for (String key : keys) {
                        headerMap.put(key, headers.get(key));
                    }
                    httpResponse.headers = headerMap;
                    httpResponse.networkMS = response.networkResponse().sentRequestAtMillis();
                    responseListener.onResponseError(httpRequester, httpResponse);
                }
            }
        });
    }

    /**
     * 构建请求体
     *
     * @param paramsMap  请求参数map
     * @param paramsList 请求参数变换的名值对list
     * @return
     */
    private RequestBody getRequestBody(Map<String, String> paramsMap, List<BasicNameValuePair> paramsList) {
        //处理params
        FormBody.Builder builder = new FormBody.Builder();

        Set<String> keySet = paramsMap.keySet();
        for (String key : keySet) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            String value = paramsMap.get(key);
            if (TextUtils.isEmpty(value)) {
                value = "";
            }
            builder.add(key, value);
            paramsList.add(new BasicNameValuePair(key, value));
        }
        RequestBody formBody = builder.build();
        return formBody;
    }

    /**
     * 构建请求建造者
     *
     * @param method      请求方式
     * @param url         请求url
     * @param requestBody 请求体
     * @param paramList   请求参数名值对list
     * @param headerMap   请求头map
     * @return Request.Builder
     */
    private Request.Builder getRequestBuilder(Method method
            , String url
            , RequestBody requestBody
            , List<BasicNameValuePair> paramList
            , Map<String, String> headerMap) {
        Request.Builder requestBuilder = new Request.Builder();
        //判断请求方式
        if (method == Method.POST) {
            requestBuilder.url(url);
            requestBuilder.method("POST", requestBody);
        } else {
            requestBuilder.url(UrlUtils.attachHttpGetParams(url, paramList));
            requestBuilder.method("GET", null);
        }
        //处理Header
        Set<String> headerKeySet = headerMap.keySet();
        for (String key : headerKeySet) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            String value = headerMap.get(key);
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            requestBuilder.header(key, value);
        }
        return requestBuilder;
    }
}
