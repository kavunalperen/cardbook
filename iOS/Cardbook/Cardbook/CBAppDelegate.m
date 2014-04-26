//
//  CBAppDelegate.m
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBAppDelegate.h"
#import <FacebookSDK/FacebookSDK.h>
#import "CBLoginViewController.h"
#import "CBRegisterViewController.h"
#import "CBNavigationController.h"
#import "CBDummyViewController.h"
#import "CBUtil.h"
#import "CBTabBarController.h"
#import "APIManager.h"
#import "CBUser.h"
#import "CBShoppingDetailViewController.h"
#import "CBMyCampaignsDetailViewController.h"

@implementation CBAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
    // Override point for customization after application launch.
    
    NSBundle *bundle = [NSBundle mainBundle];
    
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:bundle];
    
    CBUser* sharedUser = [CBUser sharedUser];
    
    if (sharedUser != nil) {
        CBTabBarController* tabbarController = [storyboard instantiateViewControllerWithIdentifier:@"TabbarController"];
        self.window.rootViewController = tabbarController;
    } else {
        CBLoginViewController* loginView = [storyboard instantiateViewControllerWithIdentifier:@"LoginViewController"];
        self.window.rootViewController = loginView;
    }
    
    [self.window makeKeyAndVisible];
    
    [[UIApplication sharedApplication] setApplicationIconBadgeNumber:0];
    
    
    NSDictionary* userInfo = [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
    if(userInfo != NULL)
    {
        NSString* notificationType = [userInfo objectForKey:@"notificationType"];
        if ([notificationType isEqualToString:@"shopping"]) {
            NSNumber* shopping = [userInfo objectForKey:@"detailId"];
            if (shopping != nil) {
                NSInteger shoppingId = [shopping integerValue];
                
                CBShoppingDetailViewController* shoppingVC = [[CBShoppingDetailViewController alloc] initWithFromPushNotification:YES];
                shoppingVC.currentShoppingId = shoppingId;
                [self.window.rootViewController presentViewController:shoppingVC animated:YES completion:nil];
                
            }
        } else if ([notificationType isEqualToString:@"campaign"]) {
            NSNumber* campaign = [userInfo objectForKey:@"detailId"];
            if (campaign != nil) {
                NSInteger campaignId = [campaign integerValue];
                
                CBMyCampaignsDetailViewController* campaignVC = [[CBMyCampaignsDetailViewController alloc] initWithFromPushNotification:YES];
                campaignVC.currentCampaignId = campaignId;
                [self.window.rootViewController presentViewController:campaignVC animated:YES completion:nil];
            }
        }
    }
    
    
    return YES;
}
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    
    BOOL urlWasHandled = [FBAppCall handleOpenURL:url
                                sourceApplication:sourceApplication
                                  fallbackHandler:^(FBAppCall *call) {
                                      NSLog(@"Unhandled deep link: %@", url);
                                      // Here goes the code to handle the links
                                      // Use the links to show a relevant view of your app to the user
                                  }];
    
    return urlWasHandled;
}
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    CBUser* sharedUser = [CBUser sharedUser];
    
    if (sharedUser != nil) {
        NSString* deviceTokenStr = [deviceToken description];
        deviceTokenStr = [deviceTokenStr stringByReplacingOccurrencesOfString:@" " withString:@""];
        deviceTokenStr = [deviceTokenStr stringByReplacingOccurrencesOfString:@"<" withString:@""];
        deviceTokenStr = [deviceTokenStr stringByReplacingOccurrencesOfString:@">" withString:@""];
        
        if (deviceTokenStr != nil) {
            [[APIManager sharedInstance] updateMobileDeviceId:deviceTokenStr
                                                 onCompletion:^(NSDictionary *responseDictionary) {
                                                     NSLog(@"response here");
                                                 }
                                                      onError:^(NSError *error) {
                                                          NSLog(@"an error occured");
                                                      }];
        }
    }
}
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
        NSLog(@"Failed to get token: %@", error);
}
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
        NSLog(@"remote notification is received");
        NSLog(@"break");
    
    if (application.applicationState == UIApplicationStateInactive) {
        NSLog(@"launch from push notification");
        if (userInfo != nil) {
            NSString* notificationType = [userInfo objectForKey:@"notificationType"];
            if ([notificationType isEqualToString:@"shopping"]) {
                NSNumber* shopping = [userInfo objectForKey:@"detailId"];
                if (shopping != nil) {
                    NSInteger shoppingId = [shopping integerValue];
                    
                    CBShoppingDetailViewController* shoppingVC = [[CBShoppingDetailViewController alloc] initWithFromPushNotification:YES];
                    shoppingVC.currentShoppingId = shoppingId;
//                    dispatch_async(dispatch_get_main_queue(), ^{
                        [self.window.rootViewController presentViewController:shoppingVC animated:YES completion:nil];
//                    });
                    
                    
                    // shopping id here
                }
            } else if ([notificationType isEqualToString:@"campaign"]) {
                NSNumber* campaign = [userInfo objectForKey:@"detailId"];
                if (campaign != nil) {
                    NSInteger campaignId = [campaign integerValue];
                    
                    CBMyCampaignsDetailViewController* campaignVC = [[CBMyCampaignsDetailViewController alloc] initWithFromPushNotification:YES];
                    campaignVC.currentCampaignId = campaignId;
                    [self.window.rootViewController presentViewController:campaignVC animated:YES completion:nil];
                    // campaign id here
                }
            }
        }
        
    }
    
}
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}
//- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
//{
//    BOOL wasHandled = [FBAppCall handleOpenURL:url sourceApplication:sourceApplication];
//    
//    return wasHandled;
//}
- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
