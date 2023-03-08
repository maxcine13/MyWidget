package com.linji.cabinetutil.util;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author qingf
 */
public class StringUtil {

    /**
     * 判断是否为正确的邮件格式
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        if (isEmpty(str))
            return false;
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }
    // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

    /**
     * 判断字符串是否为合法手机号 11位 13 14 15 16 17 18 19开头
     *
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        if (isEmpty(str)) return false;
        return str.matches("^1[3-9]\\d{9}$");
    }

    public static String CARD_ID = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
    public static String NAME = "^(([a-zA-Z+\\.?\\·?a-zA-Z+]{2,30}$)|([\\u4e00-\\u9fa5+\\·?\\u4e00-\\u9fa5+]{2,30}$))";
    //    public static String PASSWORD_REG = "(^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~`!@#$%^&*\\-_])[\\da-zA-Z~`!@#$%^&*\\-_ ]{6,18}$)";
    public static String PASSWORD_REG = "(^((?=.*[a-zA-Z])(?=.*\\d)|(?=[a-zA-Z])(?=.*[~`!@#$%^&*\\-_ ])|(?=.*\\d)(?=.*[~`!@#$%^&*\\-_ ]))[a-zA-Z\\d~`!@#$%^&*\\-_ ]{6,18}$)";

    private static String LINK_REG = "^((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";

    public static boolean isName(String name) {
        if (isEmpty(name)) return false;
        return name.matches(NAME);
    }

    public static boolean isIDCard(String str) {
        if (isEmpty(str)) return false;
        if (str.matches(CARD_ID)) {
            return calculateCardCheckCode(str);
        }else {
            return false;
        }
    }

    public static boolean isTelePhone(String str) {
        if (isEmpty(str)) return false;
        return str.matches("^0\\d{2,3}-\\d{7,8}$");
    }

    public static boolean isPhone(String str) {
        if (isEmpty(str)) return false;
        return isMobile(str) || isTelePhone(str);
    }

    public static Boolean calculateCardCheckCode(String idCardNums) {
        //由于此时身份证上前17位相乘的因子是固定的，那么提前将这个因子数组进行创建
        int[] weightings = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        String[] checkCodes = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        //初始化一个int类型的变量num17Sum用来存储前17位号码之和
        try {
            int num17Sums = 0;
            char[] charArray = idCardNums.toCharArray();
            for (int i = 0; i < weightings.length; i++) {
                //初始化一个int类型的变量numProduct用来存储每一位号码的加权值
                int numProduct = Integer.parseInt(String.valueOf(charArray[i])) * weightings[i];
                num17Sums += numProduct;
            }
            //System.out.println("您的前17位号码加权总和："+num17Sums);
            //初始化一个int类型的变量mod11用来存储取模11的值
            int mod11 = num17Sums % 11;
            char idCardLast = charArray[17];
            if (checkCodes[mod11].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String formatPhone(@NonNull String str) {
        StringBuilder phone = new StringBuilder(str);
        if (str.length() < 11) {
            for (int i = str.length(); i < 11; i++) {
                phone.append("0");
            }
        }
        return phone.toString();
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * 判断是否为浮点数或者整数
     *
     * @param str
     * @return true Or false
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 判断字符串是否为非空(包含null与"")
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str == null || "".equals(str))
            return false;
        return true;
    }

    /**
     * 判断字符串是否为非空(包含null与"","    ")
     *
     * @param str
     * @return
     */
    public static boolean isNotEmptyIgnoreBlank(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()) || "null".equals(str.trim()))
            return false;
        return true;
    }

    /**
     * 判断字符串是否为空(包含null与"")
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) return true;
        return false;
    }

    /**
     * 判断字符串是否为空(包含null与"","    ")
     *
     * @param str
     * @return
     */
    public static boolean isEmptyIgnoreBlank(String str) {
        if (str == null || "null".equals(str.trim()) || "".equals(str.trim()))
            return true;
        return false;
    }

    public static boolean isLink(String str) {
        Pattern pattern = Pattern.compile(LINK_REG);
        Matcher isLink = pattern.matcher(str);
        if (!isLink.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 密码6到18位
     *
     * @param str 数字/大小写字母/~`!@#$%^&*-_
     * @return
     */
    public static boolean isPassword(String str) {
        return str.matches(PASSWORD_REG);
    }

    public static boolean isNotPassword(String str) {
        return str.trim().length() < 6 || str.trim().length() > 18;
    }

    public static boolean contains(String curProcessName, String s) {
        if (curProcessName == null || s == null)
            return false;
        return curProcessName.contains(s);
    }

    public static boolean equals(String flag, String saveValue) {
        return flag.equals(saveValue);
    }

    /**
     * 是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isCN(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            return bytes.length != str.length();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 格式化数据类型Double ,输出格式化后字符串
     *
     * @param Data        需要格式化的数字
     * @param FormatRules 格式规则
     * @return string: 返回格式化后字符串
     */
    public static String DoubleToString(double Data, String FormatRules) {
        DecimalFormat formatter = new DecimalFormat(FormatRules);
        return formatter.format(Data);
    }

    //禁止实例化
    @SuppressWarnings("unused")
    private void ValidatorUtil() {
    }


    public static String lowerLetters() {
        return "abcdefghijklmnopqrstuvwxyz";
    }

    public static String upperLetters() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    public static String Number() {
        return "0123456789";
    }
}