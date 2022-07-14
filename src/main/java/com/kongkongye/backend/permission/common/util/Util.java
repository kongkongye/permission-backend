package com.kongkongye.backend.permission.common.util;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Util {
    /**
     * json转列表
     *
     * @param json 是{
     *             1: "3d07b92945af4d41b4d23080a9ca73fc"
     *             }这种形式的
     */
    @NonNull
    public static <T> List<T> jsonToList(@Nullable com.alibaba.fastjson.JSONObject json) {
        List<T> result = new ArrayList<>();

        if (json != null) {
            int idx = 1;
            while (true) {
                T t = (T) json.get(idx++);
                if (t != null) {
                    result.add(t);
                } else {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * excel字母转索引
     *
     * @param cellStr 类似'A'
     * @return 从0开始
     */
    public static int getCellNum(String cellStr) {
        char[] cellStrArray = cellStr.toUpperCase().toCharArray();
        int len = cellStrArray.length;
        int n = 0;
        for (int i = 0; i < len; i++) {
            n += (((int) cellStrArray[i]) - 65 + 1) * Math.pow(26, len - i - 1);
        }
        return n - 1;
    }

    /**
     * excel 索引转字母
     *
     * @param index 从0开始
     * @return 类似'A'
     */
    public static String getCellStr(int index) {
        String colName = "";

        if (index >= 26) {
            colName = getCellStr(index / 26 - 1);
            colName += (char) (65 + index % 26);
        } else {
            colName += (char) (65 + index);
        }

        return colName;
    }

//    /**
//     * json字符串转xml字符串
//     */
//    public static String jsonToXml(String jsonStr, String rootNodeName) {
//        JSONObject json = new JSONObject(jsonStr);
//        return XML.toString(json, rootNodeName);
//    }

//    /**
//     * rootNodeName为'xml'
//     * @see #xmlToJson(String, String, boolean)
//     */
//    public static String xmlToJson(String xmlStr, boolean keepString) {
//        return xmlToJson(xmlStr, "xml", keepString);
//    }

    @Nullable
    public static JSONArray getJsonArray(com.alibaba.fastjson.JSONObject json, String key) {
        Object obj = json.get(key);
        if (obj == null) {
            return null;
        }

        JSONArray result;
        if (obj instanceof JSONArray) {
            result = (JSONArray) obj;
        } else {
            result = new JSONArray(Lists.newArrayList(obj));
        }
        return result;
    }

    /**
     * xml字符串转json字符串
     */
//    public static String xmlToJson(String xmlStr, String rootNodeName, boolean keepString) {
//        return XML.toJSONObject(xmlStr, keepString).get(rootNodeName).toString();
//    }

    /**
     * 获取客户端的真实ip
     */
    public static String getRealIp(@Nullable String realIpHeader, HttpServletRequest request) {
        if (!Strings.isNullOrEmpty(realIpHeader)) {
            String realIp = request.getHeader(realIpHeader);
            if (realIp != null) {
                realIp = realIp.trim();
            }
            if (!Strings.isNullOrEmpty(realIp)) {
                return realIp;
            }
        }

        return request.getRemoteAddr();
    }

    @SneakyThrows
    public static <F, T> T toEqual(Class<T> targetCls, F from) {
        T target = targetCls.getConstructor().newInstance();
        toEqualFields(from, target);
        return target;
//        String str = MAPPER.writeValueAsString(from);
//        return MAPPER.readValue(str, targetCls);
    }

    /**
     * 对于同名，可赋值字段，赋值过去
     *
     * <br>只检测非静态变量
     */
    public static void toEqualFields(@NonNull Object from, @NonNull Object to, String... ignoreFieldNames) {
        toEqualFields(from.getClass(), from, to, ignoreFieldNames != null ? Arrays.stream(ignoreFieldNames).collect(Collectors.toSet()) : new HashSet<>());
    }

    private static void toEqualFields(@NonNull Class fromCls, @NonNull Object from, @NonNull Object to, @NonNull Set<String> ignoreFieldNames) {
        //本身
        for (Field field : fromCls.getDeclaredFields()) {
            if (!ignoreFieldNames.contains(field.getName())) {
                toEqualField(to.getClass(), from, to, field);
            }
        }
        //父
        Class parentCls = fromCls.getSuperclass();
        if (parentCls != null) {
            toEqualFields(parentCls, from, to, ignoreFieldNames);
        }
    }

    /**
     * 对于同名，可赋值字段，赋值过去
     *
     * <br>只检测非静态变量
     */
    public static void toEqualFieldsExact(@NonNull Object from, @NonNull Object to, String... includeFieldNames) {
        toEqualFieldsExact(from.getClass(), from, to, includeFieldNames != null ? Arrays.stream(includeFieldNames).collect(Collectors.toSet()) : new HashSet<>());
    }

    private static void toEqualFieldsExact(@NonNull Class fromCls, @NonNull Object from, @NonNull Object to, @NonNull Set<String> includeFieldNames) {
        //本身
        for (Field field : fromCls.getDeclaredFields()) {
            if (includeFieldNames.contains(field.getName())) {
                toEqualField(to.getClass(), from, to, field);
            }
        }
        //父
        Class parentCls = fromCls.getSuperclass();
        if (parentCls != null) {
            toEqualFieldsExact(parentCls, from, to, includeFieldNames);
        }
    }

    private static <F, T> void toEqualField(Class toCls, F from, T to, Field fromField) {
        Field toField = null;
        try {
            toField = toCls.getDeclaredField(fromField.getName());
        } catch (NoSuchFieldException e) {
        }
        if (toField != null) {//本身
            //得到值
            Object value = ReflectionUtil.getField(fromField, from);
            //可以赋值
            if (toField.getType().isAssignableFrom(fromField.getType())) {
                //设置值
                ReflectionUtil.setField(toField, to, value);
            }
        } else {//父
            Class parentToCls = toCls.getSuperclass();
            if (parentToCls != null) {
                toEqualField(parentToCls, from, to, fromField);
            }
        }
    }

    /**
     * 删除html标签（包括标签里的内容）
     */
    public static String trimHtmlTags(String str) {
        return str != null ? str.replaceAll("\\<.*?>", "") : null;
    }

    private static final Long MINUTE = 60 * 1000L;
    private static final Long HOUR = 60 * MINUTE;
    private static final Long DAY = 24 * HOUR;

    /**
     * 获取等待时间显示
     */
    public static String getWaitTimeShow(long wait) {
        long pastDay = wait / DAY;
        wait -= pastDay * DAY;
        long pastHour = wait / HOUR;
        wait -= pastHour * HOUR;
        long pastMinute = wait / MINUTE;
        wait -= pastMinute * MINUTE;
        long pastSeconds = wait / 1000;

        String result = "";
        if (pastDay > 0) {
            result += pastDay + "天";
        }
        if (pastHour > 0) {
            result += pastHour + "小时";
        }
        if (pastMinute > 0) {
            result += pastMinute + "分钟";
        }
        if (result.isEmpty()) {
            result = pastSeconds + "秒";
        }
        return result;
    }

    public static String md5(String s) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new String(Base64.encodeBase64(md.digest(s.getBytes(Charsets.UTF_8))));
    }

    /**
     * @param time 'yyyy-MM-dd'
     * @param end  是否结束时间
     * @return 毫秒
     */
    @SneakyThrows
    public static Long toMilli(String time, boolean end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long result = sdf.parse(time).getTime();
        return end ? result + 24L * 3600 * 1000 : result;
    }

    /**
     * @return 天,'yyyy-MM-dd'
     */
    @SneakyThrows
    public static String fromMilli(long milli) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(milli));
    }
}
