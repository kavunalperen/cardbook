//
//  CBBranchCell.h
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CBCompanyBranches.h"

#define BRANCH_CELL_NORMAL_CELL_IDENTIFIER @"BranchCellNormalCellIdentifier"
#define BRANCH_CELL_LAST_CELL_IDENTIFIER @"BranchCellLastCellIdentifier"

@interface CBBranchCell : UITableViewCell

@property CBCompanyBranches* relatedBranch;

@property UILabel* nameLabel;
@property UILabel* addressLabel;
@property UILabel* distanceLabel;
@property UIButton* phoneButton;
@property UIView* seperatorView;

@end
