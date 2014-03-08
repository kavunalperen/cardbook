//
//  CBShoppingDetail.h
//  Cardbook
//
//  Created by Alperen Kavun on 1.12.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CBShoppingDetail : NSObject

@property BOOL isSharedOnFacebook;
@property BOOL isSharedOnTwitter;
@property NSInteger companyId;
@property NSDate* shoppingDate;
@property NSInteger shoppingId;
@property NSArray* shoppingProductList;
@property NSString* usedShoppingPromotionCoupon;
@property NSString* usedShoppingPromotionCredit;
@property NSString* wonShoppingPromotionCoupon;
@property NSString* wonShoppingPromotionCredit;
@property NSString* shoppingDateStr;

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

+ (CBShoppingDetail*) CBShoppingDetailWithDictionary:(NSDictionary*)dictionary;

+ (CBShoppingDetail*) GetShoppingDetailWithShoppingId:(NSInteger)shoppingId;
+ (NSMutableArray*) GetAllShoppings;

- (NSString*) getDateStr;
@end
