//
//  CBMyCampaignsDetailViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 12.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBMyCampaignsDetailViewController.h"
#import "CBUtil.h"

@interface CBMyCampaignsDetailViewController ()

@end

@implementation CBMyCampaignsDetailViewController
{
    UIView* headerHolder;
    UILabel* headerTitleLabel;
    UIImageView* campaignImageView;
    
    UIView* littleInfoHolder;
    UIImageView* campaignLittleInfoHolderBackView1;
    UIImageView* campaignLittleInfoHolderBackView2;
    UILabel* campaignLittleInfoTitleLabel;
    UILabel* campaignLittleInfoDateLabel;
    UILabel* campaignLittleInfoDescriptionLabel;
    
    UIView* detailInfoHolder;
    UIImageView* detailInfoHolderBackView1;
    UIImageView* detailInfoHolderBackView2;
    UIImageView* detailInfoHolderBackView3;
    UILabel* detailInfoTitleLabel;
    UILabel* detailInfoDescriptionLabel;
}
- (CGRect) headerViewFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 263.0);
}
- (CGRect) headerBackImageViewFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 45.0);
}
- (CGRect) headerTitleLabelFrame
{
    return CGRectMake(15.0, 0.0, 200.0, 45.0);
}
- (CGRect) campaignImageViewFrame
{
    return CGRectMake(0.0, 45.0, 290.0, 218.0);
}
- (CGRect) campaignLittleInfoHolderFrame
{
    CGRect frame1 = [self campaignLittleInfoDescriptionLabelFrame];
    return CGRectMake(0.0, 263.0, 290.0, 15.0+15.0+8.0+12.0+6.0+frame1.size.height+15.0);
}
- (CGRect) campaignLittleInfoHolderBackView1Frame
{
    CGRect frame1 = [self campaignLittleInfoHolderFrame];
    return CGRectMake(0.0, 0.0, 290.0, frame1.size.height-15.0);
}
- (CGRect) campaignLittleInfoHolderBackView2Frame
{
    CGRect frame1 = [self campaignLittleInfoHolderFrame];
    return CGRectMake(0.0, frame1.size.height-15.0, 290.0, 15.0);
}
- (CGRect) campaignLittleInfoTitleLabelFrame
{
    return CGRectMake(15.0, 15.0, 260.0, 15.0);
}
- (CGRect) campaignLittleInfoDateIconFrame
{
    return CGRectMake(15.0, 38.0, 17.0, 11.0);
}
- (CGRect) campaignLittleInfoDateLabelFrame
{
    return CGRectMake(30.0, 38.0, 220.0, 12.0);
}
- (CGRect) detailInfoHolderFrame
{
    CGRect frame1 = [self campaignLittleInfoHolderFrame];
    CGRect frame2 = [self detailInfoDescriptionLabelFrame];
    return CGRectMake(0.0, frame1.origin.y+frame1.size.height, 290.0, 20.0+8.0+2.0+frame2.size.height+20.0);
}
- (CGRect) detailInfoHolderBackView1Frame
{
    return CGRectMake(0.0, 0.0, 290.0, 20.0);
}
- (CGRect) detailInfoHolderBackView2Frame
{
    CGRect frame1 = [self detailInfoHolderFrame];
    return CGRectMake(0.0, 20.0, 290.0, frame1.size.height-40.0);
}
- (CGRect) detailInfoHolderBackView3Frame
{
    CGRect frame1 = [self detailInfoHolderFrame];
    return CGRectMake(0.0, frame1.size.height-20.0, 290.0, 20.0);
}
- (CGSize) scrollViewContentSize
{
    CGRect frame1 = [self detailInfoHolderFrame];
    return CGSizeMake(290.0, frame1.origin.y+frame1.size.height+49.0+15.0);
}
- (CGRect) detailInfoTitleLabelFrame
{
    return CGRectMake(15.0, 20.0, 260.0, 13.0);
}
- (CGRect) detailInfoDescriptionLabelFrame
{
    NSString* detailInfoDescLabelStr = @"KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description";
    
    CGSize labelSize = [self text:detailInfoDescLabelStr
                     sizeWithFont:MY_CAMPAIGNS_DETAILS_DETAIL_INFO_DESCRIPTION_LABEL_FONT constrainedToSize:CGSizeMake(260.0, 1000.0)];
    
    return CGRectMake(15.0, 35.0, 260.0, labelSize.height);
}
- (CGRect) campaignLittleInfoDescriptionLabelFrame
{
    NSString* infoDescLabelStr = @"KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description";
    CGSize labelSize = [self text:infoDescLabelStr
                     sizeWithFont:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DESCRIPTION_LABEL_FONT constrainedToSize:CGSizeMake(260.0, 1000.0)];
    
    return CGRectMake(15.0, 56.0, 260.0, labelSize.height);
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
    NSLog(@"my campaigns details");
    [self stylizeNavigationBar];
    [self stylizeForDetailView];
    [self setTitleButtonText:@"KAMPANYALARIM"];
    [self.titleButton addTarget:self action:@selector(goBacktoCampaigns) forControlEvents:UIControlEventTouchUpInside];
    
    [self initCommonViews];
    [self initHeaderComponents];
    [self initLittleInfoComponents];
    [self initDetailInfoComponents];
}
- (void) initCommonViews
{
    self.scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(15.0, 69.0, 290.0, SCREEN_SIZE.height-69.0)];
    [self.scrollView setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:self.scrollView];
    [self.scrollView setContentSize:[self scrollViewContentSize]];
    [self.scrollView setShowsVerticalScrollIndicator:NO];
}
- (void) initHeaderComponents
{
    headerHolder = [[UIView alloc] initWithFrame:[self headerViewFrame]];
    [headerHolder setBackgroundColor:[UIColor clearColor]];
    [self.scrollView addSubview:headerHolder];
    
    UIImageView* headerBackImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"campaign_detail_header.png"]];
    [headerBackImageView setFrame:[self headerBackImageViewFrame]];
    [headerBackImageView setBackgroundColor:[UIColor clearColor]];
    [headerHolder addSubview:headerBackImageView];
    
    headerTitleLabel = [[UILabel alloc] initWithFrame:[self headerTitleLabelFrame]];
    [headerTitleLabel setBackgroundColor:[UIColor clearColor]];
    [headerTitleLabel setFont:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT];
    [headerTitleLabel setTextColor:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR];
    [headerTitleLabel setText:@"CINEMAXIMUM"];
    [headerHolder addSubview:headerTitleLabel];
    
    campaignImageView = [[UIImageView alloc] initWithFrame:[self campaignImageViewFrame]];
    [campaignImageView setBackgroundColor:[UIColor orangeColor]];
    [campaignImageView setClipsToBounds:YES];
    [headerHolder addSubview:campaignImageView];
}
- (void) initLittleInfoComponents
{
    littleInfoHolder = [[UIView alloc] initWithFrame:[self campaignLittleInfoHolderFrame]];
    [littleInfoHolder setBackgroundColor:[UIColor clearColor]];
    [self.scrollView addSubview:littleInfoHolder];
    
    campaignLittleInfoHolderBackView1 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"campaign_detail_white.png"]];
    [campaignLittleInfoHolderBackView1 setFrame:[self campaignLittleInfoHolderBackView1Frame]];
    [campaignLittleInfoHolderBackView1 setBackgroundColor:[UIColor clearColor]];
    [littleInfoHolder addSubview:campaignLittleInfoHolderBackView1];
    
    campaignLittleInfoHolderBackView2 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"campaign_detail_white_bottom.png"]];
    [campaignLittleInfoHolderBackView2 setFrame:[self campaignLittleInfoHolderBackView2Frame]];
    [campaignLittleInfoHolderBackView2 setBackgroundColor:[UIColor clearColor]];
    [littleInfoHolder addSubview:campaignLittleInfoHolderBackView2];
    
    campaignLittleInfoTitleLabel = [[UILabel alloc] initWithFrame:[self campaignLittleInfoTitleLabelFrame]];
    [campaignLittleInfoTitleLabel setBackgroundColor:[UIColor clearColor]];
    [campaignLittleInfoTitleLabel setFont:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_TITLE_LABEL_FONT];
    [campaignLittleInfoTitleLabel setTextColor:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_TITLE_LABEL_TEXT_COLOR];
    [campaignLittleInfoTitleLabel setText:@"3 Bilet Alana 1 Bilet Bedava"];
    [littleInfoHolder addSubview:campaignLittleInfoTitleLabel];
    
    UIImageView* dateIconView = [[UIImageView alloc] initWithFrame:[self campaignLittleInfoDateIconFrame]];
    [dateIconView setBackgroundColor:[UIColor clearColor]];
    [dateIconView setImage:[UIImage imageNamed:@"shopping_tarih_icon.png"]];
    [littleInfoHolder addSubview:dateIconView];
    
    campaignLittleInfoDateLabel = [[UILabel alloc] initWithFrame:[self campaignLittleInfoDateLabelFrame]];
    [campaignLittleInfoDateLabel setBackgroundColor:[UIColor clearColor]];
    [campaignLittleInfoDateLabel setFont:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DATE_LABEL_FONT];
    [campaignLittleInfoDateLabel setTextColor:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DATE_LABEL_TEXT_COLOR];
    [campaignLittleInfoDateLabel setText:@"30 TEMMUZ - 30 AĞUSTOS"];
    [littleInfoHolder addSubview:campaignLittleInfoDateLabel];
    
    NSString* infoDescLabelStr = @"KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description";
    
    campaignLittleInfoDescriptionLabel = [[UILabel alloc] initWithFrame:[self campaignLittleInfoDescriptionLabelFrame]];
    [campaignLittleInfoDescriptionLabel setBackgroundColor:[UIColor clearColor]];
    [campaignLittleInfoDescriptionLabel setNumberOfLines:0];
    [campaignLittleInfoDescriptionLabel setFont:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DESCRIPTION_LABEL_FONT];
    [campaignLittleInfoDescriptionLabel setTextColor:MY_CAMPAIGNS_DETAILS_LITTLE_INFO_DESCRIPTION_LABEL_TEXT_COLOR];
    [campaignLittleInfoDescriptionLabel setText:infoDescLabelStr];
    [littleInfoHolder addSubview:campaignLittleInfoDescriptionLabel];
}
- (void) initDetailInfoComponents
{
    detailInfoHolder = [[UIView alloc] initWithFrame:[self detailInfoHolderFrame]];
    [detailInfoHolder setBackgroundColor:[UIColor clearColor]];
    [self.scrollView addSubview:detailInfoHolder];
    
    detailInfoHolderBackView1 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"campaign_detail_desc_top.png"]];
    [detailInfoHolderBackView1 setFrame:[self detailInfoHolderBackView1Frame]];
    [detailInfoHolderBackView1 setBackgroundColor:[UIColor clearColor]];
    [detailInfoHolder addSubview:detailInfoHolderBackView1];
    
    detailInfoHolderBackView2 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"campaign_detail_desc_bg.png"]];
    [detailInfoHolderBackView2 setFrame:[self detailInfoHolderBackView2Frame]];
    [detailInfoHolderBackView2 setBackgroundColor:[UIColor clearColor]];
    [detailInfoHolder addSubview:detailInfoHolderBackView2];
    
    detailInfoHolderBackView3 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"campaign_detail_desc_bottom.png"]];
    [detailInfoHolderBackView3 setFrame:[self detailInfoHolderBackView3Frame]];
    [detailInfoHolderBackView3 setBackgroundColor:[UIColor clearColor]];
    [detailInfoHolder addSubview:detailInfoHolderBackView3];
    
    detailInfoTitleLabel = [[UILabel alloc] initWithFrame:[self detailInfoTitleLabelFrame]];
    [detailInfoTitleLabel setBackgroundColor:[UIColor clearColor]];
    [detailInfoTitleLabel setFont:MY_CAMPAIGNS_DETAILS_DETAIL_INFO_TITLE_LABEL_FONT];
    [detailInfoTitleLabel setTextColor:MY_CAMPAIGNS_DETAILS_DETAIL_INFO_TITLE_LABEL_TEXT_COLOR];
    [detailInfoTitleLabel setText:@"Kampanya Koşulları"];
    [detailInfoHolder addSubview:detailInfoTitleLabel];
    
    NSString* detailInfoDescLabelStr = @"KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description KAVUN deneme title label lorem ipsum description to take what it says deneme description label from company screen bla bla bla KAVUN deneme title label lorem ipsum description";
    
    detailInfoDescriptionLabel = [[UILabel alloc] initWithFrame:[self detailInfoDescriptionLabelFrame]];
    [detailInfoDescriptionLabel setBackgroundColor:[UIColor clearColor]];
    [detailInfoDescriptionLabel setNumberOfLines:0];
    [detailInfoDescriptionLabel setFont:MY_CAMPAIGNS_DETAILS_DETAIL_INFO_DESCRIPTION_LABEL_FONT];
    [detailInfoDescriptionLabel setTextColor:MY_CAMPAIGNS_DETAILS_DETAIL_INFO_DESCRIPTION_LABEL_TEXT_COLOR];
    [detailInfoDescriptionLabel setText:detailInfoDescLabelStr];
    [detailInfoHolder addSubview:detailInfoDescriptionLabel];
}
- (void) goBacktoCampaigns
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
