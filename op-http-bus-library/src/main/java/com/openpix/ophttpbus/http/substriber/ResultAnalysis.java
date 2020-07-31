package com.openpix.ophttpbus.http.substriber;



import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

 /**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description: 这个类主要用来解析Http直接返回结果的
 * History:
 * <author> <time> <version> <desc>
 */
public class ResultAnalysis<T> implements INetwork.OnResponseListener {
    private static final String TAG = "ResultAnalysis";

    private IDelevery mDelevery;
    private ResultParser<T> resultParser;

    public ResultAnalysis(IDelevery mDelevery, ResultParser<T> iResultParser) {
        this.mDelevery = mDelevery;
        this.resultParser = iResultParser;
    }

    @Override
    public void onResponseSuccess(HttpRequester httpRequester, HttpResponse httpResponse) {
        Log.d(TAG, httpRequester.toString() + "==" + httpResponse.toString());
        if (resultParser == null) {
            return;
        }
        try {
            if (httpRequester.resultType == HttpRequester.RESULT_JSON_TYPE) {
                JSONObject object = new JSONObject((String) httpResponse.result);
                resultParser.resolve((T) object);
            } else if (httpRequester.resultType == HttpRequester.RESULT_STRING_TYPE) {
                resultParser.resolve((T) httpResponse.result);
            }
        } catch (JSONException je) {
//            if(LogUtils.ISDEBUG){
//                HttpSubscriber.log("数据解析错误",je);
//            }
            resultParser.setErrorObject(ErrorObject.build(ErrorObject.ERROR_DATA_PARSE));
        } catch (Exception e) {
//            if(LogUtils.ISDEBUG){
//                HttpSubscriber.log("数据解析错误",e);
//            }
            resultParser.setErrorObject(ErrorObject.parseError(ErrorObject.ERROR_DATA_RESOLVE));
        } finally {
            mDelevery.delever(httpRequester, httpResponse, resultParser);
        }

    }

    @Override
    public void onResponseError(HttpRequester httpRequester, HttpResponse httpResponse) {
//        if(LogUtils.ISDEBUG){
//            HttpSubscriber.log(httpRequester.toString()+"=="+httpResponse.toString());
//        }
        if (resultParser == null) {
            return;
        }
        switch (httpResponse.status) {
            case HttpResponse.STATUS_ERROR_NET:
                resultParser.setErrorObject(ErrorObject.parseError(ErrorObject.ERROR_NET_FAIL));
                break;
            case HttpResponse.STATUS_ERROR_SERVRE:
                resultParser.setErrorObject(ErrorObject.parseError(ErrorObject.ERROR_SERVER_ERROR));
                break;
        }
        mDelevery.delever(httpRequester, httpResponse, resultParser);
    }
}
