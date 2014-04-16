//
//  CBCardInfo.m
//  Cardbook
//
//  Created by Alperen Kavun on 23.03.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBCardInfo.h"

static NSMutableArray* allCardInfos = nil;

@implementation CBCardInfo

- (id) initWithCompanyId:(NSInteger)companyId
            andAboutText:(NSString*)aboutText
andCallCenterPhoneNumber:(NSString*)callCenterPhoneNumber
            andInfoEmail:(NSString*)infoEmail
    andHeadOfficeAddress:(NSString*)headOfficeAddress
  andHeadOfficeLongitude:(CGFloat)headOfficeLongitude
   andHeadOfficeLatitude:(CGFloat)headOfficeLatitude
{
    if (self = [super init]) {
                    _companyId = companyId;
                    _aboutText = aboutText;
        _callCenterPhoneNumber = callCenterPhoneNumber;
                    _infoEmail = infoEmail;
            _headOfficeAddress = headOfficeAddress;
          _headOfficeLongitude = headOfficeLongitude;
           _headOfficeLatitude = headOfficeLatitude;
    }
    
    return self;
}

+ (CBCardInfo*) CBCardInfoWithDictionary:(NSDictionary*)dictionary
{
    CBCardInfo* cardInfo;
    
    NSInteger companyId = [[dictionary objectForKey:@"CompanyId"] integerValue];
    
    if (allCardInfos == nil) {
        allCardInfos = [NSMutableArray array];
    }
    cardInfo = [CBCardInfo GetCardInfoWithCompanyId:companyId];
    
    if (cardInfo == nil) {
        
        NSString* aboutText = [dictionary objectForKey:@"AboutText"];
        if (aboutText == nil || [aboutText isKindOfClass:[NSNull class]]) {
            aboutText = @"";
        }
        
        NSString* callCenterPhoneNumber = [dictionary objectForKey:@"CallCenterPhoneNumber"];
        if (callCenterPhoneNumber == nil || [callCenterPhoneNumber isKindOfClass:[NSNull class]]) {
            callCenterPhoneNumber = @"";
        }
        
        NSString* infoEmail = [dictionary objectForKey:@"InfoEmail"];
        if (infoEmail == nil || [infoEmail isKindOfClass:[NSNull class]]) {
            infoEmail = @"";
        }
        
        NSString* headOfficeAddress = nil;
        NSDictionary* headOfficeAddressDict = [dictionary objectForKey:@"HeadOfficeAddress"];
        
        if (headOfficeAddressDict != nil && ![headOfficeAddressDict isKindOfClass:[NSNull class]]) {
            
            NSString* addressLine = [headOfficeAddressDict objectForKey:@"AddressLine"];
            if (addressLine != nil && ![addressLine isKindOfClass:[NSNull class]]) {
                headOfficeAddress = addressLine;
            }
            
            NSString* countyName = [headOfficeAddressDict objectForKey:@"CountyName"];
            if (countyName != nil && ![countyName isKindOfClass:[NSNull class]]) {
                headOfficeAddress = [NSString stringWithFormat:@"%@ %@",headOfficeAddress,countyName];
            }
            
            NSString* cityName = [headOfficeAddressDict objectForKey:@"CityName"];
            if (cityName != nil && ![cityName isKindOfClass:[NSNull class]]) {
                headOfficeAddress = [NSString stringWithFormat:@"%@ - %@",headOfficeAddress,cityName];
            }
            
            NSString* countryName = [headOfficeAddressDict objectForKey:@"CountryName"];
            if (countryName != nil && ![countryName isKindOfClass:[NSNull class]]) {
                headOfficeAddress = [NSString stringWithFormat:@"%@ / %@",headOfficeAddress,countryName];
            }
        }
        
        CGFloat headOfficeLongitude = [[dictionary objectForKey:@"HeadOfficeLongitude"] floatValue];
        CGFloat headOfficeLatitude = [[dictionary objectForKey:@"HeadOfficeLatitude"] floatValue];
        
        cardInfo = [[CBCardInfo alloc] initWithCompanyId:companyId
                                            andAboutText:aboutText
                                andCallCenterPhoneNumber:callCenterPhoneNumber
                                            andInfoEmail:infoEmail
                                    andHeadOfficeAddress:headOfficeAddress
                                  andHeadOfficeLongitude:headOfficeLongitude
                                   andHeadOfficeLatitude:headOfficeLatitude];
        
        [allCardInfos addObject:cardInfo];
    }
    
    
    return cardInfo;
}

+ (CBCardInfo*) GetCardInfoWithCompanyId:(NSInteger)companyId
{
    CBCardInfo* cardInfo;
    for (CBCardInfo* c in allCardInfos) {
        if (c.companyId == companyId) {
            cardInfo = c;
            break;
        }
    }
    
    return cardInfo;
}
+ (NSMutableArray*) GetAllCardInfos
{
    return allCardInfos;
}

@end
