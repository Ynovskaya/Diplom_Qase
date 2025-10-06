package adapters;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.project.create.CreateProjectRq;
import models.project.create.CreateProjectRs;
import models.project.get.GetProjectRs;
import models.project.get.GetProjectsRs;
import org.apache.logging.log4j.ThreadContext;
import io.restassured.path.json.JsonPath;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ProjectClient {

    private final ApiClient api;

    public ProjectClient() {
        this.api = ApiClient.getInstance();
    }

    @Step("Create project via API")
    public ApiResponse<CreateProjectRs> createProject(CreateProjectRq project) {
        ThreadContext.put("requestId", "createProject-" + System.currentTimeMillis());
        log.info("Creating project: {}", project);
        try {
            return api.post("/v1/project", project, CreateProjectRs.class);
        } finally {
            ThreadContext.remove("requestId");
        }
    }

    @Step("Delete projects by codes")
    public ApiResponse<Void> deleteProject(String code) {
        ThreadContext.put("requestId", "deleteProject-" + code);
        log.info("Deleting project with code: {}", code);
        try {
            return api.delete("/v1/project/" + code, Void.class);
        } finally {
            ThreadContext.remove("requestId");
        }
    }

    @Step("Get project by code")
    public ApiResponse<GetProjectRs> getProject(String code) {
        ThreadContext.put("requestId", "getProject-" + code);
        log.info("Getting project with code: {}", code);
        try {
            return api.get("/v1/project/" + code, GetProjectRs.class);
        } finally {
            ThreadContext.remove("requestId");
        }
    }

    @Step("Get all project codes")
    public ApiResponse<List<String>> getAllProjectCodes() {
        ThreadContext.put("requestId", "getAllProjects-" + System.currentTimeMillis());
        log.info("Retrieving all projects codes");
        try {
            ApiResponse<String> raw = api.get("/v1/project?limit=100&offset=0", String.class);
            if (!raw.isOk()) return ApiResponse.failure(raw.getStatus(), raw.getRaw(), raw.getErrorMessage());
            JsonPath json = new JsonPath(raw.getData());
            List<String> codes = json.getList("result.entities.code");
            return ApiResponse.success(raw.getStatus(), codes, raw.getRaw());
        } finally {
            ThreadContext.remove("requestId");
        }
    }

    @Step("Delete all projects")
    public void deleteAllProjects() {
        ThreadContext.put("requestId", "deleteAllProjects-" + System.currentTimeMillis());
        log.info("Deleting all projects");
        try {
            ApiResponse<List<String>> codesResp = getAllProjectCodes();
            if (codesResp.isOk() && codesResp.getData() != null) {
                codesResp.getData().forEach(code -> {
                    ApiResponse<Void> del = deleteProject(code);
                    if (!del.isOk()) {
                        log.warn("Failed to delete project {} status={}", code, del.getStatus());
                    }
                });
            }
        } finally {
            ThreadContext.remove("requestId");
        }
    }
}
