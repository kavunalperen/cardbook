//
//  CBCardInfo.h
//  Cardbook
//
//  Created by Alperen Kavun on 23.03.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CBCardInfo : NSObject

@property NSInteger companyId;
@property NSString* aboutText;
@property NSString* callCenterPhoneNumber;
@property NSString* infoEmail;
@property NSString* headOfficeAddress;
@property CGFloat headOfficeLongitude;
@property CGFloat headOfficeLatitude;

- (id) initWithCompanyId:(NSInteger)companyId
            andAboutText:(NSString*)aboutText
andCallCenterPhoneNumber:(NSString*)callCenterPhoneNumber
            andInfoEmail:(NSString*)infoEmail
    andHeadOfficeAddress:(NSString*)headOfficeAddress
  andHeadOfficeLongitude:(CGFloat)headOfficeLongitude
   andHeadOfficeLatitude:(CGFloat)headOfficeLatitude;

+ (CBCardInfo*) CBCardInfoWithDictionary:(NSDictionary*)dictionary;

+ (CBCardInfo*) GetCardInfoWithCompanyId:(NSInteger)companyId;
+ (NSMutableArray*) GetAllCardInfos;

@end
