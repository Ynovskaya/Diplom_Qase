package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.TestCaseDto;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static data.UiElements.BUTTON_NEW_TEST;
import static data.UiElements.CASE_TITLE_LABEL;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static wrappers.DropdownHelper.selectFromCustomDropdown;

/**
 * Страница создания/просмотра тест-кейсов.
 * Содержит методы для заполнения формы, добавления шагов и проверки.
 */
@Log4j2
public class CasePage extends BasePage {
    private static final String SUITE_AREA_SELECTOR = "[data-suite-body-id]";
    private static final String CASE_LIST_ITEM = SUITE_AREA_SELECTOR + "[draggable]";
    private final String TITLE_INPUT = "input[name='title']";
    private final String DESCRIPTION_FIELD = ".toastui-editor-ww-container .ProseMirror-trailingBreak:first-child";
    private final String DROPDOWN_BY_LABEL = "//label[text()='%s']/following-sibling::div//span";
    private final String DROPDOWN_OPTION = "//div[text()='%s']";
    private final String BUTTON_SAVE = "Save";
    private final ProjectPage projectPage = new ProjectPage();

    public CasePage ensureCreateFormVisible() {
        BUTTON_NEW_TEST.shouldBe(visible, Duration.ofSeconds(60)).click();
        $(byText(CASE_TITLE_LABEL)).shouldBe(visible);
        return new CasePage();
    }

    public void fillForm(TestCaseDto tc) {
        $(TITLE_INPUT).append(tc.getTitle());
        $(DESCRIPTION_FIELD).append(tc.getDescription());

        selectFromCustomDropdown(DROPDOWN_BY_LABEL, "Status", DROPDOWN_OPTION, tc.getStatus());
        selectFromCustomDropdown(DROPDOWN_BY_LABEL, "Severity", DROPDOWN_OPTION, tc.getSeverity());
        selectFromCustomDropdown(DROPDOWN_BY_LABEL, "Type", DROPDOWN_OPTION, tc.getType());
        selectFromCustomDropdown(DROPDOWN_BY_LABEL, "Priority", DROPDOWN_OPTION, tc.getPriority());

        log.info("Filled test case form: {}", tc);
    }

    @Step("Open and save created case")
    public CasePage openAndSave(TestCaseDto tc) {
        ensureCreateFormVisible();
        fillForm(tc);
        $(byText(BUTTON_SAVE)).click();
        log.info("Saved test case");
        return new CasePage();
    }

    @Step("Add steps to case")
    public CasePage addSteps() {
        $(byText("Add step")).click();
        List<String> sample = List.of("Step-text", "Data-text", "Expected Result-text");

        ElementsCollection containers = $$(".Es6DHW.gMuCDP.Hm_zG7.eZv6zA.wysiwyg.dcuUk8")
                .shouldHave(CollectionCondition.size(3));

        for (int i = 0; i < containers.size(); i++) {
            SelenideElement container = containers.get(i);
            SelenideElement target;

            if (i == 0) {
                target = container.$("div[contenteditable='true']")
                        .shouldBe(visible, Duration.ofSeconds(10));
                target.click();
            } else {
                target = container.$(".toastui-editor-contents")
                        .shouldBe(visible);
            }

            // очистка через JS и ввод
            executeJavaScript("arguments[0].innerHTML = '';", target);
            target.append(sample.get(i));
        }

        return this;
    }

    public int countCases() {
        return $$(CASE_LIST_ITEM).size();
    }

    public CasePage assertOneCasePresent() {
        assertEquals(countCases(), 1, "Expected exactly 1 test case");
        return this;
    }

    public CasePage assertCaseInSuite(String suite, String caseName) {
        assertTrue(projectPage.doesCaseBelongToSuite(suite, caseName),
                "Case not found in expected suite");
        return this;
    }
}
