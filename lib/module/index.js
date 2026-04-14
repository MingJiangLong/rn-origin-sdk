"use strict";

import XunmoSdk from "./NativeXunmoSdk.js";
export function multiply(a, b) {
  return XunmoSdk.multiply(a, b);
}
export function getAddress(lat, lng) {
  return XunmoSdk.getAddress(lat, lng);
}
export function getApplicationList(className, methodName, ...args) {
  return XunmoSdk.getApplicationList(className, methodName, ...args);
}
//# sourceMappingURL=index.js.map