//
//  CBMyCardsViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBMyCardsViewController.h"
#import "CBMyCardsDetailViewController.h"
#import "CBUtil.h"
#import "CBMyCardsCell.h"

@interface CBMyCardsViewController ()

@end

@implementation CBMyCardsViewController

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
    [self stylizeForMainView];
    [self setTitleText:@"KARTLARIM"];
    [self initializeTableView];
    
//    UITapGestureRecognizer* tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapped)];
//    [self.view addGestureRecognizer:tap];
}
- (void) viewWillAppear:(BOOL)animated
{
    if (self.tableView.indexPathForSelectedRow) {
        [self.tableView deselectRowAtIndexPath:self.tableView.indexPathForSelectedRow animated:YES];
    }
}
- (void) initializeTableView
{
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(15.0, 69.0, 290.0, SCREEN_SIZE.height-69.0)];
    [self.tableView setBackgroundColor:[UIColor clearColor]];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    [self.tableView registerClass:[CBMyCardsCell class] forCellReuseIdentifier:MY_CARDS_CELL_IDENTIFIER];
    [self.tableView registerClass:[CBMyCardsCell class] forCellReuseIdentifier:MY_CARDS_LAST_CELL_IDENTIFIER];

    UIView* footerView = [UIView new];
    [footerView setFrame:CGRectMake(0.0, 0.0, 290.0, 49.0+15.0)];
    [footerView setBackgroundColor:[UIColor clearColor]];
    
    [self.tableView setTableFooterView:footerView];
    
    [self.tableView setShowsVerticalScrollIndicator:NO];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    [self.view insertSubview:self.tableView belowSubview:self.myNavigationBar];
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
    return 90.0;
}
- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    CBMyCardsCell* cell;
    if (indexPath.row == [tableView numberOfRowsInSection:indexPath.section]-1) {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_CARDS_LAST_CELL_IDENTIFIER];
    } else {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_CARDS_CELL_IDENTIFIER];
    }
    
    
    [cell.nameLabel setText:@"ADİDAS"];
    
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self performSegueWithIdentifier:@"MyCardsDetailsSegue" sender:self];
}
- (void) tapped
{
    [self performSegueWithIdentifier:@"MyCardsDetailsSegue" sender:self];
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
