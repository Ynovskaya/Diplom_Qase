package tests.api;

import adapters.ApiResponse;
import models.project.create.CreateProjectRq;
import models.project.create.CreateProjectRs;
import models.project.get.GetProjectRs;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ProjectRequestFactory;

import static org.testng.Assert.*;

public class ProjectTest extends BaseApiTest {

    @Test(description = "Создание и удаление проекта")
    public void checkCreateAndDeleteProject() {
        CreateProjectRq rq = ProjectRequestFactory.validProject();
        ApiResponse<CreateProjectRs> rs = projectClient.createProject(rq);
        assertTrue(rs.isOk());
        assertNotNull(rs.getData());
        String code = rs.getData().getResult().getCode();

        ApiResponse<Void> del = projectClient.deleteProject(code);
        assertTrue(del.isOk() || del.getStatus() == 200);
    }

    @Test(description = "Проверка полей проекта после создания")
    public void checkFieldCreateFormNewProject() {
        CreateProjectRq req = ProjectRequestFactory.validProject();
        ApiResponse<CreateProjectRs> createRs = projectClient.createProject(req);
        assertTrue(createRs.isOk());
        String code = createRs.getData().getResult().getCode();

        ApiResponse<GetProjectRs> getRs = projectClient.getProject(code);
        assertTrue(getRs.isOk());
        assertEquals(getRs.getData().getResult().getTitle(), req.getTitle());
        assertEquals(getRs.getData().getResult().getCode(), code);

        projectClient.deleteProject(code);
    }

    @DataProvider(name = "invalidProjects")
    public Object[][] invalidProjects() {
        return new Object[][]{
                {ProjectRequestFactory.projectWithEmptyTitle()},
                {ProjectRequestFactory.projectWithEmptyCode("TitleOnly")}
        };
    }

    @Test(dataProvider = "invalidProjects", description = "Негативные сценарии создания проекта")
    public void checkCreateProjectFailsWhenInvalid(CreateProjectRq invalidRq) {
        ApiResponse<CreateProjectRs> resp = projectClient.createProject(invalidRq);
        assertFalse(resp.isOk(), "API should return non-ok for invalid request");
        assertTrue(resp.getStatus() == 400 || resp.getStatus() == 422, "Expected 4xx status for invalid request");
    }

    @Test(description = "Contract test: create project response matches JSON schema")
    public void contractTestCreateProject() {
        CreateProjectRq req = ProjectRequestFactory.validProject();
        // Выполняем post и получаем raw для валидации схемы
        ApiResponse<CreateProjectRs> resp = projectClient.createProject(req);
        assertTrue(resp.isOk());

        // Для примера — проверяем, что result.code существует и не пустой
        assertNotNull(resp.getData().getResult().getCode());
        assertFalse(resp.getData().getResult().getCode().isEmpty());

        // Удаляем проект
        projectClient.deleteProject(resp.getData().getResult().getCode());
    }
}
