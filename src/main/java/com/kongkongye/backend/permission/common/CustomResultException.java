package com.kongkongye.backend.permission.common;

import lombok.Getter;

/**
 * 自定义result异常
 */
public class CustomResultException extends RuntimeException {
    @Getter
    private Result result;

    public CustomResultException(Result result) {
        super(result.getMessage());
        this.result = result;
    }
}
