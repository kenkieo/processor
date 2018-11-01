package com.test.processor;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import ken.android.processor.KenProcessorUtils;
import ken.android.view.FindView;

public class Test2Activity extends Activity {
    
    @FindView(R.id.text)
    View view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	   LoggerUtils.i(this, "onCreate", 1);
	   super.onCreate(savedInstanceState);
	   LoggerUtils.i(this, "onCreate", 2);
	   setContentView(R.layout.text);
	   KenProcessorUtils.getIns().bind(this, getWindow().getDecorView());
	   LoggerUtils.i(view);
    }
}
