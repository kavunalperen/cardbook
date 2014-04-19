//
//  CBCompanyBranches.h
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CBCompanyBranches : NSObject

@property NSInteger companyId;
@property NSInteger branchId;
@property NSString* branchName;
@property NSString* branchAddress;
@property NSString* branchPhone;
@property NSNumber* latitude;
@property NSNumber* longitude;

- (id) initWithCompanyId:(NSInteger)companyId
             andBranchId:(NSInteger)branchId
           andBranchName:(NSString*)branchName
        andBranchAddress:(NSString*)branchAddress
          andBranchPhone:(NSString*)branchPhone
             andLatitude:(NSNumber*)latitude
            andLongitude:(NSNumber*)longitude;

+ (CBCompanyBranches*) createCompanyBranchWithDictionary:(NSDictionary*)dictionary;

+ (NSMutableArray*) getAllBranchesForCompany:(NSInteger)companyId;

@end
