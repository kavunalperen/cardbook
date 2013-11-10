//
//  CBMyCardsDetailViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBMyCardsDetailViewController.h"

@interface CBMyCardsDetailViewController ()

@end

@implementation CBMyCardsDetailViewController

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
    [self setTitleButtonText:@"KARTLARIM"];
    [self.titleButton addTarget:self action:@selector(goBackToCards) forControlEvents:UIControlEventTouchUpInside];
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
