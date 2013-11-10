//
//  CBMyCardsCell.m
//  Cardbook
//
//  Created by Alperen Kavun on 10.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBMyCardsCell.h"
#import <QuartzCore/QuartzCore.h>
#import "CBUtil.h"

@implementation CBMyCardsCell
{
    UIImageView* backgroundView;
    UIImageView* accessoryView;
}

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        [self initCommonViews];
        if ([reuseIdentifier isEqualToString:MY_CARDS_CELL_IDENTIFIER]) {
            [self stylizeForNormalCell];
        } else {
            [self stylizeForLastCell];
        }
    }
    return self;
}
- (void) initCommonViews
{
    [self setSelectedBackgroundView:[UIView new]];
    
    [self setBackgroundColor:[UIColor clearColor]];
    
    self.thumbnail = [[UIImageView alloc] initWithFrame:CGRectMake(15.0, 15.0, 90.0, 60.0)];
    [self.thumbnail setBackgroundColor:[UIColor clearColor]];
    [self.thumbnail.layer setCornerRadius:5.0];
    [self.thumbnail setClipsToBounds:YES];
    [self.thumbnail.layer setShouldRasterize:YES];
    [self.thumbnail.layer setRasterizationScale:[UIScreen mainScreen].scale];
    [self.thumbnail setImage:[UIImage imageNamed:@"dummy_brand.jpg"]];
    
    self.nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(120.0, 0.0, 140.0, 90.0)];
    [self.nameLabel setBackgroundColor:[UIColor clearColor]];
    [self.nameLabel setFont:MY_CARDS_CELL_NAME_FONT];
    [self.nameLabel setTextColor:MY_CARDS_CELL_NAME_TEXT_COLOR];
    
    [self addSubview:self.thumbnail];
    [self addSubview:self.nameLabel];
    
    [self setAccessoryType:UITableViewCellAccessoryNone];
    
    UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
    accessoryView = [[UIImageView alloc] initWithFrame:CGRectMake(0.0, 0.0, 10.0, 90.0)];
    [accessoryView setBackgroundColor:[UIColor clearColor]];
    [accessoryView setImage:accessoryImage];
    [self setAccessoryView:accessoryView];
}
- (void) stylizeForNormalCell
{
    backgroundView = [UIImageView new];
    [backgroundView setImage:[UIImage imageNamed:@"main_bigcell_bg.png"]];
    [self setBackgroundView:backgroundView];
}
- (void) stylizeForLastCell
{
    backgroundView = [UIImageView new];
    [backgroundView setImage:[UIImage imageNamed:@"main_bigcell_footer_bg.png"]];
    [self setBackgroundView:backgroundView];
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
    if (selected) {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_highlighted.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CARDS_CELL_NAME_SELECTED_TEXT_COLOR];
    } else {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CARDS_CELL_NAME_TEXT_COLOR];
    }
}
- (void)setHighlighted:(BOOL)highlighted animated:(BOOL)animated
{
    [super setHighlighted:highlighted animated:animated];
    
    if (highlighted) {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_highlighted.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CARDS_CELL_NAME_SELECTED_TEXT_COLOR];
    } else {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CARDS_CELL_NAME_TEXT_COLOR];
    }
}

@end
