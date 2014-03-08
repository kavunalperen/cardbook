//
//  CBRegisterViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#define COUNTRY_PICKER_VIEW_TAG 2323
#define CITY_PICKER_VIEW_TAG 2324
#define COUNTY_PICKER_VIEW_TAG 2325

#import "CBRegisterViewController.h"
#import "CBUtil.h"
#import "APIManager.h"
#import "County.h"
#import "City.h"
#import "Country.h"
#import "NSDate+RFC1123.h"
#import "CBAppDelegate.h"
#import "CBTabBarController.h"
#import "CBNavigationController.h"
#import "CBUser.h"

@interface CBRegisterViewController ()

@end

@implementation CBRegisterViewController
{
    NSMutableArray* myCountries;
    NSInteger selectedCountryRow;
    NSInteger selectedCityRow;
    NSInteger selectedCountyRow;
    NSInteger selectedGenderRow;
    CGFloat currentYOffset;
    UIView* mainHolderView;
    UIView* pickerBackgroundView;
}
- (CGFloat) logoOffset
{
    if (IS_IPHONE_5) {
        return 70.0;
    } else {
        return 30.0;
    }
}
- (CGRect) scrollViewFrame
{
    return CGRectMake(0.0, 0.0, SCREEN_SIZE.width, SCREEN_SIZE.height);
}
- (CGRect) logoFrame
{
    return CGRectMake((SCREEN_SIZE.width-220.0)*0.5, currentYOffset, 220.0, 96.0);
}
- (CGRect) mainHolderFrame
{
    return CGRectMake(15.0, currentYOffset+96.0+20, SCREEN_SIZE.width-30.0, 320.0);
}
- (CGRect) profilePictureFrame
{
    return CGRectMake(0.0, 0.0, 65.0, 65.0);
}
- (CGRect) namefieldFrame
{
    return CGRectMake(70.0, 0.0, 220.0, 30.0);
}
- (CGRect) surnameFieldFrame
{
    return CGRectMake(70.0, 35.0, 220.0, 30.0);
}
- (CGRect) emailFieldFrame
{
    return CGRectMake(0.0, 70.0, 290.0, 30.0);
}
- (CGRect) birthDateFrame
{
    return CGRectMake(0.0, 105.0, 142.0, 30.0);
}
- (CGRect) genderFieldFrame
{
    return CGRectMake(148.0, 105.0, 142.0, 30.0);
}
- (CGRect) phoneNumberFieldFrame
{
    return CGRectMake(0.0, 140.0, 290.0, 30.0);
}
- (CGRect) countryFieldFrame
{
    return CGRectMake(0.0, 175.0, 290.0, 30.0);
}
- (CGRect) cityFieldFrame
{
    return CGRectMake(0.0, 210.0, 142.0, 30.0);
}
- (CGRect) countyFieldFrame
{
    return CGRectMake(148.0, 210.0, 142.0, 30.0);
}
- (CGRect) addressFieldFrame
{
    return CGRectMake(0.0, 245.0, 290.0, 30.0);
}
- (CGRect) updateButtonFrame
{
    return CGRectMake(210.0, 290.0, 80.0, 30.0);
}
- (CGRect) pickerBackgroundViewFrame
{
    return CGRectMake(0.0, 0.0, SCREEN_SIZE.width, SCREEN_SIZE.height);
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
    currentYOffset = [self logoOffset];
    [self initializeComponents];
    [self initializePickerViews];
    [self initializeProfilePictureAndTextFields];
    [self getAddressLists];
}
- (void) viewWillAppear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification object:self.view.window];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification object:self.view.window];
    [self configureViews];
}
- (void) configureViews
{
    
    NSString* firstName = [self.userInfos objectForKey:@"first_name"];
    NSString* lastName = [self.userInfos objectForKey:@"last_name"];
    NSString* gender = [self.userInfos objectForKey:@"gender"];
    NSString* birthdate = [self.userInfos objectForKey:@"birthdate"];
    NSString* email = [self.userInfos objectForKey:@"email"];
    NSString* imageUrl = [self.userInfos objectForKey:@"image_url"];
    
    [[APIManager sharedInstance] getImageWithURLString:imageUrl
                                          onCompletion:^(UIImage *resultImage) {
                                              [self.profilePictureView setImage:resultImage];
                                          }
                                               onError:^(NSError *error) {
                                                   NSLog(@"error occured while getting image");
                                               }];
    
    [self.nameField setText:firstName];
    [self.surnameField setText:lastName];
    if ([gender isEqualToString:@"male"]) {
        [self setSelectedGenderRow:0];
    } else {
        [self setSelectedGenderRow:1];
    }
    
    NSDateFormatter* dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"MM/dd/yyyy"];
    NSDate* bDate = [dateFormatter dateFromString:birthdate];
    
    [self.birthDatePicker setDate:bDate animated:NO];
    [self datePickerChanged:self];
    [self.emailField setText:email];
}
- (void) viewWillDisappear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
- (void)keyboardWillShow:(NSNotification *)notif {
    NSDictionary* userInfo = [notif userInfo];
    
    // Get animation info from userInfo
    NSTimeInterval animationDuration;
    UIViewAnimationCurve animationCurve;
    
//    CGFloat yOffset = IS_IPHONE_5 ? -200.0 : -160.0;
    CGFloat yOffset = -180.0;
    
    [[userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey] getValue:&animationCurve];
    [[userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey] getValue:&animationDuration];
    [UIView animateWithDuration:animationDuration
                     animations:^{
                         CGRect scrollFrame = self.scrollView.frame;
                         scrollFrame.size.height += yOffset;
                         self.scrollView.frame = scrollFrame;
                     }];
}

- (void)keyboardWillHide:(NSNotification *)notif {
    NSDictionary* userInfo = [notif userInfo];
    
    // Get animation info from userInfo
    NSTimeInterval animationDuration;
    UIViewAnimationCurve animationCurve;
    
    CGFloat yOffset = -180.0;
    
    [[userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey] getValue:&animationCurve];
    [[userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey] getValue:&animationDuration];
    
    [UIView animateWithDuration:animationDuration
                     animations:^{
                         CGRect scrollFrame = self.scrollView.frame;
                         scrollFrame.size.height -= yOffset;
                         self.scrollView.frame = scrollFrame;
                     }];
}
- (void) getAddressLists
{
    [[APIManager sharedInstance] getAddressListsWithCompletionBlock:^(NSMutableArray *allCountries) {
        NSLog(@"addressLists here");
        myCountries = allCountries;
        [self.countryField setEnabled:YES];
        [self.countryPickerView reloadAllComponents];
    } onError:^(NSError *error) {
        NSLog(@"error occured");
    }];
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
    
    self.scrollView = [[UIScrollView alloc] initWithFrame:[self scrollViewFrame]];
    [self.scrollView setBackgroundColor:[UIColor clearColor]];
    [self.scrollView setContentSize:CGSizeMake(SCREEN_SIZE.width, currentYOffset+96.0+20.0+320.0)];
    [self.view addSubview:self.scrollView];
    
    UIImage* logoImage;
    logoImage = [UIImage imageNamed:@"splash_logo.png"];
    UIImageView* logoView = [[UIImageView alloc] initWithImage:logoImage];
    [logoView setFrame:[self logoFrame]];
    [self.scrollView addSubview:logoView];
    
}
- (void) pickerBackgroundTapped
{
    [self.countryPickerView setAlpha:0.0];
    [self.cityPickerView setAlpha:0.0];
    [self.countyPickerView setAlpha:0.0];
    [self.genderPickerView setAlpha:0.0];
    [self.birthDatePicker setAlpha:0.0];
    [pickerBackgroundView setAlpha:0.0];
}
- (void) initializePickerViews
{
    
    pickerBackgroundView = [[UIView alloc] initWithFrame:[self pickerBackgroundViewFrame]];
    [pickerBackgroundView setBackgroundColor:REGISTER_VIEW_PICKER_BACKGROUND_COLOR];
    UITapGestureRecognizer* tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(pickerBackgroundTapped)];
    [pickerBackgroundView addGestureRecognizer:tap];
    
    [pickerBackgroundView setAlpha:0.0];
    [self.view insertSubview:pickerBackgroundView atIndex:100];
    
    self.countryPickerView = [[UIPickerView alloc] initWithFrame:CGRectMake((SCREEN_SIZE.width-240.0)*0.5, (SCREEN_SIZE.height-150.0)*0.5, 240.0, 150.0)];
    [self.countryPickerView setBackgroundColor:[UIColor whiteColor]];
    self.countryPickerView.delegate = self;
    self.countryPickerView.dataSource = self;
    [self.countryPickerView setShowsSelectionIndicator:YES];
    [self.countryPickerView.layer setCornerRadius:10.0];
    [self.countryPickerView setAlpha:0.0];
    [pickerBackgroundView addSubview:self.countryPickerView];
    
    self.cityPickerView = [[UIPickerView alloc] initWithFrame:CGRectMake((SCREEN_SIZE.width-240.0)*0.5, (SCREEN_SIZE.height-150.0)*0.5, 240.0, 150.0)];
    [self.cityPickerView setBackgroundColor:[UIColor whiteColor]];
    self.cityPickerView.delegate = self;
    self.cityPickerView.dataSource = self;
    [self.cityPickerView setShowsSelectionIndicator:YES];
    [self.cityPickerView.layer setCornerRadius:10.0];
    [self.cityPickerView setAlpha:0.0];
    [pickerBackgroundView addSubview:self.cityPickerView];
    
    self.countyPickerView = [[UIPickerView alloc] initWithFrame:CGRectMake((SCREEN_SIZE.width-240.0)*0.5, (SCREEN_SIZE.height-150.0)*0.5, 240.0, 150.0)];
    [self.countyPickerView setBackgroundColor:[UIColor whiteColor]];
    self.countyPickerView.delegate = self;
    self.countyPickerView.dataSource = self;
    [self.countyPickerView setShowsSelectionIndicator:YES];
    [self.countyPickerView.layer setCornerRadius:10.0];
    [self.countyPickerView setAlpha:0.0];
    [pickerBackgroundView addSubview:self.countyPickerView];
    
    self.genderPickerView = [[UIPickerView alloc] initWithFrame:CGRectMake((SCREEN_SIZE.width-240.0)*0.5, (SCREEN_SIZE.height-150.0)*0.5, 240.0, 150.0)];
    [self.genderPickerView setBackgroundColor:[UIColor whiteColor]];
    self.genderPickerView.delegate = self;
    self.genderPickerView.dataSource = self;
    [self.genderPickerView setShowsSelectionIndicator:YES];
    [self.genderPickerView.layer setCornerRadius:10.0];
    [self.genderPickerView setAlpha:0.0];
    [pickerBackgroundView addSubview:self.genderPickerView];
    
    self.birthDatePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0.0, (SCREEN_SIZE.height-150.0)*0.5, 320.0, 150.0)];
    [self.birthDatePicker setBackgroundColor:[UIColor whiteColor]];
    [self.birthDatePicker setDatePickerMode:UIDatePickerModeDate];
    NSDateFormatter* dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setTimeZone:[NSTimeZone timeZoneWithAbbreviation:@"UTC"]];
    [dateFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"];
    NSDate* minimumDate = [dateFormatter dateFromString:@"1900-01-01T00:00:00.000Z"];
    NSDate* maximumDate = [dateFormatter dateFromString:@"2010-31-12T00:00:00.000Z"];
    [self.birthDatePicker setMinimumDate:minimumDate];
    [self.birthDatePicker setMaximumDate:maximumDate];
    [self.birthDatePicker.layer setCornerRadius:10.0];
    [self.birthDatePicker setAlpha:0.0];
    [self.birthDatePicker addTarget:self action:@selector(datePickerChanged:) forControlEvents:UIControlEventValueChanged];
    [pickerBackgroundView addSubview:self.birthDatePicker];
    
    selectedCountryRow = NSIntegerMin;
    selectedCityRow = NSIntegerMin;
    selectedCountyRow = NSIntegerMin;
    selectedGenderRow = NSIntegerMin;
}
- (void) datePickerChanged:(id)sender
{
    NSDate* selectedDate = [self.birthDatePicker date];
    
    NSCalendar* calendar = [NSCalendar currentCalendar];
    NSDateComponents* components = [calendar components:NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit fromDate:selectedDate]; // Get necessary date components
    NSInteger monthInt = [components month];
    NSInteger dayInt = [components day];
    NSInteger yearInt = [components year];
    
    [self.birthDateField setText:[NSString stringWithFormat:@"%ld / %ld / %ld",(long)dayInt, (long)monthInt, (long)yearInt]];
}
- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    return 240.0;
}
- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return 30.0;
}
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 1;
}
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    NSInteger numberOfRows;
    if (pickerView == self.genderPickerView) {
        numberOfRows = 2;
    } else if (pickerView == self.countryPickerView) {
        numberOfRows = [myCountries count];
    } else if (pickerView == self.cityPickerView){
        numberOfRows = [[[myCountries objectAtIndex:selectedCountryRow] cities] count];
    } else {
        numberOfRows = [[[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:selectedCityRow] counties] count];
    }
    
    return numberOfRows;
}
- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    NSString* titleForRow;
    if (pickerView == self.genderPickerView) {
        if (row == 0) {
            titleForRow = @"Erkek";
        } else {
            titleForRow = @"Kadın";
        }
    } else if (pickerView == self.countryPickerView) {
        titleForRow = [[myCountries objectAtIndex:row] countryName];
    } else if(pickerView == self.cityPickerView) {
        titleForRow = [[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:row] cityName];
    } else {
        titleForRow = [[[[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:selectedCityRow] counties] objectAtIndex:row] countyName];
    }
    
    return titleForRow;
}
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (pickerView == self.genderPickerView) {
        [self setSelectedGenderRow:row];
    } else if (pickerView == self.countryPickerView) {
        [self setSelectedCountryRow:row];
    } else if (pickerView == self.cityPickerView) {
        [self setSelectedCityRow:row];
    } else {
        [self setSelectedCountyRow:row];
    }
}
- (void)initializeProfilePictureAndTextFields
{
    
    CBUtil* sharedUtil = [CBUtil sharedInstance];
    
    mainHolderView = [[UIView alloc] initWithFrame:[self mainHolderFrame]];
    [mainHolderView setBackgroundColor:[UIColor clearColor]];
    [self.scrollView addSubview:mainHolderView];
    
    self.profilePictureView = [[UIImageView alloc] initWithFrame:[self profilePictureFrame]];
    [self.profilePictureView.layer setCornerRadius:5.0];
    [self.profilePictureView setBackgroundColor:[UIColor whiteColor]];
    [self.profilePictureView setClipsToBounds:YES];
    [mainHolderView addSubview:self.profilePictureView];
    
    self.nameField = [self createTextFieldWithFrame:[self namefieldFrame] andPlaceHolderText:@"İsim"];
    [mainHolderView addSubview:self.nameField];
    self.surnameField = [self createTextFieldWithFrame:[self surnameFieldFrame] andPlaceHolderText:@"Soyisim"];
    [mainHolderView addSubview:self.surnameField];
    self.emailField = [self createTextFieldWithFrame:[self emailFieldFrame] andPlaceHolderText:@"E-Posta"];
    [mainHolderView addSubview:self.emailField];
    self.birthDateField = [self createTextFieldWithFrame:[self birthDateFrame] andPlaceHolderText:@"Doğum Tarihi"];
    [mainHolderView addSubview:self.birthDateField];
    self.genderField = [self createTextFieldWithFrame:[self genderFieldFrame] andPlaceHolderText:@"Cinsiyet"];
    [self.genderField setEnabled:YES];
    [mainHolderView addSubview:self.genderField];
    self.phoneNumberField = [self createTextFieldWithFrame:[self phoneNumberFieldFrame] andPlaceHolderText:@"Telefon Numarası"];
    [self.phoneNumberField setKeyboardType:UIKeyboardTypePhonePad];
    [mainHolderView addSubview:self.phoneNumberField];
    self.countryField = [self createTextFieldWithFrame:[self countryFieldFrame] andPlaceHolderText:@"Ülke"];
    [self.countryField setEnabled:NO];
    [mainHolderView addSubview:self.countryField];
    self.cityField = [self createTextFieldWithFrame:[self cityFieldFrame] andPlaceHolderText:@"Şehir"];
    [self.cityField setEnabled:NO];
    [mainHolderView addSubview:self.cityField];
    self.countyField = [self createTextFieldWithFrame:[self countyFieldFrame] andPlaceHolderText:@"İlçe"];
    [self.countyField setEnabled:NO];
    [mainHolderView addSubview:self.countyField];
    self.addressField = [self createTextFieldWithFrame:[self addressFieldFrame] andPlaceHolderText:@"Adres"];
    [mainHolderView addSubview:self.addressField];
    
    self.updateButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.updateButton setFrame:[self updateButtonFrame]];
    [self.updateButton.layer setCornerRadius:5.0];
    [self.updateButton setClipsToBounds:YES];
    [self.updateButton setBackgroundImage:[sharedUtil UIImageWithUIColor:REGISTER_VIEW_BUTTON_NORMAL_COLOR] forState:UIControlStateNormal];
    [self.updateButton setBackgroundImage:[sharedUtil UIImageWithUIColor:REGISTER_VIEW_BUTTON_HIGHLIGHTED_COLOR] forState:UIControlStateHighlighted];
    [self.updateButton.titleLabel setFont:REGISTER_VIEW_BUTTON_FONT];
    [self.updateButton setTitleColor:REGISTER_VIEW_BUTTON_TITLE_COLOR forState:UIControlStateNormal];
    [self.updateButton setTitleColor:REGISTER_VIEW_BUTTON_TITLE_COLOR forState:UIControlStateHighlighted];
    [self.updateButton setTitle:@"Güncelle" forState:UIControlStateNormal];
    [self.updateButton setTitle:@"Güncelle" forState:UIControlStateNormal];
    [self.updateButton addTarget:self action:@selector(updateInfo) forControlEvents:UIControlEventTouchUpInside];
    [mainHolderView addSubview:self.updateButton];
    
}
- (void) updateInfo
{
    NSString* facebookId = [self.userInfos objectForKey:@"facebook_id"];
    NSString* image_url = [self.userInfos objectForKey:@"image_url"];
    NSString* name = [self.nameField text];
    NSString* surname = [self.surnameField text];
    NSString* email = [self.emailField text];
    NSDate* birthdate = [self.birthDatePicker date];
    NSString* gender = (selectedGenderRow == 0) ? @"M" : @"F";
    NSString* phoneNumber = [self.phoneNumberField text];
    NSInteger selectedCountry = [[myCountries objectAtIndex:selectedCountryRow] countryId];
    NSInteger selectedCity = [[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:selectedCityRow] cityId];
    NSInteger selectedCounty = [[[[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:selectedCityRow] counties] objectAtIndex:selectedCountyRow] countyId];
    NSString* addressLine = [self.addressField text];
    
    NSCalendar* calendar = [NSCalendar currentCalendar];
    NSDateComponents* components = [calendar components:NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit fromDate:birthdate]; // Get necessary date components
    NSInteger monthInt = [components month];
    NSInteger dayInt = [components day];
    NSInteger yearInt = [components year];
    
    NSString* birthDateStr = [NSString stringWithFormat:@"%ld-%ld-%ld",(long)dayInt,(long)monthInt,(long)yearInt];
    
    [[APIManager sharedInstance] createOrUpdateUserWithFacebookId:facebookId
            andMobileDeviceId:@"1234567899"
                      andName:name
                   andSurname:surname
                     andEmail:email
                 andBirthDate:birthDateStr
         andProfilePictureUrl:image_url
                    andPhone1:phoneNumber
                    andPhone2:@""
                    andGender:gender
                 andCountryId:selectedCountry
                    andCityId:selectedCity
                  andCountyId:selectedCounty
               andAddressLine:addressLine
                 onCompletion:^(NSDictionary *responseDictionary) {
                     NSDictionary* data = [responseDictionary objectForKey:@"Data"];
                     [CBUser CBUserWithDictionary:data];
                     [[APIManager sharedInstance] getUserDetailWithCompletion:^(NSDictionary *responseDictionary) {
                         NSLog(@"response here");
                         NSString* barcodeUrl = [[responseDictionary objectForKey:@"Data"] objectForKey:@"UserBarcodeUrl"];
                         [CBUser setAndSaveBarcodeUrl:barcodeUrl];
                         [[APIManager sharedInstance] getImageWithURLString:barcodeUrl
                                                               onCompletion:^(UIImage *resultImage) {
                                                                   // image here handle!
//                                                                   [CBUser setAndSaveBarcodeImage:resultImage];
                                                                   [self performSegueWithIdentifier:@"RegisterToTabbarSegue" sender:self];
                                                               }
                                                                    onError:^(NSError *error) {
                                                                        [self performSegueWithIdentifier:@"RegisterToTabbarSegue" sender:self];
                                                                    }];
                         
                     } onError:^(NSError *error) {
                         NSLog(@"an error occured");
                     }];
                 } onError:^(NSError *error) {
                     // error handling here
                 }];
}
- (CBTextField*)createTextFieldWithFrame:(CGRect)frame andPlaceHolderText:(NSString*)placeHolderText
{
    CBTextField* textField = [[CBTextField alloc] initWithFrame:frame andForRegister:YES];
    [textField setPlaceholder:placeHolderText];
    textField.delegate = self;
    
    return textField;
}
- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField
{
    if (textField == self.countryField) {
        // open country picker
        [self.countryPickerView setAlpha:1.0];
        [pickerBackgroundView setAlpha:1.0];
        if (selectedCountryRow == NSIntegerMin) {
            [self setSelectedCountryRow:0];
        }
        [self.view endEditing:YES];
        return NO;
    } else if (textField == self.cityField){
        // open city picker
        [self.cityPickerView setAlpha:1.0];
        [pickerBackgroundView setAlpha:1.0];
        if (selectedCityRow == NSIntegerMin) {
            [self setSelectedCityRow:0];
        }
        [self.view endEditing:YES];
        return NO;
    } else if (textField == self.countyField) {
        // open county picker
        [self.countyPickerView setAlpha:1.0];
        [pickerBackgroundView setAlpha:1.0];
        if (selectedCountyRow == NSIntegerMin) {
            [self setSelectedCountyRow:0];
        }
        [self.view endEditing:YES];
        return NO;
    } else if (textField == self.genderField) {
        // open gender picker
        [self.genderPickerView setAlpha:1.0];
        [pickerBackgroundView setAlpha:1.0];
        if (selectedGenderRow == NSIntegerMin) {
            [self setSelectedGenderRow:0];
        }
        [self.view endEditing:YES];
        return NO;
    } else if (textField == self.birthDateField) {
        // open birthdate date picker
        [self.birthDatePicker setAlpha:1.0];
        [pickerBackgroundView setAlpha:1.0];
        return NO;
    } else {
        return YES;
    }
}
- (void) setSelectedCountryRow:(NSInteger)row
{
    selectedCountryRow = row;
    selectedCityRow = 0;
    selectedCountyRow = 0;
    [self.countryField setText:[[myCountries objectAtIndex:row] countryName]];
    [self.cityField setEnabled:YES];
    [self.cityPickerView reloadAllComponents];
    [self.cityPickerView selectRow:selectedCityRow inComponent:0 animated:NO];
    [self pickerView:self.cityPickerView didSelectRow:selectedCityRow inComponent:0];
//    [self pickerView:self.countyPickerView didSelectRow:selectedCountyRow inComponent:0];
}
- (void) setSelectedCityRow:(NSInteger)row
{
    selectedCityRow = row;
    selectedCountyRow = 0;
    [self.cityField setText:[[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:selectedCityRow] cityName]];
    [self.countyField setEnabled:YES];
    [self.countyPickerView reloadAllComponents];
    [self.countyPickerView selectRow:selectedCountyRow inComponent:0 animated:NO];
    [self pickerView:self.countyPickerView didSelectRow:selectedCountyRow inComponent:0];
    
}
- (void) setSelectedCountyRow:(NSInteger)row
{
    selectedCountyRow = row;
    [self.countyField setText:[[[[[[myCountries objectAtIndex:selectedCountryRow] cities] objectAtIndex:selectedCityRow] counties] objectAtIndex:selectedCountyRow] countyName]];
}
- (void) setSelectedGenderRow:(NSInteger)row
{
    selectedGenderRow = row;
    [self.genderField setText:[self pickerView:self.genderPickerView titleForRow:row forComponent:0]];
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self.view endEditing:YES];
    return YES;
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
