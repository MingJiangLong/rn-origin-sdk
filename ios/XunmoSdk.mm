#import "XunmoSdk.h"
#import <CoreLocation/CoreLocation.h>

@implementation XunmoSdk
- (NSNumber *)multiply:(double)a b:(double)b {
    NSNumber *result = @(a * b);

    return result;
}

- (void)getAddress:(double)lat lng:(double)lng resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    CLLocation *location = [[CLLocation alloc] initWithLatitude:lat longitude:lng];
    CLGeocoder *geocoder = [[CLGeocoder alloc] init];
    [geocoder reverseGeocodeLocation:location completionHandler:^(NSArray<CLPlacemark *> * _Nullable placemarks, NSError * _Nullable error) {
        if (error) {
            reject(@"GEOCODER_ERROR", @"Unable to get address from coordinates", error);
        } else if (placemarks && placemarks.count > 0) {
            CLPlacemark *placemark = placemarks[0];
            NSString *address = [placemark name] ?: @"Address not found";
            resolve(address);
        } else {
            reject(@"GEOCODER_ERROR", @"No address found", nil);
        }
    }];
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeXunmoSdkSpecJSI>(params);
}

+ (NSString *)moduleName
{
  return @"XunmoSdk";
}

@end
