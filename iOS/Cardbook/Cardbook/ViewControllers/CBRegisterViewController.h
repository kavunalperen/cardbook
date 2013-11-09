//
//  CBRegisterViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CBTextField.h"

@interface CBRegisterViewController : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate>

@property NSDictionary* userInfos;

@property UIPickerView* countryPickerView;
@property UIPickerView* cityPickerView;
@property UIPickerView* countyPickerView;
@property UIPickerView* genderPickerView;
@property UIDatePicker* birthDatePicker;

@property UIScrollView* scrollView;

@property UIImageView* profilePictureView;
@property CBTextField* nameField;
@property CBTextField* surnameField;
@property CBTextField* emailField;
@property CBTextField* birthDateField;
@property CBTextField* genderField;
@property CBTextField* phoneNumberField;
@property CBTextField* countryField;
@property CBTextField* cityField;
@property CBTextField* countyField;
@property CBTextField* addressField;

@property UIButton* updateButton;

@end
