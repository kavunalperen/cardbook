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
#import "APIManager.h"
#import "CBCompanyBranches.h"
#import "CBBranchCell.h"
#import "CBMapViewController.h"

@interface CBBranchesViewController ()

@end

@implementation CBBranchesViewController
{
    NSInteger itemCount;
    CBCompanyBranches* selectedBranch;
    CLLocationManager* locationManager;
    CLLocation* userLocation;
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if (self.allBranches == nil) {
        [[APIManager sharedInstance] getCompanyLocationListWithCompanyId:self.currentCardDetail.companyId onCompletion:^(NSDictionary *responseDictionary) {
            NSLog(@"response here");
//            self.allBranches = [CBCompanyBranches getAllBranchesForCompany:self.currentCardDetail.companyId];
            self.allBranches = [CBCompanyBranches getAllBranchesForCompany:11];
            [self.tableView reloadData];
        } onError:^(NSError *error) {
            NSLog(@"an error occured");
            [self.tableView reloadData];
        }];
    } else {
        [self.tableView reloadData];
    }
    
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self stylizeNavigationBar];
    [self stylizeForDetailView];
    [self setTitleButtonText:@"Kartlarım"];
    [self.titleButton addTarget:self action:@selector(goBackToCardDetail) forControlEvents:UIControlEventTouchUpInside];
    [self initializeTableView];
    
    locationManager = [[CLLocationManager alloc] init];
    locationManager.delegate = self;
    locationManager.distanceFilter = kCLDistanceFilterNone;
    locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    [locationManager startUpdatingLocation];
    
}
- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation {
    userLocation = newLocation;
    [self.tableView reloadData];
//    NSLog(@"OldLocation %f %f", oldLocation.coordinate.latitude, oldLocation.coordinate.longitude);
//    NSLog(@"NewLocation %f %f", newLocation.coordinate.latitude, newLocation.coordinate.longitude);
}
- (void) goBackToCardDetail
{
    [self.navigationController popViewControllerAnimated:YES];
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
    
    [self.tableView registerClass:[CBBranchCell class] forCellReuseIdentifier:BRANCH_CELL_NORMAL_CELL_IDENTIFIER];
    [self.tableView registerClass:[CBBranchCell class] forCellReuseIdentifier:BRANCH_CELL_LAST_CELL_IDENTIFIER];
    
    UIView* headerView = [UIView new];
    [headerView setBackgroundColor:[UIColor clearColor]];
    [headerView setFrame:CGRectMake(0.0, 0.0, 290.0, 39.0)];
    
    UIImageView* headerImage = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"branch_listheaderbg.png"]];
    headerImage.frame = CGRectMake(0.0, 0.0, 290.0, 39.0);
    headerImage.backgroundColor = [UIColor clearColor];
    [headerView addSubview:headerImage];
    
    UILabel* companyNameLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 10.0, 260.0, 19.0)];
    [companyNameLabel setBackgroundColor:[UIColor clearColor]];
    [companyNameLabel setFont:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT];
    [companyNameLabel setTextColor:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR];
    [companyNameLabel setText:self.currentCardDetail.companyName];
    [headerView addSubview:companyNameLabel];
    
    UIView* footerView = [UIView new];
    [footerView setFrame:CGRectMake(0.0, 0.0, 290.0, 49.0+15.0)];
    [footerView setBackgroundColor:[UIColor clearColor]];
    
    [self.tableView setTableFooterView:footerView];
    [self.tableView setTableHeaderView:headerView];
    
    [self.tableView setShowsVerticalScrollIndicator:NO];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    [self.view insertSubview:self.tableView belowSubview:self.myNavigationBar];
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    itemCount = (self.allBranches.count > 0) ? self.allBranches.count+1 : 0;
    return itemCount;
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == itemCount-1) {
        return 20.0;
    } else {
        return 62.0;
    }
}
- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    CBBranchCell* cell;
    
    if (indexPath.row == itemCount - 1) {
        cell = [tableView dequeueReusableCellWithIdentifier:BRANCH_CELL_LAST_CELL_IDENTIFIER];
    } else {
        CBCompanyBranches* currentBranch = [self.allBranches objectAtIndex:indexPath.row];
        
        cell = [tableView dequeueReusableCellWithIdentifier:BRANCH_CELL_NORMAL_CELL_IDENTIFIER];
        cell.relatedBranch = currentBranch;
        cell.nameLabel.text = currentBranch.branchName;
        cell.addressLabel.text = currentBranch.branchAddress;
        
        if (userLocation != nil) {
            if (currentBranch.latitude != nil && currentBranch.longitude != nil) {
                CLLocation* branchLocation = [[CLLocation alloc] initWithLatitude:[currentBranch.latitude doubleValue] longitude:[currentBranch.longitude doubleValue]];
                CLLocationDistance distance = [branchLocation distanceFromLocation:userLocation];
                NSInteger distanceInKm = round((distance/1000.0));
                
                cell.distanceLabel.text = [NSString stringWithFormat:@"%dkm",distanceInKm];
            } else {
            }
        } else {
        }
    }
    
    return cell;
}
- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row != itemCount-1) {
        selectedBranch = [self.allBranches objectAtIndex:indexPath.row];
        [self performSegueWithIdentifier:@"BranchToMapSegue" sender:self];
    }
}
- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqualToString:@"BranchToMapSegue"]) {
        CBMapViewController* dest = (CBMapViewController*)[segue destinationViewController];
        dest.currentBranch = selectedBranch;
    }
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
