package com.kongkongye.backend.permission.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 响应,返回给客户端的结果
 * <p>
 * 510: 客户单号相同禁止下单
 */
@Data
@NoArgsConstructor
public class Result<T> {
    public static final String KEY_DATA = "data";
    private static final String DEFAULT_CODE_SUCCESS = "200";
    private static final String DEFAULT_CODE_FAIL = "500";

    //是否成功
    private Boolean success;
    //编码(成功或失败都有)
    private String code;
    //信息
    private String message;
    //数据
    private Map<String, Object> map = new HashMap<>();

    private Result(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.map.put(KEY_DATA, data);
    }

    /**
     * 获取map里的值
     */
    public <R> R get(String key) {
        return (R) this.map.get(key);
    }

    /**
     * 设置map里的值
     */
    public Result<T> put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    /**
     * 功能最全的返回
     */
    public static <T> Result<T> of(boolean success, String code, String message, T data) {
        return new Result<>(success, code, message, data);
    }

    /**
     * @see #successWithCode(String)
     * @see #DEFAULT_CODE_SUCCESS
     */
    public static Result success() {
        return successWithCode(DEFAULT_CODE_SUCCESS);
    }

    /**
     * 成功响应
     */
    public static Result successWithCode(String code) {
        return new Result(true, code, null, null);
    }

    /**
     * @see #success(String, T)
     * @see #DEFAULT_CODE_SUCCESS
     */
    public static <T> Result<T> success(T data) {
        return success(DEFAULT_CODE_SUCCESS, data);
    }

    /**
     * 成功响应
     *
     * @param data 可为null
     */
    public static <T> Result<T> success(String code, T data) {
        return new Result(true, code, null, data);
    }

    public static Result<Map<String, Object>> success(Maps data) {
        return success(DEFAULT_CODE_SUCCESS, data);
    }

    public static Result<Map<String, Object>> success(String code, Maps data) {
        return success(code, data.getOrigin());
    }

    public static Result<List<Map<String, Object>>> successOfMaps(List<Maps> data) {
        return successOfMaps(DEFAULT_CODE_SUCCESS, data);
    }

    public static Result<List<Map<String, Object>>> successOfMaps(String code, List<Maps> data) {
        return success(code, data.stream().map(Maps::getOrigin).collect(Collectors.toList()));
    }

    public static Result<Pagination<Map<String, Object>>> successOfMaps(Pagination<Maps> data) {
        return successOfMaps(DEFAULT_CODE_SUCCESS, data);
    }

    public static Result<Pagination<Map<String, Object>>> successOfMaps(String code, Pagination<Maps> data) {
        Pagination<Map<String, Object>> newData = new Pagination<>(data.getPageSize(), data.getCurrentPage(), data.getTotal());
        newData.setDataList(data.getDataList().stream().map(Maps::getOrigin).collect(Collectors.toList()));
        return success(code, newData);
    }

    public static Result fail() {
        return fail("");
    }

    /**
     * @see #fail(String, String)
     * @see #DEFAULT_CODE_FAIL
     */
    public static Result fail(String message) {
        return fail(DEFAULT_CODE_FAIL, message);
    }

    /**
     * 失败响应
     *
     * @param message 错误信息,可为null
     */
    public static Result fail(String code, String message) {
        return new Result(false, code, message, null);
    }
}
