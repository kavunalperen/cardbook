//
//  CBMapViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "CBCompanyBranches.h"

@interface CBMapViewController : UIViewController <MKMapViewDelegate>

@property MKMapView* mapView;
@property UIView* companyNameHolder;
@property UILabel* companyName;
@property CBCompanyBranches* currentBranch;

@end
