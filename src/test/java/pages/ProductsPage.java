package pages;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static data.Elements.CREATE_NEW_PROJECT_BTN;

public class ProductsPage extends BasePage {

    private final String PROJECTS_URL = "/projects";
    private final String REMOVE_BTN = "[data-testid=remove]";
    private final String DELETE_PROJECTS_BTN = "//span[text()='Delete project']";
    private final String OPEN_ACTION_MENU_BTN = "button[aria-label='Open action menu']";

    @Step("Открытие страницы проектов")
    public ProductsPage openPage() {
        open(PROJECTS_URL);
        return this;
    }

    public ProductsPage waittingOpen() {
        CREATE_NEW_PROJECT_BTN.shouldBe(visible, Duration.ofSeconds(90));//с помощью селенида цепляемся за текст, а не локатор
        return this;
    }

    @Step("Удаление проекта")
    public ProductsPage deleteProject(String project) {
        $(byText(project))
                .ancestor("tr")
                .find(OPEN_ACTION_MENU_BTN)
                .click();
        $(REMOVE_BTN).click();
        $x(DELETE_PROJECTS_BTN).click();
        return this;
    }
}

