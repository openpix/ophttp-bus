package com.openpix.ophttpbus.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.openpix.logutils.LogUtils
import com.openpix.ophttpbus.http.HttpBusManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HttpBusManager.getInstance().register(this)
        setContentView(R.layout.activity_main)
        MyRequest.getUserInfo(67)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventUserInfo(userInfo: UserInfo){
        findViewById<TextView>(R.id.tv_info).setText(userInfo.userString)
    }

    override fun onDestroy() {
        super.onDestroy()
        HttpBusManager.getInstance().unregister(this)
    }
}