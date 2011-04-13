package com.shingrus.wswitcher;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SwitcherService extends Service {

	Notification notification;
	
	public SwitcherService() {
		super();
		notification = new Notification(R.drawable.icon, getText(R.string.ticker_text),
		        System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, SwitcherSettingsActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, getText(R.string.notification_title),
		        getText(R.string.notification_message), pendingIntent);
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
		
		startForeground(100500, notification);
		super.onStart(intent, startId);
	}
}
