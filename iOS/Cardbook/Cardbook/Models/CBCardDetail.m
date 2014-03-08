//
//  CBCardDetail.m
//  Cardbook
//
//  Created by Alperen Kavun on 23.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//
#import "CBCardDetail.h"

static NSMutableArray* allCardDetails = nil;

@implementation CBCardDetail

- (id)       initWithCompanyId:(NSInteger)companyId
           andCardbookUserCard:(NSString*)cardbookUserCard
                andCompanyName:(NSString*)companyName
            andCompanyImageUrl:(NSString*)companyImageUrl
                andCompanyCode:(NSString*)companyCode
         andCompanyDescription:(NSString*)companyDescription
              andCompanyDetail:(NSString*)companyDetail
            andCompanyPassword:(NSString*)companyPassword
andShoppingPromotionCouponList:(NSArray*)shoppingPromotionCouponList
andShoppingPromotionCreditList:(NSArray*)shoppingPromotionCreditList
                     andStatus:(NSInteger)status
       andUserWantNotification:(BOOL)userWantNotification
{
    if (self = [super init]) {
        _companyId = companyId;
        _cardbookUserCard = cardbookUserCard;
        _companyName = companyName;
        _companyImageUrl = companyImageUrl;
        _companyCode = companyCode;
        _companyDescription = companyDescription;
        _companyDetail = companyDetail;
        _companyPassword = companyPassword;
        _shoppingPromotionCouponList = shoppingPromotionCouponList;
        _shoppingPromotionCreditList = shoppingPromotionCreditList;
        _status = status;
        _userWantNotification = userWantNotification;
    }
    
    return self;
}

+ (CBCardDetail*) CBCardDetailWithDictionary:(NSDictionary*)dictionary
{
    CBCardDetail* cardDetail;
    
    NSInteger companyId = [[dictionary objectForKey:@"CompanyId"] integerValue];
    
    if (allCardDetails == nil) {
        allCardDetails = [NSMutableArray array];
    }
    cardDetail = [CBCardDetail GetCardDetailWithCompanyId:companyId];
    
    if (cardDetail == nil) {
        NSDictionary* cardbookUserCard = [dictionary objectForKey:@"CardbookUserCard"];
        NSString* cardbookUserCardNumber;
        if (![cardbookUserCard isKindOfClass:[NSNull class]] && cardbookUserCard != nil) {
            cardbookUserCardNumber = [cardbookUserCard objectForKey:@"CardNumber"];
        }
        if ([cardbookUserCardNumber isKindOfClass:[NSNull class]] || cardbookUserCardNumber == nil) {
            cardbookUserCardNumber = @"";
        }
        NSString* companyName = [dictionary objectForKey:@"CompanyName"];
        if (companyName == nil || [companyName isKindOfClass:[NSNull class]]) {
            companyName = @"";
        }
        NSString* companyImageUrl = [dictionary objectForKey:@"CompanyLogo"];
        if (companyImageUrl == nil || [companyImageUrl isKindOfClass:[NSNull class]]) {
            companyImageUrl = @"";
        }
        NSString* companyCode = [dictionary objectForKey:@"CompanyCode"];
        if (companyCode == nil || [companyCode isKindOfClass:[NSNull class]]) {
            companyCode = @"";
        }
        NSString* companyDescription = [dictionary objectForKey:@"CompanyDescription"];
        if (companyDescription == nil || [companyDescription isKindOfClass:[NSNull class]]) {
            companyDescription = @"";
        }
        NSString* companyDetail = [dictionary objectForKey:@"CompanyDetail"];
        if (companyDetail == nil || [companyDetail isKindOfClass:[NSNull class]]) {
            companyDetail = @"";
        }
        NSString* companyPassword = [dictionary objectForKey:@"CompanyPassword"];
        if (companyPassword == nil || [companyPassword isKindOfClass:[NSNull class]]) {
            companyPassword = @"";
        }
        NSArray* shoppingPromotionCouponList = [dictionary objectForKey:@"ShoppingPromotionCouponList"];
        NSArray* shoppingPromotionCreditList = [dictionary objectForKey:@"ShoppingPromotionCreditList"];
        NSInteger status = [[dictionary objectForKey:@"Status"] integerValue];
        BOOL userWantNotification = [[dictionary objectForKey:@"UserWantNotification"] boolValue];
        //        BOOL userWantNotification = YES;
        
        
        cardDetail = [[CBCardDetail alloc] initWithCompanyId:companyId
                                         andCardbookUserCard:cardbookUserCardNumber
                                              andCompanyName:companyName
                                          andCompanyImageUrl:companyImageUrl
                                              andCompanyCode:companyCode
                                       andCompanyDescription:companyDescription
                                            andCompanyDetail:companyDetail
                                          andCompanyPassword:companyPassword
                              andShoppingPromotionCouponList:shoppingPromotionCouponList
                              andShoppingPromotionCreditList:shoppingPromotionCreditList
                                                   andStatus:status
                                     andUserWantNotification:userWantNotification];
        
        [allCardDetails addObject:cardDetail];
    }
    
    
    return cardDetail;
}

+ (CBCardDetail*) GetCardDetailWithCompanyId:(NSInteger)companyId
{
    CBCardDetail* cardDetail;
    for (CBCardDetail* c in allCardDetails) {
        if (c.companyId == companyId) {
            cardDetail = c;
            break;
        }
    }
    
    return cardDetail;
}
+ (NSMutableArray*) GetAllCardDetails
{
    return allCardDetails;
}

@end
