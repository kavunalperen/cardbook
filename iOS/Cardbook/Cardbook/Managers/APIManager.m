//
//  APIManager.m
//  CHP-Mobil
//
//  Created by Eren Halici on 14.11.2012.
//  Copyright (c) 2012 Halıcı. All rights reserved.
//

#import "APIManager.h"
#import <CommonCrypto/CommonDigest.h>
#import "County.h"
#import "City.h"
#import "Country.h"
#import "CBCard.h"
#import "CBUser.h"
#import "SBJson.h"
#import "CBCampaign.h"
#import "CBShopping.h"

@implementation APIManager

static APIManager *sharedInstance = nil;

#pragma mark - Singleton and init methods

+ (id)allocWithZone:(NSZone *)zone
{
    @synchronized(self) {
        if (sharedInstance == nil) {
            sharedInstance = [super allocWithZone:zone];
            return sharedInstance;
        }
    }
    
    return nil;
}

- (id)init
{
    self = [super initWithHostName:@"test.mycardbook.gen.tr/ApplicationService"
            //    customHeaderFields:@{@"x-client-identifier" : @"iOS"}
            ];
    
    if (self) {
        // Initialization code here.
        
        [self useCache];
    }
    
    return self;
}

+ (APIManager *)sharedInstance
{
    @synchronized (self) {
        if (sharedInstance == nil) {
            sharedInstance = [[self alloc] init];
        }
    }
    
    return sharedInstance;
}
#pragma mark - Caching
- (NSString*) cacheDirectoryName {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *cacheDirectoryName = [documentsDirectory stringByAppendingPathComponent:@"CardbookAPICache"];
    return cacheDirectoryName;
}
#pragma mark - Helpers

- (NSString *)pathForOperation:(NSString *)operation
{
//    return [NSString stringWithFormat:@"%@.json", operation];
    return operation;
}

- (MKNetworkOperation *)createNetworkOperationForOperation:(NSString *)operationName
                                             andParameters:(NSDictionary *)parameters
                                              onCompletion:(CompletionBlock)completionBlock
                                                   onError:(ErrorBlock)errorBlock {
    
    MKNetworkOperation *op = [self operationWithPath:[self pathForOperation:operationName]
                                              params:parameters
                                          httpMethod:@"GET"];
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
        NSDictionary *responseDictionary = [completedOperation responseJSON];
        
        if([[responseDictionary valueForKey:@"error"] boolValue] == true){
            NSError *apiError = [NSError errorWithDomain:@"APIError"
                                                    code:[[responseDictionary objectForKey:@"error_code"] intValue]
                                                userInfo:nil];
            if (![completedOperation isCachedResponse]) {
                errorBlock(apiError);
            }
        }
        else{
            if (![completedOperation isCachedResponse]) {
                completionBlock(responseDictionary);
            }
        }
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        if (error.domain == NSURLErrorDomain && error.code == -1009) {
            NSError *connectionError = [NSError errorWithDomain:@"ConnectionError"
                                                           code:-102
                                                       userInfo:@{NSLocalizedDescriptionKey : NSLocalizedString(@"CONNECTION_ERROR", nil)}];
            if (![completedOperation isCachedResponse]) {
                errorBlock(connectionError);
            }
        } else {
            if (![completedOperation isCachedResponse]) {
                errorBlock(error);
            }
        }
    }];
    
    [self enqueueOperation:op];
    return op;
    
}
- (void) getAddressListsWithCompletionBlock:(AddressListsBlock)completionBlock
                                    onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{}.mutableCopy;
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetAddressLists"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     NSDictionary* counties = [[responseDictionary objectForKey:@"Data"] objectForKey:@"CountyList"];
                     NSDictionary* cities = [[responseDictionary objectForKey:@"Data"] objectForKey:@"CityList"];
                     NSDictionary* countries = [[responseDictionary objectForKey:@"Data"] objectForKey:@"CountryList"];
                     
                     for (NSDictionary* d in counties) {
                         [County CountyWithDictionary:d];
                     }
                     
                     for (NSDictionary* d in cities) {
                         [City CityWithDictionary:d];
                     }
                     
                     for (NSDictionary* d in countries) {
                         [Country CountryWithDictionary:d];
                     }
                     
                     completionBlock([Country GetAllCountries]);
                 } else {
                     // handle errors
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      errorBlock(error);
                  }];
}

- (void) createOrUpdateUserWithFacebookId:(NSString*)facebookId
                        andMobileDeviceId:(NSString*)mobileDeviceId
                                  andName:(NSString*)name
                               andSurname:(NSString*)surname
                                 andEmail:(NSString*)email
                             andBirthDate:(NSString*)birthDate
                     andProfilePictureUrl:(NSString*)profilePictureUrl
                                andPhone1:(NSString*)phone1
                                andPhone2:(NSString*)phone2
                                andGender:(NSString*)gender
                             andCountryId:(NSInteger)countryId
                                andCityId:(NSInteger)cityId
                              andCountyId:(NSInteger)countyId
                           andAddressLine:(NSString*)addressLine
                             onCompletion:(CompletionBlock)completionBlock
                                  onError:(ErrorBlock)errorBlock
{
    
    
    NSMutableDictionary* paramsDictionary = @{@"FacebookId":facebookId,
                                              @"Name":name,
                                              @"Surname":surname,
                                              @"Email":email,
                                              @"BirthDate":birthDate,
                                              @"ProfilePhotoUrl":profilePictureUrl,
                                              @"Phone1":phone1,
                                              @"Phone2":phone2,
                                              @"Gender":gender,
                                              @"CountryId":[NSNumber numberWithInteger:countryId],
                                              @"CityId":[NSNumber numberWithInteger:cityId],
                                              @"CountyId":[NSNumber numberWithInteger:countyId],
                                              @"AddressLine":addressLine}.mutableCopy;
    
    CGSize screenSize = [UIScreen mainScreen].bounds.size;
    CGFloat scale = [UIScreen mainScreen].scale;
    
    [paramsDictionary setObject:[NSNumber numberWithDouble:screenSize.width*scale] forKey:@"BarcodeWidth"];
    [paramsDictionary setObject:[NSNumber numberWithDouble:screenSize.height*scale] forKey:@"BarcodeHeight"];
    
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"CreateOrUpdateUser"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     completionBlock(responseDictionary);
                 } else {
                     // handle errors
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      errorBlock(error);
                  }];
}
- (void) getAllActiveCampaignListWithCompletionBlock:(CampaignsBlock)completionBlock
                                             onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{}.mutableCopy;
    
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetAllActiveCampaignList"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     for (NSDictionary* dict in [responseDictionary objectForKey:@"Data"]) {
                         [CBCampaign CBCampaignWithDictionary:dict];
                     }
                     if (completionBlock != nil) {
                         completionBlock([CBCampaign GetAllCampaigns]);
                     }
                 } else {
                     // handle errors
                 }
                 
             } andErrorBlock:^(NSError *error) {
                 ;
             }];
}
- (void) getCompanyListWithCompletionBlock:(CardsBlock)completionBlock
                                   onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{}.mutableCopy;
    
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetCompanyList"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     for (NSDictionary* dict in [responseDictionary objectForKey:@"Data"]) {
                         [CBCard CBCardWithDictionary:dict];
                     }
                     if (completionBlock != nil) {
                         completionBlock([CBCard GetAllCards]);
                     }
                 } else {
                     // handle errors
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      ;
                  }];
}
- (void) getUserDetailWithCompletion:(CompletionBlock)completionBlock
                             onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{}.mutableCopy;
    
    [self addUserIdToDictionary:paramsDictionary];
    
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetUserDetail"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     if (completionBlock != nil) {
                         completionBlock(responseDictionary);
                     } else {
                         
                     }
                 } else {
                     NSLog(@"resultCOde is no 00");
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      if (errorBlock != nil) {
                          errorBlock(error);
                      }
                  }];
}
- (void) getAllShoppingListWithCompletionBlock:(ShoppingsBlock)completionBlock
                                       onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{}.mutableCopy;
    
    [self addUserIdToDictionary:paramsDictionary];
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetAllShoppingList"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     for (NSDictionary* dict in [responseDictionary objectForKey:@"Data"]) {
                         [CBShopping CBShoppingWithDictionary:dict];
                     }
                     if (completionBlock != nil) {
                         completionBlock([CBShopping GetAllShoppings]);
                     }
                 } else {
                     // handle errors
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      errorBlock(error);
                  }];
}
- (void) getCompanyDetailContentWithCompanyId:(NSInteger)companyId
                                 onCompletion:(CardDetailBlock)completionBlock
                                      onError:(ErrorBlock)errorBlock;
{
    NSMutableDictionary* paramsDictionary = @{@"companyId":[NSNumber numberWithInteger:companyId]}.mutableCopy;
    
    [self addUserIdToDictionary:paramsDictionary];
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetCompanyDetailContent"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     
                     if (completionBlock != nil) {
                         completionBlock([CBCardDetail CBCardDetailWithDictionary:[responseDictionary objectForKey:@"Data"]]);
                     }
                 } else {
                     // handle errors
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      if (errorBlock != nil) {
                          errorBlock(error);
                      }
                  }];
}

- (void) getCompanyInfoWithCompanyId:(NSInteger)companyId
                        onCompletion:(CardInfoBlock)completionBlock
                             onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{@"companyId":[NSNumber numberWithInteger:11]}.mutableCopy;
//    NSMutableDictionary* paramsDictionary = @{@"companyId":[NSNumber numberWithInteger:companyId]}.mutableCopy;
    
    [self addUserIdToDictionary:paramsDictionary];
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetCompanyInfo"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     
                     if (completionBlock != nil) {
                         completionBlock([CBCardInfo CBCardInfoWithDictionary:[responseDictionary objectForKey:@"Data"]]);
                     }
                 } else {
                     NSLog(@"error!!");
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      if (errorBlock != nil) {
                          errorBlock(error);
                      }
                  }];
}

- (void) getCampaignDetailContentWithCampaignId:(NSInteger)campaignId
                                   onCompletion:(CampaignDetailBlock)completionBlock
                                        onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{@"campaignId":[NSNumber numberWithInteger:campaignId]}.mutableCopy;
    
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetCampaignDetailContent"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     
                     if (completionBlock != nil) {
                         completionBlock([CBCampaignDetail CBCampaignDetailWithDictionary:[responseDictionary objectForKey:@"Data"]]);
                     }
                 } else {
                     // handle errors
                     
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      errorBlock(error);
                  }];
}
- (void) getShoppingDetailContentWithShoppingId:(NSInteger)shoppingId
                                   onCompletion:(ShoppingDetailBlock)completionBlock
                                        onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{@"shoppingId":[NSNumber numberWithInteger:shoppingId]}.mutableCopy;
    
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"GetShoppingDetailContent"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     
                     if (completionBlock != nil) {
                         completionBlock([CBShoppingDetail CBShoppingDetailWithDictionary:[responseDictionary objectForKey:@"Data"]]);
                     }
                 } else {
                     // handle errors
                     
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      ;
                  }];
}

- (void) setUserNotificationStatusWithCompanyId:(NSInteger)companyId
                          andNotificationStatus:(BOOL)notificationStatus
                                   onCompletion:(CompletionBlock)completionBlock
                                        onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictionary = @{@"companyId":[NSNumber numberWithInteger:companyId],
                                              @"wantNotification":[NSNumber numberWithBool:notificationStatus]}.mutableCopy;
    
    [self addUserIdToDictionary:paramsDictionary];
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictionary];
    
    [self postRequestWithParams:paramsDictionary
                   andOperation:@"SetUserCompanyNotificationStatus"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     if (completionBlock != nil) {
                         completionBlock(responseDictionary);
                     }
                 } else {
                     // handle errors
                 }
             }
                  andErrorBlock:^(NSError *error) {
                      errorBlock(error);
                  }];
}
- (void) setUserCompanyCardWithCompanyId:(NSInteger)companyId
                           andCardNumber:(NSString*)cardNumber
                            onCompletion:(CompletionBlock)completionBlock
                                 onError:(ErrorBlock)errorBlock
{
    NSMutableDictionary* paramsDictonary = @{@"CompanyId":[NSNumber numberWithInt:companyId],
                                             @"CardNumber":cardNumber}.mutableCopy;
    
    [self addUserIdToDictionary:paramsDictonary];
    [self addAuthorizationTokenAndTimeToDictionary:paramsDictonary];
    
    [self postRequestWithParams:paramsDictonary
                   andOperation:@"SetUserCompanyCard"
             andCompletionBlock:^(NSDictionary *responseDictionary) {
                 if ([[responseDictionary objectForKey:@"ResultCode"] isEqualToString:@"00"]) {
                     if (completionBlock != nil) {
                     completionBlock(responseDictionary);
                     }
                 } else {
                     // handle errors
                 }
             } andErrorBlock:^(NSError *error) {
                 errorBlock(error);
             }];
}
- (void) addUserIdToDictionary:(NSMutableDictionary*)dictionary
{
    [dictionary setObject:[[CBUser sharedUser] userId] forKey:@"userId"];
}
- (void) addAuthorizationTokenAndTimeToDictionary:(NSMutableDictionary*)dictionary
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    
    NSString* stringFromDate = [formatter stringFromDate:[NSDate date]];
    
    [dictionary setObject:AUTHORIZATION_TOKEN forKey:@"authorizationToken"];
    [dictionary setObject:stringFromDate forKey:@"authorizationTime"];
}

- (void) postRequestWithParams:(NSDictionary*) params
                  andOperation:(NSString*)operation
            andCompletionBlock:(CompletionBlock) completionBlock
                 andErrorBlock:(ErrorBlock)errorBlock
{
    MKNetworkOperation *op = [self operationWithPath:[self pathForOperation:operation]
                                              params:params
                                          httpMethod:@"POST"];
    
    [op setPostDataEncoding:MKNKPostDataEncodingTypeJSON];
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
//        [completedOperation setPostDataEncoding:MKNKPostDataEncodingTypeJSON];
        
        NSDictionary *responseDictionary = [completedOperation responseJSON];
        
        if([[responseDictionary valueForKey:@"error"] boolValue] == true){
            NSError *apiError = [NSError errorWithDomain:@"APIError"
                                                    code:[[responseDictionary objectForKey:@"error_code"] intValue]
                                                userInfo:nil];
            if (![completedOperation isCachedResponse]) {
                errorBlock(apiError);
            }
        }
        else{
            if (![completedOperation isCachedResponse]) {
                completionBlock(responseDictionary);
            }
        }
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        if (error.domain == NSURLErrorDomain && error.code == -1009) {
            NSError *connectionError = [NSError errorWithDomain:@"ConnectionError"
                                                           code:-102
                                                       userInfo:@{NSLocalizedDescriptionKey : NSLocalizedString(@"CONNECTION_ERROR", nil)}];
            if (![completedOperation isCachedResponse]) {
                errorBlock(connectionError);
            }
        } else {
            if (![completedOperation isCachedResponse]) {
                errorBlock(error);
            }
        }
    }];
    
    [self enqueueOperation:op];
}

-(NSString*) sha1:(NSString*)input
{
    const char *cstr = [input cStringUsingEncoding:NSUTF8StringEncoding];
    NSData *data = [NSData dataWithBytes:cstr length:input.length];
    
    uint8_t digest[CC_SHA1_DIGEST_LENGTH];
    
    CC_SHA1(data.bytes, data.length, digest);
    
    NSMutableString* output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH * 2];
    
    for(int i = 0; i < CC_SHA1_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x", digest[i]];
    
    return output;
}

#pragma mark - Getting images

- (MKNetworkOperation *)getImageWithURLString:(NSString *)urlString
                                 onCompletion:(ImageBlock)completionBlock
                                      onError:(ErrorBlock)errorBlock {
    MKNetworkOperation *op = [self operationWithURLString:urlString];
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
        completionBlock([completedOperation responseImage]);
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        errorBlock(error);
    }];
    
    [self enqueueOperation:op];
    
    return op;
}
@end
