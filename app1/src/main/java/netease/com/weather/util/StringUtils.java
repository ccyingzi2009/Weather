package netease.com.weather.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 16-8-23.
 */
public class StringUtils {

    public final static String ASSERTS_PATH = "file:///android_asset/";

    public final static String IMG_NODE = "<IMG_NODE>";
    public static Object[] parseMobileContent(String content) {
        String[] lines = content.split("<br>");
        ArrayList<String> attachList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        StringBuilder ref = new StringBuilder();


        boolean refStart = false;
        for (String line : lines) {
            //处理a标签
            Pattern urlPattern = Pattern.compile("<a target=\"_blank\" href=\"([^<>]+)\"><img");
            Matcher urlMatcher = urlPattern.matcher(line);
            if (urlMatcher.find()) {
                attachList.add(urlMatcher.group(1));
                sb.append(IMG_NODE); //添加图片标签
                continue;
            }
            if (line.startsWith("修改") || line.startsWith("FROM")) {
                continue;
            }
            //引用
            if (line.startsWith(":")) {
                ref.append(line).append("<br/>");
                continue;
            }
            if (line.contains("的大作中提到")) {
                ref.append(line);
                refStart = true;
                continue;
            }

            if (line.startsWith("--")) {
                refStart = false;
                continue;
            }

            if (refStart) {
                ref.append(line);
            }

            if (line.equals("")) {
                continue;
            }

            sb.append(line).append("<br/>");
        }
        if (sb.length() > 5) {
            sb.delete(sb.length() - 5, sb.length());
        }
        if (ref.length() > 5) {
            ref.delete(ref.length() - 5, ref.length());
        }
        return new Object[]{sb.toString(), ref.toString(), attachList};
    }
}
