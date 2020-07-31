package com.openpix.ophttpbus.http.substriber;

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description: 结果处理抽象类，http请求响应回来的数据，会通过ResultAnalysis.java这个类，处理完成转换成泛型T类型，
 * 调用本类的resolve()方法,本类的实现子类，拿到处理的结果，再进行进一步处理
 * History:
 * <author> <time> <version> <desc>
 */
public abstract class ResultParser<T extends Object> {

    private ErrorObject mErrorObject;
    private HttpRequester mHttpRequester;
    private Object object;

    public abstract Object resolve(T result) throws Exception;

    public Object getTag() {
        return object;
    }

    public void setTag(Object object) {
        this.object = object;
    }

    public boolean isShowToast() {
        return mErrorObject != null && mErrorObject.getCode() >= 200100 && mErrorObject.getCode() <= 200199;
    }

    public ErrorObject getErrorObject() {
        return this.mErrorObject;
    }

    public void setErrorObject(ErrorObject errorObject) {
        this.mErrorObject = errorObject;
    }

    public int getErrorCodeID() {
        if (mErrorObject == null) {
            return 0;
        }
        return mErrorObject.getErrorID();
    }

    public boolean isSuccess() {
        return mErrorObject == null;
    }


    public void setmHttpRequester(HttpRequester mHttpRequester) {
        this.mHttpRequester = mHttpRequester;
    }

    public int getCode() {
        return mErrorObject != null ? mErrorObject.getCode() : 0;
    }

    //国际化后getMessage原本的中文不能用，只能提到上层来，通过HttpErrorMessageUtils.getMessage(int code)来getMessage
    public String getMessage() {
        return mErrorObject != null ? mErrorObject.getMessage() : "";
    }
}
