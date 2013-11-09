//
//  Country.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "Country.h"
#import "City.h"

static NSMutableArray* allCountries = nil;

@implementation Country


- (id) initWithCountryId:(NSInteger)countryId
          andCountryName:(NSString*)countryName
{
    if (self = [super init]) {
        _countryId = countryId;
        _countryName = countryName;
    }
    
    return self;
}

+ (Country*) CountryWithDictionary:(NSDictionary*)dictionary;
{
    Country* country;
    NSInteger countryId = [[dictionary objectForKey:@"CountryId"] integerValue];
    
    if (allCountries == nil) {
        allCountries = [NSMutableArray array];
    }
    
    country = [Country GetCountryWithCountryId:countryId];
    if (country == nil) {
        NSString* countryName = [dictionary objectForKey:@"CountryName"];
        country = [[Country alloc] initWithCountryId:countryId
                                      andCountryName:countryName];
        
        country.cities = [NSMutableArray array];
        
        NSMutableArray* allCities = [City GetAllCities];
        
        for (City* c in allCities) {
            if (c.countryId == country.countryId) {
                [country.cities addObject:c];
            }
        }
        
        [allCountries addObject:country];
    }
    
    return country;
}

+ (NSMutableArray*) GetAllCountries
{
    return allCountries;
}
+ (Country*) GetCountryWithCountryId:(NSInteger)countryId
{
    Country* country;
    for (Country* c in allCountries) {
        if (c.countryId == countryId) {
            country = c;
            break;
        }
    }
    
    return country;
}

@end
