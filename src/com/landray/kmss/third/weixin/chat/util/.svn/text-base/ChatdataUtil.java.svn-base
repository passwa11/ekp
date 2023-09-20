package com.landray.kmss.third.weixin.chat.util;

import com.landray.kmss.util.StringUtil;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ChatdataUtil {

    private static final String key = "LandrayWxChat@#$";
    private static final String i_v = "WeixinChat&^%$#@";

    public static Cipher getEncrypter() throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//        IvParameterSpec ivspec = new IvParameterSpec(i_v.getBytes());  // CBC模式，需要一个向量iv，可增加加密算法的强度
//        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
//        return cipher;

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");//根据传入的二进制数组 生成SecretKey
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        //初始化解码器,这里根据是加密模式还是解码模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher;
    }

    public static String encry(Cipher cipher, String content) throws Exception {
        if(StringUtil.isNull(content)) {
            return content;
        }
        //int blockSize = cipher.getBlockSize();
        byte[] dataBytes = content.getBytes();
//        int plaintextLength = dataBytes.length;
//        if (plaintextLength % blockSize != 0) {
//            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
//        }
//        byte[] plaintext = new byte[plaintextLength];
//        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        byte[] encrypted = cipher.doFinal(dataBytes);
        return new String(Hex.encodeHex(encrypted));
    }

    public static Cipher getDecrypter() throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//        IvParameterSpec ivspec = new IvParameterSpec(i_v.getBytes());
//        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
//        return cipher;

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");//根据传入的二进制数组 生成SecretKey
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        //初始化解码器,这里根据是加密模式还是解码模式
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher;
    }

    public static String decry(Cipher cipher, String content) throws Exception {
        if(StringUtil.isNull(content)) {
            return content;
        }
        byte[] encrypted1 = Hex.decodeHex(content);//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original,"UTF-8");
        return originalString;
    }
}
