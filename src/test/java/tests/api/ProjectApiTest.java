package tests.api;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.project.create.ProjectCreateResponse;
import models.project.get.ProjectDetailsResponse;
import models.project.get.ProjectsListResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ProjectFactory;

import static adapters.ProjectService.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

/**
 * Тесты для операций с проектами (API).
 */
public class ProjectApiTest extends ApiTestBase {

    @BeforeMethod
    public void cleanup() {
        purgeAllProjects();
    }

    @Test
    @Step("Create and delete project")
    public void createAndRemoveProject() {
        ProjectCreateResponse resp = createProject(ProjectFactory.validProject());
        assertTrue(resp.status);
        String code = resp.getResult().getCode();
        removeProjects(code);
    }

    @Test(groups = "smoke")
    @Step("Verify created project's fields")
    public void verifyProjectFieldsAfterCreate() {
        ProjectCreateResponse resp = createProject(ProjectFactory.validProject());
        String code = resp.getResult().getCode();

        ProjectDetailsResponse details = fetchProject(code);

        assertThat(details.getResult().getTitle())
                .isEqualTo(ProjectFactory.validProject().getTitle());

        assertThat(details.getResult().getCode()).isEqualTo(code);
        removeProjects(code);
    }

    @Test
    @Step("Negative: create project without title")
    public void createProjectMissingTitle() {
        Response response = createProjectRaw(ProjectFactory.projectWithEmptyTitle());
        assertThat(response.getStatusCode()).isEqualTo(400);

        JsonPath jp = response.jsonPath();
        assertThat(jp.getBoolean("status")).isFalse();
    }
}
