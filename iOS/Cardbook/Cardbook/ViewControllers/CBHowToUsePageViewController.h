//
//  CBHowToUsePageViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 26.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CBHowToUsePageViewController : UIPageViewController

@property UIViewController* presentingVC;

+ (CBHowToUsePageViewController*) create;

- (void) showViewController:(UIViewController*)viewController direction:(UIPageViewControllerNavigationDirection)direction;

//- (void) showRegisterViewController;
//
//- (void) showLoginViewController;
- (void) startGoThrough;

@end
