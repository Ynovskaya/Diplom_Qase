package utils;

import models.project.create.CreateProjectRq;

public class ProjectRequestFactory {

    public static CreateProjectRq validProject() {
        return CreateProjectRq.builder()
                .title("TMSAPI-" + System.currentTimeMillis())
                .code("API" + (System.currentTimeMillis() % 10000))
                .description("test")
                .group("all")
                .access("all")
                .build();
    }

    public static CreateProjectRq projectWithEmptyTitle() {
        return CreateProjectRq.builder()
                .title("")
                .code("API" + (System.currentTimeMillis() % 10000))
                .description("Project created via API with empty title")
                .group("all")
                .access("all")
                .build();
    }

    public static CreateProjectRq projectWithEmptyCode(String title) {
        return CreateProjectRq.builder()
                .title(title)
                .code("")
                .description("Project created via API with empty code")
                .group("all")
                .access("all")
                .build();
    }
}
