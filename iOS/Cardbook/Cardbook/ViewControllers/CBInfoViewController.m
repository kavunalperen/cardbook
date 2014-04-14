//
//  CBInfoViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 23.03.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBInfoViewController.h"
#import "APIManager.h"
#import "CBUtil.h"

@interface CBInfoViewController ()

@end

@implementation CBInfoViewController
{
    UILabel* companyNameLabel;
    UILabel* companyAboutTextLabel;
}
- (CGRect) backgroundFrame
{
    CGFloat backHeight;
    if (IS_IPHONE_5) {
        backHeight = 436.0;
    } else {
        backHeight = 348.0;
    }
    return CGRectMake(15.0, 69.0, 290.0, backHeight);
}
- (CGRect) scrollViewFrame
{
    CGFloat backHeight;
    if (IS_IPHONE_5) {
        backHeight = 436.0;
    } else {
        backHeight = 348.0;
    }
    return CGRectMake(15.0, 0.0, 260.0, backHeight);
}
- (CGRect) companyNameLabelFrame
{
    return CGRectMake(0.0, 0.0, 260.0, 35.0);
}
- (CGRect) companyAboutLabelFrame
{
    NSString* aboutStr = self.currentCardInfo.aboutText;
    
    CGSize labelSize = [[CBUtil sharedInstance] text:aboutStr sizeWithFont:CARD_INFO_ABOUT_TEXT_FONT constrainedToSize:CGSizeMake(260.0, 10000.0)];
    
    labelSize = CGSizeMake(ceilf(labelSize.width), ceilf(labelSize.height));
    
    return CGRectMake(0.0, 40.0, 260.0, labelSize.height);
}
- (CGSize) scrollViewContentSize
{
    CGRect labelFrame = [self companyAboutLabelFrame];
    
    return CGSizeMake(260.0, labelFrame.origin.y+labelFrame.size.height+15.0);
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
    [self.titleButton addTarget:self action:@selector(goBackToCardDetail) forControlEvents:UIControlEventTouchUpInside];
    
    [self initCommonViews];
}
- (void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if (self.currentCardInfo == nil) {
        
        CBCardInfo* cardInfo = [CBCardInfo GetCardInfoWithCompanyId:self.currentCardDetail.companyId];
        
        if (cardInfo != nil) {
            self.currentCardInfo = cardInfo;
            [self configureViews];
        } else {
            [[APIManager sharedInstance] getCompanyInfoWithCompanyId:self.currentCardDetail.companyId onCompletion:^(CBCardInfo *cardInfo) {
                self.currentCardInfo = cardInfo;
                [self configureViews];
                NSLog(@"response here");
            } onError:^(NSError *error) {
                NSLog(@"an error occured");
            }];
        }
        
    }
    
}
- (void) initCommonViews
{
    self.view.userInteractionEnabled = YES;
    UIImageView* backView = [[UIImageView alloc] initWithFrame:[self backgroundFrame]];
    [backView setBackgroundColor:[UIColor clearColor]];
    [backView setImage:[UIImage imageNamed:@"info_back.png"]];
    backView.userInteractionEnabled = YES;
    [self.view addSubview:backView];
    
    self.scrollView = [[UIScrollView alloc] initWithFrame:[self scrollViewFrame]];
    [self.scrollView setBackgroundColor:[UIColor clearColor]];
    [self.scrollView setContentSize:[self scrollViewContentSize]];
    [self.scrollView setShowsVerticalScrollIndicator:YES];
    self.scrollView.userInteractionEnabled = YES;
    [backView addSubview:self.scrollView];
    
    companyNameLabel = [[UILabel alloc] initWithFrame:[self companyNameLabelFrame]];
    [companyNameLabel setBackgroundColor:[UIColor clearColor]];
    [companyNameLabel setFont:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT];
    [companyNameLabel setTextColor:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR];
    [self.scrollView addSubview:companyNameLabel];
    
    companyAboutTextLabel = [[UILabel alloc] initWithFrame:[self companyAboutLabelFrame]];
    companyAboutTextLabel.backgroundColor = [UIColor clearColor];
    companyAboutTextLabel.numberOfLines = 0;
    companyAboutTextLabel.font = CARD_INFO_ABOUT_TEXT_FONT;
    companyAboutTextLabel.textColor = CARD_INFO_ABOUT_TEXT_COLOR;
    [self.scrollView addSubview:companyAboutTextLabel];
}
- (void) configureViews
{
    NSLog(@"configure Views");
    
    companyAboutTextLabel.frame = [self companyAboutLabelFrame];
    self.scrollView.contentSize = [self scrollViewContentSize];
    
    companyNameLabel.text = self.currentCardDetail.companyName;
    companyAboutTextLabel.text = self.currentCardInfo.aboutText;
    
}
- (void) goBackToCardDetail
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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
