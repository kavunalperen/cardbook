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
#import "APIManager.h"
#import "CBShopping.h"
#import "CBCard.h"
#import "CBShoppingDetailViewController.h"

@interface CBShoppingViewController ()

@end

@implementation CBShoppingViewController
{
    NSInteger itemCount;
    NSMutableArray* myAllShoppings;
    CBShopping* selectedShopping;
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
    [self getShoppings];
}
- (void) getShoppings
{
    CBUtil* util = [CBUtil sharedInstance];
    BOOL shouldShowForACompany = util.shouldShowForACompany;
    NSInteger companyId = util.companyId;
    
    NSMutableArray* shoppings = [CBShopping GetAllShoppings];
    
    if (shoppings != nil && shoppings.count > 0 && shouldShowForACompany) {
        myAllShoppings = [CBShopping GetAllShoppingsForCompany:companyId];
        [self.tableView reloadData];
    } else {
        myAllShoppings = [CBShopping GetAllShoppings];
        [self.tableView reloadData];
        if (myAllShoppings == nil || myAllShoppings.count == 0) {
            [[APIManager sharedInstance] getAllShoppingListWithCompletionBlock:^(NSMutableArray* allShoppings) {
                NSLog(@"response here");
                if (shouldShowForACompany) {
                    myAllShoppings = [CBShopping GetAllShoppingsForCompany:companyId];
                } else {
                    myAllShoppings = allShoppings;
                }
                [self.tableView reloadData];
            } onError:^(NSError *error) {
                NSLog(@"an error occured");
            }];
        }
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
    itemCount = [myAllShoppings count]+1;
    return itemCount;
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
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
    
    CBShopping* currentShopping;
    CBCard* relatedCard;
    
    if (indexPath.row == 0) {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_SHOPPINGS_CELL_FIRST_CELL_IDENTIFIER];
        currentShopping = [myAllShoppings objectAtIndex:indexPath.row];
        relatedCard = [CBCard GetCardWithCompanyId:currentShopping.companyId];
        [cell.nameLabel setText:[relatedCard companyName]];
        [cell.dateLabel setText:[currentShopping getDateStr]];
    } else if (indexPath.row == itemCount-1) {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_SHOPPINGS_CELL_LAST_CELL_IDENTIFIER];
    } else {
        cell = [tableView dequeueReusableCellWithIdentifier:MY_SHOPPINGS_CELL_MIDDLE_CELL_IDENTIFIER];
        currentShopping = [myAllShoppings objectAtIndex:indexPath.row];
        relatedCard = [CBCard GetCardWithCompanyId:currentShopping.companyId];
        [cell.nameLabel setText:[relatedCard companyName]];
        [cell.dateLabel setText:[currentShopping getDateStr]];
    }
    
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row != itemCount-1) {
        selectedShopping = [myAllShoppings objectAtIndex:indexPath.row];
        [self performSegueWithIdentifier:@"MyShoppingsDetailSegue" sender:self];
    }
}
- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    CBShoppingDetailViewController* shoppingDetailController = [segue destinationViewController];
    
    [shoppingDetailController setCurrentShoppingId:selectedShopping.shoppingId];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
