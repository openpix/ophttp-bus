package com.openpix.ophttpbus.test

import android.app.Application
import com.openpix.ophttpbus.callback.IRequestPreCallback
import com.openpix.ophttpbus.http.BaseRequest
import java.util.HashMap

class TestApplication : Application(){

    companion object {
        var INSTANCE: TestApplication? = null
    }

    var testRequest: BaseRequest? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        if(null == testRequest) {
            testRequest = BaseRequest()

            testRequest?.setRequestPreCallback(object : IRequestPreCallback {
                override fun onRequestPre(params: Map<String, String>?, headers: Map<String, String>?) {
                    // 如果需要在发送前需要给头或者，参数中增加参数，可以在这里处理，如增加签名
                }
            })
            testRequest?.setHttpConfig {
                var headerMap = HashMap<String,String>()
                headerMap["key1"] = "value1"
                headerMap
            }
        }
    }
}