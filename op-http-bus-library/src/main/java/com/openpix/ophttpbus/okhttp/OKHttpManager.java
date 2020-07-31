package com.openpix.ophttpbus.okhttp;



import com.openpix.ophttpbus.http.substriber.HttpRequester;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by tpx on 2016/12/16.
 */

public class OKHttpManager {
    private static OKHttpManager sInstance;
    OkHttpClient mOkHttpClient;

    private OKHttpManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpRequester.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpRequester.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OKHttpManager getInstance() {
        if (sInstance == null) {
            synchronized (OKHttpManager.class) {
                if (sInstance == null) {
                    sInstance = new OKHttpManager();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOKHttpClient() {
        return this.mOkHttpClient;
    }

    public void cancleRequest() {
        mOkHttpClient.dispatcher().cancelAll();
    }
}
