//
//  CBUser.m
//  Cardbook
//
//  Created by Alperen Kavun on 19.11.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#define USER_DEFAULTS_USER_SAVE_KEY @"UserDefaultsUserSaveKey"
#define USER_DEFAULTS_USER_BARCODE_SAVE_KEY @"UserDefaultsUserBarcodeSaveKey"
#define USER_DEFAULTS_USER_BARCODE_URL_SAVE_KEY @"UserDefaultsUserBarcodeUrlSaveKey"

#import "CBUser.h"
#import "Country.h"
#import "City.h"
#import "County.h"

static CBUser* sharedUser;

@implementation CBUser

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
{
    if (self = [super init]) {
        _userId = userId;
        _facebookId = facebookId;
        _name = name;
        _surname = surname;
        _email = email;
        _birthdate = birthdate;
        _profilePictureUrl = profilePictureUrl;
        _phone = phone;
        _gender = gender;
        _countryId = countryId;
        _cityId = cityId;
        _countyId = countyId;
        _address = address;
        
        if ([Country GetCountryWithCountryId:_countryId] == nil) {
            NSDictionary* savedDictionary = [CBUser getUserDictionaryFromUserDefaults];
            _countryString = [savedDictionary objectForKey:@"CountryStr"];
            _cityString = [savedDictionary objectForKey:@"CityStr"];
            _countyString = [savedDictionary objectForKey:@"CountyStr"];
        } else {
            _countryString = [[Country GetCountryWithCountryId:_countryId] countryName];
            _cityString = [[City GetCityWithCityId:_cityId] cityName];
            _countyString = [[County GetCountyWithCountyId:_countyId] countyName];
        }
        if ([_gender isEqualToString:@"M"]) {
            _genderString = @"Erkek";
        } else {
            _genderString = @"Kadın";
        }
    }
    sharedUser = self;
    return sharedUser;
}

+ (CBUser*) CBUserWithDictionary:(NSDictionary*)dictionary
{
    NSString* userId = [dictionary objectForKey:@"UserId"];
    NSString* facebookId = [dictionary objectForKey:@"FacebookId"];
    NSString* name = [dictionary objectForKey:@"Name"];
    NSString* surname = [dictionary objectForKey:@"Surname"];
    NSString* email = [dictionary objectForKey:@"Email"];
    
    NSString* birthDateStr = [dictionary objectForKey:@"BirthDate"];
    birthDateStr = [birthDateStr stringByReplacingOccurrencesOfString:@"/Date(" withString:@""];
    birthDateStr = [birthDateStr stringByReplacingOccurrencesOfString:@")" withString:@""];
    double timestamp = [birthDateStr  doubleValue];
    timestamp /= 1000;
    NSDate* date = [NSDate dateWithTimeIntervalSince1970:timestamp];
    
    NSDate* birthdate = date;
    
    NSString* profilePictureUrl = [dictionary objectForKey:@"ProfilePhotoUrl"];
    NSString* phone = [dictionary objectForKey:@"Phone1"];
    NSString* gender = [dictionary objectForKey:@"Gender"];
//    NSInteger countryId = [[[dictionary objectForKey:@"Address"] objectForKey:@"CountryId"] integerValue];
//    NSInteger cityId = [[[dictionary objectForKey:@"Address"] objectForKey:@"CityId"] integerValue];
//    NSInteger countyId = [[[dictionary objectForKey:@"Address"] objectForKey:@"CountyId"] integerValue];
    NSInteger countryId = 1;
    NSInteger cityId = 2;
    NSInteger countyId = 5;
    NSString* address = [[dictionary objectForKey:@"Address"] objectForKey:@"AddressLine"];
    
    
    
    NSDictionary* savedDictionary = @{@"FacebookId":facebookId,
                                      @"UserId":userId,
                                      @"Name":name,
                                      @"Surname":surname,
                                      @"Email":email,
                                      @"BirthDate":[dictionary objectForKey:@"BirthDate"],
                                      @"ProfilePhotoUrl":profilePictureUrl,
                                      @"Phone1":phone,
                                      @"Gender":gender,
                                      @"Address":[dictionary objectForKey:@"Address"],
//                                      @"CountryStr":[[Country GetCountryWithCountryId:countryId] countryName],
                                      @"CountryStr":@"Türkiye",
//                                      @"CityStr":[[City GetCityWithCityId:cityId] cityName],
                                      @"CityStr":@"Adana",
//                                      @"CountyStr":[[County GetCountyWithCountyId:countyId] countyName]
                                      @"CountyStr":@"Çukurova"
                                      };
    
    [CBUser saveUserToUserDefaults:savedDictionary];
    
    return [[CBUser alloc] initWithFacebookId:facebookId
                                    andUserId:userId
                                      andName:name
                                   andSurname:surname
                                     andEmail:email
                                 andBirthdate:birthdate
                         andProfilePictureUrl:profilePictureUrl
                                     andPhone:phone
                                    andGender:gender
                                 andCountryId:countryId
                                    andCityId:cityId
                                  andCountyId:countyId
                                   andAddress:address];
}
+ (void) saveUserToUserDefaults:(NSDictionary*)dictionary
{
    [[NSUserDefaults standardUserDefaults] setObject:dictionary forKey:USER_DEFAULTS_USER_SAVE_KEY];
    [[NSUserDefaults standardUserDefaults] synchronize];
}
+ (NSDictionary*) getUserDictionaryFromUserDefaults
{
    return [[NSUserDefaults standardUserDefaults] objectForKey:USER_DEFAULTS_USER_SAVE_KEY];
    
}
+ (void) setAndSaveBarcodeUrl:(NSString*)barcodeUrl
{
    [[NSUserDefaults standardUserDefaults] setObject:barcodeUrl forKey:USER_DEFAULTS_USER_BARCODE_URL_SAVE_KEY];
    [[NSUserDefaults standardUserDefaults] synchronize];
}
+ (void) setAndSaveBarcodeImage:(UIImage*)barcodeImage
{
    [[NSUserDefaults standardUserDefaults] setObject:barcodeImage forKey:USER_DEFAULTS_USER_BARCODE_SAVE_KEY];
    [[NSUserDefaults standardUserDefaults] synchronize];
}
+ (UIImage*) getSavedBarcodeImage
{
    return [[NSUserDefaults standardUserDefaults] objectForKey:USER_DEFAULTS_USER_BARCODE_SAVE_KEY];
}
- (NSString*) getMyBirthdateString
{
    if (_birthdateString == nil) {
        NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
        [formatter setLocale:[NSLocale currentLocale]];
        
        [formatter setDateFormat:@"dd / MM / yy"];
        
        _birthdateString = [formatter stringFromDate:_birthdate];
    }
    
    return _birthdateString;
}

+ (CBUser*) sharedUser
{
    if (sharedUser == nil) {
        NSDictionary* userDictionary = [CBUser getUserDictionaryFromUserDefaults];
        if (userDictionary != nil) {
            sharedUser = [CBUser CBUserWithDictionary:userDictionary];
        }
    }
    
    return sharedUser;
}

@end
