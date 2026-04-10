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
}

export default TurboModuleRegistry.getEnforcing<Spec>('XunmoSdk');
