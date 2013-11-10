//
//  CBBaseViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CBBaseViewController : UIViewController

@property UIView* myNavigationBar;
@property UILabel* titleLabel;
@property UIButton* titleButton;

- (void) stylizeForMainView;
- (void) stylizeForDetailView;

- (void) setTitleText:(NSString*)text;
- (void) setTitleButtonText:(NSString*)text;

@end
