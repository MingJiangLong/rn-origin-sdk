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

export function getApplicationList(className: string, methodName: string, ...args: any[]): Promise<string> {
    return XunmoSdk.getApplicationList(className, methodName, ...args);
}
