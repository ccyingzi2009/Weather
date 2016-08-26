package netease.com.weather.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by user on 16-8-26.
 */
public class JsonUtils {

    public static String toJson(Object o){
        return new Gson().toJson(o);
    }

    public static <T> T fromJson(String jsonStr, TypeToken<T> typeToken) {
         return new Gson().fromJson(jsonStr, typeToken.getType());
    }
}
