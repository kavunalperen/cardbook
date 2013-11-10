//
//  CBTextField.h
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CBTextField : UITextField

@property UIImageView* requiredView;

- (id)initWithFrame:(CGRect)frame andForRegister:(BOOL)forRegister;

@end
