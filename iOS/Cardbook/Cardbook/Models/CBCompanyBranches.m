//
//  CBCompanyBranches.m
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBCompanyBranches.h"

static NSMutableDictionary* allBranches;

@implementation CBCompanyBranches

- (id) initWithCompanyId:(NSInteger)companyId
             andBranchId:(NSInteger)branchId
           andBranchName:(NSString*)branchName
        andBranchAddress:(NSString*)branchAddress
          andBranchPhone:(NSString*)branchPhone
             andLatitude:(NSNumber*)latitude
            andLongitude:(NSNumber*)longitude
{
    if (self = [super init]) {
        _companyId = companyId;
        _branchId = branchId;
        _branchName = branchName;
        _branchAddress = branchAddress;
        _branchPhone = branchPhone;
        _latitude = latitude;
        _longitude = longitude;
    }
    
    return self;
}

+ (CBCompanyBranches*) createCompanyBranchWithDictionary:(NSDictionary*)dictionary
{
    NSNumber* cId = [dictionary objectForKey:@"CompanyId"];
    NSNumber* bId = [dictionary objectForKey:@"LocationId"];
    
    NSInteger companyId = INT32_MIN;
    NSInteger branchId = INT32_MIN;
    
    if (cId != nil && ![cId isKindOfClass:[NSNull class]]) {
        companyId = [cId integerValue];
    }
    if (bId != nil && ![bId isKindOfClass:[NSNull class]]) {
        branchId = [bId integerValue];
    }
    
    CBCompanyBranches* branch = [CBCompanyBranches getBranchWithCompanyId:companyId andBranchId:branchId];
    
    if (branch == nil) {
        
        NSString* branchName = [dictionary objectForKey:@"LocationName"];
        
        if (branchName == nil || [branchName isKindOfClass:[NSNull class]]) {
            branchName = @"";
        }
        
        NSString* branchPhone = [dictionary objectForKey:@"LocationPhone"];
        
        if (branchPhone == nil || [branchPhone isKindOfClass:[NSNull class]]) {
            branchPhone = @"";
        }
        
        NSNumber* latitude = [dictionary objectForKey:@"Latitude"];
        
        if ([latitude isKindOfClass:[NSNull class]]) {
            latitude = nil;
        }
        
        NSNumber* longitude = [dictionary objectForKey:@"Longitude"];
        
        if ([longitude isKindOfClass:[NSNull class]]) {
            longitude = nil;
        }
        
        NSString* branchAddress = nil;
        NSDictionary* branchAddressDict = [dictionary objectForKey:@"LocationAddress"];
        
        if (branchAddressDict != nil && ![branchAddressDict isKindOfClass:[NSNull class]]) {
            
            NSString* addressLine = [branchAddressDict objectForKey:@"AddressLine"];
            if (addressLine != nil && ![addressLine isKindOfClass:[NSNull class]]) {
                branchAddress = addressLine;
            }
            
            NSString* countyName = [branchAddressDict objectForKey:@"CountyName"];
            if (countyName != nil && ![countyName isKindOfClass:[NSNull class]]) {
                branchAddress = [NSString stringWithFormat:@"%@ %@",branchAddress,countyName];
            }
            
            NSString* cityName = [branchAddressDict objectForKey:@"CityName"];
            if (cityName != nil && ![cityName isKindOfClass:[NSNull class]]) {
                branchAddress = [NSString stringWithFormat:@"%@ - %@",branchAddress,cityName];
            }
            
            NSString* countryName = [branchAddressDict objectForKey:@"CountryName"];
            if (countryName != nil && ![countryName isKindOfClass:[NSNull class]]) {
                branchAddress = [NSString stringWithFormat:@"%@ / %@",branchAddress,countryName];
            }
        }
        
        branch = [[CBCompanyBranches alloc] initWithCompanyId:companyId
                                                  andBranchId:branchId
                                                andBranchName:branchName
                                             andBranchAddress:branchAddress
                                               andBranchPhone:branchPhone
                                                  andLatitude:latitude
                                                 andLongitude:longitude];
    }
    
    
    if (allBranches == nil) {
        allBranches = [NSMutableDictionary new];
    }
    
    NSMutableArray* branchesOfACompany = [allBranches objectForKey:[NSString stringWithFormat:@"%d",companyId]];
    
    if (branchesOfACompany == nil) {
        branchesOfACompany = @[branch].mutableCopy;
        [allBranches setObject:branchesOfACompany forKey:[NSString stringWithFormat:@"%d",companyId]];
    } else {
        [branchesOfACompany addObject:branch];
    }
    
    return branch;
}
+ (CBCompanyBranches*) getBranchWithCompanyId:(NSInteger)companyId andBranchId:(NSInteger)branchId
{
    if (allBranches == nil) {
        return nil;
    } else {
        NSMutableArray* branchesOfACompany = [allBranches objectForKey:[NSString stringWithFormat:@"%d",companyId]];
        if (branchesOfACompany == nil) {
            return nil;
        } else {
            CBCompanyBranches* branch = nil;
            for (CBCompanyBranches* b in branchesOfACompany) {
                if (b.branchId == branchId) {
                    branch = b;
                    break;
                }
            }
            return branch;
        }
    }
}
+ (NSMutableArray*) getAllBranchesForCompany:(NSInteger)companyId
{
    if (allBranches == nil) {
        return nil;
    } else {
        return [allBranches objectForKey:[NSString stringWithFormat:@"%d",companyId]];
    }
}

@end
