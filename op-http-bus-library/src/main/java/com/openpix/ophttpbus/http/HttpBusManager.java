package com.openpix.ophttpbus.http;

import com.openpix.ophttpbus.EventManager;
import com.openpix.ophttpbus.http.substriber.HttpSubscriber;

/**
 * Created by tangpengxiang on 2017/6/27.
 */

public class HttpBusManager implements IHttpEventManager {
    private static HttpBusManager Instance;
    private IHttpEventManager manager;

    public HttpBusManager() {

    }

    public static HttpBusManager getInstance() {
        if (Instance == null) {
            synchronized (HttpBusManager.class) {
                if (Instance == null) {
                    Instance = new HttpBusManager();
                    Instance.setManager(EventManager.INSTANCE);
                    Instance.register(new HttpSubscriber());
                }
            }
        }
        return Instance;
    }

    public void post(Object object) {
        if (null != manager) {
            manager.post(object);
        }
    }

    public void register(Object object) {
        if (object == null) return;
        manager.register(object);
    }

    public void unregister(Object object) {
        if (object == null) return;
        manager.unregister(object);
    }

    public void setManager(IHttpEventManager manager) {
        this.manager = manager;
    }
}
