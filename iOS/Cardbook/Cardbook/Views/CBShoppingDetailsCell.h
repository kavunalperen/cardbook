//
//  CBShoppingDetailsCell.h
//  Cardbook
//
//  Created by Alperen Kavun on 12.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

#define SHOPPING_DETAILS_CELL_IDENTIFIER @"ShoppingDetailsCellIdentifier"

@interface CBShoppingDetailsCell : UITableViewCell

@property UILabel* nameLabel;
@property UILabel* priceLabel;

@end
