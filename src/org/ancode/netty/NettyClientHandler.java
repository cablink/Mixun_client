package org.ancode.netty;

import java.util.HashMap;
import java.util.Map;

import org.ancode.mixun.Login;
import org.ancode.msgHandler.Kicked;
import org.ancode.msgHandler.MaskKicked;
import org.ancode.msgHandler.MaskRoomAddone;
import org.ancode.msgHandler.MaskRoomChangeRole;
import org.ancode.msgHandler.MaskRoomClose;
import org.ancode.msgHandler.MaskRoomCreate;
import org.ancode.msgHandler.MaskRoomKicked;
import org.ancode.msgHandler.MaskRoomLogout;
import org.ancode.msgHandler.MaskRoommsg;
import org.ancode.msgHandler.MessagerHandler;
import org.ancode.msgHandler.RoomAddone;
import org.ancode.msgHandler.RoomClose;
import org.ancode.msgHandler.RoomCreate;
import org.ancode.msgHandler.RoomKicked;
import org.ancode.msgHandler.RoomLogout;
import org.ancode.msgHandler.Roomfs;
import org.ancode.msgHandler.Roommsg;
import org.ancode.msgHandler.Sfs;
import org.ancode.msgHandler.TextHandler;
import org.ancode.service.MiXunService;
import org.ancode.storage.StorageManager;
import org.ancode.storage.SQLiteDB.DataDefine;
import org.ancode.util.AncodeFactory;
import org.ancode.util.LOG;
import org.ancode.util.SXmlParser;
import org.ancode.web.ICallApi;
import org.ancode.web.MixunProtocol;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NettyClientHandler extends SimpleChannelUpstreamHandler {

	private boolean readingChunks;
	private static final String TAG = "netty";
	private long mTimeOfLastReceiveK = -1;
	HashMap<String, String> mXmlMap;
	HashMap<String, MessagerHandler> mMessagerHandler;
	MessagerHandler mMessagerHandlerTemp;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		/*
		 * 当连接中断的时候，考虑采取如下步骤 1、查询当前网络状况，如果网络还是连接状态，则重连 2、
		 */
		NettyStatus.isRettyRunning = false;
		Log.v("netty", "网络故障发生");

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (!readingChunks) {
			HttpResponse response = (HttpResponse) e.getMessage();

			// Log.v("netty", "STATUS: " + response.getStatus());
			// Log.v("netty", "VERSION: " + response.getProtocolVersion());

			// if (!response.getHeaderNames().isEmpty()) {
			// for (String name : response.getHeaderNames()) {
			// for (String value : response.getHeaders(name)) {
			// Log.v("netty", "HEADER: " + name + " = " + value);
			// }
			// }
			// }

			if (response.isChunked()) {
				readingChunks = true;
				Log.v("netty", "CHUNKED CONTENT {");
			} else {
				ChannelBuffer content = response.getContent();
				if (content.readable()) {
					Log.v("netty", "CONTENT {");
					Log.v("netty", content.toString(CharsetUtil.UTF_8));
					Log.v("netty", "} END OF CONTENT");
				}
			}
		} else {
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) {
				readingChunks = false;
				Log.v("netty", "} END OF CHUNKED CONTENT");
			} else {
				Log.v("netty", chunk.getContent().toString(CharsetUtil.UTF_8));
				String content = chunk.getContent().toString(CharsetUtil.UTF_8);

				LOG.v(TAG, Thread.currentThread().getId() + ":todo");

				if (content.equals("k\n")) {
					NettyStatus.isConnectAlive = true;
					mTimeOfLastReceiveK = -1;
					return;
				}
				Log.v("web", "content=" + content);
				mXmlMap = SXmlParser.xmlResponse(content);
				if ((mXmlMap) != null && (mXmlMap.get("r") != null)) {
					mTimeOfLastReceiveK = -1;
					if (mXmlMap.get("r").equals("failed") && mXmlMap.get("w").equals("invalid")) {
						LOG.v(TAG, "StreamCometClient.ResponseCallback.failed");
						NettyStatus.isConnectAlive = false;
						MiXunService.isLogin = false;
						CloseNetworkManager();
						Intent intent = new Intent();
						Bundle values = new Bundle();
						values.putString("MiXunId", StorageManager.GetInstance().getUserProfile().MyPeerid);
						intent.putExtras(values);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setAction("org.ancode.MiXun.Login");
						intent.setClass(AncodeFactory.getInstance().getApplicationContext(), Login.class);
						AncodeFactory.getInstance().getApplicationContext().startActivity(intent);
					} else {
						LOG.v(TAG, "StreamCometClient.ResponseCallback.gotmessage");
						NettyStatus.isConnectAlive = true;
						dispatchMessage(mXmlMap);
					}
				} else {
					MixunProtocol.getInstance().delMessage("-1", new ICallApi() {
						@Override
						public void onSuccess(HashMap<String, String> result) {
						}

						@Override
						public void onFailure() {
						}
					});
				}

			}
		}
	}

	private void CloseNetworkManager() {
		// if (mNetManager != null) {
		// mNetManager.closeConnection();
		// mNetManager = null;
		// }
	}

	public void dispatchMessage(final HashMap<String, String> xmlMap) {
		// //测试,以后用到，莫删
		// try{
		// final Context context =
		// mContext.createPackageContext("org.igeek.plugintest.plugin1",
		// Context.CONTEXT_INCLUDE_CODE|Context.CONTEXT_IGNORE_SECURITY);
		// Class
		// TargetClass=context.getClassLoader().loadClass("org.igeek.plugintest.plugin1"+".PluginApplication");
		// Object o=TargetClass.newInstance();
		// boolean result=(Boolean)
		// TargetClass.getMethod("dispalymessage",String.class).invoke(o,"我运行了");
		//
		// }catch(NameNotFoundException e){
		// e.printStackTrace();
		// }catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// }catch(IllegalArgumentException e){
		// e.printStackTrace();
		// }catch (InstantiationException e) {
		// e.printStackTrace();
		// }catch(IllegalAccessException e){
		// e.printStackTrace();
		// }catch(InvocationTargetException e){
		// e.printStackTrace();
		// }catch(NoSuchMethodException e){
		// e.printStackTrace();
		// }catch (Exception e) {
		// e.printStackTrace();
		// }
		String version = xmlMap.get("version");
		if (version != null && version.equals("SIP/2.0")) {
			Bundle bundle = new Bundle();
			for (Map.Entry<String, String> m : xmlMap.entrySet()) {
				bundle.putString(m.getKey(), m.getValue());
			}
			Intent intent = new Intent();
			intent.putExtras(bundle);
		} else {
			if (xmlMap.get("msgid") != null & !xmlMap.get("msgid").equals("")) {
				Log.v("netty", "msgid:" + xmlMap.get("msgid"));
				if (xmlMap.get("msgid").equals(AncodeFactory.getInstance().LastMsgId)) {
					switch (AncodeFactory.getInstance().LastMsgIdStatus) {
					case DataDefine.MSGID_STATUS_NONE:
						// 丢弃，不可能有这种情况
						Log.v("netty", "DataDefine.MSGID_STATUS_NONE");
						break;
					case DataDefine.MSGID_STATUS_GOT:
						// 继续处理
						Log.v("netty", "DataDefine.MSGID_STATUS_GOT");
						ProcessingMsg(xmlMap);
						break;
					case DataDefine.MSGID_STATUS_SAVED:
						// 已经保存，但是还没有向服务器发送确认，发送一个确认包
						Log.v("netty", "DataDefine.MSGID_STATUS_SAVED");
						MixunProtocol.getInstance().delMessage(xmlMap.get("msgid"), new ICallApi() {

							@Override
							public void onSuccess(HashMap<String, String> result) {
								// TODO Auto-generated method stub
								AncodeFactory.getInstance().LastMsgIdStatus = DataDefine.MSGID_STATUS_ACKED;
							}

							@Override
							public void onFailure() {
								// TODO Auto-generated method stub
							}
						});
						break;
					case DataDefine.MSGID_STATUS_ACKED:
						Log.v("netty", "DataDefine.MSGID_STATUS_ACKED");
						// ack已经发过了，难道服务器没收到，重连，或者再发一个呢？
						break;
					}
				} else {
					// 如果msgid不同，不管上个msgid的状态如何，进行一个全新的处理
					Log.v("netty", "DataDefine.else");
					AncodeFactory.getInstance().LastMsgId = xmlMap.get("msgid");
					AncodeFactory.getInstance().LastMsgIdStatus = DataDefine.MSGID_STATUS_GOT;
					ProcessingMsg(xmlMap);
				}
			}
		}
	}

	// 正常处理消息
	private void ProcessingMsg(final HashMap<String, String> xmlMap) {
		mMessagerHandler = initMessagerHandlerMap(xmlMap);
		mMessagerHandlerTemp = mMessagerHandler.get(xmlMap.get("r"));
		AncodeFactory.getInstance().LastMsgIdStatus = DataDefine.MSGID_STATUS_SAVED;
		Log.v("mixun", "delmessage " + xmlMap.get("msgid"));
		MixunProtocol.getInstance().delMessage(xmlMap.get("msgid"), new ICallApi() {

			@Override
			public void onSuccess(HashMap<String, String> result) {
				// TODO Auto-generated method stub
				xmlMap.clear();
				mMessagerHandler.clear();
				AncodeFactory.getInstance().LastMsgIdStatus = DataDefine.MSGID_STATUS_ACKED;
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
			}
		});
		if (mMessagerHandlerTemp != null) {
			mMessagerHandlerTemp.process();
		}
	}

	private HashMap<String, MessagerHandler> initMessagerHandlerMap(final HashMap<String, String> xmlMap) {
		HashMap<String, MessagerHandler> messagerHandlerMap = new HashMap<String, MessagerHandler>();
		messagerHandlerMap.put("sendmsg", new TextHandler(xmlMap));
		messagerHandlerMap.put("sfs", new Sfs(xmlMap));
		messagerHandlerMap.put("room_create", new RoomCreate(xmlMap));
		messagerHandlerMap.put("maskroom_create", new MaskRoomCreate(xmlMap));
		messagerHandlerMap.put("roommsg", new Roommsg(xmlMap));
		messagerHandlerMap.put("maskroom_msg", new MaskRoommsg(xmlMap));
		messagerHandlerMap.put("room_addone", new RoomAddone(xmlMap));
		messagerHandlerMap.put("maskroom_addone", new MaskRoomAddone(xmlMap));
		messagerHandlerMap.put("maskroom_role", new MaskRoomChangeRole(xmlMap));
		messagerHandlerMap.put("roomfs", new Roomfs(xmlMap));
		messagerHandlerMap.put("room_close", new RoomClose(xmlMap));
		messagerHandlerMap.put("maskroom_close", new MaskRoomClose(xmlMap));
		messagerHandlerMap.put("room_kicked", new RoomKicked(xmlMap));
		messagerHandlerMap.put("maskroom_kick", new MaskRoomKicked(xmlMap));
		messagerHandlerMap.put("kicked", new Kicked(xmlMap));
		messagerHandlerMap.put("maskroom_kicked", new MaskKicked(xmlMap));
		messagerHandlerMap.put("logout_one", new RoomLogout(xmlMap));
		messagerHandlerMap.put("maskroom_logout_one", new MaskRoomLogout(xmlMap));
		return messagerHandlerMap;
	}
}