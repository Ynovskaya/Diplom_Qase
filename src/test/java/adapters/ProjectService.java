package adapters;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import models.project.create.ProjectCreateRequest;
import models.project.create.ProjectCreateResponse;
import models.project.get.ProjectDetailsResponse;
import io.restassured.path.json.JsonPath;

import java.util.List;

/**
 * API-операции: создание, удаление, получение.
 */

@Log4j2
public class ProjectService extends ApiBase {

    @Step("Create project")
    public static ProjectCreateResponse createProject(ProjectCreateRequest request) {
        log.info("Creating project: {}", request);
        return requestSpec()
                .body(GSON.toJson(request))
                .when()
                .post("https://api.qase.io/v1/project")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(ProjectCreateResponse.class);
    }

    @Step("Delete projects by codes")
    public static void removeProjects(String... codes) {
        for (String code : codes) {
            log.info("Deleting project: {}", code);
            requestSpec()
                    .when()
                    .delete("https://api.qase.io/v1/project/" + code)
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

    @Step("Get project by code")
    public static ProjectDetailsResponse fetchProject(String code) {
        log.info("Fetching project: {}", code);
        return requestSpec()
                .when()
                .get("https://api.qase.io/v1/project/" + code)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(ProjectDetailsResponse.class);
    }

    @Step("Create project and return raw response (for validation checks)")
    public static Response createProjectRaw(ProjectCreateRequest request) {
        log.info("createProjectRaw body: {}", request);
        return requestSpec()
                .body(request) // allow rest-assured to serialize
                .when()
                .post("https://api.qase.io/v1/project")
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("List project codes")
    public static List<String> listProjectCodes() {
        log.info("Listing projects (limit=10)");
        String raw = requestSpec()
                .when()
                .get("https://api.qase.io/v1/project?limit=10&offset=0")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .asString();

        JsonPath jp = new JsonPath(raw);
        return jp.getList("result.entities.code");
    }

    @Step("Delete all available projects (helper)")
    public static ProjectService purgeAllProjects() {
        log.info("Deleting all discovered projects");
        listProjectCodes().forEach(ProjectService::removeProjects);
        return new ProjectService();
    }
}
