//
//  CBTabBarController.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBTabBarController.h"
#import "CBUtil.h"

@interface CBTabBarController ()

@end

@implementation CBTabBarController

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
    [self initializeBarItems];
}
- (void) initializeBarItems
{
    UITabBar* appTabbar = self.tabBar;
    
//    [[UITabBar appearance] setBarTintColor:[UIColor greenColor]];
    
    [appTabbar setBarTintColor:TABBAR_TINT_COLOR];
    [appTabbar setTranslucent:YES];
//    [appTabbar setBackgroundImage:[[UIImage alloc] init]];
//    [appTabbar setShadowImage:[[UIImage alloc] init]];
    
    [appTabbar setSelectionIndicatorImage:[[UIImage alloc] init]];
    
    NSArray* selectedImagesNames = @[@"tabicon_mycards_active.png",
                                     @"tabicon_campaign_active.png",
                                     @"tabicon_shopping_active.png",
                                     @"tabicon_profile_active.png"];
    
    NSArray* unselectedImagesNames = @[@"tabicon_mycards_normal.png",
                                       @"tabicon_campaign_normal.png",
                                       @"tabicon_shopping_normal.png",
                                       @"tabicon_profile_normal.png"];
    
    NSArray* items = [appTabbar items];
    int itemCount = [items count];
    
    for (int i = 0; i < itemCount; i++) {
        UITabBarItem* currentItem = [items objectAtIndex:i];
        [currentItem setImage:[UIImage imageNamed:[unselectedImagesNames objectAtIndex:i]]];
        [currentItem setSelectedImage:[UIImage imageNamed:[selectedImagesNames objectAtIndex:i]]];
//        [currentItem setFinishedSelectedImage:[UIImage imageNamed:[selectedImagesNames objectAtIndex:i]] withFinishedUnselectedImage:[UIImage imageNamed:[unselectedImagesNames objectAtIndex:i]]];
        
        [currentItem setTitleTextAttributes:
         [NSDictionary dictionaryWithObjectsAndKeys:
          TABBAR_PASSIVE_COLOR, NSForegroundColorAttributeName,
          TABBAR_NORMAL_FONT, NSFontAttributeName,
          nil] forState:UIControlStateNormal];
        
        [currentItem setTitleTextAttributes:
         [NSDictionary dictionaryWithObjectsAndKeys:
          TABBAR_ACTIVE_COLOR, NSForegroundColorAttributeName,
          TABBAR_SELECTED_FONT, NSFontAttributeName,
          nil] forState:UIControlStateSelected];
    }
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end