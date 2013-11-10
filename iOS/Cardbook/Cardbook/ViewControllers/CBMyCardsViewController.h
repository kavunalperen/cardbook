//
//  CBMyCardsViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CBBaseViewController.h"

@interface CBMyCardsViewController : CBBaseViewController <UITableViewDataSource, UITableViewDelegate>

@property UITableView* tableView;

@end
