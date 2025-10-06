package adapters;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.create.CreateCaseRq;
import models.create.CreateCaseRs;
import org.apache.logging.log4j.ThreadContext;

@Log4j2
public class CaseClient {

    private final ApiClient api;

    public CaseClient() {
        this.api = ApiClient.getInstance();
    }

    @Step("Add case to project {project}")
    public ApiResponse<CreateCaseRs> addCase(String project, CreateCaseRq rq) {
        ThreadContext.put("requestId", "addCase-" + project + "-" + System.currentTimeMillis());
        log.info("Adding case to project {} with body: {}", project, rq);
        try {
            return api.post("/v1/case/" + project, rq, CreateCaseRs.class);
        } finally {
            ThreadContext.remove("requestId");
        }
    }
}
