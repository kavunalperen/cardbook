//
//  Country.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>
@class City;

@interface Country : NSObject

@property NSInteger countryId;
@property NSString* countryName;
@property NSMutableArray* cities;

- (id) initWithCountryId:(NSInteger)countryId
           andCountryName:(NSString*)countryName;

+ (Country*) CountryWithDictionary:(NSDictionary*)dictionary;

+ (NSMutableArray*) GetAllCountries;
+ (Country*) GetCountryWithCountryId:(NSInteger)countryId;

@end
