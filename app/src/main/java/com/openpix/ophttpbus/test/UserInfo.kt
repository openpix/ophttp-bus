package com.openpix.ophttpbus.test

import org.json.JSONObject

class UserInfo : MyResultParser(){
    var userString:String? = null
    override fun parse(result: JSONObject) {
        userString = result.getString("67")
    }
}