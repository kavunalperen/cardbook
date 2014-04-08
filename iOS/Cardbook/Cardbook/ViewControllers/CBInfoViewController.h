//
//  CBInfoViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 23.03.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBBaseViewController.h"
#import "CBCardInfo.h"
#import "CBCardDetail.h"

@interface CBInfoViewController : CBBaseViewController <UIScrollViewDelegate>

@property UIScrollView* scrollView;

@property CBCardDetail* currentCardDetail;
@property CBCardInfo* currentCardInfo;

@end
