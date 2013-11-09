//
//  City.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@class County;

@interface City : NSObject

@property NSInteger cityId;
@property NSString* cityName;
@property NSInteger countryId;
@property NSMutableArray* counties;

- (id) initWithCityId:(NSInteger)cityId
          andCityName:(NSString*)cityName
         andCountryId:(NSInteger)countryId;

+ (City*) CityWithDictionary:(NSDictionary*)dictionary;

+ (NSMutableArray*) GetAllCities;
+ (City*) GetCityWithCityId:(NSInteger)cityId;

@end
