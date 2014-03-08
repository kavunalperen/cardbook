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
@property NSString* cardbookUserCard;
@property NSString* companyName;
@property NSString* companyImageUrl;
@property NSString* companyCode;
@property NSString* companyDescription;
@property NSString* companyDetail;
@property NSString* companyPassword;
@property NSArray* shoppingPromotionCouponList;
@property NSArray* shoppingPromotionCreditList;
@property NSInteger status;
@property BOOL userWantNotification;
@property UIImage* companyLogo;

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
       andUserWantNotification:(BOOL)userWantNotification;

+ (CBCard*) CBCardWithDictionary:(NSDictionary*)dictionary;

+ (CBCard*) GetCardWithCompanyId:(NSInteger)companyId;
+ (NSMutableArray*) GetAllCards;

@end