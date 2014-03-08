//
//  CBShoppingDetail.m
//  Cardbook
//
//  Created by Alperen Kavun on 1.12.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBShoppingDetail.h"

static NSMutableArray* allShoppingDetails = nil;

@implementation CBShoppingDetail

- (id)          initWithShoppingId:(NSInteger)shoppingId
                      andCompanyId:(NSInteger)companyId
                   andShoppingDate:(NSDate*)shoppingDate
            andShoppingProductList:(NSArray*)shoppingProductList
    andUsedShoppingPromotionCoupon:(NSString*)usedShoppingPromotionCoupon
    andUsedShoppingPromotionCredit:(NSString*)usedShoppingPromotionCredit
     andWonShoppingPromotionCoupon:(NSString*)wonShoppingPromotionCoupon
     andWonShoppingPromotionCredit:(NSString*)wonShoppingPromotionCredit
             andIsSharedOnFacebook:(BOOL)isSharedOnFacebook
              andIsSharedOnTwitter:(BOOL)isSharedOnTwitter;
{
    if (self = [super init]) {
        _shoppingId = shoppingId;
        _companyId = companyId;
        _shoppingDate = shoppingDate;
        _shoppingProductList = shoppingProductList;
        _usedShoppingPromotionCoupon = usedShoppingPromotionCoupon;
        _usedShoppingPromotionCredit = usedShoppingPromotionCredit;
        _wonShoppingPromotionCoupon = wonShoppingPromotionCoupon;
        _wonShoppingPromotionCredit = wonShoppingPromotionCredit;
        _isSharedOnFacebook = isSharedOnFacebook;
        _isSharedOnTwitter = isSharedOnTwitter;
    }
    
    return self;
}

+ (CBShoppingDetail*) CBShoppingDetailWithDictionary:(NSDictionary*)dictionary
{
    CBShoppingDetail* shoppingDetail;
    
    if (allShoppingDetails == nil) {
        allShoppingDetails = [NSMutableArray array];
    }
    
    NSInteger shoppingId = [[dictionary objectForKey:@"ShoppingId"] integerValue];
    
    shoppingDetail = [CBShoppingDetail GetShoppingDetailWithShoppingId:shoppingId];
    
    if (shoppingDetail == nil) {
        
        NSInteger companyId = [[dictionary objectForKey:@"CompanyId"] integerValue];
        BOOL isSharedOnFacebook = [[dictionary objectForKey:@"IsThisShoppingSharedOnFacebook"] boolValue];
        BOOL isSharedOnTwitter = [[dictionary objectForKey:@"IsThisShoppingSharedOnTwitter"] boolValue];
        
        NSString* dateStr = [dictionary objectForKey:@"ShoppingDate"];
        if (dateStr == nil || [dateStr isKindOfClass:[NSNull class]]) {
            dateStr = @"";
        }
        dateStr = [dateStr stringByReplacingOccurrencesOfString:@"/Date(" withString:@""];
        dateStr = [dateStr stringByReplacingOccurrencesOfString:@")" withString:@""];
        double timestamp = [dateStr  doubleValue];
        timestamp /= 1000;
        NSDate* date = [NSDate dateWithTimeIntervalSince1970:timestamp];
        
        NSDate* shoppingDate = date;
        
        NSArray* shoppingProductList = [dictionary objectForKey:@"ShoppingProductList"];
        if ([shoppingProductList isKindOfClass:[NSNull class]]) {
            shoppingProductList = nil;
        }
        
//        NSString* usedShoppingPromotionCoupon = [dictionary objectForKey:@"UsedShoppingPromotionCoupon"];
        NSString* usedShoppingPromotionCoupon;
        if(usedShoppingPromotionCoupon == nil || [usedShoppingPromotionCoupon isKindOfClass:[NSNull class]]) {
            usedShoppingPromotionCoupon = @"";
        }
//        NSString* usedShoppingPromotionCredit = [dictionary objectForKey:@"UsedShoppingPromotionCredit"];
        NSString* usedShoppingPromotionCredit;
        if(usedShoppingPromotionCredit == nil || [usedShoppingPromotionCredit isKindOfClass:[NSNull class]]) {
            usedShoppingPromotionCredit = @"";
        }
//        NSString* wonShoppingPromotionCoupon = [dictionary objectForKey:@"WonShoppingPromotionCoupon"];
        NSString* wonShoppingPromotionCoupon;
        if(wonShoppingPromotionCoupon == nil || [wonShoppingPromotionCoupon isKindOfClass:[NSNull class]]) {
            wonShoppingPromotionCoupon = @"";
        }
//        NSString* wonShoppingPromotionCredit = [dictionary objectForKey:@"WonShoppingPromotionCredit"];
        NSString* wonShoppingPromotionCredit;
        if(wonShoppingPromotionCredit == nil || [wonShoppingPromotionCredit isKindOfClass:[NSNull class]]) {
            wonShoppingPromotionCredit = @"";
        }
        
        shoppingDetail = [[CBShoppingDetail alloc] initWithShoppingId:shoppingId
                                                         andCompanyId:companyId
                                                      andShoppingDate:shoppingDate
                                               andShoppingProductList:shoppingProductList
                                       andUsedShoppingPromotionCoupon:usedShoppingPromotionCoupon
                                       andUsedShoppingPromotionCredit:usedShoppingPromotionCredit
                                        andWonShoppingPromotionCoupon:wonShoppingPromotionCoupon
                                        andWonShoppingPromotionCredit:wonShoppingPromotionCredit
                                                andIsSharedOnFacebook:isSharedOnFacebook
                                                 andIsSharedOnTwitter:isSharedOnTwitter];
        
        [allShoppingDetails addObject:shoppingDetail];
    }
    
    return shoppingDetail;
}
- (NSString*) getDateStr
{
    if (_shoppingDateStr == nil) {
        NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
        [formatter setLocale:[NSLocale currentLocale]];
        NSArray* monthSymbols = [formatter monthSymbols];
        
        NSCalendar* calendar = [NSCalendar currentCalendar];
        NSDateComponents* components = [calendar components:NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit fromDate:_shoppingDate];
        NSInteger monthInt = [components month];
        NSInteger dayInt = [components day];
        
        _shoppingDateStr = [NSString stringWithFormat:@"%d %@",dayInt,[monthSymbols objectAtIndex:monthInt-1]];
    }
    
    return _shoppingDateStr;
}
+ (CBShoppingDetail*) GetShoppingDetailWithShoppingId:(NSInteger)shoppingId
{
    CBShoppingDetail* shoppingDetail;
    for (CBShoppingDetail* s in allShoppingDetails) {
        if (s.shoppingId == shoppingId) {
            shoppingDetail = s;
        }
    }
    
    return shoppingDetail;
}
+ (NSMutableArray*) GetAllShoppings
{
    return allShoppingDetails;
}
@end