package org.ancode.service;

import org.ancode.crypt.CryptorException;
import org.ancode.crypt.RSAEncryptor;
import org.ancode.netty.NettyStatus;
import org.ancode.storage.AnRoomsBean;
import org.ancode.storage.StorageManager;
import org.ancode.storage.SQLiteDB.DataDefine;
import org.ancode.util.Log;
import org.ancode.util.Msg;

public class MessangeManager {

	private String decodeChatUser(String sMsg) throws CryptorException {
		byte[] data;
		String sData;
		data = RSAEncryptor.getInstance().decryptBase64String(sMsg,
				StorageManager.GetInstance().getUserProfile().myPeerBean.PPeerid);
		sData = new String(data);
		String mMsg = Msg.getContentOfMsg(sData);
		return mMsg;
	}

	public AnRoomsBean createChatRoom(String sRoomId, String sOwner, String sRoomName, String sSecret, String showMsg,
			String time, int sRoomtype, String sRoomMaskid, boolean allowchange) {
		time = String.valueOf((Long.valueOf(time) + NettyStatus.TiemOffset));
		LOG.d("debug", "time= " + time);
		// 保存到数据库
		AnRoomsBean roomBean = new AnRoomsBean();
		roomBean.RRoomname = sRoomName;
		roomBean.RRoomid = sRoomId;
		roomBean.RAeskey = sSecret;
		roomBean.RUpdateTime = time;
		roomBean.PRecentMsg = "";
		roomBean.ROwnerid = sOwner;
		roomBean.RRoometype = sRoomtype;
		roomBean.RRoomMaskid = sRoomMaskid;
		if (allowchange) {
			roomBean.RRoomallowchange = DataDefine.TRUE;
		}
		LOG.v("wjy", "sRoomMaskid======" + sRoomMaskid);
		StorageManager.GetInstance().getRoomsList().Save(roomBean);
		return roomBean;
	}

}
