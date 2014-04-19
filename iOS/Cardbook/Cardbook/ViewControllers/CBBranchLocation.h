//
//  CBBranchLocation.h
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

@interface CBBranchLocation : NSObject <MKAnnotation> {
    CLLocationCoordinate2D coordinate;
    NSString* title;
    NSString* subtitle;
}

@property (nonatomic, readonly) CLLocationCoordinate2D coordinate;
@property (nonatomic, copy) NSString* title;
@property (nonatomic, copy) NSString* subtitle;

- (id)initWithCoordinate:(CLLocationCoordinate2D)coord
                andTitle:(NSString*)t
             andSubtitle:(NSString*)st;

@end
