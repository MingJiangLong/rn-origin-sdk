import XunmoSdk from './NativeXunmoSdk';

export function multiply(a: number, b: number): number {
    return XunmoSdk.multiply(a, b);
}

export function getAddress(lat: number, lng: number): Promise<{
    province?: string
    city?: string
    address?: string
    longitude: number
    latitude: number
    is_current: boolean
}> {

    return XunmoSdk.getAddress(lat, lng);
}

/**
 * 获取风控数据
 * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
 * @param className 
 * @param methodName 
 * @param args 
 */
export function getRiskAuditData(className: string, methodName: string, ...args: any[]): Promise<string> {
    return XunmoSdk.getRiskAuditData(className, methodName, ...args);
}
