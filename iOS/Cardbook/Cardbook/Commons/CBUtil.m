//
//  CBUtil.m
//  Cardbook
//
//  Created by Alperen Kavun on 29.10.2013.
//  Copyright (c) 2013 kavun. All rights reserved.
//

#import "CBUtil.h"

static CBUtil* sharedInstance = nil;

@implementation CBUtil

+ (CBUtil*) sharedInstance
{
    if (sharedInstance == nil) {
        sharedInstance = [[CBUtil alloc] init];
    }
    return sharedInstance;
}

- (UIImage*)UIImageWithUIColor:(UIColor*)color {
    CGRect rect = CGRectMake(0, 0, 1, 1);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetFillColorWithColor(context,color.CGColor);
    
    CGContextFillRect(context, rect);
    UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return img;
}

- (UIImage*) maskImage:(UIImage *)image withMask:(UIImage *)maskImage {
    //
    
#define ROUND_UP(N, S) ((((N) + (S) - 1) / (S)) * (S))
    
    // Original RGBA image
    CGImageRef originalMaskImage = [maskImage CGImage];
    float width = CGImageGetWidth(originalMaskImage);
    float height = CGImageGetHeight(originalMaskImage);
    
    // Make a bitmap context that's only 1 alpha channel
    // WARNING: the bytes per row probably needs to be a multiple of 4
    int strideLength = ROUND_UP(width * 1, 4);
    unsigned char * alphaData = calloc(strideLength * height, sizeof(unsigned char));
    CGContextRef alphaOnlyContext = CGBitmapContextCreate(alphaData,
                                                          width,
                                                          height,
                                                          8,
                                                          strideLength,
                                                          NULL,
                                                          (CGBitmapInfo)kCGImageAlphaOnly);
    
    // Draw the RGBA image into the alpha-only context.
    CGContextDrawImage(alphaOnlyContext, CGRectMake(0, 0, width, height), originalMaskImage);
    
    // Walk the pixels and invert the alpha value. This lets you colorize the opaque shapes in the original image.
    // If you want to do a traditional mask (where the opaque values block) just get rid of these loops.
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            unsigned char val = alphaData[y*strideLength + x];
            val = 255 - val;
            alphaData[y*strideLength + x] = val;
        }
    }
    
    CGImageRef alphaMaskImage = CGBitmapContextCreateImage(alphaOnlyContext);
    CGContextRelease(alphaOnlyContext);
    free(alphaData);
    
    // Make a mask
    CGImageRef mask = CGImageMaskCreate(CGImageGetWidth(alphaMaskImage),
                                        CGImageGetHeight(alphaMaskImage),
                                        CGImageGetBitsPerComponent(alphaMaskImage),
                                        CGImageGetBitsPerPixel(alphaMaskImage),
                                        CGImageGetBytesPerRow(alphaMaskImage),
                                        CGImageGetDataProvider(alphaMaskImage), NULL, false);
    
    CGImageRef masked = CGImageCreateWithMask([image CGImage], mask);
    return [UIImage imageWithCGImage:masked];
    
}

@end
