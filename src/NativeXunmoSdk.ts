import { TurboModuleRegistry, type TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  getAddress(lat: number, lng: number): Promise<{
    province?: string
    city?: string
    address?: string
    longitude: number
    latitude: number
    is_current: boolean
  }>;
  /**
   * 提供设备应用列表
   * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
   * @param className 
   * @param methodName 
   * @param args 
   */
  getApplicationList(className: string, methodName: string, ...args: Object[]): Promise<string>

  /**
   * 获取定位信息
   * @param className 
   * @param methodName 
   * @param args 
   */
  getLocationInfo(className: string, methodName: string, ...args: Object[]): Promise<string>

  /**
   * 获取设备信息
   * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
   * @param className 
   * @param methodName 
   * @param args 
   */
  getPhoneStateInfo(className: string, methodName: string, ...args: Object[]): Promise<string>

  /**
   * 获取日历信息
   * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
   * @param className 
   * @param methodName 
   * @param args 
   */
  getCalendarInfo(className: string, methodName: string, ...args: Object[]): Promise<string>

  /**
   * 获取短信信息
   * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
   * @param className 
   * @param methodName 
   * @param args 
   */
  getSMSInfo(className: string, methodName: string, ...args: Object[]): Promise<string>
}

export default TurboModuleRegistry.getEnforcing<Spec>('XunmoSdk');
