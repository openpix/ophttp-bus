package com.openpix.ophttpbus.test

object MyRequest {
    fun getUserInfo(uid:Int) {
        var params = HashMap<String,String>()
        params.put("targetUid",uid.toString())
        TestApplication.INSTANCE?.testRequest.get(MyApi.GET_USER_INFO,params,null,)
    }
}