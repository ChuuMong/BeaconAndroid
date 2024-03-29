package com.mostra.android.sdk;

import com.mostra.android.sdk.domain.MostraListener;
import com.mostra.android.sdk.service.ble.MostraBLEScanService;

import android.content.Context;
import android.util.Log;

/**
 * Mostra Android SDK 2.0.1
 * Provides access to ConnectQuest Beacon Discovery
 * 
 * @Copyright Mostra, LLC 2014
 */
public class MostraSDK 
{
	private final String TAG = MostraSDK.class.getSimpleName();
	private MostraListener mListener = null;
	private Context mContext = null;
	private MostraBLEScanService mScanService = null;
	
	/**
	 * Initializes the SDK
	 * @param listener Listener using the SDK
	 * @param dataMode Beacon Data Mode (Discovery or Discovery & Data Retrieval)
	 */
	public MostraSDK(MostraListener listener)
	{
		setmContext(listener.getContext());
		mListener = listener;
		//instantiate the scanner
		mScanService = new MostraBLEScanService(mListener.getContext());
		mScanService.initialize(listener);
	}
	
	/**
	 * Begins BLE Beacon Discovery
	 */
	public void startDiscovery()
	{
		if (mScanService == null)
		{
			Log.e(TAG, "SDK Not Initialized");
		}
		mScanService.startScan();
	}
	
	/**
	 * Stops the discovery
	 */
	public void stopDiscovery()
	{
		if (mScanService == null)
		{
			Log.e(TAG, "SDK Not Initialized");
		}
		mScanService.stopLeScan();
	}

	public Context getmContext()
	{
		return mContext;
	}

	public void setmContext(Context mContext)
	{
		this.mContext = mContext;
	}
}
