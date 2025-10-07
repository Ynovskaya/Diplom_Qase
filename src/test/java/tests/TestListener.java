package tests;

import lombok.extern.log4j.Log4j2;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

/**
 * Простой listener для логирования упавших тестов с длительностью.
 */
@Log4j2
public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.printf("==== FAILED: %s Duration: %ss ===%n",
                result.getName(), getExecutionSeconds(result));
    }

    private long getExecutionSeconds(ITestResult result) {
        return TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
    }
}
