//
//  CBCampaign.h
//  Cardbook
//
//  Created by Alperen Kavun on 23.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CBCampaign : NSObject

@property NSInteger campaignId;
@property NSInteger companyId;
@property NSDate* campaignStartDate;
@property NSDate* campaignEndDate;
@property NSString* campaignIconUrl;
@property UIImage* campaignIcon;
@property NSString* campaignName;
@property NSString* campaignStartDateStr;
@property NSString* campaignEndDateStr;

- (id) initWithCampaignId:(NSInteger)campaignId
             andCompanyId:(NSInteger)companyId
             andStartDate:(NSDate*)startDate
               andEndDate:(NSDate*)endDate
        andCompanyIconUrl:(NSString*)companyIconUrl
          andCampaignName:(NSString*)campaignName;

+ (CBCampaign*) CBCampaignWithDictionary:(NSDictionary*)dictionary;

+ (CBCampaign*) GetCampaignWithCampaignId:(NSInteger)campaignId;
+ (NSMutableArray*) GetAllCampaigns;
+ (NSMutableArray*) GetAllCampaignsForCompany:(NSInteger)companyId;

- (NSString*) getStartDateStr;
- (NSString*) getEndDateStr;

@end
