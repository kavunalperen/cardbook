//
//  CBBaseViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBBaseViewController.h"
#import "CBUtil.h"
#import <QuartzCore/QuartzCore.h>

@interface CBBaseViewController ()

@end

@implementation CBBaseViewController
{
    UILabel* titleButtonLabel;
    UIImageView* backImageView;
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
    [self initializeComponents];
//    [self stylizeForMainView];
}
- (void)initializeComponents
{
    self.myNavigationBar = [[UIView alloc] initWithFrame:CGRectMake(15.0, 25.0, 290.0, 44.0)];
    [self.myNavigationBar setBackgroundColor:[UIColor clearColor]];
    UIImageView* navBackView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"main_navigation_bg.png"]];
    [self.myNavigationBar addSubview:navBackView];
    [self.view addSubview:self.myNavigationBar];
}
- (void)stylizeForMainView
{
    _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 0.0, 160.0, 44.0)];
    [_titleLabel setBackgroundColor:[UIColor clearColor]];
    [_titleLabel setFont:NAVBAR_TITLE_FONT];
    [_titleLabel setTextColor:NAVBAR_TITLE_TEXT_COLOR];
    [_titleLabel.layer setShadowColor:[UIColor whiteColor].CGColor];
    [_titleLabel.layer setShadowOffset:CGSizeMake(0.0, 1.0)];
    [_titleLabel.layer setShadowOpacity:0.4];
    [_titleLabel.layer setShadowRadius:0.0];
    [self.myNavigationBar addSubview:_titleLabel];
    
    UIImage* backImage;
    
    if (IS_IPHONE_5) {
        backImage = [UIImage imageNamed:@"main_bg-568h.jpg"];
    } else {
        backImage = [UIImage imageNamed:@"main_bg.jpg"];
    }
    
    backImageView = [[UIImageView alloc] initWithImage:backImage];
    [self.view insertSubview:backImageView belowSubview:self.myNavigationBar];
    
}
- (void)stylizeForDetailView
{
    _titleButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_titleButton setBackgroundColor:[UIColor clearColor]];
    [_titleButton setFrame:CGRectMake(15.0, 0.0, 160.0, 44.0)];
    
    UIImageView* backView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"back_btn.png"]];
    [backView setFrame:CGRectMake(0.0, 0.0, 10.0, 44.0)];
    [_titleButton addSubview:backView];
    
    titleButtonLabel = [[UILabel alloc] initWithFrame:CGRectMake(20.0, 0.0, 140.0, 44.0)];
    [titleButtonLabel setBackgroundColor:[UIColor clearColor]];
    [titleButtonLabel setFont:NAVBAR_DETAIL_TITLE_FONT];
    [titleButtonLabel setTextColor:NAVBAR_DETAIL_TITLE_TEXT_COLOR];
    [_titleLabel.layer setShadowColor:[UIColor whiteColor].CGColor];
    [_titleLabel.layer setShadowOffset:CGSizeMake(0.0, 1.0)];
    [_titleLabel.layer setShadowOpacity:0.4];
    [_titleLabel.layer setShadowRadius:0.0];
    [_titleButton addSubview:titleButtonLabel];
    [self.myNavigationBar addSubview:_titleButton];
    
    UIImage* backImage;
    
    if (IS_IPHONE_5) {
        backImage = [UIImage imageNamed:@"main_bg-568h.jpg"];
    } else {
        backImage = [UIImage imageNamed:@"main_bg.jpg"];
    }
    
    backImageView = [[UIImageView alloc] initWithImage:backImage];
    [self.view insertSubview:backImageView belowSubview:self.myNavigationBar];
    
}
- (void) setTitleText:(NSString*)text
{
    [_titleLabel setText:text];
}
- (void) setTitleButtonText:(NSString*)text
{
    [titleButtonLabel setText:text];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
