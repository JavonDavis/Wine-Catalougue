package com.jwray.jwraywines.classes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jwray.jwraywines.R;

/**
 * Text View Class for list item when displaying list of wines
 * @author Javon Davis
 */
public class WineTextView extends TextView {

	public WineTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	public WineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		
	}
	
	public WineTextView(Context context) {
		super(context);
		init(null);
	}
	
	private void init(AttributeSet attrs) {
		if (attrs!=null) {
			
			 TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.WineTextView);
			 String fontName = array.getString(R.styleable.WineTextView_Prida61);
			 if (fontName!=null) {
				 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);  //loads up custom font from assests
				 setTypeface(myTypeface);
			 }
			 array.recycle();
		}
	}


}
