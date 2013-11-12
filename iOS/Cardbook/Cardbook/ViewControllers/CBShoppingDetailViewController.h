//
//  CBShoppingDetailViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 12.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBBaseViewController.h"

@interface CBShoppingDetailViewController : CBBaseViewController <UITableViewDataSource, UITableViewDelegate>

@property UITableView* tableView;

@end
