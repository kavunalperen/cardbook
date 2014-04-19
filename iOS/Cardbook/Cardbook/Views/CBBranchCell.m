//
//  CBBranchCell.m
//  Cardbook
//
//  Created by Alperen Kavun on 17.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBBranchCell.h"
#import "CBUtil.h"

@implementation CBBranchCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        [self initMainComponents];
        if ([reuseIdentifier isEqualToString:BRANCH_CELL_NORMAL_CELL_IDENTIFIER]) {
            [self stylizeForNormalCell];
        } else if ([reuseIdentifier isEqualToString:BRANCH_CELL_LAST_CELL_IDENTIFIER]) {
            [self stylizeForLastCell];
        }
    }
    return self;
}
- (void) initMainComponents
{
    [self setSelectedBackgroundView:[UIView new]];
    [self setBackgroundColor:[UIColor clearColor]];
    [self setAccessoryType:UITableViewCellAccessoryNone];
}
- (void) stylizeForNormalCell
{
    UIImageView* backgroundView2 = [UIImageView new];
    [backgroundView2 setFrame:CGRectMake(0.0, 0.0, 290.0, 62.0)];
    [backgroundView2 setImage:[UIImage imageNamed:@"branch_listbg.png"]];
    [self addSubview:backgroundView2];
    
//    self.seperatorView = [[UIView alloc] initWithFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
//    [self.seperatorView setBackgroundColor:CARD_INFO_BRANCHES_CELL_SEPERATOR_COLOR];
//    [self addSubview:self.seperatorView];
    
    self.nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 8.0, 232.0, 18.0)];
    self.nameLabel.backgroundColor = [UIColor clearColor];
    self.nameLabel.font = CARD_INFO_BRANCHES_CELL_NAME_FONT;
    self.nameLabel.textColor = CARD_INFO_BRANCHES_CELL_NAME_TEXT_COLOR;
    [self addSubview:self.nameLabel];
    
    
    self.addressLabel = [[UILabel alloc] initWithFrame:CGRectMake(15.0, 26.0, 232.0, 28.0)];
    self.addressLabel.backgroundColor = [UIColor clearColor];
    self.addressLabel.numberOfLines = 2;
    self.addressLabel.font = CARD_INFO_BRANCHES_CELL_ADDRESS_FONT;
    self.addressLabel.textColor = CARD_INFO_BRANCHES_CELL_ADDRESS_TEXT_COLOR;
    [self addSubview:self.addressLabel];
    
    self.distanceLabel = [[UILabel alloc] initWithFrame:CGRectMake(247.0, 8.0, 28.0, 18.0)];
    self.distanceLabel.backgroundColor = [UIColor clearColor];
    self.distanceLabel.textAlignment = NSTextAlignmentRight;
    self.distanceLabel.font = CARD_INFO_BRANCHES_CELL_DISTANCE_FONT;
    self.distanceLabel.textColor = CARD_INFO_BRANCHES_CELL_DISTANCE_TEXT_COLOR;
    [self addSubview:self.distanceLabel];
    
    self.phoneButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.phoneButton.frame = CGRectMake(250.0, 26.0, 30.0, 30.0);
    self.phoneButton.backgroundColor = [UIColor clearColor];
    [self.phoneButton setImage:[UIImage imageNamed:@"mycard_branch_call_normal.png"] forState:UIControlStateNormal];
    [self.phoneButton setImage:[UIImage imageNamed:@"mycard_branch_call_highlighted.png"] forState:UIControlStateHighlighted];
    [self.phoneButton setContentMode:UIViewContentModeCenter];
    [self.phoneButton addTarget:self action:@selector(callBranch) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:self.phoneButton];
//    self.phoneButton
}
- (void) callBranch
{
    NSString* phoneNumber = self.relatedBranch.branchPhone;
    if (phoneNumber != nil && ![phoneNumber isEqualToString:@""]) {
        NSString *phoneUrl = [@"tel://" stringByAppendingString:phoneNumber];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:phoneUrl]];
    }
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
//    [self.seperatorView setBackgroundColor:CARD_INFO_BRANCHES_CELL_SEPERATOR_COLOR];
    // Configure the view for the selected state
}
- (void) setFrame:(CGRect)frame
{
    [super setFrame:frame];
//    [self.seperatorView setFrame:CGRectMake(0.0, self.frame.size.height-1.0, self.frame.size.width, 1.0)];
}
@end
