//
//  CBCampaignDetail.h
//  Cardbook
//
//  Created by Alperen Kavun on 23.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CBCampaignDetail : NSObject

@property NSInteger campaignId;
@property NSInteger companyId;
@property NSDate* campaignStartDate;
@property NSDate* campaignEndDate;
@property NSString* campaignBannerUrl;
@property UIImage* campaignBanner;
@property NSString* campaignName;
@property NSString* campaignStartDateStr;
@property NSString* campaignEndDateStr;
@property NSString* campaignRules;

- (id) initWithCampaignId:(NSInteger)campaignId
             andCompanyId:(NSInteger)companyId
             andStartDate:(NSDate*)startDate
               andEndDate:(NSDate*)endDate
     andCampaignBannerUrl:(NSString*)campaignBannerUrl
          andCampaignName:(NSString*)campaignName
         andCampaignRules:(NSString*)campaignRules;

+ (CBCampaignDetail*) CBCampaignDetailWithDictionary:(NSDictionary*)dictionary;

+ (CBCampaignDetail*) GetCampaignDetailWithCampaignId:(NSInteger)campaignId;
+ (NSMutableArray*) GetAllCampaignDetails;

- (NSString*) getStartDateStr;
- (NSString*) getEndDateStr;

@end
