//
//  CBShopping.m
//  Cardbook
//
//  Created by Alperen Kavun on 1.12.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBShopping.h"

static NSMutableArray* allShoppings = nil;

@implementation CBShopping

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

+ (CBShopping*) CBShoppingWithDictionary:(NSDictionary*)dictionary
{
    CBShopping* shopping;
    
    if (allShoppings == nil) {
        allShoppings = [NSMutableArray array];
    }
    
    NSInteger shoppingId = [[dictionary objectForKey:@"ShoppingId"] integerValue];
    
    shopping = [CBShopping GetShoppingWithShoppingId:shoppingId];
    
    if (shopping == nil) {
        
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
        
        NSString* usedShoppingPromotionCoupon = [dictionary objectForKey:@"UsedShoppingPromotionCoupon"];
        if(usedShoppingPromotionCoupon == nil || [usedShoppingPromotionCoupon isKindOfClass:[NSNull class]]) {
            usedShoppingPromotionCoupon = @"";
        }
        NSString* usedShoppingPromotionCredit = [dictionary objectForKey:@"UsedShoppingPromotionCredit"];
        if(usedShoppingPromotionCredit == nil || [usedShoppingPromotionCredit isKindOfClass:[NSNull class]]) {
            usedShoppingPromotionCredit = @"";
        }
        NSString* wonShoppingPromotionCoupon = [dictionary objectForKey:@"WonShoppingPromotionCoupon"];
        if(wonShoppingPromotionCoupon == nil || [wonShoppingPromotionCoupon isKindOfClass:[NSNull class]]) {
            wonShoppingPromotionCoupon = @"";
        }
        NSString* wonShoppingPromotionCredit = [dictionary objectForKey:@"WonShoppingPromotionCredit"];
        if(wonShoppingPromotionCredit == nil || [wonShoppingPromotionCredit isKindOfClass:[NSNull class]]) {
            wonShoppingPromotionCredit = @"";
        }
        
        shopping = [[CBShopping alloc] initWithShoppingId:shoppingId
                                             andCompanyId:companyId
                                          andShoppingDate:shoppingDate
                                   andShoppingProductList:shoppingProductList
                           andUsedShoppingPromotionCoupon:usedShoppingPromotionCoupon
                           andUsedShoppingPromotionCredit:usedShoppingPromotionCredit
                            andWonShoppingPromotionCoupon:wonShoppingPromotionCoupon
                            andWonShoppingPromotionCredit:wonShoppingPromotionCredit
                                    andIsSharedOnFacebook:isSharedOnFacebook
                                     andIsSharedOnTwitter:isSharedOnTwitter];
        
        [allShoppings addObject:shopping];
    }
    
    return shopping;
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
+ (CBShopping*) GetShoppingWithShoppingId:(NSInteger)shoppingId
{
    CBShopping* shopping;
    for (CBShopping* s in allShoppings) {
        if (s.shoppingId == shoppingId) {
            shopping = s;
        }
    }
    
    return shopping;
}
+ (NSMutableArray*) GetAllShoppings
{
    return allShoppings;
}

@end
