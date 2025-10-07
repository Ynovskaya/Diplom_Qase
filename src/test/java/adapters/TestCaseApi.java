package adapters;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.create.CaseCreateRequest;
import models.create.CaseCreateResponse;

/**
 * Работа с endpoint'ом /case.
 * Обёртка над ApiBase для создания кейсов.
 */
@Log4j2
public class TestCaseApi extends ApiBase {

    @Step("Create case via API")
    public static CaseCreateResponse createCase(String projectCode, CaseCreateRequest body) {
        log.info("POST /case/{} body: {}", projectCode, body);
        return requestSpec()
                .contentType("application/json")
                .body(GSON.toJson(body))
                .when()
                .post("https://api.qase.io/v1/case/" + projectCode)
                .then()
                .log().all()
                .extract()
                .as(CaseCreateResponse.class);
    }
}
