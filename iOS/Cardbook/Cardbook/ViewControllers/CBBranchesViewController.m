//
//  CBBranchesViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 16.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBBranchesViewController.h"
#import "CBUtil.h"
#import "CBCardInfo.h"

@interface CBBranchesViewController ()

@end

@implementation CBBranchesViewController

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
    
}
- (void) stylizeNavigationBar
{
    [self.navigationController.navigationBar setHidden:YES];
}
- (void) initializeTableView
{
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(15.0, 69.0, 290.0, SCREEN_SIZE.height-69.0)];
    [self.tableView setBackgroundColor:[UIColor clearColor]];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
//    [self.tableView registerClass:[CBMyCardsCell class] forCellReuseIdentifier:MY_CARDS_CELL_IDENTIFIER];
//    [self.tableView registerClass:[CBMyCardsCell class] forCellReuseIdentifier:MY_CARDS_LAST_CELL_IDENTIFIER];
    
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
//    return [myAllCards count];
    return 0;
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
//    CBMyCardsCell* cell;
//    if (indexPath.row == [tableView numberOfRowsInSection:indexPath.section]-1) {
//        cell = [tableView dequeueReusableCellWithIdentifier:MY_CARDS_LAST_CELL_IDENTIFIER];
//    } else {
//        cell = [tableView dequeueReusableCellWithIdentifier:MY_CARDS_CELL_IDENTIFIER];
//    }
//    
//    CBCard* currentCard = [myAllCards objectAtIndex:indexPath.row];
//    
//    [cell.nameLabel setText:[currentCard companyName]];
//    
//    [cell prepareForReuse];
//    cell.relatedCard = currentCard;
//    if ([currentCard companyImageUrl] != nil && ![[currentCard companyImageUrl] isEqualToString:@""]) {
//        [cell setImageOfTheCell:[currentCard companyImageUrl]];
//    }
    
    UITableViewCell* cell;
    
    return cell;
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
