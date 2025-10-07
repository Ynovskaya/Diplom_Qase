package pages;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static data.UiElements.BUTTON_CREATE_PROJECT_MODAL;

/**
 * Страница со списком проектов (Products).
 */
public class ProductsPage extends BasePage {

    private final String PAGE_URL = "/projects";
    private final String REMOVE_BUTTON = "[data-testid=remove]";
    private final String DELETE_SPAN = "//span[text()='Delete project']";
    private final String OPEN_ACTION = "button[aria-label='Open action menu']";

    @Step("Open projects page")
    public ProductsPage openPage() {
        open(PAGE_URL);
        return this;
    }

    public ProductsPage waitForOpen() {
        BUTTON_CREATE_PROJECT_MODAL.shouldBe(visible, Duration.ofSeconds(90));
        return this;
    }

    @Step("Delete project by name")
    public ProductsPage deleteProject(String projectName) {
        $(byText(projectName))
                .ancestor("tr")
                .find(OPEN_ACTION)
                .click();
        $(REMOVE_BUTTON).click();
        $x(DELETE_SPAN).click();
        return this;
    }
}
