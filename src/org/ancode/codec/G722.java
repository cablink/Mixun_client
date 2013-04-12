package org.ancode.codec;

public class G722 implements Codec
{

	/*
	 * Acceptable values for bitrate are 48000, 56000 or 64000
	 */
	private static final int DEFAULT_BITRATE = 64000;

	public G722()
	{
		init();
	}

	boolean load()
	{
		try
		{
			System.loadLibrary("g722_jni");
			return true;
		} catch (Throwable e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public native int open(int brate);

	public native int decode(byte encoded[], short lin[], int size);

	public native int encode(short lin[], int offset, byte encoded[], int size);

	public native void close();

	public void init()
	{
		if (load())
			open(DEFAULT_BITRATE);
	}
}
