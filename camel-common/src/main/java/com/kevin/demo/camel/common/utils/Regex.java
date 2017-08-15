package com.kevin.demo.camel.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static Boolean doesMatch(String value, String regex) {
        return Pattern.compile(regex).matcher(value).matches();
    }

    public static List<String> capture(String value, String regex) {
        List<String> groupList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(value);

        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                groupList.add(m.group(i));
            }
        }

        return groupList;
    }
}
