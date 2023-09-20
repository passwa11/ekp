package com.landray.kmss.sys.attachment.io;

import java.io.InputStream;

/**
 * 解密流
 * 
 * @author 叶中奇
 * 
 */
public class DecryptionInputStream extends AbstractInputStream {
	public DecryptionInputStream(InputStream originStream) throws Exception {
		super(originStream);
	}

	public DecryptionInputStream(InputStream originStream, int encryptionMode)
			throws Exception {
		super(originStream, encryptionMode);
	}

	private boolean noEncryption;
	
	private boolean isAESEncryption;

	@Override
	protected void init() throws Exception {
		noEncryption = (cryptionMode == 0);
		if (noEncryption) {
			return;
		}
		if(cryptionMode != 0){
			initAES();
		}
		int len = markBytes.length;
		// 若字节数小于加密字节数，则不做处理
		// 去掉该判断，因为oracle下orgIn.available()返回为0，导致解密失败
		// if (orgIn.available() < len) {
		// noEncryption = true;
		// return;
		// }
		// 校验前面len个字节是否合法
		byte[] b = new byte[len];
	    int count = originStream.read(b);
	    if (count > 0) {
	    	isAESEncryption = isAESEncryption(b);
	    	if(isAESEncryption){
	    		return;
	    	}
    		for (int i = 0; i < len; i++) {//普通解密
				if (format(b[i] + 128) != (markBytes[i] + 128)) {
					additionalBytes = b;
					noEncryption = true;
					return;
				}
			}
	    }
	}

	@Override
	protected void format(byte[] b, int off, int len) {
		if (noEncryption) {
            return;
        }
		for (int i = off; i < len; i++) {
			if(isAESEncryption){//AES解密
				b[i] = aesCrypt.decrypt(b, i,1)[0];
				continue;
			}
			
			//普通解密
			if (b[i] == -128) {
                b[i] = 127;
            } else {
                b[i]--;
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
		if(isAESEncryption){//AES解密
			byte[] b = aesCrypt.decrypt(new byte[]{(byte)i});
			return b[0];
		}
		if (i == 0)//普通解密
        {
            return 255;
        }
		return i - 1;
	}

	public boolean isAESEncryption() {
		return isAESEncryption;
	}

}
