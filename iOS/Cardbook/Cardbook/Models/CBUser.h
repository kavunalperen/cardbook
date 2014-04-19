//
//  CBUser.h
//  Cardbook
//
//  Created by Alperen Kavun on 19.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#define USER_DEFAULTS_USER_SAVE_KEY @"UserDefaultsUserSaveKey"
#define USER_DEFAULTS_USER_BARCODE_SAVE_KEY @"UserDefaultsUserBarcodeSaveKey"
#define USER_DEFAULTS_USER_BARCODE_URL_SAVE_KEY @"UserDefaultsUserBarcodeUrlSaveKey"

#import <Foundation/Foundation.h>

@interface CBUser : NSObject

@property NSString* userId;
@property NSString* facebookId;
@property NSString* name;
@property NSString* surname;
@property NSString* email;
@property NSString* mobileDeviceId;
@property NSDate* birthdate;
@property NSString* birthdateString;
@property NSString* profilePictureUrl;
@property NSString* phone;
@property NSString* gender;
@property NSInteger countryId;
@property NSInteger cityId;
@property NSInteger countyId;
@property NSString* countryString;
@property NSString* cityString;
@property NSString* countyString;
@property NSString* genderString;
@property NSString* address;
@property NSString* barcodeUrl;
@property UIImage* barcodeImage;

- (id)      initWithFacebookId:(NSString*)facebookId
                     andUserId:(NSString*)userId
                       andName:(NSString*)name
                    andSurname:(NSString*)surname
                      andEmail:(NSString*)email
                  andBirthdate:(NSDate*)birthdate
          andProfilePictureUrl:(NSString*)profilePictureUrl
                      andPhone:(NSString*)phone
                     andGender:(NSString*)gender
                  andCountryId:(NSInteger)countryId
                     andCityId:(NSInteger)cityId
                   andCountyId:(NSInteger)countyId
                    andAddress:(NSString*)address;

+ (CBUser*) CBUserWithDictionary:(NSDictionary*)dictionary;

+ (CBUser*) sharedUser;
+ (void) saveUserToUserDefaults:(NSDictionary*)dictionary;
+ (NSDictionary*) getUserDictionaryFromUserDefaults;
- (NSString*) getMyBirthdateString;

+ (void) setAndSaveBarcodeUrl:(NSString*)barcodeUrl;
+ (void) setAndSaveBarcodeImage:(UIImage*)barcodeImage;
+ (UIImage*) getSavedBarcodeImage;
- (void) openBarcodeFullScreen;

@end
