package com.shingrus.wswitcher;

import com.shingrus.wswitcher.R;
import android.app.Activity;
import android.os.Bundle;


public class SwitcherSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public SwitcherSettingsActivity() {
		super();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
