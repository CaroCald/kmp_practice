package com.example.kmppractice.core.utils

import com.example.kmppractice.getPlatform

object PlatformUtils {
    fun isAndroid(): Boolean = getPlatform().name.startsWith("Android")
    fun isIOS(): Boolean = getPlatform().name.startsWith("iOS") || getPlatform().name.startsWith("iPhone")
    fun isWeb(): Boolean = getPlatform().name.contains("Web")
} 