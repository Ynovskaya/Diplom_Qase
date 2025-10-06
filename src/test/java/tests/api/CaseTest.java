package tests.api;

import adapters.ApiResponse;
import adapters.CaseClient;
import adapters.ProjectClient;
import models.create.CreateCaseRs;
import models.project.create.CreateProjectRq;
import models.project.create.CreateProjectRs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.CaseRequestFactory;
import utils.ProjectRequestFactory;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

public class CaseTest extends BaseApiTest {

    private CaseClient caseClient;

    @BeforeClass(alwaysRun = true)
    public void setupClients() {
        caseClient = new CaseClient();
    }

    @Test(groups = "smoke", description = "Add case to created project via API")
    public void addCaseToProject() {
        CreateProjectRq project = ProjectRequestFactory.validProject();
        ApiResponse<CreateProjectRs> createResp = projectClient.createProject(project);
        assertTrue(createResp.isOk(), "Project creation must succeed");

        String code = createResp.getData().getResult().getCode();
        ApiResponse<CreateCaseRs> caseResp = caseClient.addCase(code, CaseRequestFactory.caseRq());
        assertTrue(caseResp.isOk(), "Case creation must succeed");
        assertNotNull(caseResp.getData().getResult().getId(), "Created case id should not be null");
    }
}
