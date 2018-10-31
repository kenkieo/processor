package com.test.processor;

import android.app.Application;

import ken.android.processor.KenProcessorUtils;

public class MyApplication extends Application{
    
    @Override
    public void onCreate() {
	   super.onCreate();
	   KenProcessorUtils.getIns().initApplication(this);
    }
}
