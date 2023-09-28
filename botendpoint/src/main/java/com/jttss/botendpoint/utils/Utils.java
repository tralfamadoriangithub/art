package com.jttss.botendpoint.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String LINKS_REGEX = "(http[s]?:\\/\\/)?([^\\/\\s]+\\/)(.*)";

    public static List<String> getLinksFromString(final String stringToParse) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile(LINKS_REGEX);
        Matcher matcher = pattern.matcher(stringToParse);
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }
}
