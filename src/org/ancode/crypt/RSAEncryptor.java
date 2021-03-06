package org.ancode.crypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.ancode.util.KeyManager;
import org.apache.commons.codec.binary.Base64;

public class RSAEncryptor {
	// Private constructor prevents instantiation from other classes
	private RSAEncryptor() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final RSAEncryptor INSTANCE = new RSAEncryptor();
	}

	public static RSAEncryptor getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static final String ALGORITHM = "RSA";



	private Cipher getEncryptCipher(String keynick) throws CryptorException {

		PublicKey key = KeyManager.getInstance().getEncryptKey(keynick);

		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		} catch (NoSuchPaddingException e) {
			throw new CryptorException(e);
		} catch (InvalidKeyException e) {
			throw new CryptorException(e);
		}

		return cipher;
	}

	private Cipher getDecryptCipher(String keynick) throws CryptorException {

		PrivateKey key = KeyManager.getInstance().getDecryptKey(keynick);


		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		} catch (NoSuchPaddingException e) {
			throw new CryptorException(e);
		} catch (InvalidKeyException e) {
			throw new CryptorException(e);
		}

		return cipher;
	}

	/**
	 * 根据密钥名找到解密密钥后，对BASE64编码格式的数据进行解密
	 * 
	 * @param dataBase64
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptBase64String(String dataBase64, String keynick) throws CryptorException {
		boolean isBase64 = false;
		try {
			isBase64 = Base64.isArrayByteBase64(dataBase64.getBytes());
		} catch (Exception e) {

		}
		if (isBase64) {
			return decrypt(Base64.decodeBase64(dataBase64.getBytes()), keynick);
		} else
			return dataBase64.getBytes();
	}

	/**
	 * 根据密钥名找到解密密钥后，对数据进行解密
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] decrypt(byte[] data, String keynick) throws CryptorException {
		// 对数据解密
		Cipher cipher = getDecryptCipher(keynick);
		int blockSize = cipher.getBlockSize();
		byte[] bBuffer = new byte[blockSize];
		int nLoop = data.length / blockSize;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (int i = 0; i < nLoop; i++) {
				System.arraycopy(data, i * blockSize, bBuffer, 0, blockSize);

				bos.write(cipher.doFinal(bBuffer));

			}
			return bos.toByteArray();
		} catch (IllegalBlockSizeException e) {
			throw new CryptorException(e);
		} catch (BadPaddingException e) {
			throw new CryptorException(e);
		} catch (IOException e) {
			throw new CryptorException(e);
		}

	}

	/**
	 * 根据密钥名找到加密密钥后，对数据进行加密， 并且将加密结果用BASE64进行编码，返回编码后的字符串
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public String encryptBase64Encode(byte[] data, String keynick) throws CryptorException {
		return new String(Base64.encodeBase64(encrypt(data, keynick)));
	}

	/**
	 * 根据密钥名找到加密密钥后，对数据进行加密
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] encrypt(byte[] data, String keynick) throws CryptorException {
		// 对数据加密
		Cipher cipher = getEncryptCipher(keynick);
		int blockSize = cipher.getBlockSize();
		byte[] bBuffer = new byte[blockSize];
		int nLoop = data.length / blockSize;
		int nRemaining = data.length - nLoop * blockSize;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			for (int i = 0; i < nLoop; i++) {
				System.arraycopy(data, i * blockSize, bBuffer, 0, blockSize);

				bos.write(cipher.doFinal(bBuffer));
			}

			if (nRemaining > 0) {
				System.arraycopy(data, nLoop * blockSize, bBuffer, 0, nRemaining);
				bos.write(cipher.doFinal(bBuffer, 0, nRemaining));
			}
		} catch (IllegalBlockSizeException e) {
			throw new CryptorException(e);
		} catch (BadPaddingException e) {
			throw new CryptorException(e);
		} catch (IOException e) {
			throw new CryptorException(e);
		}
		return bos.toByteArray();
	}
}
