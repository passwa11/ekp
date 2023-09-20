package com.landray.kmss.km.cogroup.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	//加密方式(AES) 加密模式(ECB) 填充模式(PKCS5Padding)
	private static final String ALG="AES/ECB/PKCS5Padding";
	public static String decrypt2str(String content,String key){
		return new String(decrypt(parseHexStr2Byte(content),key));
	}
	
	public static String encrypt2str(String content,String key){
		return parseByte2HexStr(encrypt(content, key));
	}
	
	public static byte[] encrypt(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");// 使用SecretKeySpec类来根据一个字节数组构造一个
																		// SecretKey,，而无须通过一个（基于
																		// provider
																		// 的）SecretKeyFactory.
			Cipher cipher = Cipher.getInstance(ALG);// 创建密码器 //为创建 Cipher
														// 对象，应用程序调用 Cipher 的
														// getInstance 方法并将所请求转换
														// 的名称传递给它。还可以指定提供者的名称（可选）。
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent); // 按单部分操作加密或解密数据，或者结束一个多部分操作。数据将被加密或解密（具体取决于此
															// Cipher 的初始化方式）。
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, String password) {     
        try {   
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");                 
            Cipher cipher = Cipher.getInstance(ALG);// 创建密码器     
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化     
            byte[] result = cipher.doFinal(content);     
            return result; // 加密     
        } catch (NoSuchAlgorithmException e) {     
            e.printStackTrace();     
        } catch (NoSuchPaddingException e) {     
            e.printStackTrace();     
        } catch (InvalidKeyException e) {     
            e.printStackTrace();     
        } catch (IllegalBlockSizeException e) {     
                e.printStackTrace();     
        } catch (BadPaddingException e) {     
                e.printStackTrace();     
        }     
        return null;     
    }
	
	public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
}  
	
	public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
	}
}
