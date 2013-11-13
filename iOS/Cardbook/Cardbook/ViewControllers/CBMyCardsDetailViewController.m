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
#import "CBUtil.h"
#import "CBCardDetailsCouponCell.h"

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
}
- (CGRect) headerImageViewFrame
{
    return CGRectMake(15.0, 69.0, 290.0, 193.0);
}
- (CGRect) contentImageViewFrame
{
    if (IS_IPHONE_5) {
        return CGRectMake(15.0, 262.0, 290.0, 172.0);
    } else {
        return CGRectMake(15.0, 262.0, 290.0, 84.0);
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
    return CGRectMake(165.0, 38.0, 110.0, 13.0);
}
- (CGRect) remainingPointLabelFrame
{
    return CGRectMake(165.0, 55.0, 65.0, 21.0);
}
- (CGRect) totalPointLabelFrame
{
    return CGRectMake(230.0, 66.0, 45.0, 10.0);
}
- (CGRect) notificationStatusLabelFrame
{
    return CGRectMake(165.0, 82.0, 75.0, 17.0);
}
- (CGRect) notificationStatusImageFrame
{
    return CGRectMake(240.0, 78.0, 42.0, 25.0);
}
- (CGRect) cardNumberTitleLabelFrame
{
    return CGRectMake(15.0, 120.0, 48.0, 22.0);
}
- (CGRect) cardNumberFieldFrame
{
    return CGRectMake(63.0, 120.0, 147.0, 22.0);
}
- (CGRect) saveCardNumberButtonFrame
{
    return CGRectMake(220.0, 120.0, 55.0, 22.0);
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

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    NSLog(@"mycards detail");
    [self stylizeNavigationBar];
    [self stylizeForDetailView];
    [self setTitleButtonText:@"Kartlarım"];
    [self.titleButton addTarget:self action:@selector(goBackToCards) forControlEvents:UIControlEventTouchUpInside];
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
    [companyThumbnail setImage:[UIImage imageNamed:@"dummy_brand.jpg"]];
    [headerHolder addSubview:companyThumbnail];
    
    companyNameLabel = [[UILabel alloc] initWithFrame:[self companyNameLabelFrame]];
    [companyNameLabel setBackgroundColor:[UIColor clearColor]];
    [companyNameLabel setFont:CARD_DETAIL_COMPANY_NAME_FONT];
    [companyNameLabel setTextColor:CARD_DETAIL_COMPANY_NAME_TEXT_COLOR];
    [companyNameLabel setText:@"CINEMAXIMUM"];
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
    
    remainingPointLabel = [[UILabel alloc] initWithFrame:[self remainingPointLabelFrame]];
    [remainingPointLabel setBackgroundColor:[UIColor clearColor]];
    [remainingPointLabel setFont:CARD_DETAIL_COMPANY_REMAINING_POINT_FONT];
    [remainingPointLabel setTextColor:CARD_DETAIL_COMPANY_REMAINING_POINT_TEXT_COLOR];
    [remainingPointLabel setText:@"1600"];
    [remainingPointLabel.layer setShouldRasterize:YES];
    [remainingPointLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:remainingPointLabel];
    
    totalPointLabel = [[UILabel alloc] initWithFrame:[self totalPointLabelFrame]];
    [totalPointLabel setBackgroundColor:[UIColor clearColor]];
    [totalPointLabel setFont:CARD_DETAIL_COMPANY_TOTAL_POINT_FONT];
    [totalPointLabel setTextColor:CARD_DETAIL_COMPANY_TOTAL_POINT_TEXT_COLOR];
    [totalPointLabel setText:@"/ 3876"];
    [totalPointLabel.layer setShouldRasterize:YES];
    [totalPointLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:totalPointLabel];
    
    UILabel* notificationStatusLabel = [[UILabel alloc] initWithFrame:[self notificationStatusLabelFrame]];
    [notificationStatusLabel setBackgroundColor:[UIColor clearColor]];
    [notificationStatusLabel setFont:CARD_DETAIL_COMPANY_NOTIFICATION_STATUS_FONT];
    [notificationStatusLabel setTextColor:CARD_DETAIL_COMPANY_NOTIFICATION_STATUS_TEXT_COLOR];
    [notificationStatusLabel setText:@"Bildirim Durumu"];
    [notificationStatusLabel.layer setShouldRasterize:YES];
    [notificationStatusLabel.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [headerHolder addSubview:notificationStatusLabel];
    
    notificationStatusImageView = [[UIImageView alloc] initWithFrame:[self notificationStatusImageFrame]];
    [notificationStatusImageView setBackgroundColor:[UIColor clearColor]];
    [notificationStatusImageView setClipsToBounds:YES];
    [notificationStatusImageView setImage:[UIImage imageNamed:@"notification_off.png"]];
    [notificationStatusImageView setUserInteractionEnabled:YES];
    [notificationStatusImageView setContentMode:UIViewContentModeCenter];
    UITapGestureRecognizer* statusTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(changeNotificationStatusImage)];
    [notificationStatusImageView addGestureRecognizer:statusTap];
    [headerHolder addSubview:notificationStatusImageView];
    
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
    return 10;
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
    
    
    [cell.mainLabel setText:@"00000000"];
    [cell.subtitleLabel setText:@"Kupon detaylari sunlari bunlari falan filan ikinci satira gecsin diye doldur da doldur doldur da doldur kavun kavun"];
    
    return cell;
}
- (void) openCampaigns
{
    
}
- (void) openShoppings
{
    
}
- (void) saveCardNumber
{
    
}
- (void) setNotificationStatus:(BOOL)isActive
{
    if (isNotificationActive == isActive) {
        return;
    } else {
        UIImage* notificationImage;
        isNotificationActive = isActive;
        if (isNotificationActive) {
            notificationImage = [UIImage imageNamed:@"notification_off.png"];
        } else {
            notificationImage = [UIImage imageNamed:@"notification_on.png"];
        }
        [notificationStatusImageView setImage:notificationImage];
    }
}
- (void) changeNotificationStatusImage
{
    if (isNotificationActive) {
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

@end
