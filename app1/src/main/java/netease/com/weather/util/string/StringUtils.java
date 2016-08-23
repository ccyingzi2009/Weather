package netease.com.weather.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 16-8-23.
 */
public class StringUtils {

    public static Object[] parseMobileContent(String content) {
        String []lines = content.split("<br>");
        List<String> attachList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            Pattern urlPattern = Pattern.compile("<a target=\"_blank\" href=\"([ ^<>]+)\"><img");
            Matcher urlMatcher = urlPattern.matcher(line);
            if (urlMatcher.find()) {
                attachList.add(line);
                continue;
            }

            sb.append(line).append("<br/>");
        }
        return new Object[]{sb.toString(), attachList};
    }
}
