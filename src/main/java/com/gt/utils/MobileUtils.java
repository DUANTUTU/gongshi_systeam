package com.gt.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileUtils {
    public static String getMobile(String str) {
        String mobile = "";
        //手机:((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}
        //固定电话:(0\d{2}-\d{8}(-\d{1,4})?)|(0\d{3}-\d{7,8}(-\d{1,4})?)
        // 将给定的正则表达式编译到模式中
        Pattern pattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
        // 创建匹配给定输入与此模式的匹配器。
        Matcher matcher = pattern.matcher(str);
        //查找字符串中是否有符合的子字符串
        while (matcher.find()) {
            //查找到符合的即输出
//            if (mobile.equals("")) {
            mobile = matcher.group();
            break;
//            } else {
//                mobile += "," + matcher.group();
//            }
        }
        return mobile;
    }
}
