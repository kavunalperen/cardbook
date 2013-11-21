//
//  CBCard.h
//  Cardbook
//
//  Created by Alperen Kavun on 13.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CBCard : NSObject

@property NSInteger companyId;
@property NSString* companyName;
@property NSString* companyImageUrl;

- (id) initWithCompanyId:(NSInteger)companyId
          andCompanyName:(NSString*)companyName
      andCompanyImageUrl:(NSString*)companyImageUrl;

+ (CBCard*) CBCardWithDictionary:(NSDictionary*)dictionary;

+ (CBCard*) GetCardWithCompanyId:(NSInteger)companyId;
+ (NSMutableArray*) GetAllCards;

@end
