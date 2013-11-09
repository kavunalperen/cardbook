//
//  County.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface County : NSObject

@property NSInteger countyId;
@property NSString* countyName;
@property NSInteger cityId;

- (id) initWithCountyId:(NSInteger)countyId
          andCountyName:(NSString*)countyName
              andCityId:(NSInteger)cityId;

+ (County*) CountyWithDictionary:(NSDictionary*)dictionary;

+ (NSMutableArray*) GetAllCounties;
+ (County*) GetCountyWithCountyId:(NSInteger)countyId;

@end
