//
//  CBCampaign.m
//  Cardbook
//
//  Created by Alperen Kavun on 23.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBCampaign.h"

static NSMutableArray* allCampaigns = nil;

@implementation CBCampaign

- (id) initWithCampaignId:(NSInteger)campaignId
             andCompanyId:(NSInteger)companyId
             andStartDate:(NSDate*)startDate
               andEndDate:(NSDate*)endDate
        andCompanyIconUrl:(NSString*)companyIconUrl
          andCampaignName:(NSString*)campaignName
{
    if (self = [super init]) {
        _campaignId = campaignId;
        _companyId = companyId;
        _campaignStartDate = startDate;
        _campaignEndDate = endDate;
        _campaignIconUrl = companyIconUrl;
        _campaignName = campaignName;
    }
    
    return self;
}

+ (CBCampaign*) CBCampaignWithDictionary:(NSDictionary*)dictionary
{
    CBCampaign* campaign;
    
    NSInteger campaignId = [[dictionary objectForKey:@"CampaignId"] integerValue];
    
    if (allCampaigns == nil) {
        allCampaigns = [NSMutableArray array];
    }
    
    campaign = [CBCampaign GetCampaignWithCampaignId:campaignId];
    
    if (campaign == nil) {
        
        NSInteger companyId = [[dictionary objectForKey:@"CompanyId"] integerValue];
        
        NSString* startDateStr = [dictionary objectForKey:@"CampaignStartDate"];
        startDateStr = [startDateStr stringByReplacingOccurrencesOfString:@"/Date(" withString:@""];
        startDateStr = [startDateStr stringByReplacingOccurrencesOfString:@")" withString:@""];
        double timestamp = [startDateStr  doubleValue];
        timestamp /= 1000;
        NSDate* date = [NSDate dateWithTimeIntervalSince1970:timestamp];
        
        NSDate* startDate = date;
        
        NSString* endDateStr = [dictionary objectForKey:@"CampaignEndDate"];
        endDateStr = [endDateStr stringByReplacingOccurrencesOfString:@"/Date(" withString:@""];
        endDateStr = [endDateStr stringByReplacingOccurrencesOfString:@")" withString:@""];
        double timestamp2 = [endDateStr  doubleValue];
        timestamp2 /= 1000;
        NSDate* date2 = [NSDate dateWithTimeIntervalSince1970:timestamp2];
        
        NSDate* endDate = date2;
        
        NSString* companyIconUrl = [dictionary objectForKey:@"CampaignIcon"];
        
        NSString* campaignName = [dictionary objectForKey:@"CampaignName"];
        
        campaign = [[CBCampaign alloc] initWithCampaignId:campaignId
                                             andCompanyId:companyId
                                             andStartDate:startDate
                                               andEndDate:endDate
                                        andCompanyIconUrl:companyIconUrl
                                          andCampaignName:campaignName];
        
        [allCampaigns addObject:campaign];
        
    }
    
    return campaign;
}
- (NSString*) getStartDateStr
{
    if (_campaignStartDateStr == nil) {
        NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
        [formatter setLocale:[NSLocale currentLocale]];
        NSArray* monthSymbols = [formatter monthSymbols];
        
        NSCalendar* calendar = [NSCalendar currentCalendar];
        NSDateComponents* components = [calendar components:NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit fromDate:_campaignStartDate];
        NSInteger monthInt = [components month];
        NSInteger dayInt = [components day];
        
        _campaignStartDateStr = [NSString stringWithFormat:@"%d %@",dayInt,[monthSymbols objectAtIndex:monthInt-1]];
    }
    
    return _campaignStartDateStr;
}

- (NSString*) getEndDateStr
{
    if (_campaignEndDateStr == nil) {
        NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
        [formatter setLocale:[NSLocale currentLocale]];
        NSArray* monthSymbols = [formatter monthSymbols];
        
        NSCalendar* calendar = [NSCalendar currentCalendar];
        NSDateComponents* components = [calendar components:NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit fromDate:_campaignEndDate];
        NSInteger monthInt = [components month];
        NSInteger dayInt = [components day];
        
        _campaignEndDateStr = [NSString stringWithFormat:@"%d %@",dayInt,[monthSymbols objectAtIndex:monthInt-1]];
    }
    
    return _campaignEndDateStr;
}

+ (CBCampaign*) GetCampaignWithCampaignId:(NSInteger)campaignId
{
    CBCampaign* campaign;
    for (CBCampaign* c in allCampaigns) {
        if (c.campaignId == campaignId) {
            campaign = c;
            break;
        }
    }
    
    return campaign;
}
+ (NSMutableArray*) GetAllCampaigns
{
    return allCampaigns;
}
+ (NSMutableArray*) GetAllCampaignsForCompany:(NSInteger)companyId
{
    NSMutableArray* campaigns = [NSMutableArray new];
    
    for (CBCampaign* campaign in allCampaigns) {
        if (campaign.companyId == companyId) {
            [campaigns addObject:campaign];
        }
    }
    
    return campaigns;
}
@end
