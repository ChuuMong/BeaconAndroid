package com.mostra.beaconandroid;

import com.mostra.android.sdk.MostraSDK;
import com.mostra.android.sdk.domain.MostraBeacon;
import com.mostra.android.sdk.domain.MostraListener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private static final int REQUEST_ENABLE_BT = 1;

	private MostraSDK mMostraSDK;
	private BluetoothAdapter mBluetoothAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
		{
			Toast.makeText(this, "비콘을 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
			finish();
		}

		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled())
		{
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	@Override
	protected void onResume()
	{
		// TODO 자동 생성된 메소드 스텁
		super.onResume();
		if (mBluetoothAdapter.isEnabled())
		{
			mMostraSDK = new MostraSDK(mSDKListener);
			mMostraSDK.startDiscovery();
		}
	}
	
	private MostraListener mSDKListener = new MostraListener()
	{

		@Override
		public void handleGenericBeaconDiscovery(MostraBeacon beacon)
		{
			if (beacon == null)
				return;

			if (beacon.getRSSI() > -70)
			{

			}
		}

		@Override
		public Context getContext()
		{
			return getApplicationContext();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO 자동 생성된 메소드 스텁
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode > -1)
			finish();
	}

	@Override
	protected void onDestroy()
	{
		if (mMostraSDK != null)
		{
			mMostraSDK.stopDiscovery();
			System.exit(0);
		}
		super.onDestroy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
