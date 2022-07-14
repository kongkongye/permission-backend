package com.kongkongye.backend.permission.common;

import com.kongkongye.backend.permission.common.dto.IntegerDTO;
import com.kongkongye.backend.permission.common.dto.NumberDTO;
import com.kongkongye.backend.permission.common.util.StringUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 通用基类
 */
public abstract class Base {
    protected int unboxToInt(NumberDTO v) {
        return v != null && v.getValue() != null ? v.getValue().intValue() : 0;
    }

    protected int unbox(IntegerDTO v) {
        return v != null && v.getValue() != null ? v.getValue() : 0;
    }

    protected Double getDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    protected boolean isTrue(Boolean value) {
        return value != null && value;
    }

    protected boolean isFalse(Boolean value) {
        return value == null || !value;
    }

    protected static Map<String, Object> toMap(Maps data) {
        return data.getOrigin();
    }

    protected static List<Map<String, Object>> toMap(List<Maps> data) {
        return data.stream().map(Maps::getOrigin).collect(Collectors.toList());
    }

    protected static Pagination<Map<String, Object>> toMap(Pagination<Maps> data) {
        Pagination<Map<String, Object>> newData = new Pagination<>(data.getPageSize(), data.getCurrentPage(), data.getTotal());
        newData.setDataList(data.getDataList().stream().map(Maps::getOrigin).collect(Collectors.toList()));
        return newData;
    }

    protected Maps toMaps(@Nullable Map<String, Object> map) {
        return new Maps(map != null ? map : new HashMap<>());
    }

    protected List<Maps> toMaps(@Nullable List<Map<String, Object>> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list.stream().map(Maps::new).collect(Collectors.toList());
    }

    protected Pagination<Maps> toMaps(@NonNull Pagination<Map<String, Object>> pagination) {
        Pagination<Maps> result = new Pagination<>(pagination.getPageSize(), pagination.getCurrentPage(), pagination.getTotal());
        result.setDataList(pagination.getDataList().stream().map(Maps::new).collect(Collectors.toList()));
        return result;
    }

//    /**
//     * @param code 语言编码
//     */
//    protected <T> T checkNotNull(ServletRequestAttributes requestAttributes, T obj, String code) {
//        Locale locale = requestAttributes.getRequest().getLocale();
//        return Preconditions.checkNotNull(obj, I18nUtil.get(LBaseConsts.CHECK_NOT_NULL, locale, I18nUtil.get(code, locale)));
//    }
//
//    /**
//     * @param code 语言编码
//     */
//    protected String checkNotNullOrEmpty(ServletRequestAttributes requestAttributes, String obj, String code) {
//        Locale locale = requestAttributes.getRequest().getLocale();
//        Preconditions.checkArgument(!Strings.isNullOrEmpty(obj), I18nUtil.get(LBaseConsts.CHECK_NOT_NULL_OR_EMPTY, locale, I18nUtil.get(code, locale)));
//        return obj;
//    }
//
//    /**
//     * @param code 语言编码
//     * @param minLength >=0
//     * @param maxLength >=minLength
//     */
//    protected String checkLength(ServletRequestAttributes requestAttributes, String obj, String code, int minLength, int maxLength) {
//        Locale locale = requestAttributes.getRequest().getLocale();
//
//        int length = obj != null?obj.length():0;
//        Preconditions.checkArgument(length >= minLength && length <= maxLength,
//                I18nUtil.get(LBaseConsts.CHECK_LENGTH, locale, I18nUtil.get(code, locale), minLength, maxLength));
//        return obj;
//    }
//
//    /**
//     * @param code 语言编码
//     * @param minLength >=0
//     */
//    protected String checkLengthByMin(ServletRequestAttributes requestAttributes, String obj, String code, int minLength) {
//        Locale locale = requestAttributes.getRequest().getLocale();
//
//        int length = obj != null?obj.length():0;
//        Preconditions.checkArgument(length >= minLength,
//                I18nUtil.get(LBaseConsts.CHECK_LENGTH_BY_MIN, locale, I18nUtil.get(code, locale), minLength));
//        return obj;
//    }
//
//    /**
//     * @param code 语言编码
//     * @param maxLength >=0
//     */
//    protected String checkLengthByMax(ServletRequestAttributes requestAttributes, String obj, String code, int maxLength) {
//        Locale locale = requestAttributes.getRequest().getLocale();
//
//        int length = obj != null?obj.length():0;
//        Preconditions.checkArgument( length <= maxLength,
//                I18nUtil.get(LBaseConsts.CHECK_LENGTH_BY_MAX, locale, I18nUtil.get(code, locale), maxLength));
//        return obj;
//    }

    protected String fix(String str, int maxLength) {
        return StringUtil.fix(str, maxLength);
    }

    /**
     * 获取语言
     * @param code 语言编码
     */
//    protected String get(String code, Locale locale, Object... args) {
//        return I18nUtil.get(code, locale, args);
//    }

    /**
     * 包装
     */
    public <T> T wrap(T o, Consumer<T> consumer) {
        consumer.accept(o);
        return o;
    }

    public interface Converter<F, T> {
        T convert(F from);
    }

    /**
     * 转换
     *
     * @param <F> 输入
     * @param <T> 输出
     */
    public <F, T> T convert(F from, Converter<F, T> converter) {
        return converter.convert(from);
    }

    public interface Producer<T> {
        T produce();
    }

    /**
     * 生产
     *
     * @param <T> 输出
     */
    public <T> T of(Producer<T> producer) {
        return producer.produce();
    }
}
