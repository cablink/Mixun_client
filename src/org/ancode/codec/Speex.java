package org.ancode.codec;

public class Speex implements Codec {

	/*
	 * quality 1 : 4kbps (very noticeable artifacts, usually intelligible) 2 :
	 * 6kbps (very noticeable artifacts, good intelligibility) 4 : 8kbps
	 * (noticeable artifacts sometimes) 6 : 11kpbs (artifacts usually only
	 * noticeable with headphones) 8 : 15kbps (artifacts not usually noticeable)
	 */
	private static final int DEFAULT_COMPRESSION = 6;
	public static final int ECHOCANCELLER_FILTERSIZE = 1600;

	public Speex() {
		if (load()) {
			open(DEFAULT_COMPRESSION);
		}
	}

	boolean load() {
		try {
			System.loadLibrary("speex_jni");

			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	public native int open(int compression);

	// size为160的整数倍
	@Override
	public native int decode(byte encoded[], short lin[], int size);

	@Override
	public native int encode(short lin[], int offset, byte encoded[], int size);

	public native void close();

	/**
	 * init the echocanceller
	 * 
	 * @param filterlenth
	 *            IMPORTANT: means the lenth of the echocanceller's filter can't
	 *            be too long.yet can't bee to short!
	 * 
	 */
	public native void echoinit(int filterlenth);

	/**
	 * 回音消除的api
	 * 
	 * @param inputframe
	 *            is the audio as captured by the microphone
	 * @param echoframe
	 *            is the signal that was played in the speaker (and needs to be
	 *            removed)
	 * @param outputframe
	 *            is the signal with echo removed.
	 */
	public native void echocancell(short inputframe[], short echoframe[], short outputframe[]);

	/**
	 * 用来代替echocancell(),目前属于不稳定版，只有speex
	 * 1.2rc1支持。播放context播放出一帧以后告诉speex刚才播放了一帧
	 * 
	 * @param echoframe
	 *            is the signal that was played in the speaker (and needs to be
	 *            removed)
	 */
	public native void echoplayback(short echoframe[]);

	/**
	 * 用来代替echocancell(),目前属于不稳定版，只有speex 1.2rc1支持。捕捉context从声卡上捕捉到一帧以后调用
	 * 
	 * @param inputframe
	 *            is the audio as captured by the microphone
	 * @param outputframe
	 *            is the signal with echo removed.
	 */
	public native void echocapture(short inputframe[], short outputframe[]);

	public native void echoclose();

	/**
	 * 初始化属于speex的jitter buffer，确保调用的时候speex已经open()
	 * 
	 * @param sampling_rate
	 *            采样率
	 */
	public native void speexJitterInit(int sampling_rate);

	public native void speexJitterDestory();

	/**
	 * 把接收到的数据加入到jitterbuffer中，注意不包含rtp头部
	 * 
	 * @param packet
	 *            数据包
	 * @param len
	 *            包的长度
	 * @param timestamp
	 *            rtp中包含的timestamp字段
	 */
	public native void speexJitterPut(byte packet[], int len, int timestamp);

	/**
	 * 从jitterbuffer中取出一帧数据，160个字节，取出的时候已经被speex解码了。
	 * 
	 * @param out
	 * @param offset
	 *            0
	 */
	public native void speexJitterGet(short out[], int offset);

	/**
	 * 获取当前的timestimestamp
	 * 
	 * @return
	 */
	public native int speexJitterGetPointerTimestamp();

}
