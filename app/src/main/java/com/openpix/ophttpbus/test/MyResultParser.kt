package com.openpix.ophttpbus.test

import com.openpix.ophttpbus.http.substriber.ErrorObject
import com.openpix.ophttpbus.http.substriber.ResultParser
import com.openpix.ophttpbus.utils.ProtocolParser
import org.json.JSONObject

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