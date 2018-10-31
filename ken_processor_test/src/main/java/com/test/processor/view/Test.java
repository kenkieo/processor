package com.test.processor.view;

import android.content.Context;
import android.view.View;

import com.test.processor.R;

import ken.android.view.FindView;

public class Test extends View {
    
    public Test(Context context) {
	   super(context);
    }
    
    @FindView(R.id.text)
    View view;
}
