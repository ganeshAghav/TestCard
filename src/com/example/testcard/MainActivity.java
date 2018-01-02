package com.example.testcard;

import java.util.Locale;
import java.util.TreeMap;

import com.acs.audiojack.AudioJackReader;
import com.acs.audiojack.DukptReceiver;
import com.acs.audiojack.Result;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	public static final String DEFAULT_MASTER_KEY_STRING = "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
	public static final String DEFAULT_AES_KEY_STRING = "4E 61 74 68 61 6E 2E 4C 69 20 54 65 64 64 79 20";
	public static final String DEFAULT_IKSN_STRING = "FF FF 98 76 54 32 10 E0 00 00";
	public static final String DEFAULT_IPEK_STRING = "6A C2 92 FA A1 31 5B 4D 85 8A B3 A3 D7 D5 93 3A";

	private AudioManager mAudioManager;
	private AudioJackReader mReader;
	private DukptReceiver mDukptReceiver = new DukptReceiver();
	private Context mContext = this;

	private ProgressDialog mProgress;

	private Object mResponseEvent = new Object();
	private boolean mResultReady;
	private Result mResult;
	private boolean mPiccAtrReady;
	private byte[] mPiccAtr;

	private boolean mPiccResponseApduReady;
	private byte[] mPiccResponseApdu;

	private boolean mPiccResponseValueReady;
	private byte[] mPiccResponseValue;

	private boolean mPiccResponseAuthenticateReady;
	private byte[] mPiccResponseAuthenticate;

	private byte[] mMasterKey = new byte[16];
	private byte[] mNewMasterKey = new byte[16];
	private byte[] mAesKey = new byte[16];
	private byte[] mIksn = new byte[10];
	private byte[] mIpek = new byte[16];
	private int mPiccTimeout;
	private int mPiccCardType;
	private byte[] mPiccCommandApdu;
	// private String mHexString;

	private byte[] mpiccCommand;
	private byte[] mpiccCommandAuth;
	TextView txtresponsae, txtatr, txtread;
	Button btnreset, btnread, btnpower, btntransmit, btnauth, btnauthall;

	class TransmitAuthenticateForBlock04 implements Runnable {
		@Override
		public void run() {

			/* Transmit the command APDU. */
			mPiccResponseApduReady = false;
			mResultReady = false;
			if (!mReader.piccTransmit(mPiccTimeout, mPiccCommandApdu)) {
				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC response APDU. */
				showPiccResponseApdu();
			}

			/* Hide the progress. */
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mProgress.dismiss();
				};
			});
		}
	}

	class TransmitAuthenticateForBlock05 implements Runnable {
		@Override
		public void run() {

			/* Transmit the command APDU. */
			mPiccResponseApduReady = false;
			mResultReady = false;
			if (!mReader.piccTransmit(mPiccTimeout, mPiccCommandApdu)) {
				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC response APDU. */
				showPiccResponseApdu();
			}

			/* Hide the progress. */
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mProgress.dismiss();
				};
			});
		}
	}

	class TransmitAuthenticateForBlock06 implements Runnable {
		@Override
		public void run() {

			/* Transmit the command APDU. */
			mPiccResponseApduReady = false;
			mResultReady = false;
			if (!mReader.piccTransmit(mPiccTimeout, mPiccCommandApdu)) {
				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC response APDU. */
				showPiccResponseApdu();
			}

			/* Hide the progress. */
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mProgress.dismiss();
				};
			});
		}
	}

	class TransmitReadForBlock04 implements Runnable {

		@Override
		public void run() {

			/* Transmit the command APDU. */
			mPiccResponseValueReady = false;
			mResultReady = false;
			if (!mReader.piccTransmit(mPiccTimeout, mpiccCommand)) {

				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC response APDU. */
				showPiccResponseApdu1();
			}

			/* Hide the progress. */
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mProgress.dismiss();
				};
			});
		}
	}

	public class AsyncSoapLoaderAuthenticate04 extends
			AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			mPiccResponseApduReady = false;
			mResultReady = false;
			if (!mReader.piccTransmit(mPiccTimeout, mPiccCommandApdu)) {
				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC response APDU. */
				showPiccResponseApdu();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (toHexString(mPiccResponseApdu) != null) {
				new AsyncSoapLoaderReadValue04().execute();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public class AsyncSoapLoaderAuthenticate05 extends
			AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			new Thread(new TransmitAuthenticateForBlock05()).start();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public class AsyncSoapLoaderAuthenticate06 extends
			AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			new Thread(new TransmitAuthenticateForBlock06()).start();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public class AsyncSoapLoaderReadValue04 extends
			AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			/* Transmit the command APDU. */
			mPiccResponseValueReady = false;
			mResultReady = false;
			if (!mReader.piccTransmit(mPiccTimeout, mpiccCommand)) {

				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC response APDU. */
				showPiccResponseApdu1();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public class AsyncSoapLoaderReadValue05 extends
			AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			class TransmitReadForBlock05 implements Runnable {

				@Override
				public void run() {

					/* Transmit the command APDU. */
					mPiccResponseValueReady = false;
					mResultReady = false;
					if (!mReader.piccTransmit(mPiccTimeout, mpiccCommand)) {

						/* Show the request queue error. */
						showRequestQueueError();

					} else {

						/* Show the PICC response APDU. */
						showPiccResponseApdu1();
					}

					/* Hide the progress. */
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mProgress.dismiss();
						};
					});
				}
			}
			new Thread(new TransmitReadForBlock05()).start();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public class AsyncSoapLoaderReadValue06 extends
			AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			class TransmitReadForBlock06 implements Runnable {

				@Override
				public void run() {

					/* Transmit the command APDU. */
					mPiccResponseValueReady = false;
					mResultReady = false;
					if (!mReader.piccTransmit(mPiccTimeout, mpiccCommand)) {

						/* Show the request queue error. */
						showRequestQueueError();

					} else {

						/* Show the PICC response APDU. */
						showPiccResponseApdu1();
					}

					/* Hide the progress. */
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mProgress.dismiss();
						};
					});
				}
			}
			new Thread(new TransmitReadForBlock06()).start();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public class AsyncSoapLoaderForAtr extends AsyncTask<String, Void, String> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			mPiccAtrReady = false;
			mResultReady = false;
			if (!mReader.piccPowerOn(mPiccTimeout, mPiccCardType)) {

				/* Show the request queue error. */
				showRequestQueueError();

			} else {

				/* Show the PICC ATR. */
				synchronized (mResponseEvent) {

					/* Wait for the PICC ATR. */
					while (!mPiccAtrReady && !mResultReady) {

						try {
							mResponseEvent.wait(10000);
						} catch (InterruptedException e) {
						}

						break;
					}
				}
				if (mPiccAtrReady) {
					txtatr.setText(toHexString(mPiccAtr));

				} else if (mResultReady) {
					/* Show the result. */
					Toast.makeText(mContext,
							toErrorCodeString(mResult.getErrorCode()),
							Toast.LENGTH_LONG).show();
				} else {
					/* Show the timeout. */
					Toast.makeText(mContext, "The operation timed out.",
							Toast.LENGTH_LONG).show();
				}
				mPiccAtrReady = false;
				mResultReady = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();

			if (toHexString(mPiccAtr) != null) {
				new AsyncSoapLoaderAuthenticate04().execute();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("just a moment.....");
			dialog.show();
		}

	}

	public void ResetOnClick() {
		btnreset = (Button) findViewById(R.id.btnreset);
		btnreset.setOnClickListener(new OnClickListener() {
			class OnResetCompleteListener implements
					AudioJackReader.OnResetCompleteListener {

				@Override
				public void onResetComplete(AudioJackReader reader) {

					/* Hide the progress. */
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mProgress.dismiss();
						};
					});
				}
			}

			@Override
			public void onClick(View v) {
				/* Check the reset volume. */
				if (!checkResetVolume()) {
					return;
				}
				/* Show the progress. */
				mProgress.setMessage("Resetting the reader...");
				mProgress.show();
				/* Reset the reader. */
				mReader.reset(new OnResetCompleteListener());
				return;
			}
		});
	}

	public void Authenticate() {
		btnauth = (Button) findViewById(R.id.btnauthenticate);
		btnauth.setOnClickListener(new OnClickListener() {
			class Transmit2 implements Runnable {

				@Override
				public void run() {

					/* Transmit the command APDU. */
					mPiccResponseAuthenticateReady = false;
					mResultReady = false;
					if (!mReader.piccTransmit(mPiccTimeout, mpiccCommandAuth)) {

						/* Show the request queue error. */
						showRequestQueueError();

					} else {

						/* Show the PICC response APDU. */
						showPiccResponseApdu2();
					}

					/* Hide the progress. */
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mProgress.dismiss();
						};
					});
				}
			}

			@Override
			public void onClick(View v) {
				if (!checkResetVolume()) {
					return;
				}

				mProgress.setMessage("Transmitting the command APDU...");
				mProgress.show();

				new Thread(new Transmit2()).start();
				return;
			}
		});

	}

	private class OnPiccAtrAvailableListener implements
			AudioJackReader.OnPiccAtrAvailableListener {

		@Override
		public void onPiccAtrAvailable(AudioJackReader reader, byte[] atr) {

			synchronized (mResponseEvent) {

				/* Store the PICC ATR. */
				mPiccAtr = new byte[atr.length];
				System.arraycopy(atr, 0, mPiccAtr, 0, atr.length);

				/* Trigger the response event. */
				mPiccAtrReady = true;
				mResponseEvent.notifyAll();
			}
		}
	}

	private class OnPiccResponseApduAvailableListener implements
			AudioJackReader.OnPiccResponseApduAvailableListener {

		@Override
		public void onPiccResponseApduAvailable(AudioJackReader reader,
				byte[] responseApdu) {

			synchronized (mResponseEvent) {

				/* Store the PICC response APDU. */
				mPiccResponseApdu = new byte[responseApdu.length];
				// mPiccResponseValue = new byte[responseApdu.length];
				System.arraycopy(responseApdu, 0, mPiccResponseApdu, 0,
						responseApdu.length);
				// System.arraycopy(responseApdu, 0, mPiccResponseValue, 0,
				// responseApdu.length);

				/* Trigger the response event. */
				mPiccResponseApduReady = true;
				mResponseEvent.notifyAll();
			}
		}
	}

	private class OnPiccResponseApduAvailableListener1 implements
			AudioJackReader.OnPiccResponseApduAvailableListener {

		@Override
		public void onPiccResponseApduAvailable(AudioJackReader reader,
				byte[] responseApdu) {

			synchronized (mResponseEvent) {

				/* Store the PICC response APDU. */
				mPiccResponseValue = new byte[responseApdu.length];
				System.arraycopy(responseApdu, 0, mPiccResponseValue, 0,
						responseApdu.length);

				/* Trigger the response event. */
				mPiccResponseValueReady = true;
				mResponseEvent.notifyAll();
			}
		}
	}

	private class OnPiccResponseApduAvailableListener2 implements
			AudioJackReader.OnPiccResponseApduAvailableListener {

		@Override
		public void onPiccResponseApduAvailable(AudioJackReader reader,
				byte[] responseApdu) {

			synchronized (mResponseEvent) {

				/* Store the PICC response APDU. */
				mPiccResponseAuthenticate = new byte[responseApdu.length];
				System.arraycopy(responseApdu, 0, mPiccResponseAuthenticate, 0,
						responseApdu.length);

				/* Trigger the response event. */
				mPiccResponseAuthenticateReady = true;
				mResponseEvent.notifyAll();
			}
		}
	}

	private final BroadcastReceiver mHeadsetPlugReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {

				boolean plugged = (intent.getIntExtra("state", 0) == 1);

				/* Mute the audio output if the reader is unplugged. */
				mReader.setMute(!plugged);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mReader = new AudioJackReader(mAudioManager, true);

		/* Register the headset plug receiver. */
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(mHeadsetPlugReceiver, filter);

		btnreset = (Button) findViewById(R.id.btnreset);
		btnread = (Button) findViewById(R.id.btnread);
		btnpower = (Button) findViewById(R.id.btnpower);
		btntransmit = (Button) findViewById(R.id.btntransmit);
		btnauth = (Button) findViewById(R.id.btnauthenticate);
		btnauthall = (Button) findViewById(R.id.btnauthenticateall);
		txtatr = (TextView) findViewById(R.id.txtatr);
		txtresponsae = (TextView) findViewById(R.id.txtresponse);
		txtread = (TextView) findViewById(R.id.txtread);

		/* Load the new master key. */
		String newMasterKeyString = DEFAULT_MASTER_KEY_STRING;
		if ((newMasterKeyString == null) || newMasterKeyString.equals("")
				|| (toByteArray(newMasterKeyString, mNewMasterKey) != 16)) {
			newMasterKeyString = DEFAULT_MASTER_KEY_STRING;
			toByteArray(newMasterKeyString, mNewMasterKey);
		}
		newMasterKeyString = toHexString(mNewMasterKey);

		/* Load the master key. */
		String masterKeyString = DEFAULT_MASTER_KEY_STRING;
		if ((masterKeyString == null) || masterKeyString.equals("")
				|| (toByteArray(masterKeyString, mMasterKey) != 16)) {
			masterKeyString = DEFAULT_MASTER_KEY_STRING;
			toByteArray(masterKeyString, mMasterKey);
		}
		masterKeyString = toHexString(mMasterKey);

		/* Load the AES key. */
		String aesKeyString = DEFAULT_AES_KEY_STRING;
		if ((aesKeyString == null) || aesKeyString.equals("")
				|| (toByteArray(aesKeyString, mAesKey) != 16)) {
			aesKeyString = DEFAULT_AES_KEY_STRING;
			toByteArray(aesKeyString, mAesKey);
		}
		aesKeyString = toHexString(mAesKey);

		/* Load the IKSN. */
		String iksnString = DEFAULT_IKSN_STRING;
		if ((iksnString == null) || iksnString.equals("")
				|| (toByteArray(iksnString, mIksn) != 10)) {
			iksnString = DEFAULT_IKSN_STRING;
			toByteArray(iksnString, mIksn);
		}
		iksnString = toHexString(mIksn);

		/* Load the IPEK. */
		String ipekString = DEFAULT_IPEK_STRING;
		if ((ipekString == null) || ipekString.equals("")
				|| (toByteArray(ipekString, mIpek) != 16)) {
			ipekString = DEFAULT_IPEK_STRING;
			toByteArray(ipekString, mIpek);
		}
		ipekString = toHexString(mIpek);

		/* Load the PICC timeout. */
		String piccTimeoutString = "10";
		if ((piccTimeoutString == null) || piccTimeoutString.equals("")) {
			piccTimeoutString = "10";
		}
		try {
			mPiccTimeout = Integer.parseInt(piccTimeoutString);
		} catch (NumberFormatException e) {
			mPiccTimeout = 10;
		}
		piccTimeoutString = Integer.toString(mPiccTimeout);

		/* Load the PICC card type. */
		String piccCardTypeString = "8F";
		if ((piccCardTypeString == null) || piccCardTypeString.equals("")) {
			piccCardTypeString = "8F";
		}
		byte[] cardType = new byte[1];
		toByteArray(piccCardTypeString, cardType);
		mPiccCardType = cardType[0] & 0xFF;
		piccCardTypeString = toHexString(mPiccCardType);

		/* Load the PICC command APDU. Authenticate by 04 Block */
		String piccCommandApduString = "FF 86 00 00 05 01 00 04 60 00";
		if ((piccCommandApduString == null)
				|| (piccCommandApduString.equals(""))) {
			piccCommandApduString = "FF 86 00 00 05 01 00 04 60 00";// "3B FA 18 00 00 81 31 FE 45 4A 33 41 30 34 30 56 32 34 31 84";
																	// //"FF CA 00 00 00";//"00 84 00 00 08";
		}
		mPiccCommandApdu = toByteArray(piccCommandApduString);
		piccCommandApduString = toHexString(mPiccCommandApdu);

		/* Read Command */
		String piccCommandApduValue = "FF B1 00 04 04";// "FF D6 00 05 10 00 00 00 04";
		if (piccCommandApduValue == null || (piccCommandApduValue.equals(""))) {
			piccCommandApduValue = "FF B1 00 04 04";// "FF D6 00 05 10 48 65 6C 6C 6F";
		}
		mpiccCommand = toByteArray(piccCommandApduValue);
		piccCommandApduValue = toHexString(mpiccCommand);

		/* Initialize the progress dialog */
		mProgress = new ProgressDialog(mContext);
		mProgress.setCancelable(false);
		mProgress.setIndeterminate(true);

		ResetOnClick();
		// Authenticate();
		// PowerOnClick();
		// ReadOnClick();

		/* Set the PICC ATR callback. */
		mReader.setOnPiccAtrAvailableListener(new OnPiccAtrAvailableListener());

		/* Set the PICC response APDU callback. */

		mReader.setOnPiccResponseApduAvailableListener(new OnPiccResponseApduAvailableListener());

		mReader.setOnPiccResponseApduAvailableListener(new OnPiccResponseApduAvailableListener1());

		// mReader.setOnPiccResponseApduAvailableListener(new
		// OnPiccResponseApduAvailableListener2());

		/* Set the key serial number. */
		mDukptReceiver.setKeySerialNumber(mIksn);

		/* Load the initial key. */
		mDukptReceiver.loadInitialKey(mIpek);

		if (checkResetVolume()) {
			new AsyncSoapLoaderForAtr().execute();
		}
		// new Thread(new TransmitAuthenticateForBlock04()).start();
		// new Thread(new TransmitReadForBlock04()).start();

		/* Set the raw data callback. */
		// mReader.setOnRawDataAvailableListener(new
		// OnRawDataAvailableListener());

	}

	protected void onDestroy() {

		/* Unregister the headset plug receiver. */
		unregisterReceiver(mHeadsetPlugReceiver);

		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mReader.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mReader.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mProgress.dismiss();
		mReader.stop();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mProgress.dismiss();
		mReader.stop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String toErrorCodeString(int errorCode) {

		String errorCodeString = null;

		switch (errorCode) {
		case Result.ERROR_SUCCESS:
			errorCodeString = "The operation completed successfully.";
			break;
		case Result.ERROR_INVALID_COMMAND:
			errorCodeString = "The command is invalid.";
			break;
		case Result.ERROR_INVALID_PARAMETER:
			errorCodeString = "The parameter is invalid.";
			break;
		case Result.ERROR_INVALID_CHECKSUM:
			errorCodeString = "The checksum is invalid.";
			break;
		case Result.ERROR_INVALID_START_BYTE:
			errorCodeString = "The start byte is invalid.";
			break;
		case Result.ERROR_UNKNOWN:
			errorCodeString = "The error is unknown.";
			break;
		case Result.ERROR_DUKPT_OPERATION_CEASED:
			errorCodeString = "The DUKPT operation is ceased.";
			break;
		case Result.ERROR_DUKPT_DATA_CORRUPTED:
			errorCodeString = "The DUKPT data is corrupted.";
			break;
		case Result.ERROR_FLASH_DATA_CORRUPTED:
			errorCodeString = "The flash data is corrupted.";
			break;
		case Result.ERROR_VERIFICATION_FAILED:
			errorCodeString = "The verification is failed.";
			break;
		case Result.ERROR_PICC_NO_CARD:
			errorCodeString = "No card in PICC slot.";
			break;
		default:
			errorCodeString = "Error communicating with reader.";
			break;
		}

		return errorCodeString;
	}

	private String toHexString(byte[] buffer) {

		String bufferString = "";

		if (buffer != null) {

			for (int i = 0; i < buffer.length; i++) {

				String hexChar = Integer.toHexString(buffer[i] & 0xFF);
				if (hexChar.length() == 1) {
					hexChar = "0" + hexChar;
				}
				// removed the space
				bufferString += hexChar.toUpperCase(Locale.US) + " ";
			}
		}

		return bufferString;
	}

	private String toHexString(int i) {

		String hexString = Integer.toHexString(i);

		if (hexString.length() % 2 == 1) {
			hexString = "0" + hexString;
		}

		return hexString.toUpperCase(Locale.US) + " ";
	}

	private int toByteArray(String hexString, byte[] byteArray) {

		char c = 0;
		boolean first = true;
		int length = 0;
		int value = 0;
		int i = 0;

		for (i = 0; i < hexString.length(); i++) {

			c = hexString.charAt(i);
			if ((c >= '0') && (c <= '9')) {
				value = c - '0';
			} else if ((c >= 'A') && (c <= 'F')) {
				value = c - 'A' + 10;
			} else if ((c >= 'a') && (c <= 'f')) {
				value = c - 'a' + 10;
			} else {
				value = -1;
			}

			if (value >= 0) {

				if (first) {

					byteArray[length] = (byte) (value << 4);

				} else {

					byteArray[length] |= value;
					length++;
				}

				first = !first;
			}

			if (length >= byteArray.length) {
				break;
			}
		}

		return length;
	}

	private byte[] toByteArray(String hexString) {

		byte[] byteArray = null;
		int count = 0;
		char c = 0;
		int i = 0;

		boolean first = true;
		int length = 0;
		int value = 0;

		// Count number of hex characters
		for (i = 0; i < hexString.length(); i++) {

			c = hexString.charAt(i);
			if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
					&& c <= 'f') {
				count++;
			}
		}

		byteArray = new byte[(count + 1) / 2];
		for (i = 0; i < hexString.length(); i++) {

			c = hexString.charAt(i);
			if (c >= '0' && c <= '9') {
				value = c - '0';
			} else if (c >= 'A' && c <= 'F') {
				value = c - 'A' + 10;
			} else if (c >= 'a' && c <= 'f') {
				value = c - 'a' + 10;
			} else {
				value = -1;
			}

			if (value >= 0) {

				if (first) {

					byteArray[length] = (byte) (value << 4);

				} else {

					byteArray[length] |= value;
					length++;
				}

				first = !first;
			}
		}

		return byteArray;
	}

	private void showMessageDialog(int titleId, int messageId) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		builder.setMessage(messageId)
				.setTitle(titleId)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

		builder.show();
	}

	private boolean checkResetVolume() {

		boolean ret = true;

		int currentVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);

		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		if (currentVolume < maxVolume) {

			showMessageDialog(R.string.info, R.string.message_reset_info_volume);
			ret = false;
		}

		return ret;
	}

	private void showRequestQueueError() {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				/* Show the request queue error. */
				Toast.makeText(mContext, "The request cannot be queued.",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void showPiccAtr() {

		synchronized (mResponseEvent) {

			/* Wait for the PICC ATR. */
			while (!mPiccAtrReady && !mResultReady) {

				try {
					mResponseEvent.wait(10000);
				} catch (InterruptedException e) {
				}

				break;
			}

			if (mPiccAtrReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the PICC ATR. */
						txtatr.setText(toHexString(mPiccAtr));

						// String V = toHexString(mPiccAtr);
						// byte[] b = new BigInteger(V, 20).toByteArray();
						//
						// Toast.makeText(getApplicationContext(),
						// Arrays.toString(b).toString(),
						// Toast.LENGTH_LONG).show();
						// int amt = 0;
						// amt = b[3];
						// amt = amt + (b[2] * 256);
						// amt = amt + (b[1] * 256 * 256);
						// amt = amt + (b[0] * 256 * 256 * 256);
						// int Amount = amt;
						// Toast.makeText(getApplicationContext(),
						// String.valueOf(Amount), Toast.LENGTH_LONG)
						// .show();

					}
				});

			} else if (mResultReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the result. */
						Toast.makeText(mContext,
								toErrorCodeString(mResult.getErrorCode()),
								Toast.LENGTH_LONG).show();
					}
				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the timeout. */
						Toast.makeText(mContext, "The operation timed out.",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			mPiccAtrReady = false;
			mResultReady = false;
		}
	}

	private void showPiccResponseApdu() {

		synchronized (mResponseEvent) {

			/* Wait for the PICC response APDU. */
			while (!mPiccResponseApduReady && !mResultReady) {

				try {
					mResponseEvent.wait(10000);
				} catch (InterruptedException e) {
				}
				break;
			}

			if (mPiccResponseApduReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the PICC response APDU. */
						txtresponsae.setText(toHexString(mPiccResponseApdu));
						// String V=toHexString(mPiccResponseApdu);
						// String s = "F62CFDDC";
						// byte[] b = new BigInteger(s, 16).toByteArray();
						// Toast.makeText(getApplicationContext(),
						// Arrays.toString(b), Toast.LENGTH_LONG).show();
					}
				});

			} else if (mResultReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						/* Show the result. */
						Toast.makeText(mContext,
								toErrorCodeString(mResult.getErrorCode()),
								Toast.LENGTH_LONG).show();
					}
				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						/* Show the timeout. */
						Toast.makeText(mContext, "The operation timed out.",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			mPiccResponseApduReady = false;
			mResultReady = false;
		}
	}

	private void showPiccResponseApdu1() {

		synchronized (mResponseEvent) {

			/* Wait for the PICC response APDU. */
			while (!mPiccResponseValueReady && !mResultReady) {

				try {
					mResponseEvent.wait(10000);
				} catch (InterruptedException e) {
				}
				break;
			}

			if (mPiccResponseValueReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the PICC response APDU. */
						txtread.setText(toHexString(mPiccResponseValue));
						String s = toHexString(mPiccResponseValue);
						byte[] b = toByteArray(s);
						long Amount = 0;
						Amount = b[3];
						Amount = Amount + (b[2] * 256);
						Amount = Amount + (b[1] * 256 * 256);
						Amount = Amount + (b[0] * 256 * 256 * 256);
						Toast.makeText(getApplicationContext(),
								String.valueOf(Amount), Toast.LENGTH_LONG)
								.show();

					}
				});

			} else if (mResultReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						/* Show the result. */
						Toast.makeText(mContext,
								toErrorCodeString(mResult.getErrorCode()),
								Toast.LENGTH_LONG).show();
					}
				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						/* Show the timeout. */
						Toast.makeText(mContext, "The operation timed out.",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			mPiccResponseValueReady = false;
			mResultReady = false;
		}
	}

	private void showPiccResponseApdu2() {

		synchronized (mResponseEvent) {

			/* Wait for the PICC response APDU. */
			while (!mPiccResponseAuthenticateReady && !mResultReady) {

				try {
					mResponseEvent.wait(10000);
				} catch (InterruptedException e) {
				}
				break;
			}

			if (mPiccResponseAuthenticateReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the PICC response APDU. */
						txtread.setText(toHexString(mPiccResponseAuthenticate));
						// String V=toHexString(mPiccResponseValue);
					}
				});

			} else if (mResultReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						/* Show the result. */
						Toast.makeText(mContext,
								toErrorCodeString(mResult.getErrorCode()),
								Toast.LENGTH_LONG).show();
					}
				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						/* Show the timeout. */
						Toast.makeText(mContext, "The operation timed out.",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			mPiccResponseValueReady = false;
			mResultReady = false;
		}
	}

	private boolean showResult() {

		boolean ret = false;

		synchronized (mResponseEvent) {

			/* Wait for the result. */
			while (!mResultReady) {

				try {
					mResponseEvent.wait(10000);
				} catch (InterruptedException e) {
				}

				break;
			}

			ret = mResultReady;

			if (mResultReady) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the result. */
						Toast.makeText(mContext,
								toErrorCodeString(mResult.getErrorCode()),
								Toast.LENGTH_LONG).show();
					}
				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						/* Show the timeout. */
						Toast.makeText(mContext, "The operation timed out.",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			mResultReady = false;
		}

		return ret;
	}

	// private byte[] aesDecrypt(byte key[], byte[] input)
	// throws GeneralSecurityException {
	//
	// SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	// Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	// IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);
	//
	// cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	//
	// return cipher.doFinal(input);
	// }

	// private byte[] tripleDesDecrypt(byte[] key, byte[] input)
	// throws GeneralSecurityException {
	//
	// SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DESede");
	// Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
	// IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[8]);
	//
	// cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	// return cipher.doFinal(input);
	// }
}
