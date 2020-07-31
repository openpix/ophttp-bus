> ophttp-bus library

# ophttp-bus library

这个库是用来封装http请求的库，使用的是`OKHttp + Eventbus`的组合。

这样组合的好处是随处发送，到处接收。非常的便捷。

# 使用方法

## 1.库引入

在Android项目的`build.gradle`中加入如下代码：

```groovy
    implementation 'com.openpix:ophttp-bus:1.0.0'
```

## 2.权限

在AndroidManifest.xml文件中加入网络权限

```groovy
<uses-permission android:name="android.permission.INTERNET"/>
```

## 3.创建请求对象

建议在`Application`类中进行创建，这里创建一个`testRequest`


```kotlin
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
```


