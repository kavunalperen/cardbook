//
//  CBUtil.h
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#define IS_IPHONE_5 (fabs((double)[[UIScreen mainScreen] bounds].size.height - (double) 568) < DBL_EPSILON)
#define SCREEN_SIZE [[UIScreen mainScreen] bounds].size

#define USER_INFOS_FIRST_NAME @"first_name"
#define USER_INFOS_LAST_NAME @"last_name"
#define USER_INFOS_ADDRESS @"address"
#define USER_INFOS_COUNTRY @"country"
#define USER_INFOS_CITY @"city"
#define USER_INFOS_COUNTY @"county"
#define USER_INFOS_EMAIL @"email"

#import <Foundation/Foundation.h>

@interface CBUtil : NSObject

@end
