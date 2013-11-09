//
//  CBRegisterViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBRegisterViewController.h"
#import "CBUtil.h"
#import "APIManager.h"

@interface CBRegisterViewController ()

@end

@implementation CBRegisterViewController

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
    [self initializeTextFields];
    [self getAddressLists];
}
- (void) getAddressLists
{
    [[APIManager sharedInstance] getAddressListsWithCompletionBlock:^(NSDictionary *responseDictionary, NSError *error) {
        if (!error) {
            NSLog(@"address lists here");
        } else {
            NSLog(@"error while getting address lists");
        }
    }];
}
- (void) initializeComponents
{
    UIImage* backImage;
    if (IS_IPHONE_5) {
        backImage = [UIImage imageNamed:@"splash_bg-568h.jpg"];
    } else {
        backImage = [UIImage imageNamed:@"splash_bg.jpg"];
    }
    UIImageView* backgroundView = [[UIImageView alloc] initWithImage:backImage];
    [self.view addSubview:backgroundView];
    
    UIImage* logoImage;
    logoImage = [UIImage imageNamed:@"splash_logo.png"];
    UIImageView* logoView = [[UIImageView alloc] initWithImage:logoImage];
    [logoView setFrame:CGRectMake((SCREEN_SIZE.width-220.0)*0.5, 70.0, 220.0, 96.0)];
    [self.view addSubview:logoView];
    
}
- (void)initializeTextFields
{
    
}
- (void)createTextFieldWithName:(NSString*)name
{
    
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
