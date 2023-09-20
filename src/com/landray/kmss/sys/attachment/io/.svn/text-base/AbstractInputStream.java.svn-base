package com.landray.kmss.sys.attachment.io;

import java.io.IOException;
import java.io.InputStream;

import com.landray.kmss.sys.crypt.aes.AESCrypt;
import com.landray.kmss.sys.crypt.aes.AESKeySizeEnum;
import com.landray.kmss.sys.crypt.aes.TransformType;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 代理输入流基类
 * 
 * @author 叶中奇
 * 
 */
public abstract class AbstractInputStream extends InputStream {
	protected static final String attCrypMode;
	static {
		String tempString = ResourceUtil
				.getKmssConfigString("sys.att.encryption.mode");
		attCrypMode = StringUtil.isNotNull(tempString) ? tempString : "0";
	}
	private static final String DEFAULT_AES_PASSWORD = "MpBVxcMebDIICeAIBADANBgkqhkigEAAoGBAJeAgEAAoGBAKaEnEG9w0BAQEFAASH1D75s72SCS6zCCAmIwggJeAgEAAoGBAKaEnENEGlooCdQWYtIJ1VNxtH1D75s72";

	public AbstractInputStream(InputStream originStream) throws Exception {
		this(originStream, Integer.parseInt(attCrypMode));
	}

	public AbstractInputStream(InputStream originStream, int cryptionMode)
			throws Exception {
		this.cryptionMode = cryptionMode;
		this.originStream = originStream;
		init();
		if (additionalBytes == null) {
            count = 0;
        } else {
            count = additionalBytes.length;
        }
	}

	/*
	 * 加密模式：0不加密，1 普通加密（兼容不加密附件，稍微影响性能），2不加密（兼容加密附件，稍微影响性能），3AES加密（兼容不加密附件，稍微影响性能）
	 */
	protected int cryptionMode;

	protected static final byte[] markBytes = { 45, -89, -1, 61, -57, 5, -15,
			1, -126, 8, 90, 125, -79, -14, 20, 92, 29, -11, 8, -42, 2, -64,
			-12, 107, 3, -109, -49, -3, -118, 120, 74, -82 };
	
	//aesMarkBytesg与markBytes长度保持一致,32位
	protected static final byte[] aesMarkBytes = { 88, -89, -1, 61, -57, 5, -15,
			1, -126, 8, 90, 125, -79, -14, 20, 92, 29, -11, 8, -42, 2, -64,
			-12, 107, 3, -109, -49, -3, -118, 120, 74, -82 };
	
	protected AESCrypt aesCrypt;
	
	protected byte[] additionalBytes = null;

	private int count = 0;//附加字节的长度，一般为0或32

	protected InputStream originStream;

	private int pos = 0;

	/**
	 * 初始化additionalBytes参数
	 */
	protected abstract void init() throws Exception;

	/**
	 * 格式化字节码
	 * 
	 * @param b
	 * @param off
	 * @param len
	 */
	protected abstract void format(byte[] b, int off, int len);

	/**
	 * 格式化一个字节码
	 * 
	 * @param i
	 * @return
	 */
	protected abstract int format(int i);

	@Override
	public int read() throws IOException {
		if (pos == count) {
            return format(originStream.read());
        }
		return format(additionalBytes[pos++]);
	}

	/**
	 * 返回剩余的字节码长度
	 */
	@Override
	public int available() throws IOException {
		return originStream.available() + count - pos;
	}

	/**
	 * 关闭流
	 */
	@Override
	public void close() throws IOException {
		if (originStream != null){
			originStream.close();
		}
	}

	/**
	 * 标记位置，以便reset能返回这里
	 */
	@Override
	public void mark(int readlimit) {
		originStream.mark(readlimit);
	}

	/**
	 * 是否支持位置标记
	 */
	@Override
	public boolean markSupported() {
		return originStream.markSupported();
	}

	/**
	 * 读取字节码到b数组，返回读取个数
	 */
	@Override
	public int read(byte[] b) throws IOException {
		if (pos == count) {
			int result = originStream.read(b);
			format(b, 0, result);
			return result;
		}
		return read(b, 0, b.length);
	}

	/**
	 * 读取len个字节码到b数组（从off开始），返回读取个数
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (pos == count) {
			int result = originStream.read(b, off, len);
			format(b, off, result);
			return result;
		}
		int len1 = count - pos;
		int len2 = 0;
		if (len1 >= len) {
			len1 = len;
		} else {
			len2 = len - len1;
		}
		System.arraycopy(additionalBytes, pos, b, off, len1);
		pos += len1;
		if (len2 > 0) {
			if (originStream.available() == 0) {
                len2 = 0;
            } else {
                len2 = originStream.read(b, off + len1, len2);
            }
		}
		int result = len1 + len2;
		format(b, off, result);
		return result;
	}

	/**
	 * 跳转回mark的位置
	 */
	@Override
	public void reset() throws IOException {
		originStream.reset();
	}

	/**
	 * 跳过n个字节
	 */
	@Override
	public long skip(long n) throws IOException {
		if (pos == count) {
            return originStream.skip(n);
        }
		long n1 = count - pos;
		long n2 = 0;
		if (n1 >= n) {
			n1 = n;
		} else {
			n2 = n - n1;
		}
		pos += n1;
		if (n2 > 0) {
			n2 = originStream.skip(n2);
		}
		return n1 + n2;
	}
	
	/**
	 * 初始化AES加密解密器
	 * @throws Exception 
	 */
	protected void initAES() throws Exception {
		if(aesCrypt != null){
			return;
		}
		String aesPassword = getAesPassword();
		aesCrypt = new AESCrypt(aesPassword,AESKeySizeEnum.AES_128,TransformType.AES_CFB_NoPadding);
	}
	
	private String getAesPassword() throws Exception {
		String pass = ResourceUtil.getKmssConfigString("sys.att.encryption.aespass");
		if(StringUtil.isNull(pass)){
			pass = DEFAULT_AES_PASSWORD;
		}
		return pass;
	}

	protected boolean isAESEncryption(byte[] b) {
		for (int i = 0,len = b.length; i < len; i++) {
			byte deByte = aesCrypt.decrypt(b,i,1)[0];
			if (deByte != aesMarkBytes[i]) {
				return false;
			}
		}
		return true;
	}

	private static AbstractInputStream createInputStream(
			InputStream originStream, int cryptionMode, String cryptionMethod)
			throws Exception {
		AbstractInputStream result = null;
		if ("encrypt".equals(cryptionMethod)) {
			result = new EncryptionInputStream(originStream, cryptionMode);
		}
		if ("decrypt".equals(cryptionMethod)) {
			result = new DecryptionInputStream(originStream, cryptionMode);
		}
		return result;
	}

	public static AbstractInputStream createDecryptStream(
			InputStream originStream, int cryptionMode) throws Exception {
		return createInputStream(originStream, cryptionMode, "decrypt");
	}

	public static AbstractInputStream createEncryptStream(
			InputStream originStream, int cryptionMode) throws Exception {
		return createInputStream(originStream, cryptionMode, "encrypt");
	}
}
