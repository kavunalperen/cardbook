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
	
	private Context context; 
	private String font;
	
	
	public Font(Context context, String font) {
		super();
		this.context = context;
		this.font = font;
	}


	public Typeface getFont(){
		Typeface typeFace = Typeface.createFromAsset(this.context.getAssets(), "fonts/"+this.font);
		
		return typeFace;
	}
	
}
