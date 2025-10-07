package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import tests.Retry;

import static com.codeborne.selenide.Selenide.$;
import static data.UiElements.SAMPLE_PROJECT_NAME;
import static data.UiElements.HTML_VALIDATION_ATTR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pages.ProjectModalPage.INPUT_PROJECT_NAME;

/**
 * UI-тесты, связанные с проектом (создание/валидация).
 */
@Feature("Project")
@Story("Create project flows")
public class ProjectUiTest extends UiBaseTest {

    @Test(retryAnalyzer = Retry.class, groups = "smoke")
    @Description("Create and remove project")
    public void createAndRemoveProject() {
        loginPage.openPage()
                .login(user, password);
        productsPage.waitForOpen();
        projectModal.createProject(SAMPLE_PROJECT_NAME);
        projectPage.assertProjectVisible(SAMPLE_PROJECT_NAME);
        productsPage.openPage()
                .waitForOpen()
                .deleteProject(SAMPLE_PROJECT_NAME);
    }

    @Test
    @Description("Validation when creating project without name")
    public void createProjectValidation() {
        loginPage.openPage()
                .login(user, password);
        productsPage.waitForOpen();
        projectModal.createProjectInvalid();

        String validationMessage = $(INPUT_PROJECT_NAME).getAttribute(HTML_VALIDATION_ATTR);
        assertThat(validationMessage)
                .as("Check empty name field message")
                .matches("(?i)(Заполните это поле\\.|Please fill out this field\\.)");
    }
}
