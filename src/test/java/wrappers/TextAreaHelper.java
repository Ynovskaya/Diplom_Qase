package wrappers;

import static com.codeborne.selenide.Selenide.$;

/**
 * Небольшой helper для работы с текстовыми областями.
 */
public class TextAreaHelper {

    public void setText(String locator, String value) {
        $(locator).setValue(value);
    }
}
