package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import tests.Retry;

import static com.codeborne.selenide.Selenide.$;
import static data.Elements.NAME_PROJECT;
import static data.Elements.VALIDATION_MESSAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pages.ModalCreateProjectPage.PROJECT_NAME_FIELD_CSS;

@Feature("Проект ")
@Story("Создание проекта")
public class ProjectTest extends BaseTest {

    @Test(retryAnalyzer = Retry.class, groups = "smoke")
    @Description("Создание проекта и его удаление")
    public void checkCreateProject() {
        loginPage.openPage()
                .login(user, password);
        productsPage.waittingOpen();
        modalCreateProjectPage.createProject(NAME_PROJECT);
        projectPage.checkCreatingProject(NAME_PROJECT);
        productsPage.openPage()
                .waittingOpen()
                .deleteProject(NAME_PROJECT);
    }

    @Test
    @Description("Создание проекта без названия: проверка ошибки")
    public void checkCreateProjectWithNegative() {
        loginPage.openPage()
                .login(user, password);
        productsPage.waittingOpen();
        modalCreateProjectPage.createFailProject();

        String validationMessage = $(PROJECT_NAME_FIELD_CSS).getAttribute(VALIDATION_MESSAGE);
        assertThat(validationMessage)
                .as("Проверка сообщения о незаполненном поле")
                .matches("(?i)(Заполните это поле\\.|Please fill out this field\\.)");
    }
}
