//
//  County.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "County.h"

static NSMutableArray* allCounties = nil;

@implementation County

- (id) initWithCountyId:(NSInteger)countyId
          andCountyName:(NSString*)countyName
              andCityId:(NSInteger)cityId
{
    if (self = [super init]) {
        _countyId = countyId;
        _countyName = countyName;
        _cityId = cityId;
    }
    
    return self;
}

+ (County*) CountyWithDictionary:(NSDictionary *)dictionary
{
    County* county;
    NSInteger countyId = [[dictionary objectForKey:@"CountyId"] integerValue];
    if (allCounties == nil) {
        allCounties = [NSMutableArray array];
    }
    county = [County GetCountyWithCountyId:countyId];
    if (county == nil) {
        
        NSString* countyName = [dictionary objectForKey:@"CountyName"];
        NSInteger cityId = [[dictionary objectForKey:@"CityId"] integerValue];
        
        county = [[County alloc] initWithCountyId:countyId
                                    andCountyName:countyName
                                        andCityId:cityId];
        
        [allCounties addObject:county];
    }
    
    return county;
}

+ (NSMutableArray*) GetAllCounties
{
    return allCounties;
}
+ (County*) GetCountyWithCountyId:(NSInteger)countyId
{
    County* county;
    for (County* c in allCounties) {
        if (c.countyId == countyId) {
            county = c;
            break;
        }
    }
    
    return county;
}

@end
