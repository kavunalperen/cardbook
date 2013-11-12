//
//  CBShoppingDetailsCell.m
//  Cardbook
//
//  Created by Alperen Kavun on 12.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBShoppingDetailsCell.h"
#import "CBUtil.h"

@implementation CBShoppingDetailsCell
{
    UIView* backgroundView;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
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
    
    self.nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 190.0, 20.0)];
    [self.nameLabel setBackgroundColor:[UIColor clearColor]];
    [self.nameLabel setFont:MY_SHOPPINGS_DETAILS_CELL_NAME_LABEL_FONT];
    [self.nameLabel setTextColor:MY_SHOPPINGS_DETAILS_CELL_NAME_LABEL_TEXT_COLOR];
    
    self.priceLabel = [[UILabel alloc] initWithFrame:CGRectMake(190.0, 0.0, 60, 20.0)];
    [self.priceLabel setBackgroundColor:[UIColor clearColor]];
    [self.priceLabel setTextAlignment:NSTextAlignmentRight];
    [self.priceLabel setFont:MY_SHOPPINGS_DETAILS_CELL_PRICE_LABEL_FONT];
    [self.priceLabel setTextColor:MY_SHOPPINGS_DETAILS_CELL_PRICE_LABEL_TEXT_COLOR];
    [self.priceLabel setNumberOfLines:0];
    
    [self setBackgroundColor:[UIColor clearColor]];
    
    [self addSubview:self.nameLabel];
    [self addSubview:self.priceLabel];
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
