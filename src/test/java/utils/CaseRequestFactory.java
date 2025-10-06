package utils;

import com.github.javafaker.Faker;
import models.create.CreateCaseRq;

import java.util.List;
import java.util.Random;

public class CaseRequestFactory {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static CreateCaseRq caseRq() {
        return CreateCaseRq.builder()
                .title(faker.book().title())
                .description(faker.lorem().sentence())
                .priority(2)
                .severity(1)
                .type(3)
                .steps(List.of(
                        CreateCaseRq.Step.builder()
                                .action("Открыть сайт")
                                .expectedResult("Сайт открыт")
                                .data(null)
                                .build(),
                        CreateCaseRq.Step.builder()
                                .action("Авторизоваться")
                                .expectedResult("Успешно")
                                .data(null)
                                .build()
                ))
                .build();
    }

    public static Object random() {
        if (random.nextBoolean()) {
            return caseRq();
        } else {
            return null;
        }
    }
}
