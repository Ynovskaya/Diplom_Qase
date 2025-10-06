package models.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateCaseRq {

    @SerializedName("description")
    @Expose
    private final String description;

    @SerializedName("preconditions")
    @Expose
    private final String preconditions;

    @SerializedName("postconditions")
    @Expose
    private final String postconditions;

    @SerializedName("title")
    @Expose
    private final String title;

    @SerializedName("severity")
    @Expose
    private final Integer severity;

    @SerializedName("priority")
    @Expose
    private final Integer priority;

    @SerializedName("type")
    @Expose
    private final Integer type;

    @SerializedName("steps")
    @Expose
    private final List<Step> steps;

    @Data
    @Builder
    public static class Step {

        @SerializedName("action")
        @Expose
        private final String action;

        @SerializedName("expected_result")
        @Expose
        private final String expectedResult;

        @SerializedName("data")
        @Expose
        private final String data;
    }
}
