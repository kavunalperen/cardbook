//
//  CBCardDetailsCouponCell.h
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

#define CARD_DETAILS_COUPON_CELL_IDENTIFIER @"CardDetailsCouponCellIdentifier"

@interface CBCardDetailsCouponCell : UITableViewCell

@property UIImageView* thumbnail;
@property UILabel* mainLabel;
@property UILabel* subtitleLabel;
@property UIView* seperatorView;

@end
