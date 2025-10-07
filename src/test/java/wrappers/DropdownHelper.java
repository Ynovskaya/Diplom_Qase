package wrappers;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class DropdownHelper {

    public static void selectFromCustomDropdown(String dropdownPattern,
                                                String label,
                                                String optionPattern,
                                                String optionText) {
        String dropdownXpath = String.format(dropdownPattern, label);
        $(byXpath(dropdownXpath)).click();

        String optionXpath = String.format(optionPattern, optionText);
        $(byXpath(optionXpath)).click();
    }
}
