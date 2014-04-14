//
//  CBCompanyContactViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 14.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBCompanyContactViewController.h"
#import "CBUtil.h"

@interface CBCompanyContactViewController ()

@end

@implementation CBCompanyContactViewController
{
    UILabel* companyNameLabel;
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
- (CGRect) companyNameLabelFrame
{
    return CGRectMake(0.0, 0.0, 260.0, 35.0);
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
    [self setTitleButtonText:@"Kartlarım"];
    [self.titleButton addTarget:self action:@selector(goBackToCardDetail) forControlEvents:UIControlEventTouchUpInside];
    
    [self initCommonViews];
}

- (void) initCommonViews
{
    self.view.userInteractionEnabled = YES;
    UIImageView* backView = [[UIImageView alloc] initWithFrame:[self backgroundFrame]];
    [backView setBackgroundColor:[UIColor clearColor]];
    [backView setImage:[UIImage imageNamed:@"comp_contact_bg.png"]];
    backView.userInteractionEnabled = YES;
    [self.view addSubview:backView];
    
//    companyNameLabel = [[UILabel alloc] initWithFrame:[self companyNameLabelFrame]];
//    [companyNameLabel setBackgroundColor:[UIColor clearColor]];
//    [companyNameLabel setFont:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT];
//    [companyNameLabel setTextColor:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR];
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
