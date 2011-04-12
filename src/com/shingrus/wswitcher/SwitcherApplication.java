package com.shingrus.wswitcher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;


public class SwitcherApplication extends Application {
	
	public SwitcherApplication() {
		super();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		System.err.println("Configuration changed: " + newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		this.startService(new Intent(this, SwitcherService.class));
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		//stopService(new Intent(this, SwitcherService.class) );
		super.onTerminate();
	}
	
}
