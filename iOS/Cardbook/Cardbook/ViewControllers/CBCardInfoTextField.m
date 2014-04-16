//
//  CBCardInfoTextField.m
//  Cardbook
//
//  Created by Alperen Kavun on 14.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBCardInfoTextField.h"
#import "CBUtil.h"

@implementation CBCardInfoTextField

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

//- (void) drawPlaceholderInRect:(CGRect)rect
//{
//    //    [LOGIN_PHONE_TEXT_FIELD_PLACEHOLDER_COLOR setFill];
//    //    [[UIColor redColor] setFill];
//    
//    NSMutableParagraphStyle *paragraphStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
//    /// Set line break mode
//    paragraphStyle.lineBreakMode = NSLineBreakByClipping;
//    /// Set text alignment
//    paragraphStyle.alignment = self.textAlignment;
//    
//    //    if (IS_IOS_7) {
//    [[self placeholder] drawInRect:CGRectMake(rect.origin.x, rect.origin.y, rect.size.width, rect.size.height) withAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
//         paragraphStyle, NSParagraphStyleAttributeName,
//         CARD_INFO_INPUTS_TEXT_FONT, NSFontAttributeName,
//         CARD_INFO_INPUTS_PLACEHOLDER_COLOR, NSForegroundColorAttributeName,
//         nil]];
//}

- (void) drawPlaceholderInRect:(CGRect)rect
{
    //    [LOGIN_PHONE_TEXT_FIELD_PLACEHOLDER_COLOR setFill];
    //    [[UIColor redColor] setFill];
    
    NSMutableParagraphStyle *paragraphStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    /// Set line break mode
    paragraphStyle.lineBreakMode = NSLineBreakByClipping;
    /// Set text alignment
    paragraphStyle.alignment = self.textAlignment;
    
    //    if (IS_IOS_7) {
    [[self placeholder] drawInRect:CGRectMake(rect.origin.x, rect.origin.y+2.0, rect.size.width, rect.size.height) withAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
             paragraphStyle, NSParagraphStyleAttributeName,
             CARD_INFO_INPUTS_TEXT_FONT, NSFontAttributeName,
             CARD_INFO_INPUTS_PLACEHOLDER_COLOR, NSForegroundColorAttributeName,
             nil]];
}
- (CGRect)textRectForBounds:(CGRect)bounds
{
    return CGRectInset(bounds, 5.0, 8.0);
}
- (CGRect)editingRectForBounds:(CGRect)bounds {
    return CGRectInset(bounds, 5.0, 8.0);
}
@end
