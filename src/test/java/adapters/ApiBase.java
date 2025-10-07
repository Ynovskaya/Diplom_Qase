package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;

/**
 * Поддерживающий базовый класс для API-запросов.
 * Содержит общие настройки сериализации и шаблон для запросов.
 */
public class ApiBase {
    // Gson: конфигурация так, чтобы сериализовать только помеченные поля
    public static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    // Токен читается либо из системного свойства, либо из config.properties
    private static final String AUTH_TOKEN = System.getProperty("token", PropertyReader.getProperty("token"));

    /**
     * Возвращает шаблон запроса (RequestSpecification) с общими заголовками/настройками.
     */
    public static RequestSpecification requestSpec() {
        return given()
                .contentType(ContentType.JSON)
                .header("Token", AUTH_TOKEN)
                .log().all();
    }
}
