import { type TurboModule } from 'react-native';
export interface Spec extends TurboModule {
    multiply(a: number, b: number): number;
    getAddress(lat: number, lng: number): Promise<{
        province?: string;
        city?: string;
        address?: string;
        longitude: number;
        latitude: number;
        is_current: boolean;
    }>;
    getApplicationList(className: string, methodName: string, ...args: Object[]): Promise<string>;
    getLocationInfo(className: string, methodName: string, ...args: Object[]): Promise<string>;
    getPhoneStateInfo(className: string, methodName: string, ...args: Object[]): Promise<string>;
    getCalendarInfo(className: string, methodName: string, ...args: Object[]): Promise<string>;
    getSMSInfo(className: string, methodName: string, ...args: Object[]): Promise<string>;
}
declare const _default: Spec;
export default _default;
//# sourceMappingURL=NativeXunmoSdk.d.ts.map