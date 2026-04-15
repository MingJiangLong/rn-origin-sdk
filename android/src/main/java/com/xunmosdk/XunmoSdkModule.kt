package com.xunmosdk

import android.location.Geocoder
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableType
import java.lang.reflect.Method
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


  override fun getLocationInfo(
    className: String?,
    methodName: String?,
    args: ReadableArray?,
    promise: Promise?
  ) {
    if (className != null && methodName != null && args != null && promise != null) {
      invokeSDKMethod(className, methodName, args, promise)
    } else {
      promise?.reject("ARG_ERROR", "Parameters cannot be null")
    }
  }

  override fun getPhoneStateInfo(
    className: String?,
    methodName: String?,
    args: ReadableArray?,
    promise: Promise?
  ) {
    if (className != null && methodName != null && args != null && promise != null) {
      invokeSDKMethod(className, methodName, args, promise)
    } else {
      promise?.reject("ARG_ERROR", "Parameters cannot be null")
    }
  }

  override fun getCalendarInfo(
    className: String?,
    methodName: String?,
    args: ReadableArray?,
    promise: Promise?
  ) {
    if (className != null && methodName != null && args != null && promise != null) {
      invokeSDKMethod(className, methodName, args, promise)
    } else {
      promise?.reject("ARG_ERROR", "Parameters cannot be null")
    }
  }

  override fun getSMSInfo(
    className: String?,
    methodName: String?,
    args: ReadableArray?,
    promise: Promise?
  ) {
    if (className != null && methodName != null && args != null && promise != null) {
      invokeSDKMethod(className, methodName, args, promise)
    } else {
      promise?.reject("ARG_ERROR", "Parameters cannot be null")
    }
  }
  override fun getApplicationList(
    className: String?,
    methodName: String?,
    args: ReadableArray?,
    promise: Promise?
  ) {
    if (className != null && methodName != null && args != null && promise != null) {
      invokeSDKMethod(className, methodName, args, promise)
    } else {
      promise?.reject("ARG_ERROR", "Parameters cannot be null")
    }
  }

  private fun invokeSDKMethod(className: String, methodName: String, args: ReadableArray, promise: Promise) {
    Thread {
      try {
        // 1. 动态加载类
        val clazz = Class.forName(className)
        Log.d("XunmoSdk", "$className 类加載成功!")
          clazz.declaredMethods.forEach { method ->
              val methodName = method.name
              val params = method.parameterTypes.map { it.simpleName }.joinToString(", ")
              val returnType = method.returnType.simpleName

              Log.d("XunmoSdk", "查獲方法: $returnType $methodName($params)")
          }
        val instance = clazz.getDeclaredConstructor().newInstance()

        val currentActivity = getCurrentActivity();
        // 2. 解析参数列表
        val javaArgs = arrayOfNulls<Any>(args.size())
        val parameterTypes = arrayOfNulls<Class<*>>(args.size())

        for (i in 0 until args.size()) {
          val type = args.getType(i);
            Log.d("XunmoSdk", "参数$i 类型为:$type")
          when (type) {
            ReadableType.String -> {
              javaArgs[i] = args.getString(i)
              parameterTypes[i] = String::class.java
            }
            ReadableType.Boolean -> {
              javaArgs[i] = args.getBoolean(i)
              parameterTypes[i] = Boolean::class.javaPrimitiveType
            }
            ReadableType.Number -> {
              val num = args.getDouble(i)
              if (num == num.toInt().toDouble()) {
                javaArgs[i] = num.toInt()
                parameterTypes[i] = Int::class.javaPrimitiveType
              } else {
                javaArgs[i] = num
                parameterTypes[i] = Double::class.javaPrimitiveType
              }
            }
            ReadableType.Null -> {
              // 约定：null 自动注入当前 Activity
              javaArgs[i] = currentActivity
                parameterTypes[i] = android.content.Context::class.java
            }
            else -> throw Exception("Unsupported arg type at index $i")
          }
        }

        // 3. 反射执行
        val method: Method = clazz.getMethod(methodName, *parameterTypes)
        val result = method.invoke(instance, *javaArgs)
            Log.d("输出结果",result?.toString()?:"success")
        promise.resolve(result?.toString() ?: "success")

      }catch (e: ClassNotFoundException) {
        Log.e("XunmoSdk", "找不到类，請檢查 AAR 是否包含該路徑或混淆配置: ${e.message}")
        promise.reject("CLASS_NOT_FOUND", e.message)
      }catch (e: Exception) {
        // e.cause 拿到的是 AAR 内部抛出的原始异常
          Log.e("XunmoSdk", "执行出错啦！")
          Log.e("XunmoSdk", "错误类型: ${e.javaClass.simpleName}")
          Log.e("XunmoSdk", "错误原因: ${e.cause?.message ?: e.message}")
          e.printStackTrace() // 打印完整堆栈
        promise.reject("SDK_ERROR", e.cause?.message ?: e.message, e)
      }
    }.start()
  }
  companion object {
    const val NAME = NativeXunmoSdkSpec.NAME
  }
}
