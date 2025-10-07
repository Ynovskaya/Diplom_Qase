package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.*;
import tests.TestListener;
import utils.PropertyReader;

import java.io.ByteArrayInputStream;

import static adapters.ProjectService.purgeAllProjects;
import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Общие настройки для UI-тестов: browser config, listeners, lifecycle hooks.
 */
@Log4j2
@Listeners(TestListener.class)
public class UiBaseTest {
    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected ProjectPage projectPage;
    protected ProjectModalPage projectModal;
    protected CasePage casePage;

    protected final String user = System.getProperty("user", PropertyReader.getProperty("user"));
    protected final String password = System.getProperty("password", PropertyReader.getProperty("password"));

    @BeforeSuite(alwaysRun = true)
    public void beforeSuiteCleanup() {
        log.info("Suite start - cleaning projects");
        purgeAllProjects();
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        log.info("Launching browser: {}", browser);
        if ("chrome".equalsIgnoreCase(browser)) {
            Configuration.browser = "chrome";
        } else if ("firefox".equalsIgnoreCase(browser)) {
            Configuration.browser = "firefox";
        } else {
            throw new IllegalArgumentException("Unknown browser: " + browser);
        }

        Configuration.baseUrl = "https://app.qase.io";
        Configuration.timeout = 5000;
        Configuration.clickViaJs = true;
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );

        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        projectPage = new ProjectPage();
        projectModal = new ProjectModalPage();
        casePage = new CasePage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        log.info("Closing browser and post-cleanup");

        if (!result.isSuccess()) {
            try {
                byte[] screenshot = ((TakesScreenshot) com.codeborne.selenide.WebDriverRunner.getWebDriver())
                        .getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));
                log.error("Test failed: {} — screenshot attached", result.getName());
            } catch (Exception e) {
                log.error("Failed to capture screenshot: {}", e.getMessage());
            }
        }

        closeWebDriver();
        purgeAllProjects();
    }
}
