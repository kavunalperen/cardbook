//
//  CBBranchLocation.m
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBBranchLocation.h"

@implementation CBBranchLocation

@synthesize coordinate, title, subtitle;

- (id)initWithCoordinate:(CLLocationCoordinate2D)coord
                andTitle:(NSString*)t
             andSubtitle:(NSString*)st
{
    coordinate = coord;
    title = t;
    subtitle = st;
    return self;
}

@end
