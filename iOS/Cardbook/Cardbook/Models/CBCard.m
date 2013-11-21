//
//  CBCard.m
//  Cardbook
//
//  Created by Alperen Kavun on 13.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBCard.h"

static NSMutableArray* allCards = nil;

@implementation CBCard

- (id) initWithCompanyId:(NSInteger)companyId
          andCompanyName:(NSString*)companyName
      andCompanyImageUrl:(NSString*)companyImageUrl
{
    if (self = [super init]) {
        _companyId = companyId;
        _companyName = companyName;
        _companyImageUrl = companyImageUrl;
    }
    
    return self;
}

+ (CBCard*) CBCardWithDictionary:(NSDictionary*)dictionary
{
    CBCard* card;
    
    NSInteger companyId = [[dictionary objectForKey:@"CompanyId"] integerValue];
    
    if (allCards == nil) {
        allCards = [NSMutableArray array];
    }
    card = [CBCard GetCardWithCompanyId:companyId];
    
    if (card == nil) {
        NSString* companyName = [dictionary objectForKey:@"CompanyName"];
        NSString* companyImageUrl = [dictionary objectForKey:@"CompanyLogo"];
        
        card = [[CBCard alloc] initWithCompanyId:companyId
                                  andCompanyName:companyName
                              andCompanyImageUrl:companyImageUrl];
        
        [allCards addObject:card];
    }
    
    
    return card;
}

+ (CBCard*) GetCardWithCompanyId:(NSInteger)companyId
{
    CBCard* card;
    for (CBCard* c in allCards) {
        if (c.companyId == companyId) {
            card = c;
            break;
        }
    }
    
    return card;
}
+ (NSMutableArray*) GetAllCards
{
    return allCards;
}

@end
