//
//  City.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "City.h"
#import "County.h"

static NSMutableArray* allCities = nil;

@implementation City

- (id) initWithCityId:(NSInteger)cityId
          andCityName:(NSString*)cityName
         andCountryId:(NSInteger)countryId
{
    if (self = [super init]) {
        _cityId = cityId;
        _cityName = cityName;
        _countryId = countryId;
    }
    
    return self;
}

+ (City*) CityWithDictionary:(NSDictionary*)dictionary;
{
    City* city;
    
    NSInteger cityId = [[dictionary objectForKey:@"CityId"] integerValue];
    
    if (allCities == nil) {
        allCities = [NSMutableArray array];
    }
    
    city = [City GetCityWithCityId:cityId];
    if (city == nil) {
        NSString* cityName = [dictionary objectForKey:@"CityName"];
        NSInteger countryId = [[dictionary objectForKey:@"CountryId"] integerValue];
        
        city = [[City alloc] initWithCityId:cityId
                                andCityName:cityName
                               andCountryId:countryId];
        
        city.counties = [NSMutableArray array];
        
        NSMutableArray* allCounties = [County GetAllCounties];
        
        for (County* c in allCounties) {
            if (c.cityId == city.cityId) {
                [city.counties addObject:c];
            }
        }
        
        [allCities addObject:city];
    }
    
    return city;
}

+ (NSMutableArray*) GetAllCities
{
    return allCities;
}
+ (City*) GetCityWithCityId:(NSInteger)cityId
{
    City* city;
    for (City* c in allCities) {
        if (c.cityId == cityId) {
            city = c;
            break;
        }
    }
    
    return city;
}

@end
