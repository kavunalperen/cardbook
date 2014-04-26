//
//  CBHowToUsePageViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 26.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBHowToUsePageViewController.h"
#import "CBHowToUseContentViewController.h"

@interface CBHowToUsePageViewController () <UIPageViewControllerDelegate,UIPageViewControllerDataSource>

@property NSArray* contentViewControllers;
@property UIPageControl* pageControl;

@end

@implementation CBHowToUsePageViewController
{
    NSInteger currentIndex;
}

- (CGRect) pageControlFrame
{
    return CGRectMake(0.0, self.view.frame.size.height-110.0, 320.0,22.0);
}

+ (CBHowToUsePageViewController *)create
{
    NSDictionary *options = [NSDictionary dictionaryWithObject:
                             [NSNumber numberWithInteger:UIPageViewControllerSpineLocationMin]
                                                        forKey: UIPageViewControllerOptionSpineLocationKey];
    return [[CBHowToUsePageViewController alloc]
            initWithTransitionStyle:UIPageViewControllerTransitionStyleScroll
            navigationOrientation:UIPageViewControllerNavigationOrientationHorizontal
            options:options];
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
    
    [[UIApplication sharedApplication] setStatusBarHidden:YES];
    
//    UIView* indicator = [self.view.subviews objectAtIndex:0];
//    [indicator setFrame:CGRectMake(indicator.frame.origin.x, self.view.frame.size.height - 150.0, indicator.frame.size.width, indicator.frame.size.height)];
//    UIImageView* imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"gothroughbackground.png"]];
//    [imageView setFrame:CGRectMake(0.0, 0.0, self.view.frame.size.width, self.view.frame.size.height)];
//    [imageView setContentMode:UIViewContentModeCenter];
//    [self.view insertSubview:imageView atIndex:0];
    
    self.pageControl = [[UIPageControl alloc] initWithFrame:[self pageControlFrame]];
    [self.pageControl setNumberOfPages:5];
    [self.pageControl setCurrentPage:0];
//    [self.pageControl setIndicatorDiameter:10.0];
    [self.view addSubview:self.pageControl];
//    [self hidePageControl];
    
    self.dataSource = self;
    self.delegate = self;
    // Do any additional setup after loading the view.
    
    self.contentViewControllers = @[
                                    [[CBHowToUseContentViewController alloc] initWithUIImage:[UIImage imageNamed:@"ios001.png"]],
                                    [[CBHowToUseContentViewController alloc] initWithUIImage:[UIImage imageNamed:@"ios002.png"]],
                                    [[CBHowToUseContentViewController alloc] initWithUIImage:[UIImage imageNamed:@"ios003.png"]],
                                    [[CBHowToUseContentViewController alloc] initWithUIImage:[UIImage imageNamed:@"ios004.png"]],
                                    [[CBHowToUseContentViewController alloc] initWithUIImage:[UIImage imageNamed:@"ios005.png"]]
                                    ];
    
    CBHowToUseContentViewController* lastVc = (CBHowToUseContentViewController*)[self.contentViewControllers objectAtIndex:4];
    lastVc.view.userInteractionEnabled = YES;
    
    UITapGestureRecognizer* tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(closeHowToUse)];
    [lastVc.view addGestureRecognizer:tapGesture];
    
    [self setViewControllers:[NSArray arrayWithObject:[self.contentViewControllers objectAtIndex:0]] direction:UIPageViewControllerNavigationDirectionForward animated:YES completion:^(BOOL finished) {
        
    }];
}
- (void) closeHowToUse
{
    [self dismissViewControllerAnimated:NO completion:^{
        [[UIApplication sharedApplication] setStatusBarHidden:NO];
        [self.presentingVC performSegueWithIdentifier:@"RegisterToTabbarSegue" sender:self.presentingVC];
    }];
}
- (void)startGoThrough
{
    [self showViewController:[self.contentViewControllers objectAtIndex:0] direction:UIPageViewControllerNavigationDirectionForward];
    [self.pageControl setCurrentPage:0];
    [self showPageControl];
}
-(void) showPageControl
{
    [UIView animateWithDuration:0.1 animations:^{
        self.pageControl.alpha = 1.0;
    }];
}
- (void) hidePageControl
{
    [UIView animateWithDuration:0.1 animations:^{
        self.pageControl.alpha = 0.0;
    }];
}

- (void) showViewController:(UIViewController*)viewController direction:(UIPageViewControllerNavigationDirection)direction
{
    __block CBHowToUsePageViewController *blocksafeSelf = self;
    [self setViewControllers:@[viewController] direction:direction animated:YES completion:^(BOOL finished){
        if(finished)
        {
            dispatch_async(dispatch_get_main_queue(), ^{
                [blocksafeSelf setViewControllers:@[viewController] direction:direction animated:NO completion:NULL];// bug fix for uipageview controller
            });
        }
    }];
}


- (UIViewController *)pageViewController:(UIPageViewController *)pageViewController viewControllerAfterViewController:(UIViewController *)viewController
{
    
    for(int i=0;i<self.contentViewControllers.count ;i++)
    {
        if(viewController == [self.contentViewControllers objectAtIndex:i])
        {
            if(i+1 < self.contentViewControllers.count)
            {
                return [self.contentViewControllers objectAtIndex:i+1];
            }
        }
    }
    return nil;
}
- (UIViewController *)pageViewController:(UIPageViewController *)pageViewController viewControllerBeforeViewController:(UIViewController *)viewController
{
    
    for(int i=0;i<self.contentViewControllers.count ;i++)
    {
        if(viewController == [self.contentViewControllers objectAtIndex:i])
        {
            if(i-1 >= 0)
            {
                return [self.contentViewControllers objectAtIndex:i-1];
            }
        }
    }
    return nil;
}

//- (void)pageViewController:(UIPageViewController *)pageViewController willTransitionToViewControllers:(NSArray *)pendingViewControllers
//{
//    UIViewController* viewController = [pendingViewControllers objectAtIndex:0];
//    if(viewController == self.loginViewController || viewController == self.registerViewController)
//    {
//        [self hideLoginButton];
//        [self hidePageControl];
//    }
//    else
//    {
//        [self showLoginButton];
//    }
//    NSInteger index = [self.contentViewControllers indexOfObject:viewController];
//    index--;
//    if(index < 0)
//    {
//        [self hidePageControl];
//    }
//}

-(void)pageViewController:(UIPageViewController *)pageViewController didFinishAnimating:(BOOL)finished previousViewControllers:(NSArray *)previousViewControllers transitionCompleted:(BOOL)completed
{
    UIViewController* viewController = [self.viewControllers objectAtIndex:0];

    NSInteger index = [self.contentViewControllers indexOfObject:viewController];
    [self.pageControl setCurrentPage:index];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
