//
//  CBCompanyContactViewController.m
//  Cardbook
//
//  Created by Alperen Kavun on 14.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBCompanyContactViewController.h"
#import "CBUtil.h"
#import "APIManager.h"
#import "CBCardInfoTextField.h"
#import <QuartzCore/QuartzCore.h>

@interface CBCompanyContactViewController ()

@end

@implementation CBCompanyContactViewController
{
    UILabel* companyNameLabel;
    UILabel* headOfficeLabel;
    UILabel* addressLabel;
    UILabel* phoneTitleLabel;
    UILabel* emailTitleLabel;
    UITextView* phoneLabel;
    UITextView* emailLabel;
    
    UILabel* sendMessageLabel;
    
    CBCardInfoTextField* messageSubjectField;
    UITextView* messageView;
    UILabel* placeHolderLabel;
    
    UIButton* sendButton;
}
- (CGRect) backgroundFrame
{
    CGFloat backHeight;
    if (IS_IPHONE_5) {
        backHeight = 436.0;
    } else {
        backHeight = 348.0;
    }
    return CGRectMake(15.0, 69.0, 290.0, backHeight);
}
- (CGRect) mainHolderFrame
{
    CGFloat backHeight;
    if (IS_IPHONE_5) {
        backHeight = 436.0;
    } else {
        backHeight = 348.0;
    }
    return CGRectMake(30.0, 69.0, 260.0, backHeight);
}
- (CGRect) companyNameLabelFrame
{
    return CGRectMake(0.0, 0.0, 260.0, 30.0);
}
- (CGRect) headOfficeLabelFrame
{
    return CGRectMake(0.0, 30.0, 260.0, 15.0);
}
- (CGRect) addressLabelFrame
{
    return CGRectMake(0.0, 45.0, 260.0, 50.0);
}
- (CGRect) phoneTitleLabelFrame
{
    return CGRectMake(0.0, 100.0, 60.0, 20.0);
}
- (CGRect) emailTitleLabelFrame
{
    return CGRectMake(0.0, 125.0, 60.0, 20.0);
}
- (CGRect) phoneLabelFrame
{
    return CGRectMake(60.0, 103.0, 200.0, 14.0);
}
- (CGRect) emailLabelFrame
{
    return CGRectMake(60.0, 128.0, 200.0, 14.0);
}
- (CGRect) sendMessageTitleFrame
{
    return CGRectMake(0.0, 175.0, 260.0, 15.0);
}
- (CGRect) messageSubjectFieldFrame
{
    return CGRectMake(0.0, 200.0, 260.0, 32.0);
}
- (CGRect) messageViewFrame
{
    if (IS_IPHONE_5) {
        return CGRectMake(0.0, 242.0, 260.0, 138.0);
    } else {
        return CGRectMake(0.0, 242.0, 260.0, 50.0);
    }
    
}
- (CGRect) sendButtonFrame
{
    if (IS_IPHONE_5) {
        return CGRectMake(183.0, 390.0, 77.0, 32.0);
    } else {
        return CGRectMake(183.0, 302.0, 77.0, 32.0);
    }
    
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
    
    [self stylizeNavigationBar];
    [self stylizeForDetailView];
    [self setTitleButtonText:@"Kartlarım"];
    [self.titleButton addTarget:self action:@selector(goBackToCardDetail) forControlEvents:UIControlEventTouchUpInside];
    
    [self initCommonViews];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification object:self.view.window];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification object:self.view.window];
}
- (void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if (self.currentCardInfo == nil) {
        
        CBCardInfo* cardInfo = [CBCardInfo GetCardInfoWithCompanyId:self.currentCardDetail.companyId];
        
        if (cardInfo != nil) {
            self.currentCardInfo = cardInfo;
            [self configureViews];
        } else {
            [[APIManager sharedInstance] getCompanyInfoWithCompanyId:self.currentCardDetail.companyId onCompletion:^(CBCardInfo *cardInfo) {
                self.currentCardInfo = cardInfo;
                [self configureViews];
                NSLog(@"response here");
            } onError:^(NSError *error) {
                NSLog(@"an error occured");
            }];
        }
    } else {
        [self configureViews];
    }
    
}
- (void) configureViews
{
    companyNameLabel.text = self.currentCardDetail.companyName;
    headOfficeLabel.text = @"Merkez Ofis";
    addressLabel.text = self.currentCardInfo.headOfficeAddress;
    phoneTitleLabel.text = @"Telefon";
    emailTitleLabel.text = @"E-Posta";
    phoneLabel.text = [NSString stringWithFormat:@": %@",self.currentCardInfo.callCenterPhoneNumber];
    emailLabel.text = [NSString stringWithFormat:@": %@",self.currentCardInfo.infoEmail];
    
    sendMessageLabel.text = @"Firmaya Mesaj Gönder";
}
- (void) initCommonViews
{
    self.view.userInteractionEnabled = YES;
    UIImageView* backView = [[UIImageView alloc] initWithFrame:[self backgroundFrame]];
    [backView setBackgroundColor:[UIColor clearColor]];
    [backView setImage:[UIImage imageNamed:@"comp_contact_bg.png"]];
    backView.userInteractionEnabled = YES;
    [self.view addSubview:backView];
    
    UIView* mainHolderView = [[UIView alloc] initWithFrame:[self mainHolderFrame]];
    [mainHolderView setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:mainHolderView];
    
    companyNameLabel = [[UILabel alloc] initWithFrame:[self companyNameLabelFrame]];
    [companyNameLabel setBackgroundColor:[UIColor clearColor]];
    [companyNameLabel setFont:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_FONT];
    [companyNameLabel setTextColor:MY_CAMPAIGNS_DETAILS_TITLE_LABEL_TEXT_COLOR];
    [mainHolderView addSubview:companyNameLabel];
    
    headOfficeLabel = [[UILabel alloc] initWithFrame:[self headOfficeLabelFrame]];
    headOfficeLabel.backgroundColor = [UIColor clearColor];
    headOfficeLabel.font = CARD_INFO_COMPANY_CONTACT_TITLE_FONT;
    headOfficeLabel.textColor = CARD_INFO_COMPANY_CONTACT_TITLE_TEXT_COLOR;
    [mainHolderView addSubview:headOfficeLabel];
    
    addressLabel = [[UILabel alloc] initWithFrame:[self addressLabelFrame]];
    addressLabel.backgroundColor = [UIColor clearColor];
    addressLabel.numberOfLines = 0;
    addressLabel.font = CARD_INFO_COMPANY_ADDRESS_FONT;
    addressLabel.textColor = CARD_INFO_COMPANY_ADDRESS_TEXT_COLOR;
    [mainHolderView addSubview:addressLabel];
    
    phoneTitleLabel = [[UILabel alloc] initWithFrame:[self phoneTitleLabelFrame]];
    phoneTitleLabel.backgroundColor = [UIColor clearColor];
    phoneTitleLabel.font = CARD_INFO_COMPANY_ADDRESS_FONT;
    phoneTitleLabel.textColor = CARD_INFO_COMPANY_ADDRESS_TEXT_COLOR;
    [mainHolderView addSubview:phoneTitleLabel];
    
    emailTitleLabel = [[UILabel alloc] initWithFrame:[self emailTitleLabelFrame]];
    emailTitleLabel.backgroundColor = [UIColor clearColor];
    emailTitleLabel.font = CARD_INFO_COMPANY_ADDRESS_FONT;
    emailTitleLabel.textColor = CARD_INFO_COMPANY_ADDRESS_TEXT_COLOR;
    [mainHolderView addSubview:emailTitleLabel];
    
    
    phoneLabel = [[UITextView alloc] initWithFrame:[self phoneLabelFrame]];
    [phoneLabel setBackgroundColor:[UIColor clearColor]];
    [phoneLabel setContentInset:UIEdgeInsetsZero];
    [phoneLabel setTextContainerInset:UIEdgeInsetsZero];
    [phoneLabel setFont:CARD_INFO_COMPANY_PHONE_TEXT_FONT];
    [phoneLabel setTextColor:CARD_INFO_COMPANY_PHONE_TEXT_COLOR];
    [phoneLabel setEditable:NO];
    [phoneLabel setScrollEnabled:NO];
    [phoneLabel setTintColor:CARD_INFO_COMPANY_PHONE_TEXT_COLOR];
    [phoneLabel setDataDetectorTypes:UIDataDetectorTypePhoneNumber];
    [mainHolderView addSubview:phoneLabel];
    
    emailLabel = [[UITextView alloc] initWithFrame:[self emailLabelFrame]];
    [emailLabel setBackgroundColor:[UIColor clearColor]];
    [emailLabel setContentInset:UIEdgeInsetsZero];
    [emailLabel setTextContainerInset:UIEdgeInsetsZero];
    [emailLabel setFont:CARD_INFO_COMPANY_PHONE_TEXT_FONT];
    [emailLabel setTextColor:CARD_INFO_COMPANY_PHONE_TEXT_COLOR];
    [emailLabel setEditable:NO];
    [emailLabel setScrollEnabled:NO];
    [emailLabel setTintColor:CARD_INFO_COMPANY_PHONE_TEXT_COLOR];
    [emailLabel setDataDetectorTypes:UIDataDetectorTypeLink];
    [mainHolderView addSubview:emailLabel];
    
    sendMessageLabel = [[UILabel alloc] initWithFrame:[self sendMessageTitleFrame]];
    sendMessageLabel.backgroundColor = [UIColor clearColor];
    sendMessageLabel.font = CARD_INFO_COMPANY_CONTACT_TITLE_FONT;
    sendMessageLabel.textColor = CARD_INFO_COMPANY_CONTACT_TITLE_TEXT_COLOR;
    [mainHolderView addSubview:sendMessageLabel];
    
    messageSubjectField = [[CBCardInfoTextField alloc] initWithFrame:[self messageSubjectFieldFrame]];
    messageSubjectField.backgroundColor = CARD_INFO_INPUTS_BACKGROUND_COLOR;
    messageSubjectField.font = CARD_INFO_INPUTS_TEXT_FONT;
    messageSubjectField.textColor = CARD_INFO_INPUTS_TEXT_COLOR;
    messageSubjectField.placeholder = @"Mesaj başlığını yazınız";
    messageSubjectField.layer.cornerRadius = 5.0;
    messageSubjectField.layer.shouldRasterize = YES;
    messageSubjectField.layer.rasterizationScale = [UIScreen mainScreen].scale;
    messageSubjectField.delegate = self;
    messageSubjectField.tintColor = CARD_INFO_INPUTS_TEXT_COLOR;
    messageSubjectField.clearButtonMode = UITextFieldViewModeWhileEditing;
    [mainHolderView addSubview:messageSubjectField];
    
    messageView = [[UITextView alloc] initWithFrame:[self messageViewFrame]];
    messageView.backgroundColor = CARD_INFO_INPUTS_BACKGROUND_COLOR;
    messageView.font = CARD_INFO_INPUTS_TEXT_FONT;
    messageView.textColor = CARD_INFO_INPUTS_TEXT_COLOR;
    messageView.layer.cornerRadius = 5.0;
    messageView.layer.shouldRasterize = YES;
    messageView.layer.rasterizationScale = [UIScreen mainScreen].scale;
    messageView.delegate = self;
    messageView.tintColor = CARD_INFO_INPUTS_TEXT_COLOR;
    [mainHolderView addSubview:messageView];
    
    placeHolderLabel = [[UILabel alloc] initWithFrame:CGRectMake(5.0, 8.0, 200.0, 16.0)];
    [placeHolderLabel setBackgroundColor:[UIColor clearColor]];
    [placeHolderLabel setText:@"Mesajınızı yazınız"];
    [placeHolderLabel setFont:CARD_INFO_INPUTS_TEXT_FONT];
    [placeHolderLabel setTextColor:CARD_INFO_INPUTS_PLACEHOLDER_COLOR];
    [messageView addSubview:placeHolderLabel];
    
    sendButton = [UIButton buttonWithType:UIButtonTypeCustom];
    sendButton.frame = [self sendButtonFrame];
    sendButton.backgroundColor = [UIColor clearColor];
    [sendButton setTitle:@"Gönder" forState:UIControlStateNormal];
    [sendButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [sendButton.titleLabel setFont:CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_FONT];
    [sendButton setBackgroundImage:[[CBUtil sharedInstance] UIImageWithUIColor:CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_NORMAL_COLOR] forState:UIControlStateNormal];
    [sendButton setBackgroundImage:[[CBUtil sharedInstance] UIImageWithUIColor:CARD_DETAIL_CAMPAIGNS_LINK_BUTTON_HIGHLIGHTED_COLOR] forState:UIControlStateHighlighted];
    [sendButton.layer setCornerRadius:5.0];
    [sendButton.layer setShouldRasterize:YES];
    [sendButton.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [sendButton setClipsToBounds:YES];
    [sendButton addTarget:self action:@selector(sendMessageToCompany) forControlEvents:UIControlEventTouchUpInside];
    [mainHolderView addSubview:sendButton];
    
    UITapGestureRecognizer* tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapped)];
    [self.view addGestureRecognizer:tapGesture];
}
- (void)textViewDidChange:(UITextView *)textView {
    if (textView.text.length == 0) {
        [placeHolderLabel setAlpha:1.0];
    } else {
        [placeHolderLabel setAlpha:0.0];
    }
    CGRect line = [textView caretRectForPosition:
                   textView.selectedTextRange.start];
    CGFloat overflow = line.origin.y + line.size.height
    - ( textView.contentOffset.y + textView.bounds.size.height
       - textView.contentInset.bottom - textView.contentInset.top );
    if ( overflow > 0 ) {
        // We are at the bottom of the visible text and introduced a line feed, scroll down (iOS 7 does not do it)
        // Scroll caret to visible area
        CGPoint offset = textView.contentOffset;
        offset.y += overflow + 7; // leave 7 pixels margin
        // Cannot animate with setContentOffset:animated: or caret will not appear
        [UIView animateWithDuration:.2 animations:^{
            [textView setContentOffset:offset];
        }];
    }
}
- (void) sendMessageToCompany
{
    NSString* messageSubject = messageSubjectField.text;
    NSString* message = messageView.text;
    
    if (messageSubject == nil || [messageSubject isEqualToString:@""]) {
        NSLog(@"alert here");
    } else {
        if (message == nil || [message isEqualToString:@""]) {
            NSLog(@"alert here");
        } else {
            sendButton.enabled = NO;
            [[APIManager sharedInstance] sendMailToCompany:self.currentCardDetail.companyId andMessage:message andMessageSubject:messageSubject onCompletion:^(NSDictionary *responseDictionary) {
                // successfull
                NSLog(@"response here");
            } onError:^(NSError *error) {
                NSLog(@"an error occured");
            }];
        }
    }
    NSLog(@"send message");
}
- (void) tapped
{
    [self.view endEditing:YES];
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self.view endEditing:YES];
    return YES;
}
//- (BOOL) text
- (void) goBackToCardDetail
{
    [self.navigationController popViewControllerAnimated:YES];
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
- (void)keyboardWillShow:(NSNotification *)notif {
    NSDictionary* userInfo = [notif userInfo];
    
    // Get animation info from userInfo
    NSTimeInterval animationDuration;
    UIViewAnimationCurve animationCurve;
    
    [[userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey] getValue:&animationCurve];
    [[userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey] getValue:&animationDuration];
    
    [UIView animateWithDuration:animationDuration
                     animations:^{
                         CGRect frame = self.view.frame;
                         frame.origin.y = -164;
                         [self.view setFrame:frame];
                     }];
}

- (void)keyboardWillHide:(NSNotification *)notif {
    NSDictionary* userInfo = [notif userInfo];
    
    // Get animation info from userInfo
    NSTimeInterval animationDuration;
    UIViewAnimationCurve animationCurve;
    
    [[userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey] getValue:&animationCurve];
    [[userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey] getValue:&animationDuration];
    
    [UIView animateWithDuration:animationDuration
                     animations:^{
                         CGRect frame = self.view.frame;
                         frame.origin.y = 0;
                         [self.view setFrame:frame];
                     }];
}
- (void) dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
@end
