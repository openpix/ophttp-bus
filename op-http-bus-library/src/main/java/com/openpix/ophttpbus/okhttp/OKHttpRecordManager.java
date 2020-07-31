package com.openpix.ophttpbus.okhttp;



import com.openpix.ophttpbus.http.substriber.HttpRequester;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by tpx on 2016/12/16.
 */

public class OKHttpRecordManager {
    private static OKHttpRecordManager sInstance;
    OkHttpClient mOkHttpClient;

    private OKHttpRecordManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpRequester.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpRequester.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OKHttpRecordManager getInstance() {
        if (sInstance == null) {
            synchronized (OKHttpRecordManager.class) {
                if (sInstance == null) {
                    sInstance = new OKHttpRecordManager();
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
