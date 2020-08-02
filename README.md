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

请求对象具有独立的请求头和设置独立的请求回调.


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

## 4.常见通用解析器

通常情况下，服务器返回的json都是有固定的格式的。

那么就可以创建一个通用的解析器。

如这里假设服务器返回如下格式：

```json
{
state:0,
content:{}
message:'xxx'
}
```

通用解析器

```java
abstract  class MyResultParser : ResultParser<JSONObject>() {
    override fun resolve(result: JSONObject?): Any {
        val state = ProtocolParser.getJsonInt(result, "state")
        if (state != 0 || !result!!.has("content")) {
            if (result!!.has("message")) {
                this.errorObject = ErrorObject.build(state,
                        ProtocolParser.getJsonStr(result, "message"))
            } else {
                this.errorObject = ErrorObject.build(state)
            }
            return this
        }
        var result = ProtocolParser.getJsonObj(result, "content")
        parse(result)
        return this
    }
    abstract fun parse(result:JSONObject)
}
```


## 5.创建请求类

范例:`MyRequest` 

```java
fun getUserInfo(uid:Int) {
        var params = HashMap<String,String>()
        params.put("targetUid",uid.toString())
        TestApplication.INSTANCE?.testRequest?.get(MyApi.GET_USER_INFO,params,null,UserInfo())
    }
```

## 6.回调解析对象

这个需要继承自解析器，如果你创建了通用解析器，那么就集成自通用解析器，如果没有创建，就继承自`ResultParser`

范例：

```java
class UserInfo : MyResultParser(){
    var userString:String? = null
    override fun parse(result: JSONObject) {
        userString = result.getString("67")
    }
}
```

## 7.发送请求与接收

**发送请求** <++>

```java
MyRequest.getUserInfo(67)
```

**注册解注册Eventbus** <++>

如在`onCreate()方法中` <++>

```java
HttpBusManager.getInstance().register(this)
```

`onDestroy()`

```java
override fun onDestroy() {
        super.onDestroy()
        HttpBusManager.getInstance().unregister(this)
    }
```

**接受Http回调方法** 

```java
@Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventUserInfo(userInfo: UserInfo){
        findViewById<TextView>(R.id.tv_info).setText(userInfo.userString)
    }
```

详细请参考这个`Demo` 中的代码。


