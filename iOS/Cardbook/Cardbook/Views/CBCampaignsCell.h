//
//  CBCampaignsCell.h
//  Cardbook
//
//  Created by Alperen Kavun on 11.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "APIManager.h"
#import "CBCampaign.h"

#define MY_CAMPAIGNS_CELL_IDENTIFIER @"MyCampaignsCellIdentifier"

@interface CBCampaignsCell : UITableViewCell


@property UIImageView* thumbnail;
@property UILabel* nameLabel;
@property UILabel* detailLabel;
@property UILabel* dateLabel;
@property NSString* loadingImageUrlString;
@property MKNetworkOperation* imageLoadingOperation;
@property CBCampaign* relatedCampaign;

- (void) setImageOfTheCell:(NSString*)imageUrl;
- (void) prepareForReuse;

@end
