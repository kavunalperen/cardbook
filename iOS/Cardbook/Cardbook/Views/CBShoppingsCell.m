//
//  CBShoppingsCell.m
//  Cardbook
//
//  Created by Alperen Kavun on 11.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBShoppingsCell.h"
#import "CBUtil.h"

@implementation CBShoppingsCell
{
//    UIImageView* backgroundView;
    UIImageView* accessoryView;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        [self initCommonViewsWithReuseIdentifier:reuseIdentifier];
        if ([reuseIdentifier isEqualToString:MY_SHOPPINGS_CELL_FIRST_CELL_IDENTIFIER]) {
            [self stylizeForFirstCell];
        } else if ([reuseIdentifier isEqualToString:MY_SHOPPINGS_CELL_MIDDLE_CELL_IDENTIFIER]) {
            [self stylizeForMiddleCell];
        } else {
            [self stylizeForLastCell];
        }
    }
    return self;
}
- (void) initCommonViewsWithReuseIdentifier:(NSString*)reuseIdentifier
{
    [self setSelectedBackgroundView:[UIView new]];
    [self setBackgroundColor:[UIColor clearColor]];
    [self setAccessoryType:UITableViewCellAccessoryNone];
    
    if (![reuseIdentifier isEqualToString:MY_SHOPPINGS_CELL_LAST_CELL_IDENTIFIER]) {
        self.nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 0.0, 162.0, 42.0)];
        [self.nameLabel setBackgroundColor:[UIColor clearColor]];
        [self.nameLabel setFont:MY_SHOPPINGS_CELL_NAME_FONT];
        [self.nameLabel setTextColor:MY_SHOPPINGS_CELL_NAME_NORMAL_TEXT_COLOR];
        
        self.dateLabel = [[UILabel alloc] initWithFrame:CGRectMake(177.0, 0.0, 80.0, 42.0)];
        [self.dateLabel setBackgroundColor:[UIColor clearColor]];
        [self.dateLabel setTextAlignment:NSTextAlignmentRight];
        [self.dateLabel setFont:MY_SHOPPINGS_CELL_DATE_LABEL_FONT];
        [self.dateLabel setTextColor:MY_SHOPPINGS_CELL_DATE_LABEL_TEXT_COLOR];
        
//        [self addSubview:self.nameLabel];
//        [self addSubview:self.dateLabel];
    }
    
    UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
    accessoryView = [[UIImageView alloc] initWithFrame:CGRectMake(0.0, 0.0, 10.0, 90.0)];
    [accessoryView setBackgroundColor:[UIColor clearColor]];
    [accessoryView setImage:accessoryImage];
//    [self setAccessoryView:accessoryView];
}
- (void) stylizeForFirstCell
{
    UIImageView* backgroundView1 = [UIImageView new];
    [backgroundView1 setFrame:CGRectMake(0.0, 0.0, 290.0, 20.0)];
    [backgroundView1 setImage:[UIImage imageNamed:@"campaign_detail_desc_top.png"]];
    UIImageView* backgroundView2 = [UIImageView new];
    [backgroundView2 setFrame:CGRectMake(0.0, 20.0, 290.0, 22.0)];
    [backgroundView2 setImage:[UIImage imageNamed:@"campaign_detail_desc_bg.png"]];
    [self addSubview:backgroundView1];
    [self addSubview:backgroundView2];
    
    self.seperatorView = [[UIView alloc] initWithFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
    [self.seperatorView setBackgroundColor:CARD_DETAIL_COUPON_CELL_SEPERATOR_COLOR];
    [self addSubview:self.seperatorView];
    
    [self addSubview:self.nameLabel];
    [self addSubview:self.dateLabel];
    [self setAccessoryView:accessoryView];

}
- (void) stylizeForMiddleCell
{
    UIImageView* backgroundView2 = [UIImageView new];
    [backgroundView2 setFrame:CGRectMake(0.0, 0.0, 290.0, 42.0)];
    [backgroundView2 setImage:[UIImage imageNamed:@"campaign_detail_desc_bg.png"]];
    [backgroundView2 setContentMode:UIViewContentModeScaleAspectFill];
    [self addSubview:backgroundView2];
    
    self.seperatorView = [[UIView alloc] initWithFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
    [self.seperatorView setBackgroundColor:CARD_DETAIL_COUPON_CELL_SEPERATOR_COLOR];
    [self addSubview:self.seperatorView];
    
    [self addSubview:self.nameLabel];
    [self addSubview:self.dateLabel];
    [self setAccessoryView:accessoryView];
}
- (void) stylizeForLastCell
{
    UIImageView* backgroundView2 = [UIImageView new];
    [backgroundView2 setFrame:CGRectMake(0.0, 0.0, 290.0, 20.0)];
    [backgroundView2 setImage:[UIImage imageNamed:@"campaign_detail_desc_bottom.png"]];
    [backgroundView2 setContentMode:UIViewContentModeScaleAspectFill];
    [self addSubview:backgroundView2];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];
    [self.seperatorView setBackgroundColor:MY_SHOPPINGS_CELL_SEPERATOR_COLOR];
    // Configure the view for the selected state
    if (selected) {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_highlighted.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_SHOPPINGS_CELL_NAME_SELECTED_TEXT_COLOR];
    } else {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_SHOPPINGS_CELL_NAME_NORMAL_TEXT_COLOR];
    }
}
- (void)setHighlighted:(BOOL)highlighted animated:(BOOL)animated
{
    [super setHighlighted:highlighted animated:animated];
    [self.seperatorView setBackgroundColor:MY_SHOPPINGS_CELL_SEPERATOR_COLOR];
    if (highlighted) {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_highlighted.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_SHOPPINGS_CELL_NAME_SELECTED_TEXT_COLOR];
    } else {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_SHOPPINGS_CELL_NAME_NORMAL_TEXT_COLOR];
    }
}
- (void) setFrame:(CGRect)frame
{
    [super setFrame:frame];
    [self.seperatorView setFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
}
@end
