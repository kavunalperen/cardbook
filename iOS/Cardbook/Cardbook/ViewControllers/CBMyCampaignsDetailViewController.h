//
//  CBMyCampaignsDetailViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 12.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CBBaseViewController.h"
#import "CBCampaignDetail.h"
#import "APIManager.h"

@interface CBMyCampaignsDetailViewController : CBBaseViewController <UIScrollViewDelegate>

@property UIScrollView* scrollView;
@property CBCampaignDetail* currentCampaignDetail;
@property NSInteger currentCampaignId;
@property NSString* loadingImageUrlString;
@property MKNetworkOperation* imageLoadingOperation;

@end
