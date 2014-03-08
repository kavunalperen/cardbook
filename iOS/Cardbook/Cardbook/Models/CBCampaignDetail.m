//
//  CBCampaignDetail.m
//  Cardbook
//
//  Created by Alperen Kavun on 23.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBCampaignDetail.h"

static NSMutableArray* allCampaignDetails = nil;

@implementation CBCampaignDetail

- (id) initWithCampaignId:(NSInteger)campaignId
             andCompanyId:(NSInteger)companyId
             andStartDate:(NSDate*)startDate
               andEndDate:(NSDate*)endDate
     andCampaignBannerUrl:(NSString*)campaignBannerUrl
          andCampaignName:(NSString*)campaignName
         andCampaignRules:(NSString*)campaignRules
{
    if (self = [super init]) {
        _campaignId = campaignId;
        _companyId = companyId;
        _campaignEndDate = endDate;
        _campaignStartDate = startDate;
        _campaignBannerUrl = campaignBannerUrl;
        _campaignName = campaignName;
        _campaignRules = campaignRules;
    }
    
    return self;
}

+ (CBCampaignDetail*) CBCampaignDetailWithDictionary:(NSDictionary*)dictionary
{
    CBCampaignDetail* campaignDetail;
    
    NSInteger campaignId = [[dictionary objectForKey:@"CampaignId"] integerValue];
    
    if (allCampaignDetails == nil) {
        allCampaignDetails = [NSMutableArray array];
    }
    
    campaignDetail = [CBCampaignDetail GetCampaignDetailWithCampaignId:campaignId];
    
    if (campaignDetail == nil) {
        
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
        
        NSString* campaignBannerUrl = [dictionary objectForKey:@"CampaignBanner"];
        
        NSString* campaignName = [dictionary objectForKey:@"CampaignName"];
        
        NSDictionary* campaignDetailList = [dictionary objectForKey:@"CampaignDetailList"];
        
        NSString* campaignRules = @"";
        if (![campaignDetailList isKindOfClass:[NSNull class]] && campaignDetailList != nil) {
            for (NSDictionary* dict in campaignDetailList) {
                NSString* currentText = [dict objectForKey:@"CampaignDetailText"];
                
                if (![currentText isKindOfClass:[NSNull class]] && currentText != nil) {
                    campaignRules = [campaignRules stringByAppendingString:currentText];
                    campaignRules = [campaignRules stringByAppendingString:@"\n"];
                }
            }
        }
        if (campaignRules == nil) {
            campaignRules = @"";
        }
        
        campaignDetail = [[CBCampaignDetail alloc] initWithCampaignId:campaignId
                                                         andCompanyId:companyId
                                                         andStartDate:startDate
                                                           andEndDate:endDate
                                                 andCampaignBannerUrl:campaignBannerUrl
                                                      andCampaignName:campaignName
                                                     andCampaignRules:campaignRules];
        
    }
    
    return campaignDetail;
}

+ (CBCampaignDetail*) GetCampaignDetailWithCampaignId:(NSInteger)campaignId
{
    CBCampaignDetail* campaignDetail;
    for (CBCampaignDetail* c in allCampaignDetails) {
        if (c.campaignId == campaignId) {
            campaignDetail = c;
            break;
        }
    }
    
    return campaignDetail;
}
+ (NSMutableArray*) GetAllCampaignDetails
{
    return allCampaignDetails;
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

@end
