//
//  CBCampaignViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CBBaseViewController.h"

@interface CBCampaignViewController : CBBaseViewController <UITableViewDataSource, UITableViewDelegate>

@property UITableView* tableView;
//@property BOOL shouldShowForACompany;
//@property NSInteger companyId;

//+ (CBCampaignViewController*) lastInstance;



@end
