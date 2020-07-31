package com.openpix.ophttpbus.http.substriber;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
public interface IHttpHandler {
    public void handle(HttpRequestMode httpRequestMode);

    public void handleCancleEvent(CancleEvent cancleEvent);

}
