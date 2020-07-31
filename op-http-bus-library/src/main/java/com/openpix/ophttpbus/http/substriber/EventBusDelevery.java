package com.openpix.ophttpbus.http.substriber;


import com.openpix.ophttpbus.http.HttpBusManager;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * History:
 * <author> <time> <version> <desc>
 */
public class EventBusDelevery implements IDelevery {

    private HttpBusManager busManager;

    public EventBusDelevery(HttpBusManager busManager) {
        this.busManager = busManager;
    }


    @Override
    public void delever(HttpRequester httpRequester, HttpResponse httpResponse, Object deleveryObject) {
        if (deleveryObject != null) {
            busManager.post(deleveryObject);
        }
    }
}
