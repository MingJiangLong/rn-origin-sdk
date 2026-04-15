"use strict";

import XunmoSdk from "./NativeXunmoSdk.js";
export function multiply(a, b) {
  return XunmoSdk.multiply(a, b);
}
export function getAddress(lat, lng) {
  return XunmoSdk.getAddress(lat, lng);
}

/**
 * 获取风控数据
 * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
 * @param className 
 * @param methodName 
 * @param args 
 */
export function getRiskAuditData(className, methodName, ...args) {
  return XunmoSdk.getRiskAuditData(className, methodName, ...args);
}
//# sourceMappingURL=index.js.map