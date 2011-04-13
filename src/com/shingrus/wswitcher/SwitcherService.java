package com.shingrus.wswitcher;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class SwitcherService extends Service {

	class ConfigChangesReciever extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED,
					true)) {
				System.err.println("Wifi connected");
			} else if (!intent.getBooleanExtra(
					WifiManager.EXTRA_SUPPLICANT_CONNECTED, true)) {
				System.err.println("Wifi disconnected from network");
			}

		}

		@Override
		public IBinder peekService(Context myContext, Intent service) {
			// TODO Auto-generated method stub
			return super.peekService(myContext, service);
		}

	}
	
	Notification notification;
	ConfigChangesReciever recv;
	
	
	public SwitcherService() {
		super();
		recv = new ConfigChangesReciever();
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
		
		//register reciver
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

		registerReceiver(recv, filter);
		
		super.onStart(intent, startId);
	}
}
