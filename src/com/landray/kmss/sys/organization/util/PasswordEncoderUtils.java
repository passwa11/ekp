package com.landray.kmss.sys.organization.util;

import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.profile.password.interfaces.IPasswordConfusionEncrypt;
import com.landray.kmss.sys.profile.password.util.PasswordConfusionUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Utf8;

/**
 *
 * Utility for constant time comparison to prevent against timing attacks.
 *
 */
public class PasswordEncoderUtils {
    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderUtils.class);

    private static IKmssPasswordEncoder passwordEncoder;

    private static IKmssPasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = (IKmssPasswordEncoder) SpringBeanUtil.getBean("passwordEncoder");
        }
        return passwordEncoder;
    }

    /**
     * 比较password与pwEncoged是否相同的密码<br/>
     * 此方法兼容原本使用md5方式加密的旧数据<br/>
     * @param password 明文密码
     * @param pwEncoged 经过加密后的密码
     * @return
     */
    public static boolean checkPassWordEquals(String password, String pwEncoged){
        //former compatible, if current algorithm is not MD5(the legacy algorithm), try again in MD5
        boolean formerMatched = false;
        try {
            formerMatched = pwEncoged.equals(getPasswordEncoder().encodePassword(password));
            if (!formerMatched
                && !"MD5".equalsIgnoreCase(ResourceUtil.getKmssConfigString("kmss.org.encoder.algorithm"))) {
                IPasswordConfusionEncrypt md5 = PasswordConfusionUtil.getEncrypt("MD5");
                formerMatched = pwEncoged.equals(md5.encrypt(password));
            }
        } catch (Exception e) {
            logger.error("密码比对出错,{}",e);
        }
        return formerMatched;
    }

    /**
     * Constant time comparison to prevent against timing attacks.
     * @param expected
     * @param actual
     * @return
     */
    public static boolean equals(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);
        int expectedLength = expectedBytes == null ? -1 : expectedBytes.length;
        int actualLength = actualBytes == null ? -1 : actualBytes.length;
        if (expectedLength != actualLength) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedLength; i++) {
            result |= expectedBytes[i] ^ actualBytes[i];
        }
        return result == 0;
    }

    private static byte[] bytesUtf8(String s) {
        if(s == null) {
            return null;
        }

        return Utf8.encode(s);
    }
    private PasswordEncoderUtils() {}
}
