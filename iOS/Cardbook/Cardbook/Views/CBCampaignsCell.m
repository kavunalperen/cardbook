//
//  CBCampaignsCell.m
//  Cardbook
//
//  Created by Alperen Kavun on 11.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBCampaignsCell.h"
#import "CBUtil.h"

@implementation CBCampaignsCell
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
        if ([reuseIdentifier isEqualToString:MY_CAMPAIGNS_CELL_IDENTIFIER]) {
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
    
    self.nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(120.0, 15.0, 140.0, 15.0)];
    [self.nameLabel setBackgroundColor:[UIColor clearColor]];
    [self.nameLabel setFont:MY_CAMPAIGNS_CELL_NAME_FONT];
    [self.nameLabel setTextColor:MY_CAMPAIGNS_CELL_NAME_TEXT_COLOR];
    
    self.detailLabel = [[UILabel alloc] initWithFrame:CGRectMake(120.0, 32.0, 140.0, 30.0)];
    [self.detailLabel setBackgroundColor:[UIColor clearColor]];
    [self.detailLabel setFont:MY_CAMPAIGNS_CELL_DETAIL_LABEL_FONT];
    [self.detailLabel setTextColor:MY_CAMPAIGNS_CELL_DETAIL_LABEL_TEXT_COLOR];
    [self.detailLabel setNumberOfLines:2];
    
    self.dateLabel = [[UILabel alloc] initWithFrame:CGRectMake(120.0, 64.0, 140.0, 12.0)];
    [self.dateLabel setBackgroundColor:[UIColor clearColor]];
    [self.dateLabel setFont:MY_CAMPAIGNS_CELL_DATE_LABEL_FONT];
    [self.dateLabel setTextColor:MY_CAMPAIGNS_CELL_DATE_LABEL_TEXT_COLOR];
    
    [self addSubview:self.thumbnail];
    [self addSubview:self.nameLabel];
    [self addSubview:self.detailLabel];
    [self addSubview:self.dateLabel];
    
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
        [self.nameLabel setTextColor:MY_CAMPAIGNS_CELL_NAME_SELECTED_TEXT_COLOR];
    } else {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CAMPAIGNS_CELL_NAME_TEXT_COLOR];
    }
}
- (void)setHighlighted:(BOOL)highlighted animated:(BOOL)animated
{
    [super setHighlighted:highlighted animated:animated];
    
    if (highlighted) {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_highlighted.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CAMPAIGNS_CELL_NAME_SELECTED_TEXT_COLOR];
    } else {
        UIImage* accessoryImage = [UIImage imageNamed:@"cell_detail_arrow_normal.png"];
        [accessoryView setImage:accessoryImage];
        [self.nameLabel setTextColor:MY_CAMPAIGNS_CELL_NAME_TEXT_COLOR];
    }
}

@end
