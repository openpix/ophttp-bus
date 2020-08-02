package com.openpix.ophttpbus

import com.openpix.ophttpbus.http.IHttpEventManager
import org.greenrobot.eventbus.EventBus

object EventManager : IHttpEventManager {
    val mEventBus:EventBus
    override fun post(`object`: Any) {
        if (`object` == null) {
            return
        }
        mEventBus.post(`object`)
    }

    override fun register(`object`: Any) {
        if (`object` == null) return
        try {
            if (!mEventBus.isRegistered(`object`)) {
                mEventBus.register(`object`)
            }
        } catch (e: Exception) {
        }
    }

    override fun unregister(`object`: Any) {
        if (`object` == null) return
        try {
            if (mEventBus.isRegistered(`object`)) {
                mEventBus.unregister(`object`)
            }
        } catch (e: Exception) {
        }
    }

    init {
        mEventBus = EventBus.getDefault()
    }
}