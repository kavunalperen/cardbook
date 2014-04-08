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

- (CGRect) scrollViewFrame
{
    return CGRectMake(15.0, 69.0, 290.0, SCREEN_SIZE.height-69.0);
}
- (CGSize) scrollViewContentSize
{
    return CGSizeMake(290.0, 435.0+49.0+15.0);
}
- (CGRect) scrollViewBackFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 435.0);
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
    self.scrollView = [[UIScrollView alloc] initWithFrame:[self scrollViewFrame]];
    [self.scrollView setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:self.scrollView];
    [self.scrollView setContentSize:[self scrollViewContentSize]];
    [self.scrollView setShowsVerticalScrollIndicator:NO];
    
    UIImageView* backView = [[UIImageView alloc] initWithFrame:[self scrollViewBackFrame]];
    [backView setBackgroundColor:[UIColor clearColor]];
    [backView setImage:[UIImage imageNamed:@"main_bg.png"]];
    [self.scrollView addSubview:backView];
}
- (void) configureViews
{
    
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
