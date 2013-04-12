package org.ancode.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ancode.service.MiXunService;
import org.ancode.storage.StorageManager;
import org.ancode.util.AncodeFactory;
import org.ancode.util.LOG;
import org.ancode.util.SConfig;
import org.ancode.util.SUtil;
import org.ancode.util.SXmlParser;
import org.ancode.web.SCore;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;

/**
 * 自定义的 异常处理类 , 实现了 UncaughtExceptionHandler接口
 * 
 * @author Administrator
 * 
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
	// 这里将needUploadError 设置成false以关闭上传错误
	boolean needUploadError = true;
	// 只有一个 MyCrash-Handler
	private static MyCrashHandler myCrashHandler;
	private Context context;
	private final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	private MyCrashHandler() {
	}

	public static synchronized MyCrashHandler getInstance() {
		if (myCrashHandler != null) {
			return myCrashHandler;
		} else {
			myCrashHandler = new MyCrashHandler();
			return myCrashHandler;
		}
	}

	public void init(Context context) {
		this.context = context;
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		LOG.e("error", "对不起，密讯出错挂掉了= =!");
		// 1.获取当前程序的版本号. 版本的id
		try {
			String versioninfo = SConfig.getInstance().getUpdateVersion();
			String versionNo = SConfig.getInstance().getVersion();
			String mixInfo = versioninfo + ":" + versionNo;
			// 2.获取手机的硬件信息.
			String mobileInfo = getMobileInfo();
			// 3.把错误的堆栈信息 获取出来
			String errorinfo = getErrorInfo(arg1);
			// 4.把所有的信息 还有信息对应的时间 提交到服务器
			String userid = StorageManager.GetInstance().getPreferenceSavable("LastUid").PValue;
			String date = dataFormat.format(new Date());
			LOG.e("error", "==> ERROR-BEGIN <================================================================");
			String errorText = "版本信息:\n" + mixInfo + "\n用户ID:\n" + userid + "\n时间:\n" + date + "\n手机信息:\n" + mobileInfo
					+ "\n错误信息:\n" + errorinfo;
			LOG.e("error", errorText);
			LOG.e("error", "==> ERROR-END   <================================================================");

			// errorText = new
			// String(Base64.encodeBase64(errorText.getBytes()));
			if (needUploadError) {
				new postErrorInfoToServerTask().execute(errorText);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取错误的信息
	 * 
	 * @param arg1
	 * @return
	 */
	private String getErrorInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw);
		pw.close();
		String error = writer.toString();
		return error;
	}

	/**
	 * 获取手机的硬件信息
	 * 
	 * @return
	 */
	private String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		// 通过反射获取系统的硬件信息
		try {
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				// 暴力反射 ,获取私有的信息
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name + "=" + value);
				sb.append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 获取手机的版本信息
	 * 
	 * @return
	 */
	private String getVersionInfo() {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "版本号未知";
		}
	}

	private class postErrorInfoToServerTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;
			ContentValues content = new ContentValues();
			content.put("crash", params[0]);
			try {
				result = SCore.getInstance().postRequest("/crash/report/call.aspx", content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (result != null) {
				result = SXmlParser.xmlResponseTag(result, "r");
				if (result != null && result.equals("ok")) {
					// 提交成功
				}
			}
			SUtil.getInstance().cancelNoficationRoomMessage(AncodeFactory.getInstance().getApplicationContext());
			SUtil.getInstance().notificationCancel(AncodeFactory.getInstance().getApplicationContext());
			// MiXunService.mSystrayIcon.cancelNotification();
			StorageManager.GetInstance().UNLOADALL();// 释放数据库连接
			boolean isServiceStop = AncodeFactory.getInstance().getApplicationContext()
					.stopService(new Intent(AncodeFactory.getInstance().getApplicationContext(), MiXunService.class));
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;

		}

	}

}