//
//  APIManager.h
//  CHP-Mobil
//
//  Created by Eren ; on 14.11.2012.
//  Copyright (c) 2012 Halıcı. All rights reserved.
//

#define AUTHORIZATION_TOKEN @"B05D84EC9F5F2AD042273956090435C3"

#import "MKNetworkEngine.h"

typedef void (^CompletionBlock) (NSDictionary *responseDictionary, NSError* error);
typedef void (^ProductsBlock) (NSArray *responseArray);
typedef void (^QuestionsBlock) (NSArray *responseArray);
typedef void (^ErrorBlock) (NSError *error);

@interface APIManager : MKNetworkEngine

+ (APIManager *)sharedInstance;

- (NSString *)pathForOperation:(NSString *)operation;

- (NSString*) sha1:(NSString*)input;

- (MKNetworkOperation *)createNetworkOperationForOperation:(NSString *)operationName
                                             andParameters:(NSDictionary *)parameters
                                              onCompletion:(CompletionBlock)completionBlock
                                                   onError:(ErrorBlock)errorBlock;

- (void) postRequestWithParams:(NSDictionary*) params
                  andOperation:(NSString*)opearation
            andCompletionBlock:(CompletionBlock) completionBlock
                 andErrorBlock:(ErrorBlock)errorBlock;

- (void) getAddressListsWithCompletionBlock:(CompletionBlock)completionBlock;

@end
