package org.wx.test;

import org.wx.test.EscCommand;
import org.wx.test.EscCommand.CODEPAGE;
import org.wx.test.EscCommand.ENABLE;
import org.wx.test.EscCommand.FONT;
import org.wx.test.EscCommand.HRI_POSITION;
import org.wx.test.EscCommand.JUSTIFICATION;
import org.wx.test.GpCom;
import org.wx.test.GpUtils;
import org.wx.test.LabelCommand;
import org.wx.test.LabelCommand.BARCODETYPE;
import org.wx.test.LabelCommand.BITMAP_MODE;
import org.wx.test.LabelCommand.DIRECTION;
import org.wx.test.LabelCommand.EEC;
import org.wx.test.LabelCommand.FONTMUL;
import org.wx.test.LabelCommand.FONTTYPE;
import org.wx.test.LabelCommand.MIRROR;
import org.wx.test.LabelCommand.READABEL;
import org.wx.test.LabelCommand.RESPONSE_MODE;
import org.wx.test.LabelCommand.ROTATION;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.wx.test.R;

import static android.R.attr.delay;

/**
 * This is the main Activity that displays the current session.
 */
public class BTPrinterDemo extends Activity {
	// Debugging
	private static final String TAG = "BTPrinter";
	private static final boolean D = true;

	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int MESSAGE_JUDGE_DEVICE = 6;
	public static final int MESSAGE_REQUEST_STATE = 7;

    public static final byte NORMAL=0x00;
    public static final byte OPEN=0x01;
    public static final byte NOPAPAER=0x04;
    public static final byte PRINTING=0x20;
	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	//User
	private  boolean IsLabel;
    byte[] PRINTER = { 'M', 'O', 'D', 'E', 'L', ':', 'G', 'P', '-', '2', '1', '2', '0',0x0d,0x0a};
	// Layout Views
	private TextView mTitle;
	private EditText mOutEditText;
	private Button mSendButton;
	private Button mPrintButton;
	private Button mClearButton;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the services
	private BluetoothService mService = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		// Set up the window layout
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);

		// Set up the custom title
		mTitle = (TextView) findViewById(R.id.title_left_text);
		mTitle.setText(R.string.app_title);
		mTitle = (TextView) findViewById(R.id.title_right_text);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.i(TAG, "onCreate:蓝牙适配器 "+mBluetoothAdapter);

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
		}
	}


	public void connect(View view){
		Intent serverIntent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Log.i(TAG, "onStart: ===========NotisEnabled");
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the session
		} else {
			Log.i(TAG, "onStart: ===========isEnabled");
			if (mService == null)
				init();
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mService.getState() == BluetoothService.STATE_NONE) {
				// Start the Bluetooth services
				mService.start();
			}
		}
	}

	private void init() {
		Log.d(TAG, "setupChat()");

		// Initialize the compose field with a listener for the return key
		mOutEditText = (EditText) findViewById(R.id.edit_text_out);

		// Initialize the send button with a listener that for click events
		mSendButton = (Button) findViewById(R.id.button_send);
		mSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


                //query status

                mService.write(new byte[]{0x1b,0x21,0x3f});
                /*
            此为查询打印机状态指令
                返回值
                正常	0x00
                开盖	0x01
                缺纸	0x04
                正在打印0x20
                在mHandler中处理
                 */
			}
		});
		// Initialize the print button with a listener that for click events
		mPrintButton = (Button) findViewById(R.id.button_print);
		mPrintButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage("~!T");
                /*
                此为查询型号指令如果返回
                MODEL:GP-2120
                则为标签打印机，超时不返回则不是标签打印机
                在mHandler中处理
                 */
			}
		});

		// Initialize the print button with a listener that for click events
		mClearButton = (Button) findViewById(R.id.button_clear);
		mClearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LabelCommand tsc = new LabelCommand();
				tsc.addSize(60, 60); // 设置标签尺寸，按照实际尺寸设置
				tsc.addGap(0); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
				tsc.addDirection(DIRECTION.BACKWARD, MIRROR.NORMAL);// 设置打印方向
				tsc.addReference(0, 0);// 设置原点坐标
				tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
				tsc.addCls();// 清除打印缓冲区
				// 绘制简体中文
				 tsc.addText(20, 20, FONTTYPE.SIMPLIFIED_CHINESE, ROTATION.ROTATION_0, FONTMUL.MUL_1, FONTMUL.MUL_1,
				        "Welcome to use SMARNET printer!");
				// 绘制图片

				 tsc.addQRCode(250, 80, EEC.LEVEL_L, 5, ROTATION.ROTATION_0, " www.smarnet.cc");
				// 绘制一维条码
				tsc.add1DBarcode(20, 250, BARCODETYPE.CODE128, 100, READABEL.EANBEL, ROTATION.ROTATION_0, "SMARNET");
				tsc.addPrint(1, 1); // 打印标签
				tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
				Vector<Byte> datas = tsc.getCommand(); // 发送数据
				byte[] bytes = GpUtils.ByteTo_byte(datas);
				mService.write(bytes);
			}
		});

		// Initialize the BluetoothService to perform bluetooth connections
		mService = new BluetoothService(this, mHandler);
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth services
		if (mService != null)
			mService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	// private void ensureDiscoverable() {
	// if(D) Log.d(TAG, "ensure discoverable");
	// if (mBluetoothAdapter.getScanMode() !=
	// BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
	// Intent discoverableIntent = new
	// Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	// discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
	// 300);
	// startActivity(discoverableIntent);
	// }
	// }

//	/**
//	 * Set font gray scale
//	 */
//	private void fontGrayscaleSet(int ucFontGrayscale) {
//		// Check that we're actually connected before trying anything
//		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
//			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
//					.show();
//			return;
//		}
//		if (ucFontGrayscale < 1)
//			ucFontGrayscale = 4;
//		if (ucFontGrayscale > 8)
//			ucFontGrayscale = 8;
//		byte[] send = new byte[3];// ESC m n
//		send[0] = 0x1B;
//		send[1] = 0x6D;
//		send[2] = (byte) ucFontGrayscale;
//		mService.write(send);
//	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 * 
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothService to write
			byte [] send ;
			try {
				send = message.getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				send = message.getBytes();
			}
				
				mService.write(send);
				//
				// // Reset out string buffer to zero and clear the edit text field
				// mOutStringBuffer.setLength(0);
				// mOutEditText.setText(mOutStringBuffer);
		}
	}

	// // The action listener for the EditText widget, to listen for the return
	// key
	// private TextView.OnEditorActionListener mWriteListener =
	// new TextView.OnEditorActionListener() {
	// public boolean onEditorAction(TextView view, int actionId, KeyEvent
	// event) {
	// // If the action is a key-up event on the return key, send the message
	// if (actionId == EditorInfo.IME_NULL && event.getAction() ==
	// KeyEvent.ACTION_UP) {
	// String message = view.getText().toString();
	// sendMessage(message);
	// }
	// if(D) Log.i(TAG, "END onEditorAction");
	// return true;
	// }
	// };

	// The Handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					mTitle.setText(R.string.title_connected_to);
					mTitle.append(mConnectedDeviceName);
					break;
				case BluetoothService.STATE_CONNECTING:
					mTitle.setText(R.string.title_connecting);
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
					mTitle.setText(R.string.title_not_connected);
					break;
				}
				break;
			case MESSAGE_WRITE:
				// byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				// String writeMessage = new String(writeBuf);
				break;
			case MESSAGE_READ:

                // byte[] readBuf=subBytes((byte[]) msg.obj,0,msg.arg1);
				//construct a string from the valid bytes in the buffer
                //String readMessage = new String(readBuf, 0, msg.arg1);
                //Log.e(TAG,"Get"+readMessage);
				break;
		     case MESSAGE_REQUEST_STATE:

                 byte[] TempBuf=subBytes((byte[]) msg.obj,0,msg.arg1);
                 Log.e(TAG,"Get"+TempBuf);
                 byte status=TempBuf[0];
                 switch (status) {
                     case NORMAL:
                         Toast.makeText(getApplicationContext(),
                                 "Printer is OK",
                                 Toast.LENGTH_SHORT).show();
                        break;
                     case OPEN:
                         Toast.makeText(getApplicationContext(),
                                 "Printer is opened",
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case NOPAPAER:
                         Toast.makeText(getApplicationContext(),
                                 "No paper",
                                 Toast.LENGTH_SHORT).show();
                         break;
                     case PRINTING:
                         Toast.makeText(getApplicationContext(),
                                 "Printer is working",
                                 Toast.LENGTH_SHORT).show();
                         break;
                 }
				 break;
	     	case MESSAGE_JUDGE_DEVICE:
                IsLabel=false;
				byte[] DeviceBuf = subBytes((byte[]) msg.obj,0,msg.arg1);
				IsLabel = judgePrinter(PRINTER,DeviceBuf);
                Log.e(TAG,"Get"+DeviceBuf.toString());
                if (IsLabel)
                {Toast.makeText(getApplicationContext(),
                            "Printer is label printer ",
                            Toast.LENGTH_SHORT).show();
                }
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				if (BluetoothAdapter.checkBluetoothAddress(address)) {
					BluetoothDevice device = mBluetoothAdapter
							.getRemoteDevice(address);
					// Attempt to connect to the device
					mService.connect(device);
				}
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a session
				init();
			} else {
				// User did not enable Bluetooth or an error occured
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scan:
			// Launch the DeviceListActivity to see devices and do scan
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return true;
		case R.id.disconnect:
			// disconnect
			mService.stop();
			return true;
		}
		return false;
	}

    private boolean judgePrinter(byte[] table, byte[] readPrinter) {
        boolean result = false;
        int readLength = readPrinter.length;
        if (readLength < 5) {
            return result;
        }

        int tableLength = table.length;
        if (tableLength >= readLength) {
            for (int i = 0; i < readLength; i++) {
                if (table[i] != readPrinter[i]) {
                    return result;
                }
            }
        } else {
            return false;
        }

        result = true;

        return result;
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }
}