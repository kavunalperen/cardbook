//
//  CBShoppingDetailViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 12.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBShoppingDetailViewController.h"
#import "CBUtil.h"
#import "CBShoppingDetailsCell.h"

@interface CBShoppingDetailViewController ()

@end

@implementation CBShoppingDetailViewController
{
    UIView* headerHolder;
    UILabel* headerTitleLabel;
    UILabel* headerDateLabel;
    UILabel* headerProductNameLabel;
    UILabel* headerProductPriceLabel;
    
    UIView* contentHolder;
    
    UIView* footerHolder;
    
    UILabel* earnedPointPKLabel;
    UILabel* earnedPointPPLabel;
    
    UILabel* spentPointPKLabel;
    UILabel* spentPointPPLabel;
    
    UIButton* facebookButton;
}
- (CGRect) headerHolderFrame
{
    return CGRectMake(15.0, 69.0, 290.0, 100.0);
}
- (CGRect) headerHolderBackViewFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 100.0);
}
- (CGRect) headerShoppingIconFrame
{
    return CGRectMake(20.0, 15.0, 17.0, 11.0);
}
- (CGRect) headerDateIconFrame
{
    return CGRectMake(20.0, 35.0, 17.0, 11.0);
}
- (CGRect) headerTitleLabelFrame
{
    return CGRectMake(37.0, 13.0, 200.0, 15.0);
}
- (CGRect) headerDateLabelFrame
{
    return CGRectMake(37.0, 33.0, 200.0, 15.0);
}
- (CGRect) headerProductNameLabelFrame
{
    return CGRectMake(20.0, 61.0, 150.0, 36.0);
}
- (CGRect) headerProductPriceLabelFrame
{
    return CGRectMake(170.0, 61.0, 100.0, 36.0);
}
- (CGFloat) tableViewHeight
{
    CGFloat tableViewHeight;
    if (IS_IPHONE_5) {
        tableViewHeight = 135.0;
    } else {
        tableViewHeight = 47.0;
    }
    return tableViewHeight;
}
- (CGRect) tableViewFrame
{
    return CGRectMake(20.0, 8.0, 250.0, [self tableViewHeight]);
}
- (CGRect) contentHolderFrame
{
    return CGRectMake(15.0, 169.0, 290.0, [self tableViewHeight]+8.0+15.0);
}
- (CGRect) contentHolderBack1Frame
{
    CGRect frame = [self contentHolderFrame];
    return CGRectMake(0.0, 0.0, frame.size.width, frame.size.height-20.0);
}
- (CGRect) contentHolderBack2Frame
{
    CGRect frame = [self contentHolderFrame];
    return CGRectMake(0.0, frame.size.height-20.0, frame.size.width, 20.0);
}
- (CGRect) footerHolderFrame
{
    CGRect frame = [self contentHolderFrame];
    return CGRectMake(15.0, frame.origin.y+frame.size.height, 290.0, 176.0);
}
- (CGRect) earnedPointsHolderFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 62.0);
}
- (CGRect) earnedPointsBackViewFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 62.0);
}
- (CGRect) spentPointsHolderFrame
{
    return CGRectMake(0.0, 62.0, 290.0, 62.0);
}
- (CGRect) spentPointsBackViewFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 62.0);
}
- (CGRect) facebookHolderFrame
{
    return CGRectMake(0.0, 124.0, 290.0, 52.0);
}
- (CGRect) facebookBackViewFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 52.0);
}
- (CGRect) facebookButtonFrame
{
    return CGRectMake(70.0, 10.0, 150.0, 32.0);
}
- (CGRect) pointIconsFrame
{
    return CGRectMake(15.0, 10.0, 17.0, 11.0);
}
- (CGRect) pointsMainTitleLabelFrame
{
    return CGRectMake(32.0, 10.0, 200.0, 13.0);
}
- (CGRect) pointsPKTitleLabelFrame
{
    return CGRectMake(15.0, 30.0, 28.0, 16.0);
}
- (CGRect) pointsPKLabelFrame
{
    return CGRectMake(45.0, 30.0, 100.0, 16.0);
}
- (CGRect) pointsPPTitleLabelFrame
{
    return CGRectMake(160.0, 30.0, 28.0, 16.0);
}
- (CGRect) pointsPPLabelFrame
{
    return CGRectMake(188.0, 30.0, 60.0, 16.0);
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
    [self stylizeNavigationBar];
    [self stylizeForDetailView];
    [self setTitleButtonText:@"Alışverişlerim"];
    [self.titleButton addTarget:self action:@selector(goBacktoShoppings) forControlEvents:UIControlEventTouchUpInside];
    [self initHeaderComponents];
    [self initContentComponents];
    [self initFooterComponents];
}
- (void) initHeaderComponents
{
    headerHolder = [[UIView alloc] initWithFrame:[self headerHolderFrame]];
    [headerHolder setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:headerHolder];
    
    UIImageView* headerHolderBackView = [[UIImageView alloc] initWithFrame:[self headerHolderBackViewFrame]];
    [headerHolderBackView setImage:[UIImage imageNamed:@"shopping_detail_top_bg.png"]];
    [headerHolder addSubview:headerHolderBackView];
    
    UIImageView* headerShoppingIconView = [[UIImageView alloc] initWithFrame:[self headerShoppingIconFrame]];
    [headerShoppingIconView setBackgroundColor:[UIColor clearColor]];
    [headerShoppingIconView setImage:[UIImage imageNamed:@"shopping_firma_icon.png"]];
    [headerHolder addSubview:headerShoppingIconView];
    
    headerTitleLabel = [[UILabel alloc] initWithFrame:[self headerTitleLabelFrame]];
    [headerTitleLabel setBackgroundColor:[UIColor clearColor]];
    [headerTitleLabel setFont:MY_SHOPPINGS_DETAILS_HEADER_TITLE_LABEL_FONT];
    [headerTitleLabel setTextColor:MY_SHOPPINGS_DETAILS_HEADER_TITLE_TEXT_COLOR];
    [headerTitleLabel setText:@"CINEMAXIMUM"];
    [headerHolder addSubview:headerTitleLabel];
    
    UIImageView* headerDateIconView = [[UIImageView alloc] initWithFrame:[self headerDateIconFrame]];
    [headerDateIconView setBackgroundColor:[UIColor clearColor]];
    [headerDateIconView setImage:[UIImage imageNamed:@"shopping_tarih_icon.png"]];
    [headerHolder addSubview:headerDateIconView];
    
    headerDateLabel = [[UILabel alloc] initWithFrame:[self headerDateLabelFrame]];
    [headerDateLabel setBackgroundColor:[UIColor clearColor]];
    [headerDateLabel setFont:MY_SHOPPINGS_DETAILS_HEADER_DATE_LABEL_FONT];
    [headerDateLabel setTextColor:MY_SHOPPINGS_DETAILS_HEADER_DATE_TEXT_COLOR];
    [headerDateLabel setText:@"29 Ekim 2013"];
    [headerHolder addSubview:headerDateLabel];
    
    headerProductNameLabel = [[UILabel alloc] initWithFrame:[self headerProductNameLabelFrame]];
    [headerProductNameLabel setBackgroundColor:[UIColor clearColor]];
    [headerProductNameLabel setFont:MY_SHOPPINGS_DETAILS_HEADER_PRODUCTS_LABEL_FONT];
    [headerProductNameLabel setTextColor:MY_SHOPPINGS_DETAILS_HEADER_PRODUCTS_TEXT_COLOR];
    [headerProductNameLabel setText:@"Ürün Adı"];
    [headerHolder addSubview:headerProductNameLabel];
    
    headerProductPriceLabel = [[UILabel alloc] initWithFrame:[self headerProductPriceLabelFrame]];
    [headerProductPriceLabel setBackgroundColor:[UIColor clearColor]];
    [headerProductPriceLabel setTextAlignment:NSTextAlignmentRight];
    [headerProductPriceLabel setFont:MY_SHOPPINGS_DETAILS_HEADER_PRODUCTS_LABEL_FONT];
    [headerProductPriceLabel setTextColor:MY_SHOPPINGS_DETAILS_HEADER_PRODUCTS_TEXT_COLOR];
    [headerProductPriceLabel setText:@"Ürün Fiyatı"];
    [headerHolder addSubview:headerProductPriceLabel];
}
- (void) initContentComponents
{
    contentHolder = [[UIView alloc] initWithFrame:[self contentHolderFrame]];
    [contentHolder setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:contentHolder];
    
    UIImageView* contentHolderBackView1 = [[UIImageView alloc] initWithFrame:[self contentHolderBack1Frame]];
    [contentHolderBackView1 setBackgroundColor:[UIColor clearColor]];
    [contentHolderBackView1 setImage:[UIImage imageNamed:@"campaign_detail_desc_bg.png"]];
    [contentHolder addSubview:contentHolderBackView1];
    
    UIImageView* contentHolderBackView2 = [[UIImageView alloc] initWithFrame:[self contentHolderBack2Frame]];
    [contentHolderBackView2 setBackgroundColor:[UIColor clearColor]];
    [contentHolderBackView2 setImage:[UIImage imageNamed:@"campaign_detail_desc_bottom.png"]];
    [contentHolder addSubview:contentHolderBackView2];
    
    self.tableView = [[UITableView alloc] initWithFrame:[self tableViewFrame]];
    [self.tableView setBackgroundColor:[UIColor clearColor]];
    [contentHolder addSubview:self.tableView];
    
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    [self.tableView registerClass:[CBShoppingDetailsCell class] forCellReuseIdentifier:SHOPPING_DETAILS_CELL_IDENTIFIER];
    
    [self.tableView setShowsVerticalScrollIndicator:NO];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
}
- (void) initFooterComponents
{
    footerHolder = [[UIView alloc] initWithFrame:[self footerHolderFrame]];
    [footerHolder setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:footerHolder];
    
    UIView* earnedPointsHolder = [[UIView alloc] initWithFrame:[self earnedPointsHolderFrame]];
    [earnedPointsHolder setBackgroundColor:[UIColor clearColor]];
    [footerHolder addSubview:earnedPointsHolder];
    
    UIImageView* earnedPointsBackView = [[UIImageView alloc] initWithFrame:[self earnedPointsBackViewFrame]];
    [earnedPointsBackView setBackgroundColor:[UIColor clearColor]];
    [earnedPointsBackView setImage:[UIImage imageNamed:@"shopping_detail_orange_bg.png"]];
    [earnedPointsHolder addSubview:earnedPointsBackView];
    
    UIImageView* earnedIcon = [[UIImageView alloc] initWithFrame:[self pointIconsFrame]];
    [earnedIcon setBackgroundColor:[UIColor clearColor]];
    [earnedIcon setImage:[UIImage imageNamed:@"shopping_kazanma_icon.png"]];
    [earnedPointsHolder addSubview:earnedIcon];
    
    UILabel* earnedPointsMainTitleLabel = [[UILabel alloc] initWithFrame:[self pointsMainTitleLabelFrame]];
    [earnedPointsMainTitleLabel setBackgroundColor:[UIColor clearColor]];
    [earnedPointsMainTitleLabel setFont:MY_SHOPPINGS_DETAILS_EARNED_POINTS_TITLE_LABEL_FONT];
    [earnedPointsMainTitleLabel setTextColor:MY_SHOPPINGS_DETAILS_EARNED_POINTS_TITLE_LABEL_TEXT_COLOR];
    [earnedPointsMainTitleLabel setText:@"Bu Alışverişten Kazandığınız:"];
    [earnedPointsHolder addSubview:earnedPointsMainTitleLabel];
    
    UILabel* earnedPKTitle = [[UILabel alloc] initWithFrame:[self pointsPKTitleLabelFrame]];
    [earnedPKTitle setBackgroundColor:[UIColor clearColor]];
    [earnedPKTitle setFont:MY_SHOPPINGS_DETAILS_EARNED_POINTS_OTHER_TITLE_LABELS_FONT];
    [earnedPKTitle setTextColor:MY_SHOPPINGS_DETAILS_EARNED_POINTS_OTHER_TITLE_LABELS_TEXT_COLOR];
    [earnedPKTitle setText:@"PK"];
    [earnedPointsHolder addSubview:earnedPKTitle];
    
    earnedPointPKLabel = [[UILabel alloc] initWithFrame:[self pointsPKLabelFrame]];
    [earnedPointPKLabel setBackgroundColor:[UIColor clearColor]];
    [earnedPointPKLabel setFont:MY_SHOPPINGS_DETAILS_EARNED_POINTS_POINTS_LABELS_FONT];
    [earnedPointPKLabel setTextColor:MY_SHOPPINGS_DETAILS_EARNED_POINTS_POINTS_LABELS_TEXT_COLOR];
    [earnedPointPKLabel setText:@"#34698"];
    [earnedPointsHolder addSubview:earnedPointPKLabel];
    
    UILabel* earnedPPTitle = [[UILabel alloc] initWithFrame:[self pointsPPTitleLabelFrame]];
    [earnedPPTitle setBackgroundColor:[UIColor clearColor]];
    [earnedPPTitle setFont:MY_SHOPPINGS_DETAILS_EARNED_POINTS_OTHER_TITLE_LABELS_FONT];
    [earnedPPTitle setTextColor:MY_SHOPPINGS_DETAILS_EARNED_POINTS_OTHER_TITLE_LABELS_TEXT_COLOR];
    [earnedPPTitle setText:@"PP"];
    [earnedPointsHolder addSubview:earnedPPTitle];
    
    earnedPointPPLabel = [[UILabel alloc] initWithFrame:[self pointsPPLabelFrame]];
    [earnedPointPPLabel setBackgroundColor:[UIColor clearColor]];
    [earnedPointPPLabel setFont:MY_SHOPPINGS_DETAILS_EARNED_POINTS_POINTS_LABELS_FONT];
    [earnedPointPPLabel setTextColor:MY_SHOPPINGS_DETAILS_EARNED_POINTS_POINTS_LABELS_TEXT_COLOR];
    [earnedPointPPLabel setText:@"1600"];
    [earnedPointsHolder addSubview:earnedPointPPLabel];
    
    
    
    UIView* spentPointsHolder = [[UIView alloc] initWithFrame:[self spentPointsHolderFrame]];
    [spentPointsHolder setBackgroundColor:[UIColor clearColor]];
    [footerHolder addSubview:spentPointsHolder];
    
    UIImageView* spentPointsBackView = [[UIImageView alloc] initWithFrame:[self spentPointsBackViewFrame]];
    [spentPointsBackView setBackgroundColor:[UIColor clearColor]];
    [spentPointsBackView setImage:[UIImage imageNamed:@"shopping_detail_blue_bg.png"]];
    [spentPointsHolder addSubview:spentPointsBackView];
    
    UIImageView* spentIcon = [[UIImageView alloc] initWithFrame:[self pointIconsFrame]];
    [spentIcon setBackgroundColor:[UIColor clearColor]];
    [spentIcon setImage:[UIImage imageNamed:@"shopping_harcama_icon.png"]];
    [spentPointsHolder addSubview:spentIcon];
    
    UILabel* spentPointsMainTitleLabel = [[UILabel alloc] initWithFrame:[self pointsMainTitleLabelFrame]];
    [spentPointsMainTitleLabel setBackgroundColor:[UIColor clearColor]];
    [spentPointsMainTitleLabel setFont:MY_SHOPPINGS_DETAILS_SPENT_POINTS_TITLE_LABEL_FONT];
    [spentPointsMainTitleLabel setTextColor:MY_SHOPPINGS_DETAILS_SPENT_POINTS_TITLE_LABEL_TEXT_COLOR];
    [spentPointsMainTitleLabel setText:@"Bu Alışverişte Kullandığınız:"];
    [spentPointsHolder addSubview:spentPointsMainTitleLabel];
    
    UILabel* spentPKTitle = [[UILabel alloc] initWithFrame:[self pointsPKTitleLabelFrame]];
    [spentPKTitle setBackgroundColor:[UIColor clearColor]];
    [spentPKTitle setFont:MY_SHOPPINGS_DETAILS_SPENT_POINTS_OTHER_TITLE_LABELS_FONT];
    [spentPKTitle setTextColor:MY_SHOPPINGS_DETAILS_SPENT_POINTS_OTHER_TITLE_LABELS_TEXT_COLOR];
    [spentPKTitle setText:@"PK"];
    [spentPointsHolder addSubview:spentPKTitle];
    
    spentPointPKLabel = [[UILabel alloc] initWithFrame:[self pointsPKLabelFrame]];
    [spentPointPKLabel setBackgroundColor:[UIColor clearColor]];
    [spentPointPKLabel setFont:MY_SHOPPINGS_DETAILS_SPENT_POINTS_POINTS_LABELS_FONT];
    [spentPointPKLabel setTextColor:MY_SHOPPINGS_DETAILS_SPENT_POINTS_POINTS_LABELS_TEXT_COLOR];
    [spentPointPKLabel setText:@"#34698"];
    [spentPointsHolder addSubview:spentPointPKLabel];
    
    UILabel* spentPPTitle = [[UILabel alloc] initWithFrame:[self pointsPPTitleLabelFrame]];
    [spentPPTitle setBackgroundColor:[UIColor clearColor]];
    [spentPPTitle setFont:MY_SHOPPINGS_DETAILS_SPENT_POINTS_OTHER_TITLE_LABELS_FONT];
    [spentPPTitle setTextColor:MY_SHOPPINGS_DETAILS_SPENT_POINTS_OTHER_TITLE_LABELS_TEXT_COLOR];
    [spentPPTitle setText:@"PP"];
    [spentPointsHolder addSubview:spentPPTitle];
    
    spentPointPPLabel = [[UILabel alloc] initWithFrame:[self pointsPPLabelFrame]];
    [spentPointPPLabel setBackgroundColor:[UIColor clearColor]];
    [spentPointPPLabel setFont:MY_SHOPPINGS_DETAILS_SPENT_POINTS_POINTS_LABELS_FONT];
    [spentPointPPLabel setTextColor:MY_SHOPPINGS_DETAILS_SPENT_POINTS_POINTS_LABELS_TEXT_COLOR];
    [spentPointPPLabel setText:@"1600"];
    [spentPointsHolder addSubview:spentPointPPLabel];
    
    
    UIView* facebookHolder = [[UIView alloc] initWithFrame:[self facebookHolderFrame]];
    [facebookHolder setBackgroundColor:[UIColor clearColor]];
    [footerHolder addSubview:facebookHolder];
    
    UIImageView* facebookBackView = [[UIImageView alloc] initWithFrame:[self facebookBackViewFrame]];
    [facebookBackView setBackgroundColor:[UIColor clearColor]];
    [facebookBackView setImage:[UIImage imageNamed:@"shopping_detail_share_bg.png"]];
    [facebookHolder addSubview:facebookBackView];
    
    facebookButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [facebookButton setFrame:[self facebookButtonFrame]];
    [facebookButton setBackgroundColor:[UIColor clearColor]];
    [facebookButton setImage:[UIImage imageNamed:@"share_button_normal.png"] forState:UIControlStateNormal];
    [facebookButton setImage:[UIImage imageNamed:@"share_button_highlighted.png"] forState:UIControlStateHighlighted];
    [facebookButton addTarget:self action:@selector(shareOnFacebook) forControlEvents:UIControlEventTouchUpInside];
    [facebookHolder addSubview:facebookButton];
}
- (void) shareOnFacebook
{
    
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
    return 20.0;
}
- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    CBShoppingDetailsCell* cell = [tableView dequeueReusableCellWithIdentifier:SHOPPING_DETAILS_CELL_IDENTIFIER];
    
    [cell.nameLabel setText:@"Adana Kebap"];
    [cell.priceLabel setText:@"10,50"];
    
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self performSegueWithIdentifier:@"MyShoppingsDetailSegue" sender:self];
}

- (void) goBacktoShoppings
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
