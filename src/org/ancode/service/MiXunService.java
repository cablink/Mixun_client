package org.ancode.service;

import java.net.URISyntaxException;

import net.sqlcipher.database.SQLiteDatabase;

import org.ancode.exception.MyCrashHandler;
import org.ancode.filetrans.FileReceiveTask;
import org.ancode.filetrans.FileTaskManager;

import org.ancode.msgHandler.Roomfs;
import org.ancode.msgHandler.Sfs;
import org.ancode.netty.NettyClient;
import org.ancode.receive.UpdateAlarmService;
import org.ancode.storage.AnCloudFileBean;
import org.ancode.storage.AnMessageBean;
import org.ancode.storage.MsgExtraBean;
import org.ancode.storage.StorageManager;
import org.ancode.texttrans.TextTaskManager;
import org.ancode.util.AncodeFactory;
import org.ancode.util.AncodeFactory.ConnectionEventListener;
import org.ancode.util.LOG;
import org.ancode.util.ProductVersion;
import org.ancode.util.SConfig;
import org.ancode.util.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class MiXunService extends Service implements ConnectionEventListener, OnSharedPreferenceChangeListener {
	private final String TAG = "MiXunService";
	private final IBinder mBinder = new LocalBinder();
	public static SystrayIcon mSystrayIcon;
	private FileTaskManager mFileTaskManager;
	private TextTaskManager mTextTaskManager = new TextTaskManager();
	public static boolean isLogin = true;

	onMiXunServiceChanged service_state_listener;

	public static final String CMD_NETWORK_CHANGED = "network_changed";
	// 设置成单例模式

	private static MiXunService INSTANCE = null;

	public static MiXunService GetInstance() {
		if (INSTANCE == null)
			INSTANCE = new MiXunService();
		return INSTANCE;
	}
	
	static {
	    if(android.os.Build.VERSION.SDK_INT < 9) {
	        java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
	        java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
	    }
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			String ifRecvFile = intent.getStringExtra("ifRecvFile");
			String fileId = intent.getStringExtra("fileId");
			String roomid = intent.getStringExtra("roomid");
			if (ifRecvFile != null) {
				if (ifRecvFile.equals("true")) {
					if (TextUtils.isEmpty(roomid)) {
						Sfs.ifRecvFile(true, fileId);
					} else {
						Roomfs.ifRecvFile(true, fileId, roomid);
					}
				} else {
					if (TextUtils.isEmpty(roomid)) {
						Sfs.ifRecvFile(false, fileId);
					} else {
						Roomfs.ifRecvFile(true, fileId, roomid);
					}

				}
			}
		}
		return START_STICKY;
	}

	String uid = "";

	@Override
	public void onCreate() {
		super.onCreate();
		LOG.d("69", "PangolinService 0nCreate");
		MyCrashHandler handler = MyCrashHandler.getInstance();
		handler.init(getApplicationContext());
		Thread.setDefaultUncaughtExceptionHandler(handler);
		AncodeFactory.getInstance().setApplicationContext(getApplicationContext());
		if (!ifSdPresent()) {
			Toast.makeText(AncodeFactory.getInstance().getApplicationContext(), "sdcard不可用", Toast.LENGTH_SHORT).show();
			onDestroy();
		}
		SQLiteDatabase.loadLibs(this);

		uid = StorageManager.GetInstance().getPreferenceSavable("LastUid").PValue;
		if (uid == null) {
			return;
		} else {
			boolean result = false;
			if (StorageManager.GetInstance().isProfileExist(this, uid) && (!StorageManager.GetInstance().initiated)) {
				result = StorageManager.GetInstance().LoadProfile(getApplicationContext(), uid);
				if (!result) {
					LOG.e("mixun", "用户配置文件载入失败" + uid);
				}
			} else {// 不需要重新载入了
			}
		}
		mFileTaskManager = new FileTaskManager();
		mTextTaskManager = new TextTaskManager();
		service_state_listener = new onMiXunServiceChanged();
		// Check the network state
		Util.getNetwork(this);
		// mStreamCometClient = new StreamCometClient(getApplicationContext());
		// mStreamCometClient.execute();
		AncodeFactory.getInstance().addPangolinServiceEventListener(service_state_listener);
		AncodeFactory.getInstance().addEventListener(this);
		mSystrayIcon = new SystrayIcon(getApplicationContext());
		FileTaskManager mFileTaskManager = new FileTaskManager();
		// Check software update task
		doUpdateTask();
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

		try {
			Log.v("mixun", "test netty");
			NettyClient nt = new NettyClient("https://www.han2011.com/" + "/getmessage/"
					+ StorageManager.GetInstance().getUserProfile().getSession() + "/call.xml");
			nt.start();

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean ifSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
		StorageManager.GetInstance().UNLOADALL();// 释放数据库连接
		LOG.v("MiXumService", "onDestroy");
		isLogin = false;
		if (mSystrayIcon != null) {
			mSystrayIcon.cancelNotification();
		}

		Process.killProcess(Process.myPid());
		System.exit(0);

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub\
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public MiXunService getService() {
			return MiXunService.this;
		}
	}

	public void InterfaceManager() {

	}

	public void NetMonitor() {

	}

	public void GetFileServer(String fileID, int type) {

	}

	public void MessageReceiver() {

	}

	public void SendServerTask(long taskid) {
		// TODO Auto-generated method stub

	}

	public String SendFile(MsgExtraBean sendBean) {
		return this.SendFile(sendBean, false, null, null);
	}

	public String SendFile(MsgExtraBean sendBean, boolean isRoomSendFile, String aeskey, String roomid) {
		return mFileTaskManager.startFileSendTask(sendBean, isRoomSendFile, aeskey, roomid);
	}

	public void CloudFileUpload(AnCloudFileBean cloudbean) {
		mFileTaskManager.startCloudFileUploadTask(cloudbean);
	}

	public void CloudFileDownload(AnCloudFileBean cloudfile) {
		FileReceiveTask task = new FileReceiveTask(false);
		task.setMbean(cloudfile);
		task.setFileId(cloudfile.fileid);
		task.setCloudFileDownload(true);
		task.setFilename(cloudfile.filename);
		mFileTaskManager.startFileReceiveTask(task, false);
	}

	public void SendQuickTextMessage(AnMessageBean messageBean, int msgType) {
		if (msgType == 2) {
			mTextTaskManager.sendImageMessageOut(messageBean);
		} else
			mTextTaskManager.quickMessageSending(messageBean, msgType);

	}

	public void stopSendTextMessage() {
		mTextTaskManager.StopWork();
	}

	public void StartSendTextMessage() {
		LOG.d("69", "startSendTextMessage");
		mTextTaskManager.StartWork();
	}

	/**
	 * 重启密讯的服务
	 * 
	 * @return
	 */
	public boolean reStartMiXunService() {
		// 清除各个状态
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
		StorageManager.GetInstance().UNLOADALL();// 释放数据库连接
		LOG.v("MiXumService", "onDestroy");
		// isLogin = false;
		if (mSystrayIcon != null) {
			mSystrayIcon.cancelNotification();
		}
		AncodeFactory.getInstance().removePangolinServiceEventListener(service_state_listener);
		AncodeFactory.getInstance().removeEventListener(this);
		service_state_listener = null;
		mFileTaskManager = null;
		mTextTaskManager = null;
		System.gc();
		try {
			Thread.sleep(2000);// 这里睡两秒钟，是为了等待系统把notification清除掉//否则运行太快，下面的监听器还来不及更改状态
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 重新进入状态
		SQLiteDatabase.loadLibs(this);

		uid = StorageManager.GetInstance().getPreferenceSavable("LastUid").PValue;
		if (uid == null) {
			return false;
		} else {
			boolean result = false;
			if (StorageManager.GetInstance().isProfileExist(this, uid) && (!StorageManager.GetInstance().initiated)) {
				result = StorageManager.GetInstance().LoadProfile(getApplicationContext(), uid);
				if (!result) {
					LOG.e("mixun", "用户配置文件载入失败" + uid);
				}
			} else {// 不需要重新载入了
			}
		}
		mFileTaskManager = new FileTaskManager();
		mTextTaskManager = new TextTaskManager();
		service_state_listener = new onMiXunServiceChanged();
		// Check the network state
		Util.getNetwork(this);
		AncodeFactory.getInstance().addPangolinServiceEventListener(service_state_listener);
		AncodeFactory.getInstance().addEventListener(this);
		mSystrayIcon = new SystrayIcon(getApplicationContext());
		// Check software update task
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		return true;

	}

	private class onMiXunServiceChanged implements AncodeFactory.MiXunServiceEventListener {

		@Override
		public void onServiceOnline() {
			// LOG.System.out("service online");
			mSystrayIcon.setPangolinStatus(true);
			mSystrayIcon.showNotification();
			mSystrayIcon.setOnline();
		}

		@Override
		public void onServiceOffline() {
			LOG.System.out("service offline");
			mSystrayIcon.setPangolinStatus(false);
			mSystrayIcon.showNotification();
			mSystrayIcon.setOffline();
		}
	}

	private void doUpdateTask() {
		// 服务第一次启动时，先注册定时检查更新机制
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean result = prefs.getBoolean(getString(R.string.key_update_enable), true);
		Intent intent = new Intent(this, UpdateAlarmService.class);
		intent.setAction("repeating");
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

		if (result) {
			// 开始时间
			SConfig.getInstance().setUpdateDownloading(false);
			SConfig.getInstance().setVersion(ProductVersion.getVersionNumber());
			long firstime = SystemClock.elapsedRealtime();
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(sender);
			// 默认为每12小时检查一次更新
			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 12 * 60 * 60 * 1000, sender);
		} else {
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(sender);
		}
	}

	@Override
	public void onConnectionChanged() {
		LOG.v("netty", "service.onConnectionChanged");
		// 网络状态发生改变，但是具体什么状态，还需要探测
		Log.v("netty", "" + AncodeFactory.getInstance().isNetworkAvailiable());
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(getString(R.string.key_update_enable))) {
			doUpdateTask();
		}

	}

}
