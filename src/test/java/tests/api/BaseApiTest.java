package tests.api;

import adapters.ApiClient;
import adapters.ProjectClient;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected static ProjectClient projectClient;

    @BeforeClass(alwaysRun = true)
    public void init() {
        // Инициализируем ApiClient из пропертей
        ApiClient.initFromProperties();
        projectClient = new ProjectClient();

        // Добавим базовый контекст
        ThreadContext.put("testId", this.getClass().getSimpleName() + "-" + System.currentTimeMillis());
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        // Очистка данных
        projectClient.deleteAllProjects();
        ThreadContext.clearAll();
    }
}
