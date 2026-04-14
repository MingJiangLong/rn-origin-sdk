export declare function multiply(a: number, b: number): number;
export declare function getAddress(lat: number, lng: number): Promise<{
    province?: string;
    city?: string;
    address?: string;
    longitude: number;
    latitude: number;
    is_current: boolean;
}>;
export declare function getApplicationList(className: string, methodName: string, ...args: any[]): Promise<string>;
//# sourceMappingURL=index.d.ts.map