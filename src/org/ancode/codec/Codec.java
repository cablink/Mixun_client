package org.ancode.codec;

/***
 * this class is a interface that all codec should implement basic encode and
 * decode capability Codecs which inherit from {@link CodecBase} only need to
 * implement encode, decode and init
 * */
public interface Codec
{
	/**
	 * Decode a linear pcm audio stream
	 * 
	 * @param encoded
	 *            The encoded audio stream
	 * 
	 * @param lin
	 *            The linear pcm audio frame buffer in which to place the
	 *            decoded stream
	 * 
	 * @param size
	 *            The size of the encoded frame
	 * 
	 * @returns The size of the decoded frame
	 */
	int decode(byte encoded[], short lin[], int size);

	/**
	 * Encode a linear pcm audio stream
	 * 
	 * @param lin
	 *            The linear stream to encode
	 * 
	 * @param offset
	 *            The offset into the linear stream to begin
	 * 
	 * @param encoded
	 *            The buffer to place the encoded stream
	 * 
	 * @param size
	 *            the size of the linear pcm stream (in words)
	 * 
	 * @returns the length (in bytes) of the encoded stream
	 */
	int encode(short lin[], int offset, byte alaw[], int frames);

}
