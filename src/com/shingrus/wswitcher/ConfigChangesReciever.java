package com.shingrus.wswitcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class ConfigChangesReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, true)) {
			System.err.println("Wifi connected");
		} 
		else if (!intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, true)) {
			System.err.println("Wifi disconnected from network");
		}

	}

	@Override
	public IBinder peekService(Context myContext, Intent service) {
		// TODO Auto-generated method stub
		return super.peekService(myContext, service);
	}

}
