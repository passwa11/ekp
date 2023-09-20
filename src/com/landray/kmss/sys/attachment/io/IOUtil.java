package com.landray.kmss.sys.attachment.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(IOUtil.class);
	
	public static final int defaultBufSize = 1024 ;
	
	public static void write(InputStream in, OutputStream out)throws IOException {
		write(in, out, 0);
	}
	
	public static void write(InputStream in, OutputStream out, int bufSize)throws IOException {
		if (in == null || out == null) {
			return;
		}
		try {
			int _bufSize = (bufSize != 0) ? bufSize : defaultBufSize;
			byte[] buffer = new byte[_bufSize];
			int len;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} finally {
			if(in!=null){
				try {
					in.close();
				}catch (Exception e){
				}
			}
			if(out!=null){
				try {
					out.flush();
				}catch (Exception e){
				}
				try {
					out.close();
				}catch (Exception e){
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		File from, to;
		from = new File("d:/temp/1.txt");
		to = new File("d:/temp/2.txt");
		if (!to.exists()) {
            to.createNewFile();
        }
		write(new EncryptionInputStream(new FileInputStream(from)),
				new FileOutputStream(to));

		from = new File("d:/temp/2.txt");
		to = new File("d:/temp/3.txt");
		if (!to.exists()) {
            to.createNewFile();
        }
		write(new DecryptionInputStream(new FileInputStream(from)),
				new FileOutputStream(to));
		logger.info("完成");
	}
}
