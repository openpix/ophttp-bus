package com.openpix.ophttpbus.http.substriber;

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
public class HttpResponse<T> {
    public static final int STATUS_OK = 0;
    public static final int STATUS_ERROR_NET = 1;
    public static final int STATUS_ERROR_SERVRE = 2;
    public T result;
    public int status;
    public int httpStatusCode;
    public Throwable throwable;
    public long networkMS;
    public Map<String, String> headers;

    public HttpResponse() {

    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public long getNetworkMS() {
        return networkMS;
    }

    public void setNetworkMS(long networkMS) {
        this.networkMS = networkMS;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    @Override
    public String toString() {
        return "HttpResponse{" +
                "result='" + result + '\'' +
                ", status=" + status +
                ", httpStatusCode=" + httpStatusCode +
                ", throwable=" + throwable +
                '}';
    }
}
