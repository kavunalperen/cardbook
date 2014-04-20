//
//  CBMapViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBMapViewController.h"
#import "CBBranchLocation.h"
#import "CBUtil.h"
#import "CBCard.h"

#define METERS_PER_MILE 1609.344

@interface CBMapViewController ()

@end

@implementation CBMapViewController

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
    [self initCommonViews];
}
- (void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if (self.currentBranch != nil) {
        CLLocationCoordinate2D zoomLocation;
        zoomLocation.latitude = [self.currentBranch.latitude doubleValue];
        zoomLocation.longitude= [self.currentBranch.longitude doubleValue];
        
        MKCoordinateRegion viewRegion = MKCoordinateRegionMakeWithDistance(zoomLocation, 0.5*METERS_PER_MILE, 0.5*METERS_PER_MILE);
        
        // 3
        [self.mapView setRegion:viewRegion animated:YES];
        
        CBBranchLocation* myAnnotation = [[CBBranchLocation alloc] initWithCoordinate:zoomLocation andTitle:self.currentBranch.branchName andSubtitle:self.currentBranch.branchAddress];
        [self.mapView addAnnotation:myAnnotation];
        
        [self.mapView selectAnnotation:myAnnotation animated:YES];
        
//        self.companyName.text = [[CBCard GetCardWithCompanyId:self.currentBranch.companyId] companyName];
        self.companyName.text = [[CBCard GetCardWithCompanyId:10] companyName];
        
    }
}
- (void) initCommonViews
{
    self.mapView = [[MKMapView alloc] initWithFrame:self.view.frame];
    self.mapView.delegate = self;
    self.mapView.showsUserLocation = YES;
    [self.view addSubview:self.mapView];
    
    self.companyNameHolder = [[UIView alloc] initWithFrame:CGRectMake(15.0, 15.0, 221.0, 36.0)];
    self.companyNameHolder.backgroundColor = [UIColor clearColor];
    [self.mapView addSubview:self.companyNameHolder];
    
    UIImageView* imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"map_name_holder.png"]];
    imageView.backgroundColor = [UIColor clearColor];
    imageView.frame = CGRectMake(0.0, 0.0, 221.0, 36.0);
    [self.companyNameHolder addSubview:imageView];
    
    self.companyName = [[UILabel alloc] initWithFrame:CGRectMake(10.0, 0.0, 200.0, 36.0)];
    self.companyName.backgroundColor = [UIColor clearColor];
    [self.companyName setFont:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT];
    [self.companyName setTextColor:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR];
    [self.companyNameHolder addSubview:self.companyName];
    
    UIButton* closeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    closeButton.frame = CGRectMake(260.0, 15.0, 36.0, 36.0);
    closeButton.backgroundColor = [UIColor orangeColor];
    [closeButton addTarget:self action:@selector(closeMapView) forControlEvents:UIControlEventTouchUpInside];
    [self.mapView addSubview:closeButton];
    
}
- (void) closeMapView
{
    [self dismissViewControllerAnimated:YES completion:nil];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end