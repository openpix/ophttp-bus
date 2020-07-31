package com.openpix.ophttpbus.http.substriber;


import com.openpix.ophttpbus.http.HttpBusManager;
import com.openpix.ophttpbus.okhttp.OKHttpNetwork;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description: http默认处理类
 * History:
 * <author> <time> <version> <desc>
 */
public class HttpDefaultHandler implements IHttpHandler {

    private INetwork mNetwork;

    private IDelevery mDelevery;

    public HttpDefaultHandler(INetwork network, IDelevery delevery) {
        mDelevery = delevery;
        mNetwork = network;
    }

    public static IHttpHandler newDefaultHandler() {
        return new HttpDefaultHandler(new OKHttpNetwork(), new EventBusDelevery(HttpBusManager.getInstance()));
    }

    @Override
    public void handle(final HttpRequestMode httpRequestMode) {
        final ResultAnalysis resultAnalysis = new ResultAnalysis(mDelevery, httpRequestMode.mParser);
        mNetwork.performRequest(httpRequestMode.mHttpRequester, resultAnalysis);
    }

    @Override
    public void handleCancleEvent(CancleEvent cancleEvent) {
    }

}
