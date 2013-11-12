//
//  CBShoppingViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBShoppingViewController.h"
#import "CBUtil.h"
#import "CBShoppingsCell.h"

@interface CBShoppingViewController ()

@end

@implementation CBShoppingViewController
{
    NSInteger itemCount;
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
    NSLog(@"shopping");
    [self stylizeNavigationBar];
    [self stylizeForMainView];
    [self setTitleText:@"ALIŞVERİŞLERİM"];
    [self initializeTableView];
}
- (void) stylizeNavigationBar
{
    [self.navigationController.navigationBar setHidden:YES];
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
    
    [self.tableView registerClass:[CBShoppingsCell class] forCellReuseIdentifier:MY_SHOPPINGS_CELL_FIRST_CELL_IDENTIFIER];
    [self.tableView registerClass:[CBShoppingsCell class] forCellReuseIdentifier:MY_SHOPPINGS_CELL_MIDDLE_CELL_IDENTIFIER];
    [self.tableView registerClass:[CBShoppingsCell class] forCellReuseIdentifier:MY_SHOPPINGS_CELL_LAST_CELL_IDENTIFIER];
    
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
    itemCount = 10;
    return itemCount;
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
//    NSInteger temprowcount = [self.tableView numberOfRowsInSection:0];
    if (indexPath.row == 0) {
        return 42.0;
    }
    else if (indexPath.row == itemCount-1) {
        return 20.0;
    } else {
        return 42.0;
    }
}
- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    CBShoppingsCell* cell;
    
    if (indexPath.row == 0) {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_SHOPPINGS_CELL_FIRST_CELL_IDENTIFIER];
        [cell.nameLabel setText:@"ADİDAS"];
        [cell.dateLabel setText:@"29 Ekim 2013"];
    } else if (indexPath.row == itemCount-1) {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_SHOPPINGS_CELL_LAST_CELL_IDENTIFIER];
    } else {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_SHOPPINGS_CELL_MIDDLE_CELL_IDENTIFIER];
        [cell.nameLabel setText:@"ADİDAS"];
        [cell.dateLabel setText:@"29 Ekim 2013"];
    }
    
    
    
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self performSegueWithIdentifier:@"MyShoppingsDetailSegue" sender:self];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
