package com.shingrus.wswitcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WSwitcherBootReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
        	Intent service = new Intent(context, SwitcherService.class);
        	//startService(i);
        	context.startService(service);
        }

	}

}
