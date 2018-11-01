package com.test.processor;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ken.android.processor.KenProcessorUtils;
import ken.android.view.FindView;
import ken.android.view.ViewClick;

public class TestActivity extends Activity {
    
    @FindView(R.id.text)
    View mTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	   LoggerUtils.i(this, "onCreate", 1);
	   super.onCreate(savedInstanceState);
	   LoggerUtils.i(this, "onCreate", 2);
	   setContentView(R.layout.text);
	   KenProcessorUtils.getIns().bind(this, getWindow().getDecorView());
	   startActivity(new Intent(this, Test2Activity.class));
    }
    
    @ViewClick(R.id.text)
    void gotoClick(View v) {}
}
