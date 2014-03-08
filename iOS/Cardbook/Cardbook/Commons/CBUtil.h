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

// cards detail related macros
#define CARD_DETAIL_THUMBNAIL_BORDER_COLOR [UIColor colorWithRed:232.0/255.0 green:232.0/255.0 blue:232.0/255.0 alpha:1.0]

#define CARD_DETAIL_COMPANY_NAME_FONT [UIFont fontWithName:@"Roboto-Black" size:14.0]
#define CARD_DETAIL_COMPANY_NAME_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:232.0/255.0 alpha:1.0]

#define CARD_DETAIL_COMPANY_POINT_TITLE_FONT [UIFont fontWithName:@"Roboto-Regular" size:10.0]
#define CARD_DETAIL_COMPANY_POINT_TITLE_TEXT_COLOR [UIColor colorWithRed:112.0/255.0 green:112.0/255.0 blue:116.0/255.0 alpha:1.0]

#define CARD_DETAIL_COMPANY_REMAINING_POINT_FONT [UIFont fontWithName:@"Roboto-Black" size:25.0]
#define CARD_DETAIL_COMPANY_REMAINING_POINT_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0]
#define CARD_DETAIL_COMPANY_TOTAL_POINT_FONT [UIFont fontWithName:@"Roboto-Bold" size:10.0]
#define CARD_DETAIL_COMPANY_TOTAL_POINT_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0]

#define CARD_DETAIL_COMPANY_NOTIFICATION_STATUS_FONT [UIFont fontWithName:@"Roboto-Regular" size:9.0]
#define CARD_DETAIL_COMPANY_NOTIFICATION_STATUS_TEXT_COLOR [UIColor colorWithRed:112.0/255.0 green:112.0/255.0 blue:116.0/255.0 alpha:1.0]

#define CARD_DETAIL_CARD_NUMBER_TITLE_FONT [UIFont fontWithName:@"Roboto-Regular" size:12.0]
#define CARD_DETAIL_CARD_NUMBER_TITLE_TEXT_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]
#define CARD_DETAIL_CARD_NUMBER_FONT [UIFont fontWithName:@"Roboto-Medium" size:12.0]
#define CARD_DETAIL_CARD_NUMBER_TEXT_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]

#define CARD_DETAIL_SAVE_BUTTON_NORMAL_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]
#define CARD_DETAIL_SAVE_BUTTON_HIGHLIGHTED_COLOR [UIColor colorWithRed:10.0/255.0 green:170.0/255.0 blue:248.0/255.0 alpha:1.0]
#define CARD_DETAIL_SAVE_BUTTON_FONT [UIFont fontWithName:@"Roboto-Regular" size:12.0]

#define CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_NORMAL_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]
#define CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_HIGHLIGHTED_COLOR [UIColor colorWithRed:10.0/255.0 green:170.0/255.0 blue:248.0/255.0 alpha:1.0]
#define CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_FONT [UIFont fontWithName:@"Roboto-Regular" size:14.0]

#define CARD_DETAIL_SHOPPINGS_LINK_BUTTON_NORMAL_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]
#define CARD_DETAIL_SHOPPINGS_LINK_BUTTON_HIGHLIGHTED_COLOR [UIColor colorWithRed:10.0/255.0 green:170.0/255.0 blue:248.0/255.0 alpha:1.0]
#define CARD_DETAIL_SHOPPINGS_LINK_BUTTON_FONT [UIFont fontWithName:@"Roboto-Regular" size:14.0]

#define CARD_DETAIL_COUPON_CELL_MAIN_LABEL_FONT [UIFont fontWithName:@"Roboto-Medium" size:14.0]
#define CARD_DETAIL_COUPON_CELL_MAIN_LABEL_TEXT_COLOR [UIColor colorWithRed:10.0/255.0 green:170.0/255.0 blue:248.0/255.0 alpha:1.0]
#define CARD_DETAIL_COUPON_CELL_SUBTITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:12.0]
#define CARD_DETAIL_COUPON_CELL_SUBTITLE_LABEL_TEXT_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]
#define CARD_DETAIL_COUPON_CELL_SEPERATOR_COLOR [UIColor colorWithRed:229.0/255.0 green:229.0/255.0 blue:229.0/255.0 alpha:1.0]

// campaigns related macros
#define MY_CAMPAIGNS_CELL_NAME_FONT [UIFont fontWithName:@"Roboto-Medium" size:16.0]
#define MY_CAMPAIGNS_CELL_NAME_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:0.0/255.0 blue:0.0/255.0 alpha:1.0]
#define MY_CAMPAIGNS_CELL_NAME_SELECTED_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_CELL_DETAIL_LABEL_FONT [UIFont fontWithName:@"Roboto-Regular" size:12.0]
#define MY_CAMPAIGNS_CELL_DETAIL_LABEL_TEXT_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_CELL_DATE_LABEL_FONT [UIFont fontWithName:@"Roboto-Regular" size:9.0]
#define MY_CAMPAIGNS_CELL_DATE_LABEL_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0]

// campaign detail screen related macros
#define MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Black" size:17.0]
#define MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_DETAILS_LITTLE_INFO_TITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Medium" size:17.0]
#define MY_CAMPAIGNS_DETAILS_LITTLE_INFO_TITLE_LABEL_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:0.0/255.0 blue:0.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DATE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:14.0]
#define MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DATE_LABEL_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DESCRIPTION_LABEL_FONT [UIFont fontWithName:@"Roboto-Regular" size:11.0]
#define MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DESCRIPTION_LABEL_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_DETAILS_DETAIL_INFO_TITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:10.0]
#define MY_CAMPAIGNS_DETAILS_DETAIL_INFO_TITLE_LABEL_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0]

#define MY_CAMPAIGNS_DETAILS_DETAIL_INFO_DESCRIPTION_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:10.0]
#define MY_CAMPAIGNS_DETAILS_DETAIL_INFO_DESCRIPTION_LABEL_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

// shoppings related macros
#define MY_SHOPPINGS_CELL_NAME_FONT [UIFont fontWithName:@"Roboto-Medium" size:16.0]
#define MY_SHOPPINGS_CELL_NAME_NORMAL_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:0.0/255.0 blue:0.0/255.0 alpha:1.0]
#define MY_SHOPPINGS_CELL_NAME_SELECTED_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_CELL_DATE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:10.0]
#define MY_SHOPPINGS_CELL_DATE_LABEL_TEXT_COLOR [UIColor colorWithRed:142.0/255.0 green:142.0/255.0 blue:147.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_CELL_SEPERATOR_COLOR [UIColor colorWithRed:221.0/255.0 green:221.0/255.0 blue:221.0/255.0 alpha:1.0]

// shopping details related macros
#define MY_SHOPPINGS_DETAILS_HEADER_TITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Black" size:17.0]
#define MY_SHOPPINGS_DETAILS_HEADER_TITLE_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_HEADER_DATE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:12.0]
#define MY_SHOPPINGS_DETAILS_HEADER_DATE_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_HEADER_PRODUCTS_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:14.0]
#define MY_SHOPPINGS_DETAILS_HEADER_PRODUCTS_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_CELL_NAME_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:12.0]
#define MY_SHOPPINGS_DETAILS_CELL_NAME_LABEL_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_CELL_PRICE_LABEL_FONT [UIFont fontWithName:@"Roboto-Regular" size:12.0]
#define MY_SHOPPINGS_DETAILS_CELL_PRICE_LABEL_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_EARNED_POINTS_TITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:12.0]
#define MY_SHOPPINGS_DETAILS_EARNED_POINTS_TITLE_LABEL_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:149.0/255.0 blue:0.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_EARNED_POINTS_OTHER_TITLE_LABELS_FONT [UIFont fontWithName:@"Roboto-Light" size:19.0]
#define MY_SHOPPINGS_DETAILS_EARNED_POINTS_OTHER_TITLE_LABELS_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_EARNED_POINTS_POINTS_LABELS_FONT [UIFont fontWithName:@"Roboto-Bold" size:19.0]
#define MY_SHOPPINGS_DETAILS_EARNED_POINTS_POINTS_LABELS_TEXT_COLOR [UIColor colorWithRed:255.0/255.0 green:149.0/255.0 blue:0.0/255.0 alpha:1.0]


#define MY_SHOPPINGS_DETAILS_SPENT_POINTS_TITLE_LABEL_FONT [UIFont fontWithName:@"Roboto-Light" size:12.0]
#define MY_SHOPPINGS_DETAILS_SPENT_POINTS_TITLE_LABEL_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_SPENT_POINTS_OTHER_TITLE_LABELS_FONT [UIFont fontWithName:@"Roboto-Light" size:19.0]
#define MY_SHOPPINGS_DETAILS_SPENT_POINTS_OTHER_TITLE_LABELS_TEXT_COLOR [UIColor colorWithRed:104.0/255.0 green:104.0/255.0 blue:104.0/255.0 alpha:1.0]

#define MY_SHOPPINGS_DETAILS_SPENT_POINTS_POINTS_LABELS_FONT [UIFont fontWithName:@"Roboto-Bold" size:19.0]
#define MY_SHOPPINGS_DETAILS_SPENT_POINTS_POINTS_LABELS_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

// profile screen related macros

#define PROFILE_IMAGE_BORDER_COLOR [UIColor colorWithRed:231.0/255.0 green:231.0/255.0 blue:231.0/255.0 alpha:1.0]

#define PROFILE_NAME_LABEL_FONT [UIFont fontWithName:@"Roboto-Regular" size:14.0]
#define PROFILE_NAME_LABEL_TEXT_COLOR [UIColor colorWithRed:0.0/255.0 green:122.0/255.0 blue:255.0/255.0 alpha:1.0]

#define PROFILE_INFO_TITLE_LABELS_FONT [UIFont fontWithName:@"Roboto-Regular" size:14.0]
#define PROFILE_INFO_TITLE_LABELS_TEXT_COLOR [UIColor colorWithRed:98.0/255.0 green:98.0/255.0 blue:98.0/255.0 alpha:1.0]

#define PROFILE_INFO_LABELS_FONT [UIFont fontWithName:@"Roboto-Light" size:14.0]
#define PROFILE_INFO_LABELS_TEXT_COLOR [UIColor colorWithRed:98.0/255.0 green:98.0/255.0 blue:98.0/255.0 alpha:1.0]

#import <Foundation/Foundation.h>

@interface CBUtil : NSObject

+ (CBUtil*)sharedInstance;

@property BOOL shouldShowForACompany;
@property NSInteger companyId;

- (UIImage*)UIImageWithUIColor:(UIColor*)color;
- (UIImage*) maskImage:(UIImage *)image withMask:(UIImage *)maskImage;

@end
