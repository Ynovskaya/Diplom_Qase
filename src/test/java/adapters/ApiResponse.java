package adapters;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {
    private final boolean ok;
    private final int status;
    private final T data;
    private final String raw;
    private final String errorMessage;
    private final Exception exception;

    private ApiResponse(boolean ok, int status, T data, String raw, String errorMessage, Exception exception) {
        this.ok = ok;
        this.status = status;
        this.data = data;
        this.raw = raw;
        this.errorMessage = errorMessage;
        this.exception = exception;
    }

    public static <T> ApiResponse<T> success(int status, T data, String raw) {
        return new ApiResponse<>(true, status, data, raw, null, null);
    }

    public static <T> ApiResponse<T> failure(int status, String raw, String errorMessage) {
        return new ApiResponse<>(false, status, null, raw, errorMessage, null);
    }

    public static <T> ApiResponse<T> error(Exception e) {
        return new ApiResponse<>(false, -1, null, null, e.getMessage(), e);
    }
}
