package com.openpix.ophttpbus.callback

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description: 请求发送前回调，如果需要在发送前需要给头或者，参数中增加参数，可以在这里处理，如增加签名
 * History:
 * <author> <time> <version> <desc>
 */
interface IRequestPreCallback {
    fun onRequestPre(params:Map<String, String>?, headers:Map<String, String>?)
}