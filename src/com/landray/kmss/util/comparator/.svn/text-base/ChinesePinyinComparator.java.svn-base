package com.landray.kmss.util.comparator;

import com.github.promeg.pinyinhelper.Pinyin;
import com.landray.kmss.util.KmssPinyinUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.util.Locale;

public class ChinesePinyinComparator {
    public static int compare(String str1, String str2) {
        /* null 最小 */
        if (str1 == null && str2 == null) {
            return 0;
        }
        if (str1 == null && str2 != null) {
            return -1;
        }
        if (str1 != null && str2 == null) {
            return 1;
        }

        /* 一些特殊情况 */
        if (str1.equals(str2)) {
            return 0;
        }
        if (str1.startsWith(str2)) {
            return 1;
        }
        if (str2.startsWith(str1)) {
            return -1;
        }

        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        for (int i = 0; i < charArray1.length; i++) {
            char c1 = charArray1[i];
            char c2 = charArray2[i];
            if (c1 == c2) {
                continue; // 两个字符相等，则比较下一个字符
            }
            String p1 = getPinyinString(c1);
            String p2 = getPinyinString(c2);

            // 两个字符都不是中文字符，则直接进行减法操作
            if (p1 == null && p2 == null) {
                return c1 - c2;
            }
            // 中文字符排最后
            if (p1 == null && p2 != null) {
                return -1;
            }
            // 中文字符排最后
            if (p1 != null && p2 == null) {
                return 1;
            }
            // 都是中文字符，则比较其拼音字符串
            return p1.compareTo(p2);
        }

        // 理论上不可能运行到此处
        return 168;
    }

    /**
     * 将输入的汉字字符转换成拼音字符串返回， 如果是多音字则返回第一个读音，如果不是汉字则返回null
     *
     * @param c 要转换的汉字字符
     * @return
     */
    public static String getPinyinString(char c) {
        if (!Pinyin.isChinese(c)) {
            return null;
        } else {
            return KmssPinyinUtil.toPinyin(c, true);
        }
    }

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ChinesePinyinComparator.class);

    public static String getPinyinString(String message) {
        if (message == null) {
            return null;
        }
        char[] chars = message.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (!Pinyin.isChinese(chars[i])) {
                logger.warn("Cannot match PINYING of character [" + chars[i] + "] in message [" + message + "]");
                return null;
            } else {
                String pinyin = KmssPinyinUtil.toPinyin(chars[i], true);
                sb.append(pinyin);
            }
        }
        return sb.toString();
    }

    /**
     * 将输入的汉字字符转换成拼音字符串返回，设置返回格式为小写不带音标， 如果是多音字则返回第一个读音，如果不是汉字则返回原文
     *
     * @param c 要转换的汉字字符
     * @return
     * @author limh
     */
    public static String getPinyinStringWithDefaultFormat(String message) {
        if (StringUtil.isNull(message)) {
            return "";
        }
        char[] chars = message.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            try {

                if (!Pinyin.isChinese(chars[i])) {
                    sb.append(Character.toLowerCase(chars[i]));
                } else {
                    String pinYin = KmssPinyinUtil.toPinyin(chars[i], true);
                    sb.append(pinYin);
                }
            } catch (Exception e) {
                logger.warn("字符串转化为拼音出现异常：" + e);
            }
        }
        return sb.toString();
    }

    /**
     * 获取指定资源的拼音首字母，如果资源不是中文字符串则返回null
     *
     * @param messageKey <bundle>:<key>
     * @param locale
     * @return
     */
    public static Character getFirstPinyinChar(String messageKey, Locale locale) {
        return getFirstPinyinChar(ResourceUtil.getString(messageKey, locale));
    }

    public static Character getFirstPinyinChar(String message) {
        if (message == null || message.length() < 1) {
            return null;
        }
        String pinyin = getPinyinString(message.charAt(0));
        if (pinyin == null || pinyin.length() < 1) {
            return null;
        }
        char c = pinyin.charAt(0);
        c = Character.toUpperCase(c);
        return new Character(c);
    }

    public static void main(String[] args) {
        logger.info(getPinyinStringWithDefaultFormat("UIt"));
    }
}
