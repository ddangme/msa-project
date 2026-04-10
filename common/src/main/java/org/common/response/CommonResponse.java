package org.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(true, null, "성공");
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, data, "성공");
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>(true, data, message);
    }

    public static <T> CommonResponse<T> fail(String message) {
        return new CommonResponse<>(false, null, message);
    }
}
