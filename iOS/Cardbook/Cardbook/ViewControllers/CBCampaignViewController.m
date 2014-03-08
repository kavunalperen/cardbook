//
//  CBCampaignViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBCampaignViewController.h"
#import "CBUtil.h"
#import "CBCampaignsCell.h"
#import "APIManager.h"
#import "CBCampaign.h"
#import "CBCard.h"
#import "CBMyCampaignsDetailViewController.h"

//static CBCampaignViewController* __lastInstance = nil;

@interface CBCampaignViewController ()

@end

@implementation CBCampaignViewController
{
    NSMutableArray* myAllCampaigns;
    CBCampaign* selectedCampaign;
}

//+ (CBCampaignViewController*) lastInstance
//{
//    return __lastInstance;
//}

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
    NSLog(@"campaign");
    [self stylizeNavigationBar];
    [self stylizeForMainView];
    [self setTitleText:@"KAMPANYALARIM"];
    [self initializeTableView];
    
//    __lastInstance = self;
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
    [self getCampaings];
}
- (void) getCampaings
{
    CBUtil* util = [CBUtil sharedInstance];
    BOOL shouldShowForACompany = util.shouldShowForACompany;
    NSInteger companyId = util.companyId;
    
    NSMutableArray* campaigns = [CBCampaign GetAllCampaigns];
    
    if (campaigns != nil && campaigns.count > 0 && shouldShowForACompany) {
        myAllCampaigns = [CBCampaign GetAllCampaignsForCompany:companyId];
        [self.tableView reloadData];
    } else {
        myAllCampaigns = [CBCampaign GetAllCampaigns];
        [self.tableView reloadData];
        if (myAllCampaigns == nil || myAllCampaigns.count == 0) {
            [[APIManager sharedInstance] getAllActiveCampaignListWithCompletionBlock:^(NSMutableArray* allCampaigns) {
                NSLog(@"response here");
                if (shouldShowForACompany) {
                    myAllCampaigns = [CBCampaign GetAllCampaignsForCompany:companyId];
                } else {
                    myAllCampaigns = allCampaigns;
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
    
    [self.tableView registerClass:[CBCampaignsCell class] forCellReuseIdentifier:MY_CAMPAIGNS_CELL_IDENTIFIER];
    
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
    return [myAllCampaigns count];
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
    CBCampaignsCell* cell;
    
    cell = [tableView dequeueReusableCellWithIdentifier:MY_CAMPAIGNS_CELL_IDENTIFIER];
    
    CBCampaign* currentCampaign = [myAllCampaigns objectAtIndex:indexPath.row];
    CBCard* relatedCard = [CBCard GetCardWithCompanyId:currentCampaign.companyId];
    
    [cell.nameLabel setText:[relatedCard companyName]];
    [cell.detailLabel setText:[currentCampaign campaignName]];
    [cell.dateLabel setText:[NSString stringWithFormat:@"%@ - %@",[currentCampaign getStartDateStr],[currentCampaign getEndDateStr]]];
    
    [cell prepareForReuse];
    cell.relatedCampaign = currentCampaign;
    if ([currentCampaign campaignIconUrl] != nil && ![[currentCampaign campaignIconUrl] isEqualToString:@""]) {
        [cell setImageOfTheCell:[currentCampaign campaignIconUrl]];
    }
    
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    selectedCampaign = [myAllCampaigns objectAtIndex:indexPath.row];
    
    [self performSegueWithIdentifier:@"MyCampaignsDetailsSegue" sender:self];
}
- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    CBMyCampaignsDetailViewController* campaignDetailController = [segue destinationViewController];
    
    [campaignDetailController setCurrentCampaignId:selectedCampaign.campaignId];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
@end
