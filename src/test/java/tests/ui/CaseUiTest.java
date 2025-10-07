package tests.ui;

import dto.TestCaseDto;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utils.CaseFactory;

import static data.UiElements.SAMPLE_PROJECT_NAME;

public class CaseUiTest extends UiBaseTest {

    @Test(groups = "smoke")
    @Description("Create test case in UI")
    public void createCaseUi() {
        TestCaseDto tc = CaseFactory.uiDto();
        loginPage.openPage()
                .login(user, password);
        productsPage.waitForOpen();
        projectModal.createProject(SAMPLE_PROJECT_NAME);
        casePage.openAndSave(tc);
        projectPage.openRepository(SAMPLE_PROJECT_NAME);
        casePage.assertOneCasePresent();
    }
}
