package tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer: повторяет падающий тест до 3 раз.
 */
public class Retry implements IRetryAnalyzer {

    private int attempts = 1;
    private static final int MAX = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (attempts < MAX) {
                attempts++;
                result.setStatus(ITestResult.FAILURE);
                System.out.println("Retrying test — attempt " + attempts);
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
        } else {
            result.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
