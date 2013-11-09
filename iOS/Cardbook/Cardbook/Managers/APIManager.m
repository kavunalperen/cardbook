//
//  APIManager.m
//  CHP-Mobil
//
//  Created by Eren Halici on 14.11.2012.
//  Copyright (c) 2012 Halıcı. All rights reserved.
//

#import "APIManager.h"
#import <CommonCrypto/CommonDigest.h>

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

#pragma mark - Caching

//- (NSString*) cacheDirectoryName {
//    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES);
//    NSString *documentsDirectory = [paths objectAtIndex:0];
//    NSString *cacheDirectoryName = [documentsDirectory stringByAppendingPathComponent:@"dimAPICache"];
//    return cacheDirectoryName;
//}

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
                                 //                                                userInfo:@{NSLocalizedDescriptionKey : [responseDictionary valueForKey:operationName]}];
                                                userInfo:@{NSLocalizedDescriptionKey : [responseDictionary valueForKey:@"error_message"]}];
            errorBlock(apiError);
        }
        else{
            completionBlock(responseDictionary,nil);
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

- (void) addQuestionWithQuestion:(NSString *)question
                       andAnswer:(NSString *)answer
              andCompletionBlock:(CompletionBlock) completionBlock
                         onError:(ErrorBlock) errorBlock
{
    [self postRequestWithParams:@{
//            @"auth_token" : [[[AuthenticationManager sharedInstance] getLastLoggedUser] token],
            @"question" : question,
            @"answer" : answer
                                }
                   andOperation:@"your_questions/add_question"
             andCompletionBlock:completionBlock
                  andErrorBlock:errorBlock];
}
- (void) getAddressListsWithCompletionBlock:(CompletionBlock)completionBlock
{
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    
    NSString* stringFromDate = [formatter stringFromDate:[NSDate date]];
    
    [self postRequestWithParams:@{@"authorizationToken" : AUTHORIZATION_TOKEN, @"authorizationTime" : stringFromDate} andOperation:@"GetAddressLists" andCompletionBlock:^(NSDictionary *responseDictionary, NSError* error) {
        completionBlock([responseDictionary objectForKey:@"Data"],nil);
    } andErrorBlock:^(NSError *error) {
        completionBlock(nil,error);
    }];
}
- (void) postRequestWithParams:(NSDictionary*) params andOperation:(NSString*)operation andCompletionBlock:(CompletionBlock) completionBlock andErrorBlock:(ErrorBlock)errorBlock
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
            completionBlock(responseDictionary,nil);
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
@end
