//
//  APIManager.h
//  CHP-Mobil
//
//  Created by Eren ; on 14.11.2012.
//  Copyright (c) 2012 Halıcı. All rights reserved.
//

#define AUTHORIZATION_TOKEN @"B05D84EC9F5F2AD042273956090435C3"

#import "MKNetworkEngine.h"

typedef void (^CompletionBlock) (NSDictionary *responseDictionary);
typedef void (^ErrorBlock) (NSError *error);
typedef void (^ImageBlock) (UIImage *resultImage);
typedef void (^AddressListsBlock) (NSMutableArray* allCountries);
typedef void (^CardsBlock) (NSMutableArray* allCards);

@interface APIManager : MKNetworkEngine

+ (APIManager *)sharedInstance;

- (NSString *)pathForOperation:(NSString *)operation;

- (NSString*) sha1:(NSString*)input;

- (MKNetworkOperation *)createNetworkOperationForOperation:(NSString *)operationName
                                             andParameters:(NSDictionary *)parameters
                                              onCompletion:(CompletionBlock)completionBlock
                                                   onError:(ErrorBlock)errorBlock;

- (MKNetworkOperation *)getImageWithURLString:(NSString *)urlString
                                 onCompletion:(ImageBlock)completionBlock
                                      onError:(ErrorBlock)errorBlock;

- (void) postRequestWithParams:(NSDictionary*) params
                  andOperation:(NSString*)opearation
            andCompletionBlock:(CompletionBlock) completionBlock
                 andErrorBlock:(ErrorBlock)errorBlock;

- (void) getAddressListsWithCompletionBlock:(AddressListsBlock)completionBlock
                                    onError:(ErrorBlock)errorBlock;

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
                                  onError:(ErrorBlock)errorBlock;

- (void) getCompanyListWithCompletionBlock:(CardsBlock)completionBlock
                                   onError:(ErrorBlock)errorBlock;

- (void) addAuthorizationTokenAndTimeToDictionary:(NSMutableDictionary*)dictionary;

@end