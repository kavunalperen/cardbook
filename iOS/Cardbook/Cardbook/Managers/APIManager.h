//
//  APIManager.h
//  CHP-Mobil
//
//  Created by Eren ; on 14.11.2012.
//  Copyright (c) 2012 Halıcı. All rights reserved.
//

#define AUTHORIZATION_TOKEN @"B05D84EC9F5F2AD042273956090435C3"

#import "MKNetworkEngine.h"
#import "CBCardDetail.h"
#import "CBCampaignDetail.h"
#import "CBShoppingDetail.h"
#import "CBCardInfo.h"

typedef void (^CompletionBlock) (NSDictionary *responseDictionary);
typedef void (^ErrorBlock) (NSError *error);
typedef void (^ImageBlock) (UIImage *resultImage);
typedef void (^AddressListsBlock) (NSMutableArray* allCountries);
typedef void (^CardsBlock) (NSMutableArray* allCards);
typedef void (^CardDetailBlock) (CBCardDetail* cardDetail);
typedef void (^CampaignsBlock) (NSMutableArray* allCampaigns);
typedef void (^CampaignDetailBlock) (CBCampaignDetail* campaignDetail);
typedef void (^ShoppingsBlock) (NSMutableArray* allShoppings);
typedef void (^ShoppingDetailBlock) (CBShoppingDetail* campaignDetail);
typedef void (^CardInfoBlock) (CBCardInfo* cardInfo);

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

- (void) getUserDetailWithCompletion:(CompletionBlock)completionBlock
                             onError:(ErrorBlock)errorBlock;

- (void) updateMobileDeviceId:(NSString*)mobileDeviceId
                 onCompletion:(CompletionBlock)completionBlock
                      onError:(ErrorBlock)errorBlock;

- (void) getCompanyListWithCompletionBlock:(CardsBlock)completionBlock
                                   onError:(ErrorBlock)errorBlock;

- (void) getCompanyDetailContentWithCompanyId:(NSInteger)companyId
                                 onCompletion:(CardDetailBlock)completionBlock
                                      onError:(ErrorBlock)errorBlock;

- (void) getCompanyInfoWithCompanyId:(NSInteger)companyId
                        onCompletion:(CardInfoBlock)completionBlock
                             onError:(ErrorBlock)errorBlock;

- (void) setUserNotificationStatusWithCompanyId:(NSInteger)companyId
                          andNotificationStatus:(BOOL)notificationStatus
                                   onCompletion:(CompletionBlock)completionBlock
                                        onError:(ErrorBlock)errorBlock;

- (void) setUserCompanyCardWithCompanyId:(NSInteger)companyId
                           andCardNumber:(NSString*)cardNumber
                            onCompletion:(CompletionBlock)completionBlock
                                 onError:(ErrorBlock)errorBlock;

- (void) getAllActiveCampaignListWithCompletionBlock:(CampaignsBlock)completionBlock
                                             onError:(ErrorBlock)errorBlock;

- (void) getCampaignDetailContentWithCampaignId:(NSInteger)campaignId
                                   onCompletion:(CampaignDetailBlock)completionBlock
                                        onError:(ErrorBlock)errorBlock;

- (void) getAllShoppingListWithCompletionBlock:(ShoppingsBlock)completionBlock
                                       onError:(ErrorBlock)errorBlock;

- (void) getShoppingDetailContentWithShoppingId:(NSInteger)shoppingId
                                   onCompletion:(ShoppingDetailBlock)completionBlock
                                        onError:(ErrorBlock)errorBlock;

- (void) addAuthorizationTokenAndTimeToDictionary:(NSMutableDictionary*)dictionary;

@end
