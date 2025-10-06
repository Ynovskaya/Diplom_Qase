package data;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Elements {

    public static SelenideElement CREATE_PROJECT_BTN = $(byText("Create project"));
    public static SelenideElement CREATE_NEW_PROJECT_BTN = $(byText("Create new project"));
    public static SelenideElement NEW_TEST_BTN = $(byText("New test"));
    public static String NAME_PROJECT = "TMS";
    public static String TITLE_CASE_TXT = "Title";
    public static String VALIDATION_MESSAGE = "validationMessage";
    public static String ERROR_MESSAGE = "Заполните это поле.";
}
