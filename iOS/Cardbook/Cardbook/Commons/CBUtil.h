//
//  CBUtil.h
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#define IS_IPHONE_5 (fabs((double)[[UIScreen mainScreen] bounds].size.height - (double) 568) < DBL_EPSILON)
#define SCREEN_SIZE [[UIScreen mainScreen] bounds].size

#define USER_INFOS_FIRST_NAME @"first_name"
#define USER_INFOS_LAST_NAME @"last_name"
#define USER_INFOS_ADDRESS @"address"
#define USER_INFOS_COUNTRY @"country"
#define USER_INFOS_CITY @"city"
#define USER_INFOS_COUNTY @"county"
#define USER_INFOS_EMAIL @"email"

// register view
#define REGISTER_VIEW_INPUT_FONT [UIFont fontWithName:@"Roboto-Regular" size:14.0]
#define REGISTER_VIEW_BUTTON_FONT [UIFont fontWithName:@"Roboto-Bold" size:14.0]
#define REGISTER_VIEW_BUTTON_NORMAL_COLOR [UIColor colorWithRed:122.0/255.0 green:255.0/255.0 blue:255.0/255.0 alpha:1.0]
#define REGISTER_VIEW_BUTTON_HIGHLIGHTED_COLOR [UIColor colorWithRed:255.0/255.0 green:255.0/255.0 blue:255.0/255.0 alpha:1.0]
#define REGISTER_VIEW_BUTTON_TITLE_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]
#define REGISTER_VIEW_PICKER_BACKGROUND_COLOR [UIColor colorWithRed:0.0/255.0 green:0.0/255.0 blue:0.0/255.0 alpha:0.7]

// tabbar related macros
#define TABBAR_TINT_COLOR [UIColor colorWithRed:255.0/255.0 green:255.0/255.0 blue:255.0/255.0 alpha:0.4]
#define TABBAR_PASSIVE_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]
#define TABBAR_ACTIVE_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define TABBAR_NORMAL_FONT [UIFont fontWithName:@"Roboto-Regular" size:11.0]
#define TABBAR_SELECTED_FONT [UIFont fontWithName:@"Roboto-Regular" size:11.0]

// navbar related macros
#define NAVBAR_TITLE_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]
#define NAVBAR_TITLE_FONT [UIFont fontWithName:@"Roboto-Black" size:17.0]
#define NAVBAR_DETAIL_TITLE_TEXT_COLOR [UIColor colorWithRed:98.0/255.0 green:98.0/255.0 blue:102.0/255.0 alpha:1.0]
#define NAVBAR_DETAIL_TITLE_FONT [UIFont fontWithName:@"Roboto-Light" size:17.0]

// my cards related macros
#define MY_CARDS_CELL_NAME_FONT [UIFont fontWithName:@"Roboto-Medium" size:16.0]
#define MY_CARDS_CELL_NAME_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:0.0/255.0 blue:0.0/255.0 alpha:1.0]
#define MY_CARDS_CELL_NAME_SELECTED_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#import <Foundation/Foundation.h>

@interface CBUtil : NSObject

+ (CBUtil*)sharedInstance;

- (UIImage*)UIImageWithUIColor:(UIColor*)color;
- (UIImage*) maskImage:(UIImage *)image withMask:(UIImage *)maskImage;

@end
