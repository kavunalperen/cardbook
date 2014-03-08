//
//  CBLoginViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBLoginViewController.h"
#import "CBUtil.h"
#import "CBRegisterViewController.h"

@interface CBLoginViewController ()

@end

@implementation CBLoginViewController
{
    NSMutableDictionary* userInfos;
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
    [self initializeComponents];
}
- (void) viewWillAppear:(BOOL)animated
{
//    if (YES) {
//        CBRegisterViewController* registerView = [[CBRegisterViewController alloc] init];
//        [self presentViewController:registerView animated:NO completion:^{
//            ;
//        }];
//    }
}
- (void) initializeComponents
{
    UIImage* backImage;
    if (IS_IPHONE_5) {
        backImage = [UIImage imageNamed:@"splash_bg-568h.jpg"];
    } else {
        backImage = [UIImage imageNamed:@"splash_bg.jpg"];
    }
    UIImageView* backgroundView = [[UIImageView alloc] initWithImage:backImage];
    [self.view addSubview:backgroundView];
    
    UIImage* logoImage;
    logoImage = [UIImage imageNamed:@"splash_logo.png"];
    UIImageView* logoView = [[UIImageView alloc] initWithImage:logoImage];
    [logoView setFrame:CGRectMake((SCREEN_SIZE.width-220.0)*0.5, 140.0, 220.0, 96.0)];
    [self.view addSubview:logoView];
    
    UIButton* loginWithFacebookButton;
    loginWithFacebookButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [loginWithFacebookButton setFrame:CGRectMake((SCREEN_SIZE.width-241.0)*0.5, 277.0, 241.0, 47.0)];
    [loginWithFacebookButton setBackgroundImage:[UIImage imageNamed:@"splash_face_btn_normal.png"] forState:UIControlStateNormal];
    [loginWithFacebookButton setBackgroundImage:[UIImage imageNamed:@"splash_face_btn_highlighted.png"] forState:UIControlStateHighlighted];
    [loginWithFacebookButton addTarget:self action:@selector(loginWithFacebook) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:loginWithFacebookButton];
    
}
- (void) loginWithFacebook
{
    NSLog(@"login with facebook");
    [FBSession openActiveSessionWithReadPermissions:@[@"basic_info"] allowLoginUI:YES completionHandler:^(FBSession *session, FBSessionState status, NSError *error) {
        if (status == FBSessionStateOpen && !error) {
            [self sessionOpened];
        }
    }];
}
- (void) sessionOpened
{
    [FBRequestConnection startForMeWithCompletionHandler:^(FBRequestConnection *connection, id result, NSError *error) {
        if (!error) {
            userInfos = [NSMutableDictionary dictionary];
            NSString* firstName = [result objectForKey:@"first_name"];
            if (firstName == nil || [firstName isKindOfClass:[NSNull class]]) {
                firstName = @"";
            }
            NSString* lastName = [result objectForKey:@"last_name"];
            if (lastName == nil || [lastName isKindOfClass:[NSNull class]]) {
                lastName = @"";
            }
            NSString* gender = [result objectForKey:@"gender"];
            if (gender == nil || [gender isKindOfClass:[NSNull class]]) {
                gender = @"";
            }
            NSString* email = [result objectForKey:@"email"];
            if (email == nil || [email isKindOfClass:[NSNull class]]) {
                email = @"";
            }
            NSString* birthdate = [result objectForKey:@"birthday"];
            if (birthdate == nil || [birthdate isKindOfClass:[NSNull class]]) {
                birthdate = @"";
            }
            NSString* facebookId = [result objectForKey:@"id"];
            if (facebookId == nil || [facebookId isKindOfClass:[NSNull class]]) {
                facebookId = @"";
            }
            
            [userInfos setObject:firstName forKey:@"first_name"];
            [userInfos setObject:lastName forKey:@"last_name"];
            [userInfos setObject:gender forKey:@"gender"];
            [userInfos setObject:email forKey:@"email"];
            [userInfos setObject:birthdate forKey:@"birthdate"];
            [userInfos setObject:[NSString stringWithFormat:@"http://graph.facebook.com/%@/picture/style=large",facebookId] forKey:@"image_url"];
            [userInfos setObject:facebookId forKey:@"facebook_id"];
            [self performSegueWithIdentifier:@"LoginToRegisterSegue" sender:self];
        } else {
            // error occured handle
        }
    }];
}
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqualToString:@"LoginToRegisterSegue"]) {
        CBRegisterViewController* registerViewController = [segue destinationViewController];
        [registerViewController setUserInfos:userInfos];
    }
}
- (void)loginViewFetchedUserInfo:(FBLoginView *)loginView user:(id<FBGraphUser>)user
{
    NSLog(@"fetched user info");
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
