package utils;

import models.project.create.ProjectCreateRequest;

/**
 * Фабрика проектов (тела запросов).
 */
public class ProjectFactory {

    public static ProjectCreateRequest validProject() {
        return ProjectCreateRequest.builder()
                .title("TMSAPI")
                .code("API")
                .description("test")
                .group("all")
                .access("all")
                .build();
    }

    public static ProjectCreateRequest projectWithEmptyTitle() {
        return ProjectCreateRequest.builder()
                .title("")
                .code("API")
                .description("Project created via API with empty title")
                .group("all")
                .access("all")
                .build();
    }

    public static ProjectCreateRequest projectWithEmptyCode(String title) {
        return ProjectCreateRequest.builder()
                .title(title)
                .code("")
                .description("Project created via API with empty code")
                .group("all")
                .access("all")
                .build();
    }
}
