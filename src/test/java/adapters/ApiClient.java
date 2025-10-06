package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import utils.PropertyReader;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@Log4j2
public final class ApiClient {

    private static ApiClient instance;

    private final String baseUrl;
    private final String token;
    private final int retries;
    private final long retryDelayMs;
    private final Gson gson;

    private ApiClient(String baseUrl, String token, int retries, long retryDelayMs) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.token = token;
        this.retries = retries;
        this.retryDelayMs = retryDelayMs;
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public static synchronized ApiClient initFromProperties() {
        if (instance == null) {
            String base = System.getProperty("api.base", PropertyReader.getProperty("api.base"));
            String token = System.getProperty("token", PropertyReader.getProperty("token"));
            int retries = Integer.parseInt(System.getProperty("api.retries",
                    PropertyReader.getProperty("api.retries", "2")));
            long retryDelay = Long.parseLong(System.getProperty("api.retry.delay.ms",
                    PropertyReader.getProperty("api.retry.delay.ms", "500")));
            instance = new ApiClient(base, token, retries, retryDelay);
        }
        return instance;
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) throw new IllegalStateException("ApiClient is not initialized. Call initFromProperties() first.");
        return instance;
    }

    public Gson gson() { return gson; }

    private <T> ApiResponse<T> executeWithRetry(Supplier<Response> requestSupplier, Function<Response, ApiResponse<T>> onResponse) {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        try {
            int attempt = 0;
            while (true) {
                attempt++;
                try {
                    log.info("API call attempt {} requestId={}", attempt, requestId);
                    Response resp = requestSupplier.get();
                    ApiResponse<T> result = onResponse.apply(resp);
                    if (result.isOk() || attempt >= retries + 1) {
                        return result;
                    } else {
                        log.warn("Attempt {} failed with status {}, will retry (requestId={})", attempt, result.getStatus(), requestId);
                    }
                } catch (Exception ex) {
                    log.error("Exception during API call attempt {} requestId={} : {}", attempt, requestId, ex.toString());
                    if (attempt >= retries + 1) return ApiResponse.error(ex);
                }
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ignored) {
                }
            }
        } finally {
            ThreadContext.remove("requestId");
        }
    }

    // POST generic
    public <T> ApiResponse<T> post(String path, Object body, Class<T> responseClass) {
        String url = baseUrl + path;
        log.info("POST {}", url);
        return executeWithRetry(
                () -> given()
                        .contentType(JSON)
                        .header("Token", token)
                        .body(gson.toJson(body))
                        .log().ifValidationFails()
                        .when()
                        .post(url),
                resp -> {
                    String raw = resp.getBody() != null ? resp.getBody().asString() : null;
                    log.debug("Response (status={}): {}", resp.getStatusCode(), raw);
                    if (resp.getStatusCode() >= 200 && resp.getStatusCode() < 300) {
                        if (responseClass == Void.class) {
                            return ApiResponse.success(resp.getStatusCode(), null, raw);
                        }
                        T data = gson.fromJson(raw, responseClass);
                        return ApiResponse.success(resp.getStatusCode(), data, raw);
                    } else {
                        return ApiResponse.failure(resp.getStatusCode(), raw, "HTTP " + resp.getStatusCode());
                    }
                }
        );
    }

    // GET generic
    public <T> ApiResponse<T> get(String path, Class<T> responseClass) {
        String url = baseUrl + path;
        log.info("GET {}", url);
        return executeWithRetry(
                () -> given()
                        .contentType(JSON)
                        .header("Token", token)
                        .log().ifValidationFails()
                        .when()
                        .get(url),
                resp -> {
                    String raw = resp.getBody() != null ? resp.getBody().asString() : null;
                    log.debug("Response (status={}): {}", resp.getStatusCode(), raw);
                    if (resp.getStatusCode() >= 200 && resp.getStatusCode() < 300) {
                        if (responseClass == Void.class) {
                            return ApiResponse.success(resp.getStatusCode(), null, raw);
                        }
                        T data = gson.fromJson(raw, responseClass);
                        return ApiResponse.success(resp.getStatusCode(), data, raw);
                    } else {
                        return ApiResponse.failure(resp.getStatusCode(), raw, "HTTP " + resp.getStatusCode());
                    }
                }
        );
    }

    // DELETE generic
    public <T> ApiResponse<T> delete(String path, Class<T> responseClass) {
        String url = baseUrl + path;
        log.info("DELETE {}", url);
        return executeWithRetry(
                () -> given()
                        .contentType(JSON)
                        .header("Token", token)
                        .log().ifValidationFails()
                        .when()
                        .delete(url),
                resp -> {
                    String raw = resp.getBody() != null ? resp.getBody().asString() : null;
                    log.debug("Response (status={}): {}", resp.getStatusCode(), raw);
                    if (resp.getStatusCode() >= 200 && resp.getStatusCode() < 300) {
                        if (responseClass == Void.class) {
                            return ApiResponse.success(resp.getStatusCode(), null, raw);
                        }
                        T data = gson.fromJson(raw, responseClass);
                        return ApiResponse.success(resp.getStatusCode(), data, raw);
                    } else {
                        return ApiResponse.failure(resp.getStatusCode(), raw, "HTTP " + resp.getStatusCode());
                    }
                }
        );
    }
}
