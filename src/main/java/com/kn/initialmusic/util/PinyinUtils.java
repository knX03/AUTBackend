package com.kn.initialmusic.util;

import io.micrometer.common.util.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinUtils {
    /**
     * 汉字转换为汉语拼音首字母，英文字符转化为首字母大写
     *
     * @param a
     * @return
     */
    public static String getFirst(String a) throws BadHanyuPinyinOutputFormatCombination {
        String pinyinName = "";
        if (StringUtils.isNotBlank(a)) {
            if (isChinese(a)) {
                //是汉字
                char[] nameChar = a.toCharArray();
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
                defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
                defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                for (int i = 0; i < nameChar.length; i++) {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                }
                return pinyinName;
            } else if (isEnglish(a)) {
                //不是汉字
                if (a.length() >= 1) {
                    pinyinName = a.substring(0, 1).toUpperCase();
                    return pinyinName;
                }
            } else {
                return "1";
            }
        }
        return null;
    }

    private static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }

    private static boolean isEnglish(String str) {
        String regEx = "[A-Za-z]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }
}
