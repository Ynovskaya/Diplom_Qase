package pages;

import io.qameta.allure.Step;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static data.UiElements.BUTTON_CREATE_PROJECT_MODAL;
import static data.UiElements.BUTTON_CREATE_PROJECT;

/**
 * Модальное окно создания проекта.
 */
@Data
@Log4j2
public class ProjectModalPage extends BasePage {

    public static final String INPUT_PROJECT_NAME = "#project-name";
    private String radioPublic = "input[value='public']";

    public ProjectModalPage openModal() {
        BUTTON_CREATE_PROJECT.shouldBe(visible);
        return this;
    }

    @Step("Fill and submit create project modal")
    public ProductsPage createProject(String name) {
        log.info("Creating project: {}", name);
        BUTTON_CREATE_PROJECT_MODAL.click();
        $(INPUT_PROJECT_NAME).setValue(name);
        $(radioPublic).click();
        $(radioPublic).shouldBe(selected);
        BUTTON_CREATE_PROJECT.click();
        return new ProductsPage();
    }

    @Step("Attempt to create project without name")
    public ProjectModalPage createProjectInvalid() {
        BUTTON_CREATE_PROJECT_MODAL.click();
        $(radioPublic).click();
        $(radioPublic).shouldBe(selected);
        BUTTON_CREATE_PROJECT.click();
        return this;
    }
}
