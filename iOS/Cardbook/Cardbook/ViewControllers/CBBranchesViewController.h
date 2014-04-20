//
//  CBBranchesViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 16.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBBaseViewController.h"
#import "CBCardDetail.h"
#import <MapKit/MapKit.h>

@interface CBBranchesViewController : CBBaseViewController <UITableViewDataSource, UITableViewDelegate, CLLocationManagerDelegate>

@property UITableView* tableView;
@property CBCardDetail* currentCardDetail;
@property NSMutableArray* allBranches;

@end
