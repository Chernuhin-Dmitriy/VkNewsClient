package com.example.vknewsclient.presentation.main

import android.app.Application
import com.vk.id.VKID
import java.util.Locale

class VkNewsClientApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
        VKID.instance.setLocale(Locale("ru"))
        VKID.logsEnabled = true  // Включаем логи
    }
}