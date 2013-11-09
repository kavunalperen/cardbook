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
        //        [self useCache];
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
                                                userInfo:@{NSLocalizedDescriptionKey : [responseDictionary valueForKey:@"error_message"]}];
            errorBlock(apiError);
        }
        else{
            completionBlock(responseDictionary);
        }
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        if (error.domain == NSURLErrorDomain && error.code == -1009) {
            NSError *connectionError = [NSError errorWithDomain:@"ConnectionError"
                                                           code:-102
                                                       userInfo:@{NSLocalizedDescriptionKey : NSLocalizedString(@"CONNECTION_ERROR", nil)}];
            errorBlock(connectionError);
        } else {
            errorBlock(error);
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
                                              @"CountryId":[NSNumber numberWithInt:countryId],
                                              @"CityId":[NSNumber numberWithInt:cityId],
                                              @"CountyId":[NSNumber numberWithInt:countyId],
                                              @"AddressLine":addressLine}.mutableCopy;
    
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
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
        NSDictionary *responseDictionary = [completedOperation responseJSON];

        if([[responseDictionary valueForKey:@"error"] boolValue] == true){
            NSError *apiError = [NSError errorWithDomain:@"APIError"
                                                    code:[[responseDictionary objectForKey:@"error_code"] intValue]
                                                userInfo:@{NSLocalizedDescriptionKey : [responseDictionary valueForKey:@"error_message"]}];
            if(errorBlock != nil)
                errorBlock(apiError);
        }
        else{
            completionBlock(responseDictionary);
        }
        
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        if (error.domain == NSURLErrorDomain && error.code == -1009) {
            NSError *connectionError = [NSError errorWithDomain:@"ConnectionError"
                                                           code:-102
                                                       userInfo:@{NSLocalizedDescriptionKey : NSLocalizedString(@"CONNECTION_ERROR", nil)}];
            if(errorBlock != nil)
                errorBlock(connectionError);
        } else {
            if(errorBlock != nil)
                errorBlock(error);
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
