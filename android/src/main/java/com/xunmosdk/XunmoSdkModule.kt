package com.xunmosdk

import android.location.Geocoder
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import java.util.Locale


class XunmoSdkModule(reactContext: ReactApplicationContext) :
  NativeXunmoSdkSpec(reactContext) {

  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  override fun getAddress(lat: Double, lng: Double, promise: Promise) {
    Thread {
      try {
        val geocoder = Geocoder(reactApplicationContext, Locale.getDefault())
        // 检查 Geocoder 是否可用（某些国产 ROM 阉割了 Google 服务导致不可用）
        if (!Geocoder.isPresent()) {
          promise.reject("GEOCODER_UNAVAILABLE", "设备不支持地理编码服务")
          return@Thread
        }

        val addresses = geocoder.getFromLocation(lat, lng, 1)
        val address = addresses?.getOrNull(0)
        if (address != null) {
          val result = Arguments.createMap().apply {
            // 确保所有字段都有回退值，防止 putString(null) 崩溃
            putString("address", address.getAddressLine(0) ?: "")

            // 城市判断逻辑优化
            val cityName = address.locality ?: address.subAdminArea ?: address.adminArea ?: ""
            putString("city", cityName)

            putString("province", address.adminArea ?: "")
            putBoolean("is_current", true)
            putDouble("longitude", lng)
            putDouble("latitude", lat)
          }
          promise.resolve(result)
        } else {
          promise.reject("NOT_FOUND", "未找到该坐标对应的地址")
        }
      }catch (e: java.io.IOException) {
        promise.reject("NETWORK_ERROR", "地理编码服务连接超时，请检查网络或代理设置")
      }catch (e: Exception) {
        promise.reject("GEOCODER_ERROR", e.localizedMessage ?: "未知错误")
      }
    }.start()
  }

  companion object {
    const val NAME = NativeXunmoSdkSpec.NAME
  }
}
