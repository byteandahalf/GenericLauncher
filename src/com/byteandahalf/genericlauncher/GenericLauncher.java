package com.byteandahalf.genericlauncher;

import android.app.Application;

public class GenericLauncher extends Application {
	@Override
	public void onCreate() {
		Utils.setContext(getApplicationContext());
		super.onCreate();
	}
}
