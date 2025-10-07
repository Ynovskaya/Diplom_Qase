package tests.api;

import io.qameta.allure.Step;
import org.testng.annotations.Test;
import utils.CaseFactory;
import utils.ProjectFactory;

import static adapters.TestCaseApi.createCase;
import static adapters.ProjectService.createProject;
import models.create.CaseCreateRequest;

/**
 * API-тесты, связанные с кейсами
 */
public class CaseApiTest extends ApiTestBase {

    @Test(groups = "smoke")
    @Step("Add case to project via API")
    public void addCaseToProject() {
        createProject(ProjectFactory.validProject());
        String code = ProjectFactory.validProject().getCode();
        CaseCreateRequest req = CaseFactory.caseRequest();
        createCase(code, req);
    }
}
