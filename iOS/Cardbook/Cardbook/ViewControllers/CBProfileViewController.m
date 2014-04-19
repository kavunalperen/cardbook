//
//  CBProfileViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBProfileViewController.h"
#import "CBUtil.h"
#import <QuartzCore/QuartzCore.h>
#import "CBUser.h"
#import "APIManager.h"
#import "CBRegisterViewController.h"

@interface CBProfileViewController ()

@end

@implementation CBProfileViewController
{
    UIImageView* profileImageView;
    UILabel* nameLabel;
    UIImageView* barcodeView;
    
    UILabel* emailLabel;
    UILabel* birthdateLabel;
    UILabel* genderLabel;
    UILabel* phoneLabel;
    UILabel* countryLabel;
    UILabel* cityLabel;
    UILabel* countyLabel;
    UILabel* addressLabel;
}
- (CGRect) scrollViewFrame
{
    return CGRectMake(15.0, 69.0, 290.0, SCREEN_SIZE.height-69.0);
}
- (CGSize) scrollViewContentSize
{
    return CGSizeMake(290.0, 435.0+49.0+15.0);
}
- (CGRect) scrollViewBackFrame
{
    return CGRectMake(0.0, 0.0, 290.0, 435.0);
}
- (CGRect) profileImageViewFrame
{
    return CGRectMake(15.0, 15.0, 90.0, 90.0);
}
- (CGRect) nameLabelFrame
{
    return CGRectMake(120.0, 15.0, 155.0, 42.0);
}
- (CGRect) barcodeViewFrame
{
    return CGRectMake(120.0, 58.0, 155.0, 43.0);
}
- (CGRect) infoHolderFrame
{
    return CGRectMake(20.0, 122.0, 250.0, 300.0);
}
- (CGRect) emailTitleLabelFrame
{
    return CGRectMake(0.0, 0.0, 90.0, 35.0);
}
- (CGRect) emailLabelFrame
{
    return CGRectMake(100.0, 0.0, 150.0, 35.0);
}
- (CGRect) birthdayTitleLabelFrame
{
    return CGRectMake(0.0, 35.0, 90.0, 35.0);
}
- (CGRect) birthdayLabelFrame
{
    return CGRectMake(100.0, 35.0, 150.0, 35.0);
}
- (CGRect) genderTitleLabelFrame
{
    return CGRectMake(0.0, 70.0, 90.0, 35.0);
}
- (CGRect) genderLabelFrame
{
    return CGRectMake(100.0, 70.0, 150.0, 35.0);
}
- (CGRect) phoneTitleLabelFrame
{
    return CGRectMake(0.0, 105.0, 90.0, 35.0);
}
- (CGRect) phoneLabelFrame
{
    return CGRectMake(100.0, 105.0, 150.0, 35.0);
}
- (CGRect) countryTitleLabelFrame
{
    return CGRectMake(0.0, 140.0, 90.0, 35.0);
}
- (CGRect) countryLabelFrame
{
    return CGRectMake(100.0, 140.0, 150.0, 35.0);
}
- (CGRect) cityTitleLabelFrame
{
    return CGRectMake(0.0, 175.0, 90.0, 35.0);
}
- (CGRect) cityLabelFrame
{
    return CGRectMake(100.0, 175.0, 150.0, 35.0);
}
- (CGRect) countyTitleLabelFrame
{
    return CGRectMake(0.0, 210.0, 90.0, 35.0);
}
- (CGRect) countyLabelFrame
{
    return CGRectMake(100.0, 210.0, 150.0, 35.0);
}
- (CGRect) addressTitleLabelFrame
{
    return CGRectMake(0.0, 245.0, 90.0, 35.0);
}
- (CGRect) addressLabelFrame
{
    return CGRectMake(100.0, 245.0, 150.0, 55.0);
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
	// Do any additional setup after loading the view.
    NSLog(@"profile");
    [self stylizeNavigationBar];
    [self stylizeForMainView];
    [self setTitleText:@"PROFİLİM"];
    [self initMainComponents];
    [self initHeaderComponents];
    [self initInfoComponents];
}
- (void) stylizeForMainView
{
    [super stylizeForMainView];
    
    UIButton* editButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [editButton setFrame:CGRectMake(246.0, 0.0, 44.0, 44.0)];
    [editButton setBackgroundColor:[UIColor clearColor]];
    [editButton setImage:[UIImage imageNamed:@"profile_update_btn_normal.png"] forState:UIControlStateNormal];
    [editButton setImage:[UIImage imageNamed:@"profile_update_btn_highlighted.png"] forState:UIControlStateHighlighted];
    [editButton setContentMode:UIViewContentModeCenter];
    [editButton addTarget:self action:@selector(updateProfile) forControlEvents:UIControlEventTouchUpInside];
    [self.myNavigationBar addSubview:editButton];
}
- (void) updateProfile
{
    CBRegisterViewController* registerVC = [[CBRegisterViewController alloc] init];
    registerVC.forUpdate = YES;
    [self.navigationController presentViewController:registerVC animated:YES completion:nil];
    NSLog(@"update profile");
}
- (void) viewWillAppear:(BOOL)animated
{
    [self fillViewsWithUserInfos];
}
- (void) fillViewsWithUserInfos
{
    CBUser* sharedUser = [CBUser sharedUser];
    
    [[APIManager sharedInstance] imageAtURL:[NSURL URLWithString:[sharedUser profilePictureUrl]] onCompletion:^(UIImage *fetchedImage, NSURL *url, BOOL isInCache) {
        [profileImageView setImage:fetchedImage];
    }];
    
    [nameLabel setText:[NSString stringWithFormat:@"%@ %@",sharedUser.name,sharedUser.surname]];
    
    [emailLabel setText:[sharedUser email]];
    
    [birthdateLabel setText:[sharedUser getMyBirthdateString]];
    
    [genderLabel setText:[sharedUser genderString]];
    
    [phoneLabel setText:[sharedUser phone]];
    
    [countryLabel setText:[sharedUser countryString]];
    
    [cityLabel setText:[sharedUser cityString]];
    
    [countyLabel setText:[sharedUser countyString]];
    
    [addressLabel setText:[sharedUser address]];
}
- (void) initMainComponents
{
    self.scrollView = [[UIScrollView alloc] initWithFrame:[self scrollViewFrame]];
    [self.scrollView setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:self.scrollView];
    [self.scrollView setContentSize:[self scrollViewContentSize]];
    [self.scrollView setShowsVerticalScrollIndicator:NO];
    
    UIImageView* backView = [[UIImageView alloc] initWithFrame:[self scrollViewBackFrame]];
    [backView setBackgroundColor:[UIColor clearColor]];
    [backView setImage:[UIImage imageNamed:@"profile_bg.png"]];
    [self.scrollView addSubview:backView];
}
- (void) initHeaderComponents
{
    profileImageView = [[UIImageView alloc] initWithFrame:[self profileImageViewFrame]];
    [profileImageView setBackgroundColor:[UIColor clearColor]];
    [profileImageView.layer setCornerRadius:7.0];
    [profileImageView.layer setBorderWidth:1.0];
    [profileImageView.layer setShouldRasterize:YES];
    [profileImageView.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [profileImageView.layer setBorderColor:PROFILE_IMAGE_BORDER_COLOR.CGColor];
    [profileImageView setClipsToBounds:YES];
    [self.scrollView addSubview:profileImageView];
    
    nameLabel = [[UILabel alloc] initWithFrame:[self nameLabelFrame]];
    [nameLabel setBackgroundColor:[UIColor clearColor]];
    [nameLabel setFont:PROFILE_NAME_LABEL_FONT];
    [nameLabel setTextColor:PROFILE_NAME_LABEL_TEXT_COLOR];
    [nameLabel setNumberOfLines:2];
//    [nameLabel setText:@"Egemen Ebuzer DURSUN"];
    [self.scrollView addSubview:nameLabel];
    
    barcodeView = [[UIImageView alloc] initWithFrame:[self barcodeViewFrame]];
    [barcodeView setBackgroundColor:[UIColor clearColor]];
    [barcodeView setClipsToBounds:YES];
    [barcodeView setImage:[UIImage imageNamed:@"profile_barcode.png"]];
    [barcodeView setUserInteractionEnabled:YES];
    [self.scrollView addSubview:barcodeView];
    
    UITapGestureRecognizer* tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(openBarcode)];
    [barcodeView addGestureRecognizer:tapGesture];
}
- (void) openBarcode
{
    [[CBUser sharedUser] openBarcodeFullScreen];
}
- (void) initInfoComponents
{
    UIView* infoHolder = [[UIView alloc] initWithFrame:[self infoHolderFrame]];
    [infoHolder setBackgroundColor:[UIColor clearColor]];
    [self.scrollView addSubview:infoHolder];
    
    UILabel* emailTitleLabel = [[UILabel alloc] initWithFrame:[self emailTitleLabelFrame]];
    [emailTitleLabel setBackgroundColor:[UIColor clearColor]];
    [emailTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [emailTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [emailTitleLabel setText:@"E-Posta"];
    [infoHolder addSubview:emailTitleLabel];
    
    emailLabel = [[UILabel alloc] initWithFrame:[self emailLabelFrame]];
    [emailLabel setBackgroundColor:[UIColor clearColor]];
    [emailLabel setFont:PROFILE_INFO_LABELS_FONT];
    [emailLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [emailLabel setText:@"eedursun@gmail.com"];
    [infoHolder addSubview:emailLabel];
    
    
    UILabel* birthdayTitleLabel = [[UILabel alloc] initWithFrame:[self birthdayTitleLabelFrame]];
    [birthdayTitleLabel setBackgroundColor:[UIColor clearColor]];
    [birthdayTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [birthdayTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [birthdayTitleLabel setText:@"Doğum Tarihi"];
    [infoHolder addSubview:birthdayTitleLabel];
    
    birthdateLabel = [[UILabel alloc] initWithFrame:[self birthdayLabelFrame]];
    [birthdateLabel setBackgroundColor:[UIColor clearColor]];
    [birthdateLabel setFont:PROFILE_INFO_LABELS_FONT];
    [birthdateLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [birthdateLabel setText:@"12 / 05 / 1977"];
    [infoHolder addSubview:birthdateLabel];
    
    UILabel* genderTitleLabel = [[UILabel alloc] initWithFrame:[self genderTitleLabelFrame]];
    [genderTitleLabel setBackgroundColor:[UIColor clearColor]];
    [genderTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [genderTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [genderTitleLabel setText:@"Cinsiyet"];
    [infoHolder addSubview:genderTitleLabel];
    
    genderLabel = [[UILabel alloc] initWithFrame:[self genderLabelFrame]];
    [genderLabel setBackgroundColor:[UIColor clearColor]];
    [genderLabel setFont:PROFILE_INFO_LABELS_FONT];
    [genderLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [genderLabel setText:@"Erkek"];
    [infoHolder addSubview:genderLabel];
    
    UILabel* phoneTitleLabel = [[UILabel alloc] initWithFrame:[self phoneTitleLabelFrame]];
    [phoneTitleLabel setBackgroundColor:[UIColor clearColor]];
    [phoneTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [phoneTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [phoneTitleLabel setText:@"Telefon No"];
    [infoHolder addSubview:phoneTitleLabel];
    
    phoneLabel = [[UILabel alloc] initWithFrame:[self phoneLabelFrame]];
    [phoneLabel setBackgroundColor:[UIColor clearColor]];
    [phoneLabel setFont:PROFILE_INFO_LABELS_FONT];
    [phoneLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [phoneLabel setText:@"+905323337420"];
    [infoHolder addSubview:phoneLabel];
    
    UILabel* countryTitleLabel = [[UILabel alloc] initWithFrame:[self countryTitleLabelFrame]];
    [countryTitleLabel setBackgroundColor:[UIColor clearColor]];
    [countryTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [countryTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [countryTitleLabel setText:@"Ülke"];
    [infoHolder addSubview:countryTitleLabel];
    
    countryLabel = [[UILabel alloc] initWithFrame:[self countryLabelFrame]];
    [countryLabel setBackgroundColor:[UIColor clearColor]];
    [countryLabel setFont:PROFILE_INFO_LABELS_FONT];
    [countryLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [countryLabel setText:@"Türkiye"];
    [infoHolder addSubview:countryLabel];
    
    UILabel* cityTitleLabel = [[UILabel alloc] initWithFrame:[self cityTitleLabelFrame]];
    [cityTitleLabel setBackgroundColor:[UIColor clearColor]];
    [cityTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [cityTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [cityTitleLabel setText:@"Şehir"];
    [infoHolder addSubview:cityTitleLabel];
    
    cityLabel = [[UILabel alloc] initWithFrame:[self cityLabelFrame]];
    [cityLabel setBackgroundColor:[UIColor clearColor]];
    [cityLabel setFont:PROFILE_INFO_LABELS_FONT];
    [cityLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [cityLabel setText:@"Ankara"];
    [infoHolder addSubview:cityLabel];
    
    UILabel* countyTitleLabel = [[UILabel alloc] initWithFrame:[self countyTitleLabelFrame]];
    [countyTitleLabel setBackgroundColor:[UIColor clearColor]];
    [countyTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [countyTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [countyTitleLabel setText:@"İlçe"];
    [infoHolder addSubview:countyTitleLabel];
    
    countyLabel = [[UILabel alloc] initWithFrame:[self countyLabelFrame]];
    [countyLabel setBackgroundColor:[UIColor clearColor]];
    [countyLabel setFont:PROFILE_INFO_LABELS_FONT];
    [countyLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
//    [countyLabel setText:@"Çankaya"];
    [infoHolder addSubview:countyLabel];
    
    UILabel* addressTitleLabel = [[UILabel alloc] initWithFrame:[self addressTitleLabelFrame]];
    [addressTitleLabel setBackgroundColor:[UIColor clearColor]];
    [addressTitleLabel setFont:PROFILE_INFO_TITLE_LABELS_FONT];
    [addressTitleLabel setTextColor:PROFILE_INFO_TITLE_LABELS_TEXT_COLOR];
    [addressTitleLabel setText:@"Adres"];
    [infoHolder addSubview:addressTitleLabel];
    
    addressLabel = [[UILabel alloc] initWithFrame:[self addressLabelFrame]];
    [addressLabel setBackgroundColor:[UIColor clearColor]];
    [addressLabel setFont:PROFILE_INFO_LABELS_FONT];
    [addressLabel setTextColor:PROFILE_INFO_LABELS_TEXT_COLOR];
    [addressLabel setNumberOfLines:2];
//    [addressLabel setText:@"1059 cd. No: 30 / 11 Aşağı Öveçler Çankaya / Ankara PK: 06543"];
    [infoHolder addSubview:addressLabel];
}
- (void) stylizeNavigationBar
{
    [self.navigationController.navigationBar setHidden:YES];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
