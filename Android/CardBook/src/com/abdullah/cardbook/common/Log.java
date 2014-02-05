package com.abdullah.cardbook.common;

public class Log {
	private static String TAG = "com.cardbook.android";
	private static Boolean IS_DEBUG=true;

	public static void i(String message) {
        if(IS_DEBUG)
    		android.util.Log.i(TAG, message);
	}

}
