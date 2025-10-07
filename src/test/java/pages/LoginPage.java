package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница логина.
 */
public class LoginPage extends BasePage {
    private final String EMAIL_FIELD = "[name=email]";
    private final String PASSWORD_FIELD = "[name='password']";
    private final String SMALL_ERROR = "//small";

    @Step("Open login page")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Authorize user")
    public ProductsPage login(String user, String password) {
        $(EMAIL_FIELD).shouldBe(visible).setValue(user);
        $(PASSWORD_FIELD).setValue(password).submit();
        return new ProductsPage();
    }

    public String fetchErrorMessage() {
        return $x(SMALL_ERROR).text();
    }
}
