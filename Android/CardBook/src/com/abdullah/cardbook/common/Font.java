package com.abdullah.cardbook.common;

import android.content.Context;
import android.graphics.Typeface;

public class Font {

	public static String ROBOTO_BOLD="Roboto-Bold.ttf";
	public static String ROBOTO_BOLD_ITALIC="Roboto-BoldItalic.ttf";
	public static String ROBOTO_ITALIC="Roboto-Italic.ttf";
	public static String ROBOTO_LIGHT="Roboto-Light.ttf";
	public static String ROBOTO_LIGHT_ITALIC="Roboto-LightItalic.ttf";
	public static String ROBOTO_MEDIUM="Roboto-Medium.ttf";
	public static String ROBOTO_MEDIUM_ITALIC="Roboto-MediumItalic.ttf";
	public static String ROBOTO_REGULAR="Roboto-Regular.ttf";
    public static String ROBOTO_BLACK="Roboto-Black.ttf";


    public static Typeface getFont(Context context, String font){
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/"+font);

        return typeFace;
    }
	
}
