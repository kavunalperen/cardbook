//
//  CBMyCardsDetailViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBMyCardsDetailViewController.h"
#import "CBUtil.h"
#import <QuartzCore/QuartzCore.h>
#import "CBCardDetailsCouponCell.h"
#import "APIManager.h"
#import "CBCampaignViewController.h"
#import "CBInfoViewController.h"
#import "CBCompanyContactViewController.h"

@interface CBMyCardsDetailViewController ()

@end

@implementation CBMyCardsDetailViewController
{
    UIImageView* companyThumbnail;
    UILabel* companyNameLabel;
    UILabel* remainingPointLabel;
    UILabel* totalPointLabel;
    UIImageView* notificationStatusImageView;
    BOOL isNotificationActive;
    UIButton* saveCardNumberButton;
    UIButton* campaignsButton;
    UIButton* shoppingsButton;
    BOOL isLoaded;
}
- (CGRect) headerImageViewFrame
{
    return CGRectMake(15.0, 69.0, 290.0, 220.0);
}
- (CGRect) contentImageViewFrame
{
    if (IS_IPHONE_5) {
        return CGRectMake(15.0, 289.0, 290.0, 145.0);
    } else {
        return CGRectMake(15.0, 289.0, 290.0, 57.0);
    }
}
- (CGRect) footerImageViewFrame
{
    if (IS_IPHONE_5) {
        return CGRectMake(15.0, 434.0, 290.0, 70.0);
    } else {
        return CGRectMake(15.0, 346.0, 290.0, 70.0);
    }
}
- (CGRect) companyThumbnailFrame
{
    return CGRectMake(15.0, 15.0, 135.0, 90.0);
}
- (CGRect) companyNameLabelFrame
{
    return CGRectMake(165.0, 20.0, 110.0, 18.0);
}
- (CGRect) companyPointTitleLabelFrame
{
    return CGRectMake(165.0, 50.0, 110.0, 13.0);
}
- (CGRect) remainingPointLabelFrame
{
    NSString* string = [NSString stringWithFormat:@"%d",_currentCardDetail.usableCredit];
    
    CGSize labelSize = [self text:string
                     sizeWithFont:CARD_DETAIL_COMPANY_REMAINING_POINT_FONT
                constrainedToSize:CGSizeMake(65.0, 21.0)];
    
    labelSize = CGSizeMake(ceilf(labelSize.width), ceilf(labelSize.height));
    
    return CGRectMake(165.0, 67.0, labelSize.width, 21.0);
}
- (CGRect) totalPointLabelFrame
{
    CGRect other = [self remainingPointLabelFrame];
    
    return CGRectMake(other.origin.x+other.size.width+2.0, 78.0, 45.0, 10.0);
}
- (CGRect) notificationStatusLabelFrame
{
    return CGRectMake(165.0, 82.0, 75.0, 17.0);
}
- (CGRect) companyButtonBarFrame
{
    return CGRectMake(15.0, 113.0, 260.0, 22.0);
}
- (CGRect) infoButtonFrame
{
    return CGRectMake(0.0, 0.0, 49.0, 22.0);
}
- (CGRect) seperator1Frame
{
    return CGRectMake(53.0, 0.0, 2.0, 22.0);
}
- (CGRect) shopsButtonFrame
{
    return CGRectMake(59.0, 0.0, 44.0, 22.0);
}
- (CGRect) seperator2Frame
{
    return CGRectMake(107.0, 0.0, 2.0, 22.0);
}
- (CGRect) contactButtonFrame
{
    return CGRectMake(113.0, 0.0, 46.0, 22.0);
}
- (CGRect) seperator3Frame
{
    return CGRectMake(163.0, 0.0, 2.0, 22.0);
}
- (CGRect) notificationStatusImageFrame
{
    return CGRectMake(169.0, 0.0, 90.0, 22.0);
}
- (CGRect) cardNumberTitleLabelFrame
{
    return CGRectMake(15.0, 146.0, 48.0, 22.0);
}
- (CGRect) cardNumberFieldFrame
{
    return CGRectMake(63.0, 146.0, 147.0, 22.0);
}
- (CGRect) saveCardNumberButtonFrame
{
    return CGRectMake(220.0, 146.0, 55.0, 22.0);
}
- (CGRect) campaignsButtonFrame
{
    return CGRectMake(152.0, 15.0, 123.0, 32.0);
}
- (CGRect) shoppingsButtonFrame
{
    return CGRectMake(15.0, 15.0, 123.0, 32.0);
}
- (CGRect) tableViewFrame
{
    CGRect frame = [self contentImageViewFrame];
    return CGRectMake(frame.origin.x+15.0, frame.origin.y, frame.size.width-30.0, frame.size.height);
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void) fillViewsWithCompanyInfos
{
    if (self.currentCompanyLogo) {
        [companyThumbnail setImage:self.currentCompanyLogo];
    }
    
    if (_currentCardDetail.companyName) {
        [companyNameLabel setText:_currentCardDetail.companyName];
    }
    if (_currentCardDetail.userWantNotification == YES) {
        [notificationStatusImageView setImage:[UIImage imageNamed:@"notification_on.png"]];
    } else {
        [notificationStatusImageView setImage:[UIImage imageNamed:@"notification_off.png"]];
    }
    if (_currentCardDetail.cardbookUserCard == nil || [_currentCardDetail.cardbookUserCard isEqualToString:@""]) {
        //        [self.cardNumberField setText:@"0000 0000 0000 0000"];
    } else {
        [self.cardNumberField setText:_currentCardDetail.cardbookUserCard];
    }
    if (_currentCardDetail.totalCredit > INT32_MIN) {
        [totalPointLabel setFrame:[self totalPointLabelFrame]];
        [totalPointLabel setText:[NSString stringWithFormat:@"/ %d",_currentCardDetail.totalCredit]];
    }
    if (_currentCardDetail.usableCredit > INT32_MIN) {
        [remainingPointLabel setFrame:[self remainingPointLabelFrame]];
        [remainingPointLabel setText:[NSString stringWithFormat:@"%d",_currentCardDetail.usableCredit]];
    }
}
- (void) configureViews
{
    isLoaded = YES;
    [self fillViewsWithCompanyInfos];
}
- (void) viewWillAppear:(BOOL)animated
{
    if (self.currentCardDetail == nil) {
        CBCardDetail* detail = [CBCardDetail GetCardDetailWithCompanyId:self.currentCompanyId];
        if (detail) {
            self.currentCardDetail = detail;
            [self configureViews];
        } else {
        
            [[APIManager sharedInstance] getCompanyDetailContentWithCompanyId:self.currentCompanyId
                                                                 onCompletion:^(CBCardDetail* cardDetail) {
                                                                     self.currentCardDetail = cardDetail;
                                                                     [self configureViews];
                                                                     NSLog(@"response here");
                                                                 } onError:^(NSError *error) {
                                                                     NSLog(@"an error occured");
                                                                 }];
        }
    }
}
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    NSLog(@"mycards detail");
    [self stylizeNavigationBar];
    [self stylizeForDetailView];
    [self setTitleButtonText:@"Kartlarım"];
    [self.titleButton addTarget:self action:@selector(goBackToCards) forControlEvents:UIControlEventTouchUpInside];
    
    isLoaded = NO;
    
    [self initCommonViews];
    [self initHeaderComponents];
    [self initFooterComponents];
    [self initContentComponents];
    
}
- (void) initCommonViews
{
    UIImage* headerImage = [UIImage imageNamed:@"mycards_detail_header.png"];
    UIImage* contentImage = [UIImage imageNamed:@"mycards_detail_content.png"];
    UIImage* footerImage = [UIImage imageNamed:@"mycards_detail_footer.png"];
    
    UIImageView* headerImageView = [[UIImageView alloc] initWithImage:headerImage];
    UIImageView* contentImageView = [[UIImageView alloc] initWithImage:contentImage];
    UIImageView* footerImageView = [[UIImageView alloc] initWithImage:footerImage];
    
    [headerImageView setFrame:[self headerImageViewFrame]];
    [contentImageView setFrame:[self contentImageViewFrame]];
    [footerImageView setFrame:[self footerImageViewFrame]];
    
    [self.view addSubview:headerImageView];
    [self.view addSubview:contentImageView];
    [self.view addSubview:footerImageView];
}
- (void) initHeaderComponents
{
    CBUtil* sharedUtil = [CBUtil sharedInstance];
    
    UIView* headerHolder = [[UIView alloc] initWithFrame:[self headerImageViewFrame]];
    [headerHolder setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:headerHolder];
    [headerHolder.layer setShouldRasterize:YES];
    [headerHolder.layer setRasterizationScale:[UIScreen mainScreen].scale];
    
    companyThumbnail = [[UIImageView alloc] initWithFrame:[self companyThumbnailFrame]];
    [companyThumbnail setBackgroundColor:[UIColor clearColor]];
    [companyThumbnail.layer setCornerRadius:5.0];
    [companyThumbnail.layer setBorderColor:CARD_DETAIL_THUMBNAIL_BORDER_COLOR.CGColor];
    [companyThumbnail.layer setBorderWidth:1.0];
    [companyThumbnail.layer setShouldRasterize:YES];
    [companyThumbnail.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [companyThumbnail setClipsToBounds:YES];
//    [companyThumbnail setImage:[UIImage imageNamed:@"dummy_brand.jpg"]];
    [headerHolder addSubview:companyThumbnail];
    
    companyNameLabel = [[UILabel alloc] initWithFrame:[self companyNameLabelFrame]];
    [companyNameLabel setBackgroundColor:[UIColor clearColor]];
    [companyNameLabel setFont:CARD_DETAIL_COMPANY_NAME_FONT];
    [companyNameLabel setTextColor:CARD_DETAIL_COMPANY_NAME_TEXT_COLOR];
//    [companyNameLabel setText:@"CINEMAXIMUM"];
    [companyNameLabel.layer setShouldRasterize:YES];
    [companyNameLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:companyNameLabel];
    
    UILabel* companyPointTitleLabel = [[UILabel alloc] initWithFrame:[self companyPointTitleLabelFrame]];
    [companyPointTitleLabel setBackgroundColor:[UIColor clearColor]];
    [companyPointTitleLabel setFont:CARD_DETAIL_COMPANY_POINT_TITLE_FONT];
    [companyPointTitleLabel setTextColor:CARD_DETAIL_COMPANY_POINT_TITLE_TEXT_COLOR];
    [companyPointTitleLabel setText:@"Marka Puanınız"];
    [companyPointTitleLabel.layer setShouldRasterize:YES];
    [companyPointTitleLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:companyPointTitleLabel];
    
    remainingPointLabel = [[UILabel alloc] init];
//    remainingPointLabel = [[UILabel alloc] initWithFrame:[self remainingPointLabelFrame]];
    [remainingPointLabel setBackgroundColor:[UIColor clearColor]];
    [remainingPointLabel setFont:CARD_DETAIL_COMPANY_REMAINING_POINT_FONT];
    [remainingPointLabel setTextColor:CARD_DETAIL_COMPANY_REMAINING_POINT_TEXT_COLOR];
    [remainingPointLabel.layer setShouldRasterize:YES];
    [remainingPointLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:remainingPointLabel];
    
    totalPointLabel = [[UILabel alloc] init];
//    totalPointLabel = [[UILabel alloc] initWithFrame:[self totalPointLabelFrame]];
    [totalPointLabel setBackgroundColor:[UIColor clearColor]];
    [totalPointLabel setFont:CARD_DETAIL_COMPANY_TOTAL_POINT_FONT];
    [totalPointLabel setTextColor:CARD_DETAIL_COMPANY_TOTAL_POINT_TEXT_COLOR];
//    [totalPointLabel setText:@"/ 3876"];
    [totalPointLabel.layer setShouldRasterize:YES];
    [totalPointLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:totalPointLabel];
    
    UIView* buttonBarView = [[UIView alloc] initWithFrame:[self companyButtonBarFrame]];
    [buttonBarView setBackgroundColor:[UIColor clearColor]];
    [headerHolder addSubview:buttonBarView];
    
    UIButton* infoButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [infoButton setFrame:[self infoButtonFrame]];
    [infoButton setBackgroundColor:[UIColor clearColor]];
    [infoButton setBackgroundImage:[UIImage imageNamed:@"mycard_company_info_normal.png"] forState:UIControlStateNormal];
    [infoButton setBackgroundImage:[UIImage imageNamed:@"mycard_company_info_highlighted.png"] forState:UIControlStateHighlighted];
    [infoButton addTarget:self action:@selector(openInfoScreen) forControlEvents:UIControlEventTouchUpInside];
    [buttonBarView addSubview:infoButton];
    
    UIImageView* seperator1 = [[UIImageView alloc] initWithFrame:[self seperator1Frame]];
    [seperator1 setBackgroundColor:[UIColor clearColor]];
    [seperator1 setImage:[UIImage imageNamed:@"mycard_ayrac.png"]];
    [buttonBarView addSubview:seperator1];
    
    UIButton* shopsButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [shopsButton setFrame:[self shopsButtonFrame]];
    [shopsButton setBackgroundColor:[UIColor clearColor]];
    [shopsButton setBackgroundImage:[UIImage imageNamed:@"mycard_company_branch_normal.png"] forState:UIControlStateNormal];
    [shopsButton setBackgroundImage:[UIImage imageNamed:@"mycard_company_branch_highlighted.png"] forState:UIControlStateHighlighted];
    [shopsButton addTarget:self action:@selector(openShopsScreen) forControlEvents:UIControlEventTouchUpInside];
    [buttonBarView addSubview:shopsButton];
    
    UIImageView* seperator2 = [[UIImageView alloc] initWithFrame:[self seperator2Frame]];
    [seperator2 setBackgroundColor:[UIColor clearColor]];
    [seperator2 setImage:[UIImage imageNamed:@"mycard_ayrac.png"]];
    [buttonBarView addSubview:seperator2];
    
    UIButton* contactButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [contactButton setFrame:[self contactButtonFrame]];
    [contactButton setBackgroundColor:[UIColor clearColor]];
    [contactButton setBackgroundImage:[UIImage imageNamed:@"mycard_company_call_normal.png"] forState:UIControlStateNormal];
    [contactButton setBackgroundImage:[UIImage imageNamed:@"mycard_company_call_highlighted.png"] forState:UIControlStateHighlighted];
    [contactButton addTarget:self action:@selector(openContactScreen) forControlEvents:UIControlEventTouchUpInside];
    [buttonBarView addSubview:contactButton];
    
    UIImageView* seperator3 = [[UIImageView alloc] initWithFrame:[self seperator3Frame]];
    [seperator3 setBackgroundColor:[UIColor clearColor]];
    [seperator3 setImage:[UIImage imageNamed:@"mycard_ayrac.png"]];
    [buttonBarView addSubview:seperator3];
    
    notificationStatusImageView = [[UIImageView alloc] initWithFrame:[self notificationStatusImageFrame]];
    [notificationStatusImageView setBackgroundColor:[UIColor clearColor]];
    [notificationStatusImageView setClipsToBounds:YES];
    [notificationStatusImageView setUserInteractionEnabled:YES];
    [notificationStatusImageView setContentMode:UIViewContentModeCenter];
    UITapGestureRecognizer* statusTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(changeNotificationStatusImage)];
    [notificationStatusImageView addGestureRecognizer:statusTap];
    [buttonBarView addSubview:notificationStatusImageView];
    
    
    UILabel* cardNumberTitleLabel = [[UILabel alloc] initWithFrame:[self cardNumberTitleLabelFrame]];
    [cardNumberTitleLabel setBackgroundColor:[UIColor clearColor]];
    [cardNumberTitleLabel setFont:CARD_DETAIL_CARD_NUMBER_TITLE_FONT];
    [cardNumberTitleLabel setTextColor:CARD_DETAIL_CARD_NUMBER_TITLE_TEXT_COLOR];
    [cardNumberTitleLabel setText:@"Kart No:"];
    [cardNumberTitleLabel.layer setShouldRasterize:YES];
    [cardNumberTitleLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:cardNumberTitleLabel];
    
    self.cardNumberField = [[CBTextField alloc] initWithFrame:[self cardNumberFieldFrame] andForRegister:NO];
    [self.cardNumberField setBackgroundColor:[UIColor whiteColor]];
    [self.cardNumberField.layer setCornerRadius:3.0];
    [self.cardNumberField.layer setShouldRasterize:YES];
    [self.cardNumberField.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [self.cardNumberField setFont:CARD_DETAIL_CARD_NUMBER_FONT];
    [self.cardNumberField setTextColor:CARD_DETAIL_CARD_NUMBER_TEXT_COLOR];
    [self.cardNumberField setTextAlignment:NSTextAlignmentCenter];
    [self.cardNumberField setPlaceholder:@"0000 0000 0000 0000"];
    self.cardNumberField.delegate = self;
    [headerHolder addSubview:self.cardNumberField];
    
    saveCardNumberButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [saveCardNumberButton setFrame:[self saveCardNumberButtonFrame]];
    [saveCardNumberButton setBackgroundColor:[UIColor clearColor]];
    [saveCardNumberButton setTitle:@"Kaydet" forState:UIControlStateNormal];
    [saveCardNumberButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [saveCardNumberButton.titleLabel setFont:CARD_DETAIL_SAVE_BUTTON_FONT];
    [saveCardNumberButton setBackgroundImage:[sharedUtil UIImageWithUIColor:CARD_DETAIL_SAVE_BUTTON_NORMAL_COLOR] forState:UIControlStateNormal];
    [saveCardNumberButton setBackgroundImage:[sharedUtil UIImageWithUIColor:CARD_DETAIL_SAVE_BUTTON_HIGHLIGHTED_COLOR] forState:UIControlStateHighlighted];
    [saveCardNumberButton.layer setCornerRadius:3.0];
    [saveCardNumberButton.layer setShouldRasterize:YES];
    [saveCardNumberButton.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [saveCardNumberButton setClipsToBounds:YES];
    [saveCardNumberButton addTarget:self action:@selector(saveCardNumber) forControlEvents:UIControlEventTouchUpInside];
    [headerHolder addSubview:saveCardNumberButton];
    
}
- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqualToString:@"InfoScreenSegue"]) {
        CBInfoViewController* dest = (CBInfoViewController*)[segue destinationViewController];
        dest.currentCardDetail = self.currentCardDetail;
    } else if ([segue.identifier isEqualToString:@"CompanyContactScreenSegue"]) {
        CBCompanyContactViewController* dest = (CBCompanyContactViewController*)[segue destinationViewController];
        dest.currentCardDetail = self.currentCardDetail;
    }
}
- (void) openInfoScreen
{
    if (!isLoaded) {
        return;
    }
    
    [self performSegueWithIdentifier:@"InfoScreenSegue" sender:self];
    
    NSLog(@"open info screen");
}
- (void) openShopsScreen
{
    if (!isLoaded) {
        return;
    }
    NSLog(@"open shops screen");
}
- (void) openContactScreen
{
    if (!isLoaded) {
        return;
    }
    
    [self performSegueWithIdentifier:@"CompanyContactScreenSegue" sender:self];
    NSLog(@"open contact screen");
}
- (void) initFooterComponents
{
    CBUtil* sharedUtil = [CBUtil sharedInstance];
    
    UIView* footerHolder = [[UIView alloc] initWithFrame:[self footerImageViewFrame]];
    [footerHolder setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:footerHolder];
    [footerHolder.layer setShouldRasterize:YES];
    [footerHolder.layer setRasterizationScale:[UIScreen mainScreen].scale];
    
    campaignsButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [campaignsButton setFrame:[self campaignsButtonFrame]];
    [campaignsButton setBackgroundColor:[UIColor clearColor]];
    [campaignsButton setTitle:@"Kampanyalar" forState:UIControlStateNormal];
    [campaignsButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [campaignsButton.titleLabel setFont:CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_FONT];
    [campaignsButton setBackgroundImage:[sharedUtil UIImageWithUIColor:CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_NORMAL_COLOR] forState:UIControlStateNormal];
    [campaignsButton setBackgroundImage:[sharedUtil UIImageWithUIColor:CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_HIGHLIGHTED_COLOR] forState:UIControlStateHighlighted];
    [campaignsButton.layer setCornerRadius:5.0];
    [campaignsButton.layer setShouldRasterize:YES];
    [campaignsButton.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [campaignsButton setClipsToBounds:YES];
    [campaignsButton addTarget:self action:@selector(openCampaigns) forControlEvents:UIControlEventTouchUpInside];
    [footerHolder addSubview:campaignsButton];
    
    shoppingsButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [shoppingsButton setFrame:[self shoppingsButtonFrame]];
    [shoppingsButton setBackgroundColor:[UIColor clearColor]];
    [shoppingsButton setTitle:@"Alışverişler" forState:UIControlStateNormal];
    [shoppingsButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [shoppingsButton.titleLabel setFont:CARD_DETAIL_SHOPPINGS_LINK_BUTTON_FONT];
    [shoppingsButton setBackgroundImage:[sharedUtil UIImageWithUIColor:CARD_DETAIL_SHOPPINGS_LINK_BUTTON_NORMAL_COLOR] forState:UIControlStateNormal];
    [shoppingsButton setBackgroundImage:[sharedUtil UIImageWithUIColor:CARD_DETAIL_SHOPPINGS_LINK_BUTTON_HIGHLIGHTED_COLOR] forState:UIControlStateHighlighted];
    [shoppingsButton.layer setCornerRadius:5.0];
    [campaignsButton.layer setShouldRasterize:YES];
    [shoppingsButton.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [shoppingsButton setClipsToBounds:YES];
    [shoppingsButton addTarget:self action:@selector(openShoppings) forControlEvents:UIControlEventTouchUpInside];
    [footerHolder addSubview:shoppingsButton];
}
- (void) initContentComponents
{
    self.tableView = [[UITableView alloc] initWithFrame:[self tableViewFrame]];
    [self.tableView setBackgroundColor:[UIColor clearColor]];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    [self.tableView setShowsVerticalScrollIndicator:NO];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    [self.view addSubview:self.tableView];
    
    [self.tableView registerClass:[CBCardDetailsCouponCell class] forCellReuseIdentifier:CARD_DETAILS_COUPON_CELL_IDENTIFIER];
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.currentCardDetail.shoppingPromotionCouponList count];
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 66.0;
}
- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    CBCardDetailsCouponCell* cell;
    
    cell = [tableView dequeueReusableCellWithIdentifier:CARD_DETAILS_COUPON_CELL_IDENTIFIER];
    
    NSDictionary* currentCoupon = [self.currentCardDetail.shoppingPromotionCouponList objectAtIndex:indexPath.row];
    
    [cell.mainLabel setText:[currentCoupon objectForKey:@"CompanyPromotionId"]];
    [cell.subtitleLabel setText:[currentCoupon objectForKey:@"CompanyPromotionText"]];
    
    return cell;
}
- (void) openCampaigns
{
    if (!isLoaded) {
        return;
    }
    CBUtil* util = [CBUtil sharedInstance];
    util.shouldShowForACompany = YES;
    util.companyId = _currentCompanyId;
    UINavigationController* navController = (UINavigationController*)[self.tabBarController.viewControllers objectAtIndex:1];
    [navController popToRootViewControllerAnimated:NO];
    [self.tabBarController setSelectedIndex:1];
}
- (void) openShoppings
{
    if (!isLoaded) {
        return;
    }
    CBUtil* util = [CBUtil sharedInstance];
    util.shouldShowForACompany = YES;
    util.companyId = _currentCompanyId;
    UINavigationController* navController = (UINavigationController*)[self.tabBarController.viewControllers objectAtIndex:2];
    [navController popToRootViewControllerAnimated:NO];
    [self.tabBarController setSelectedIndex:2];
}
- (void) saveCardNumber
{
    if (!isLoaded) {
        return;
    }
    [self.view endEditing:YES];
    NSString* cardNumber = [self.cardNumberField text];
    if (cardNumber != nil && ![cardNumber isEqualToString:@""]) {
        // API call
        [[APIManager sharedInstance] setUserCompanyCardWithCompanyId:self.currentCardDetail.companyId
                                                       andCardNumber:cardNumber
                                                        onCompletion:^(NSDictionary *responseDictionary) {
                                                            NSLog(@"response here");
                                                            _currentCardDetail.cardbookUserCard = cardNumber;
                                                            [self fillViewsWithCompanyInfos];
                                                        } onError:^(NSError *error) {
                                                            NSLog(@"an error occured");
                                                        }];
    }
}
- (BOOL) textFieldShouldBeginEditing:(UITextField *)textField
{
    return isLoaded;
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self.view endEditing:YES];
    return YES;
}
- (void) setNotificationStatus:(BOOL)isActive
{
    if (_currentCardDetail.userWantNotification == isActive) {
        return;
    } else {
        if (isActive == NO) {
            [[APIManager sharedInstance] setUserNotificationStatusWithCompanyId:_currentCardDetail.companyId
                                                          andNotificationStatus:NO
                                                                   onCompletion:^(NSDictionary *responseDictionary) {
                                                                       NSLog(@"response here");
                                                                       _currentCardDetail.userWantNotification = NO;
                                                                       UIImage* notificationImage = [UIImage imageNamed:@"notification_off.png"];
                                                                       [notificationStatusImageView setImage:notificationImage];
                                                                   } onError:^(NSError *error) {
                                                                       NSLog(@"an error occured");
                                                                   }];
            
        } else {
            [[APIManager sharedInstance] setUserNotificationStatusWithCompanyId:_currentCardDetail.companyId
                                                          andNotificationStatus:YES
                                                                   onCompletion:^(NSDictionary *responseDictionary) {
                                                                       NSLog(@"response here");
                                                                       _currentCardDetail.userWantNotification = YES;
                                                                       UIImage* notificationImage = [UIImage imageNamed:@"notification_on.png"];
                                                                       [notificationStatusImageView setImage:notificationImage];
                                                                   } onError:^(NSError *error) {
                                                                       NSLog(@"an error occured");
                                                                   }];
        }
        
    }
}
//- (BOOL) textF
- (void) changeNotificationStatusImage
{
    if (_currentCardDetail.userWantNotification) {
        [self setNotificationStatus:NO];
    } else {
        [self setNotificationStatus:YES];
    }
}
- (void) goBackToCards
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void) stylizeNavigationBar
{
    [self.navigationController.navigationBar setHidden:YES];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(CGSize)text:(NSString*)text sizeWithFont:(UIFont*)font constrainedToSize:(CGSize)size{
    
    NSDictionary *attributesDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                          font, NSFontAttributeName,
                                          nil];
    
    CGRect frame = [text boundingRectWithSize:size
                                      options:(NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading)
                                   attributes:attributesDictionary
                                      context:nil];
    
    return frame.size;
    
}
@end
