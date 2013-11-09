//
//  CBTextField.m
//  Cardbook
//
//  Created by Alperen Kavun on 9.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBTextField.h"
#import "CBUtil.h"
#import <QuartzCore/QuartzCore.h>

@implementation CBTextField

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        
        [self setFont:REGISTER_VIEW_INPUT_FONT];
        [self setBackgroundColor:[UIColor whiteColor]];
        [self.layer setCornerRadius:5.0];
        [self.layer setMasksToBounds:YES];
        [self.layer setShouldRasterize:YES];
        [self.layer setRasterizationScale:[UIScreen mainScreen].scale];
        _requiredView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"input_zorunlu_icon.png"]];
        [_requiredView setFrame:CGRectMake(self.frame.size.width-10.0, 0.0, 10.0, 30.0)];
        [self addSubview:_requiredView];
    }
    return self;
}
- (CGRect)textRectForBounds:(CGRect)bounds
{
    return CGRectInset( bounds , 5 , 0 );
}
- (CGRect)editingRectForBounds:(CGRect)bounds
{
    return CGRectInset( bounds , 5 , 0 );
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
