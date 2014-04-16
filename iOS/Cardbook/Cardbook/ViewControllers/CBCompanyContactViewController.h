//
//  CBCompanyContactViewController.h
//  Cardbook
//
//  Created by Alperen Kavun on 14.04.2014.
//  Copyright (c) 2014 kavun. All rights reserved.
//

#import "CBBaseViewController.h"
#import "CBCardDetail.h"
#import "CBCardInfo.h"

@interface CBCompanyContactViewController : CBBaseViewController <UITextFieldDelegate, UITextViewDelegate>

@property CBCardDetail* currentCardDetail;
@property CBCardInfo* currentCardInfo;

@end
