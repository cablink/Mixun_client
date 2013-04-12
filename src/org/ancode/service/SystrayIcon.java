package org.ancode.service;


import org.ancode.mixun.UnlockActi;
import org.ancode.storage.StorageManager;
import org.ancode.util.LOG;
import org.ancode.util.SUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author qiuqi
 * 在Systray上显示Icon依据mPangolinStatus和mGuiStatus的状态进行显示，为了最优显示过程，设定如下状态表：
 * 	mPangolinStatus		mGuiStatus		结果			状态代号
 * 		true				true		false+on	S3
 * 		true				false		true+on		S1
 * 		false				true		true+off	S2
 * 		false				false		true+off	S2
 * 
 * 状态代号的详细说明：
 * 状态代号		是否显示		显示内容
 * 	S1			true		在线
 * 	S2			true		离线
 * 	S3			false		在线
 * 
 * 状态迁移及对应动作如下：
 * 	S1->S2 (取消显示，内容off，显示）
	S1->S3 (取消显示）
	S2->S1 (取消显示，内容on, 显示)
	S2->S3 (取消显示，内容on)
	S3->S1 (显示）
	S3->S2 (内容off, 显示)
 */

public class SystrayIcon {
	private final Context mCt;
	private final int notification_id = 19172439;
	private final NotificationManager nm;
	private final Notification notification;
	private int mLastNotificationStatus = 2; // 系统初始状态为显示离线状态
	private final Intent mNotifyIntent;
	private final String mNotifyContent;
	private final int mNotifyIcon;
	
	private boolean mPangolinStatus = false;
	PendingIntent mPt;

	public SystrayIcon(Context ct) {
		mCt = ct;
		nm = (NotificationManager) mCt.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifyIntent = new Intent(Intent.ACTION_MAIN);
		mNotifyIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// mNotifyIntent.setClass(mCt, MainGuiActivity.class);
		mNotifyIntent.setClass(mCt, UnlockActi.class);
		mNotifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		mNotifyContent = "离线";
		mNotifyIcon = SUtil.getInstance().getOfflineIcon();

		notification = new Notification(mNotifyIcon, mNotifyContent, System.currentTimeMillis());
		// notification.flags = Notification.FLAG_NO_CLEAR;
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		// notification.defaults = Notification.DEFAULT_VIBRATE;
		mPt = PendingIntent.getActivity(mCt, 0, mNotifyIntent, 0);
		notification.setLatestEventInfo(mCt, mNotifyContent, "", mPt);
		nm.notify(notification_id, notification);
	}

	public void setPangolinStatus(boolean status) {
		mPangolinStatus = status;
	}



	private int getNotificationState() {
	  if (mPangolinStatus == true) {
			return 1;
		} else {
			return 2;
		}
	}

	private int getStateChanged() {
		int current = getNotificationState();
		int change = mLastNotificationStatus * 10 + current;
		mLastNotificationStatus = current;
		return change;
	}

	private void setContentOff() {
		notification.icon = SUtil.getInstance().getOfflineIcon();
		notification.tickerText = "离线";
		notification.when=System.currentTimeMillis();
		notification.setLatestEventInfo(mCt, "离线", "", mPt);
	}

	private void setContentOn() {
		notification.icon = SUtil.getInstance().getOnlineIcon();

		String str = "密讯" + StorageManager.GetInstance().getUserProfile().myPeerBean.PPeerid + "号在线";
		// String str = "密讯" + "20885" + "号在线";
		notification.when=System.currentTimeMillis();
		notification.tickerText = str;
		notification.setLatestEventInfo(mCt, str, "", mPt);
	}

	public void setOnline() {
		if (notification.icon == SUtil.getInstance().getOnlineIcon())
			return;
		setContentOn();
		nm.notify(notification_id, notification);
	}

	public void setOffline() {
		if (notification.icon == SUtil.getInstance().getOfflineIcon())
			return;
		setContentOff();
		nm.notify(notification_id, notification);
	}

	public void showNotification() {
		int state = getStateChanged();
		switch (state) {
		case 11:
			if (notification.icon == SUtil.getInstance().getOfflineIcon()) {
				setContentOn();
				nm.notify(notification_id, notification);
			}
			break;
		case 22:
			if (notification.icon == SUtil.getInstance().getOnlineIcon()) {
				setContentOff();
				nm.notify(notification_id, notification);
			}
			break;
		case 12:
			LOG.v("icon", "12");
			nm.cancel(notification_id); // 不需要先取消，再显示
			setContentOff();
			nm.notify(notification_id, notification);
			break;
		case 13:
			LOG.v("icon", "13");
			nm.cancel(notification_id);
			break;
		case 21:
			LOG.v("icon", "21");
			nm.cancel(notification_id);
			setContentOn();
			nm.notify(notification_id, notification);
			break;
		case 23:
			LOG.v("icon", "23");
			nm.cancel(notification_id);
			setContentOff();
			break;
		case 31:
			LOG.v("icon", "31");
			nm.notify(notification_id, notification);
			break;
		case 32:
			LOG.v("icon", "32");
			setContentOff();
			nm.notify(notification_id, notification);
			break;
		default:
			LOG.v("icon", "default:" + state);
			break;
		}
	}

	public void cancelNotification() {
		nm.cancel(notification_id);
	}
}
