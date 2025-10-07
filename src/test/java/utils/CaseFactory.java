package utils;

import com.github.javafaker.Faker;
import dto.TestCaseDto;
import models.create.CaseCreateRequest;

import java.util.List;
import java.util.Random;

/**
 * Фабрика тестовых данных для кейсов (API и UI).
 */
public class CaseFactory {

    private static final Faker FAKER = new Faker();
    private static final Random RAND = new Random();

    /**
     * API request object factory
     */
    public static CaseCreateRequest caseRequest() {
        return CaseCreateRequest.builder()
                .title(FAKER.book().title())
                .description(FAKER.lorem().sentence())
                .priority(2)
                .severity(1)
                .type(3)
                .steps(List.of(
                        CaseCreateRequest.Step.builder()
                                .action("Open website")
                                .expectedResult("Site opened")
                                .build(),
                        CaseCreateRequest.Step.builder()
                                .action("Authenticate")
                                .expectedResult("Success")
                                .build()
                ))
                .build();
    }

    /**
     * UI DTO factory
     */
    public static TestCaseDto uiDto() {
        return TestCaseDto.builder()
                .title(FAKER.book().title())
                .description(FAKER.lorem().sentence())
                .status("Actual")
                .severity("Major")
                .priority("Medium")
                .type("Functional")
                .build();
    }

    /**
     * Randomly returns API or UI object
     */
    public static Object randomCase() {
        if (RAND.nextBoolean()) {
            return caseRequest();
        } else {
            return uiDto();
        }
    }
}
