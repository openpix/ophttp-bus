package com.openpix.ophttpbus.http;

/**
 * Event事件管理
 * Created by tangpengxiang on 2017/6/27.
 */

public interface IHttpEventManager {
    /**
     * 发布事件
     *
     * @param object
     */
    public void post(Object object);

    /**
     * 注册
     *
     * @param object
     */
    public void register(Object object);

    /**
     * 反注册
     *
     * @param object
     */
    public void unregister(Object object);
}