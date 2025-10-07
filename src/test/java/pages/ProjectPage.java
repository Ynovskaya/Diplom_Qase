package pages;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static data.UiElements.BUTTON_NEW_TEST;

/**
 * Страница проекта (репозиторий проекта).
 */
public class ProjectPage extends BasePage {
    private static final String TEST_XPATH_TEMPLATE = "//*[text()='%s']//ancestor::*[@data-suite-body-id]/ancestor::*[@class][1]/following-sibling::*[1]//*[text()='%s']";
    private static final String PROJECT_PATH = "/project";

    @Step("Check created project visible")
    public ProjectPage assertProjectVisible(String name) {
        BUTTON_NEW_TEST.shouldBe(visible, Duration.ofSeconds(60));
        $(byText(name)).shouldBe(visible);
        return this;
    }

    @Step("Open project repository")
    public ProjectPage openRepository(String name) {
        open(PROJECT_PATH + "/" + name);
        BUTTON_NEW_TEST.shouldBe(visible, Duration.ofSeconds(60));
        return this;
    }

    public boolean doesCaseBelongToSuite(String suite, String caseName) {
        return $x(String.format(TEST_XPATH_TEMPLATE, suite, caseName)).exists();
    }
}
