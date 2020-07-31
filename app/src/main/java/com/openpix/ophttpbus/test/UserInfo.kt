package com.openpix.ophttpbus.test

import com.openpix.ophttpbus.http.substriber.ErrorObject
import com.openpix.ophttpbus.http.substriber.ResultParser
import com.openpix.ophttpbus.utils.ProtocolParser
import org.json.JSONObject

class UserInfo : ResultParser<JSONObject>() {
    override fun resolve(result: JSONObject?): Any {
        val state = ProtocolParser.getJsonInt(result, "state")
        if (state != 0 || !result!!.has("content")) {
            if (result!!.has("message")) {
                mInnerParser.setErrorObject(
                    ErrorObject.build(
                        state,
                        ProtocolParser.getJsonStr(result, "message")
                    )
                )
            } else {
                mInnerParser.setErrorObject(ErrorObject.build(state))
            }
            return mInnerParser
        }
        result = ProtocolParser.getJsonObj(result, "content")
        mInnerParser.parse(result)
        return mInnerParser
    }

}