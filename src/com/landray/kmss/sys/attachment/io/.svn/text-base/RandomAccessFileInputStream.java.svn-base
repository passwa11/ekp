package com.landray.kmss.sys.attachment.io;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileInputStream extends AbstractInputStream {
	private RandomAccessFile file;
	private long currentPosition;
	private long endPosition;
	private boolean noEncryption;
	private boolean isAESEncryption;
	
	public static byte[] markBytes() {
		return markBytes;
	}

	public RandomAccessFileInputStream(RandomAccessFile raFile,
			long startPosition, long length) throws Exception {
		super(null);
		this.file = raFile;
		this.currentPosition = startPosition;
		_init();
		this.endPosition = (this.currentPosition + length);
	}

	@Override
	public int available() {
		return (int) (this.endPosition - this.currentPosition);
	}

	@Override
	public void close() throws IOException {
		this.file.close();
	}

	@Override
	public int read() throws IOException {
		synchronized (this.file) {
			int retval = -1;
			if (this.currentPosition < this.endPosition) {
				this.file.seek(this.currentPosition);
				this.currentPosition += 1L;
				retval = this.file.read();
			}
			return retval;
		}
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int offset, int length) throws IOException {
		if (length > available()) {
			length = available();
		}
		int amountRead = -1;

		if (available() > 0) {
			synchronized (this.file) {
				this.file.seek(this.currentPosition);
				amountRead = this.file.read(b, offset, length);
				format(b, offset, amountRead);
			}
		}

		if (amountRead > 0) {
			this.currentPosition += amountRead;
		}
		return amountRead;
	}

	@Override
	public long skip(long amountToSkip) {
		long amountSkipped = Math.min(amountToSkip, available());
		this.currentPosition += amountSkipped;
		return amountSkipped;
	}

	private void _init() throws Exception {
		this.noEncryption = (this.cryptionMode == 0);
		if (this.noEncryption) {
			return;
		}
		if(cryptionMode != 0){
			initAES();
		}
		int len = markBytes.length;

		byte[] b = new byte[len];
		this.file.seek(0L);
		this.file.read(b);
		
		isAESEncryption = isAESEncryption(b);
    	if(isAESEncryption){
    		this.currentPosition += len;
    		return;
    	}
		for (int i = 0; i < len; i++) {
			if (format(b[i] + 128) != markBytes[i] + 128) {
				this.noEncryption = true;
				return;
			}
		}
		this.currentPosition += len;
	}

	@Override
	protected void init() throws IOException {
	}

	@Override
	protected void format(byte[] b, int off, int len) {
		if (this.noEncryption) {
            return;
        }
		for (int i = off; i < len; i++){
			if(isAESEncryption){//AES解密
				b[i] = aesCrypt.decrypt(b, i, 1)[0];
				continue;
			}
			
			//普通解密
			if (b[i] == -128) {
				b[i] = 127;
			} else {
				int tmp35_33 = i;
				byte[] tmp35_32 = b;
				tmp35_32[tmp35_33] = ((byte) (tmp35_32[tmp35_33] - 1));
			}
		}
	}

	@Override
	protected int format(int i) {
		if (this.noEncryption) {
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
}
