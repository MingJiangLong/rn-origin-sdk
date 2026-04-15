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
   * 获取风控数据
   * 前置条件需要在RN项目的根目录添加 libs文件夹并将对应aar放置该目录
   * @param className 
   * @param methodName 
   * @param args 
   */
  getRiskAuditData(className: string, methodName: string, ...args: Object[]): Promise<string>
}

export default TurboModuleRegistry.getEnforcing<Spec>('XunmoSdk');
