//
//  CBShoppingsCell.h
//  Cardbook
//
//  Created by Alperen Kavun on 11.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

#define MY_SHOPPINGS_CELL_FIRST_CELL_IDENTIFIER @"MyShoppingsCellFirstCellIdentifier"
#define MY_SHOPPINGS_CELL_MIDDLE_CELL_IDENTIFIER @"MyShoppingsCellMiddleCellIdentifier"
#define MY_SHOPPINGS_CELL_LAST_CELL_IDENTIFIER @"MyShoppingsCellLastCellIdentifier"

@interface CBShoppingsCell : UITableViewCell

@property UILabel* nameLabel;
@property UILabel* dateLabel;
@property UIView* seperatorView;

@end
