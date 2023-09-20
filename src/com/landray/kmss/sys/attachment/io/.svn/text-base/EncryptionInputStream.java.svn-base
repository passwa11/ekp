package com.landray.kmss.sys.attachment.io;

import java.io.InputStream;

/**
 * 加密流
 * 
 * @author 叶中奇
 * 
 */
public class EncryptionInputStream extends AbstractInputStream {
	public EncryptionInputStream(InputStream originStream) throws Exception {
		super(originStream);
	}

	public EncryptionInputStream(InputStream originStream, int encryptionMode)
			throws Exception {
		super(originStream, encryptionMode);
	}

	private boolean noEncryption;

	@Override
	protected void init() throws Exception {
		noEncryption = (cryptionMode != 1 && cryptionMode != 3);
		if (noEncryption) {
			return;
		}
		if(cryptionMode == 3){
			initAES();
		}
		additionalBytes = cryptionMode == 1 ? markBytes : aesMarkBytes;
	}

	@Override
	protected void format(byte[] b, int off, int len) {
		if (noEncryption) {
			return;
		}
		for (int i = off; i < len; i++) {
			if(cryptionMode == 3){//AES加密
				b[i] = aesCrypt.encrypt(b, i,1)[0];
				continue;
			}
			
			//普通加密
			if (b[i] == 127) {
				b[i] = -128;
			} else {
				b[i]++;
			}
		}
	}

	@Override
	protected int format(int i) {
		if (noEncryption) {
			return i;
		}
		if (i == -1) {
            return -1;
        }
		if(cryptionMode == 3){//AES加密
			byte[] b = aesCrypt.encrypt(new byte[]{(byte)i});
			return b[0];
		}
		if (i == 255)//普通加密
        {
            return 0;
        }
		return i + 1;
	}

}
