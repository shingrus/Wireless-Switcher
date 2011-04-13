package com.shingrus.wswitcher;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class SwitcherService extends Service {

	Notification notification;
	
	public SwitcherService() {
		super();
	}

	@Override
	public void onCreate() {
		System.out.println("Create new service");
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {

		String SSIDName = ""; 
		
		WifiManager wmf = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		if (wmf != null){
			WifiInfo info = wmf.getConnectionInfo();
			if (info != null) {
				SSIDName = info.getSSID();
			}
		}
		notification = new Notification(R.drawable.icon,  SSIDName,
		        System.currentTimeMillis());
		notification.flags |= Notification.FLAG_NO_CLEAR;

		
		Intent notificationIntent = new Intent(this, SwitcherSettingsActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, SSIDName,"", pendingIntent);
		startForeground(100500, notification);
		
		super.onStart(intent, startId);
	}
}
