//
//  CBMyCardsDetailViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBBaseViewController.h"
#import "CBTextField.h"
//#import "CBCard.h"
#import "CBCardDetail.h"

@interface CBMyCardsDetailViewController : CBBaseViewController <UITextFieldDelegate, UITableViewDataSource, UITableViewDelegate>

@property UITableView* tableView;
@property CBTextField* cardNumberField;
@property NSInteger currentCompanyId;
@property CBCardDetail* currentCardDetail;
@property UIImage* currentCompanyLogo;

@end
