//
//  CBCardDetailsCouponCell.m
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBCardDetailsCouponCell.h"
#import "CBUtil.h"

@implementation CBCardDetailsCouponCell
{
    UIView* backgroundView;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self initCommonViews];
    }
    return self;
}
- (void) initCommonViews
{
    backgroundView = [UIView new];
    [backgroundView setBackgroundColor:[UIColor clearColor]];
    [self setBackgroundView:backgroundView];
    
    [self setSelectionStyle:UITableViewCellSelectionStyleNone];
    
    self.accessoryType = UITableViewCellAccessoryNone;
    
    self.seperatorView = [[UIView alloc] initWithFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
    [self.seperatorView setBackgroundColor:CARD_DETAIL_COUPON_CELL_SEPERATOR_COLOR];
    [self addSubview:self.seperatorView];
    
    self.thumbnail = [[UIImageView alloc] initWithFrame:CGRectMake(0.0, 10.0, 13.0, 13.0)];
    [self.thumbnail setBackgroundColor:[UIColor greenColor]];
    [self.thumbnail setClipsToBounds:YES];
//    [self.thumbnail setImage:[UIImage imageNamed:@""]];
    
    self.mainLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 10.0, 150.0, 13.0)];
    [self.mainLabel setBackgroundColor:[UIColor clearColor]];
    [self.mainLabel setFont:CARD_DETAIL_COUPON_CELL_MAIN_LABEL_FONT];
    [self.mainLabel setTextColor:CARD_DETAIL_COUPON_CELL_MAIN_LABEL_TEXT_COLOR];
    
    self.subtitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 23.0, 230.0, 33.0)];
    [self.subtitleLabel setBackgroundColor:[UIColor clearColor]];
    [self.subtitleLabel setFont:CARD_DETAIL_COUPON_CELL_SUBTITLE_LABEL_FONT];
    [self.subtitleLabel setTextColor:CARD_DETAIL_COUPON_CELL_SUBTITLE_LABEL_TEXT_COLOR];
    [self.subtitleLabel setNumberOfLines:0];
    
    [self addSubview:self.thumbnail];
    [self addSubview:self.mainLabel];
    [self addSubview:self.subtitleLabel];
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (void) setFrame:(CGRect)frame
{
    [super setFrame:CGRectMake(frame.origin.x+15.0, frame.origin.y, frame.size.width-30.0, frame.size.height)];
    [self.seperatorView setFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
}
@end
