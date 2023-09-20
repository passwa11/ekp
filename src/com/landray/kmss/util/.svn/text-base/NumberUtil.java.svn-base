package com.landray.kmss.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 常用的数据格式转换方法。
 *
 * @author 吴兵
 * @version 1.0 2006-09-25
 */
public class NumberUtil {

    /**
     * 用###,###.##格式化字符串值。
     *
     * @param value - 值
     * @return 被转换后的字符串。
     */
    public static String roundDecimal(Object value) {
        return roundDecimal(value, "###,###.##");
    }

    /**
     * 用指定格式格式化字符串值。
     *
     * @param value   - 值
     * @param pattern -格式
     * @return 被转换后的字符串。
     */
    public static String roundDecimal(Object value, String pattern) {
        String res = null;
        if (pattern == null || "".equals(pattern.trim())) {
            pattern = "###,###.##";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        try {
            if (value instanceof String) {
                res = df.format(new Double(value.toString()));
            } else {
                res = df.format(value);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 用指定格式格式化字符串值。
     * 以“0”补位时：
     * <p>
     * 如果数字少了，就会补“0”，小数和整数都会补；
     * <p>
     * 如果数字多了，就切掉，但只切小数的末尾，整数不能切；
     * <p>
     * 同时被切掉的小数位会进行四舍五入处理。
     *
     * @param value   - 值
     * @param pattern -格式
     * @return 被转换后的字符串。
     */
    public static String roundDecimalPattern(Object value, String pattern) {
        String res = null;
        if (pattern == null || "".equals(pattern.trim())) {
            pattern = "###,##0.00";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.HALF_UP);
        try {
            if (value instanceof String) {
                res = df.format(new Double(value.toString()));
            } else {
                res = df.format(value);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 保留两位小数。
     *
     * @param fTemp - 要保留的数据
     * @return 被转换后的字符串。
     */
    public static String roundDecimal(double fTemp) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(fTemp);
    }

    public static String roundDecimal(float fTemp) {
        return roundDecimal((double) fTemp);
    }

    /**
     * 保留指定的位小数。
     *
     * @param fTemp - 要保留的数据
     * @param pos   - 指定的位数
     * @return 被转换后的字符串。
     */
    public static String roundDecimal(double fTemp, int pos) {
        // String s = "";
        // for (int i = 0; i < pos; i++) {
        // s += "#";
        // }
        // DecimalFormat df = new DecimalFormat("#." + s);
        DecimalFormat df = new DecimalFormat("#.################");
        df.setMaximumFractionDigits(pos);
        return df.format(fTemp);
    }

    public static String roundDecimal(float fTemp, int pos) {
        return roundDecimal(Double.valueOf(String.valueOf(fTemp)), pos);
    }

    public static Double round(double doubleParam, int digit) {
        String digitString = "0";
        for (int i = 0; i < digit; i++) {
            digitString = digitString + "0";
        }
        DecimalFormat df = new DecimalFormat("#." + digitString);
        df.setMaximumFractionDigits(digit);
        Double rtnDouble = Double.valueOf(df.format(doubleParam));
        return rtnDouble;
    }

    /**
     * 修正double的误差
     *
     * @param d
     * @return
     */
    public static double correctDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.################");
        String s = df.format(d);
        int begin = s.indexOf(".");
        if (begin == -1) {
            return d;
        }
        int index = s.indexOf("00000", begin);
        if (index == -1) {
            index = s.indexOf("99999", begin);
        }
        if (index == -1) {
            return d;
        }
        StringBuffer sb = new StringBuffer("#.");
        for (int i = 2; i < index; i++) {
            sb.append('#');
        }
        sb.append("E0");
        df.applyPattern(sb.toString());
        return Double.parseDouble(df.format(d));
    }

    /**
     * 数字转换为大写中文
     *
     * @param val
     * @return 中文数字
     * @throws Exception
     * @author 朱国荣 2016-08-11
     */
    public static String transNumToChina(String val) throws Exception {
        // 清除多余的空格
        val = val.trim();
        StringBuffer chineseValue = new StringBuffer(); // 转换后的汉字金额
        // 利用正则表达式判断是否为数字,最多只能接受8位小数
        Boolean strResult = val.matches("^(-?[0-9]{1,12})(.[0-9]{1,8})?$");
        // 数字才做转化
        if (strResult) {
            Double num = Double.parseDouble(val);
            // 如果是负数,前面加"负"字
            if (num < 0) {
                chineseValue.append("负");
                num = Math.abs(num);
            }
            // 提示超出可计算范围
            if (num.compareTo(Double.MAX_VALUE) > 0
                    || num.compareTo(Double.MIN_VALUE) < 0) {
                chineseValue.append("超出大写可计算范围");
                return chineseValue.toString();
            }
            String numberValue = String.valueOf(Math.round(num * 100)); // 数字金额
            String String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
            String String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
            int len = numberValue.length(); // numberValue的字符串长度
            String Ch1; // 数字的汉语读法
            String Ch2; // 数字位的汉字读法
            int nZero = 0; // 用来计算连续的零值的个数
            int String3 = 0; // 指定位置的数值
            if ("0".equals(numberValue)) {
                chineseValue.append("零元整");
                return chineseValue.toString();
            }
            String2 = String2.substring(String2.length() - len); // 取出对应位数的STRING2的值
            for (int i = 0; i < len; i++) {
                String3 = Integer.parseInt(numberValue.substring(i, i + 1), 10); // 取出需转换的某一位的值
                if (i != (len - 3) && i != (len - 7) && i != (len - 11)
                        && i != (len - 15)) {
                    if (String3 == 0) {
                        Ch1 = "";
                        Ch2 = "";
                        nZero = nZero + 1;
                    } else if (String3 != 0 && nZero != 0) {
                        Ch2 = String2.substring(i, i + 1);
                        if ("角".equals(Ch2)) {
                            Ch1 = String1.substring(String3, String3 + 1);
                        } else {
                            Ch1 = "零" + String1.substring(String3, String3 + 1);
                        }
                        nZero = 0;
                    } else {
                        Ch1 = String1.substring(String3, String3 + 1);
                        Ch2 = String2.substring(i, i + 1);
                        nZero = 0;
                    }
                    // 该位是万亿，亿，万，元位等关键位
                } else {
                    if (String3 != 0 && nZero != 0) {
                        Ch1 = "零" + String1.substring(String3, String3 + 1);
                        Ch2 = String2.substring(i, i + 1);
                        nZero = 0;
                    } else if (String3 != 0 && nZero == 0) {
                        Ch1 = String1.substring(String3, String3 + 1);
                        Ch2 = String2.substring(i, i + 1);
                        nZero = 0;
                    } else if (String3 == 0 && nZero >= 3) {
                        Ch1 = "";
                        Ch2 = "";
                        nZero = nZero + 1;
                    } else {
                        Ch1 = "";
                        Ch2 = String2.substring(i, i + 1);
                        nZero = nZero + 1;
                    }
                    // 如果该位是亿位或元位，则必须写上
                    if (i == (len - 11) || i == (len - 3)) {
                        Ch2 = String2.substring(i, i + 1);
                    }
                }
                chineseValue.append(Ch1).append(Ch2);
            }
            int String4 = 0;
            if (len > 2) {
                String4 = Integer.parseInt(numberValue.substring(len - 2,
                        len - 1), 10);
            }
            // 最后一位（分）为0时，加上“整”
            if (String3 == 0 && String4 == 0) {
                chineseValue.append("整");
            }
        } else {
            throw new Exception("传入的参数不是正常的数字！");
        }
        return chineseValue.toString();
    }

    private static DecimalFormat simpleDecimalFormat = new DecimalFormat(
            "#.################");

    public static String toSimpleString(double d) {
        return simpleDecimalFormat.format(d);
    }
}
