package com.xunmosdk

import com.facebook.react.bridge.ReactApplicationContext

class XunmoSdkModule(reactContext: ReactApplicationContext) :
  NativeXunmoSdkSpec(reactContext) {

  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = NativeXunmoSdkSpec.NAME
  }
}
