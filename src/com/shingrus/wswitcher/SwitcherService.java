package com.shingrus.wswitcher;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

/**
 * 
 * @author shingrus
 * @version 1.0
 * 
 */
public class SwitcherService extends Service {

	public class ConfigChangesReciever extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			final Intent notificationIntent = new Intent(context, SwitcherSettingsActivity.class);
			final PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			
			final String action = intent.getAction();
			System.err.println("Action:" + action);
			if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            		NetworkInfo ni = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            		if (ni.isConnected() == true && ni.getType() == ConnectivityManager.TYPE_WIFI) {
            			//String bssid = intent.getStringExtra(WifiManager.EXTRA_BSSID);
            			WifiInfo info = wm.getConnectionInfo();
            			String ssid = info.getSSID();
            			notification.setLatestEventInfo(context, "Connected", "SSID:" + ssid, contentIntent);
            			notificationManager.notify(R.integer.notyfyId, notification);
            			
            		}
            		else {
            			System.err.println("disconnected");
            			notification.setLatestEventInfo(context, "disconnected", "disconnected", contentIntent);
            			notificationManager.notify(R.integer.notyfyId, notification);
            			
            		}
            }
            else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            	int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
            		    WifiManager.WIFI_STATE_UNKNOWN);
            	switch(extraWifiState){
            	  case WifiManager.WIFI_STATE_DISABLED:
          			notification.setLatestEventInfo(context, "disconnected", "disconnected", contentIntent);
        			notificationManager.notify(R.integer.notyfyId, notification);
        			break;
            	  }
            }
            else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            	Intent i = new Intent(context, SwitcherService.class);
            	startService(i);
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
	private NotificationManager notificationManager;
	
	
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
		
		
		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi != null && wifi.isConnected()) {
			WifiManager wmf = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
			if (wmf != null){
				WifiInfo info = wmf.getConnectionInfo();
				if (info != null) {
					SupplicantState ss =  info.getSupplicantState();
					NetworkInfo.DetailedState ns = WifiInfo.getDetailedStateOf(ss);
					
					SSIDName = info.getSSID();
				}
			}

		}
		
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		notification = new Notification(R.drawable.icon,  SSIDName,
		        System.currentTimeMillis());
		notification.flags |= Notification.FLAG_NO_CLEAR;

		
		
		
		Intent notificationIntent = new Intent(this, SwitcherSettingsActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, SSIDName,"", pendingIntent);
		startForeground(R.integer.notyfyId, notification);
		
		//register reciver
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		//ilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

		registerReceiver(recv, filter);
		
		super.onStart(intent, startId);
	}
}
