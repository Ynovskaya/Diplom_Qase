package dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO для передачи данных тест-кейса в UI-слое.
 */
@Data
@Builder
public class TestCaseDto {
    private String title;
    private String status;
    private String description;
    private String suite;
    private String severity;
    private String priority;
    private String type;
    private String layer;
    private boolean flaky;
    private String behavior;
    private String automationStatus;
    private String preconditions;
    private String postconditions;
    private String tags;
    private String attachments;
    private String[][] steps;
}
