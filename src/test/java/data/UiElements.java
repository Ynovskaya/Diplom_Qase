package data;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

/**
 * UI-локаторы и тестовые константы.
 * Здесь храним общие элементы страницы и тексты, используемые в тестах.
 */
public final class UiElements {

    public static final SelenideElement BUTTON_CREATE_PROJECT = $(byText("Create project"));
    public static final SelenideElement BUTTON_CREATE_PROJECT_MODAL = $(byText("Create new project"));
    public static final SelenideElement BUTTON_NEW_TEST = $(byText("New test"));

    public static final String SAMPLE_PROJECT_NAME = "TMS";
    public static final String CASE_TITLE_LABEL = "Title";
    public static final String HTML_VALIDATION_ATTR = "validationMessage";

    // Сообщение в двух языковых вариациях
    public static final String EMPTY_FIELD_MESSAGE = "Заполните это поле.";
}
