//
//  CBHowToUseContentViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 26.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBHowToUseContentViewController.h"

@interface CBHowToUseContentViewController ()

@end

@implementation CBHowToUseContentViewController
{
    UIImage* image;
}

- (id)initWithUIImage:(UIImage *)_image
{
    if(self = [super init])
    {
        image = _image;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    UIImageView* imageView = [[UIImageView alloc] initWithImage:image];
    [imageView setFrame:CGRectMake(0.0, 0.0, self.view.frame.size.width, self.view.frame.size.height)];
    [imageView setContentMode:UIViewContentModeScaleAspectFill];
    [self.view insertSubview:imageView atIndex:0];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
