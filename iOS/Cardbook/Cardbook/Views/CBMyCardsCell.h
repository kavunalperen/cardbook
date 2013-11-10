//
//  CBMyCardsCell.h
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

#define MY_CARDS_CELL_IDENTIFIER @"MyCardsCellIdentifier"
#define MY_CARDS_LAST_CELL_IDENTIFIER @"MyCardsLastCellIdentifier"

@interface CBMyCardsCell : UITableViewCell

@property UIImageView* thumbnail;
@property UILabel* nameLabel;

@end
