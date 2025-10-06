package models.project.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProjectRs {

    @SerializedName("status")
    @Expose
    private final Boolean status;

    @SerializedName("result")
    @Expose
    private final Result result;

    // ---------- Nested ----------
    @Data
    @Builder
    public static class Result {
        @SerializedName("title")
        @Expose
        private final String title;

        @SerializedName("code")
        @Expose
        private final String code;

        @SerializedName("counts")
        @Expose
        private final Counts counts;
    }

    @Data
    @Builder
    public static class Counts {
        @SerializedName("cases")
        @Expose
        private final Integer cases;

        @SerializedName("suites")
        @Expose
        private final Integer suites;

        @SerializedName("milestones")
        @Expose
        private final Integer milestones;

        @SerializedName("runs")
        @Expose
        private final Runs runs;

        @SerializedName("defects")
        @Expose
        private final Defects defects;
    }

    @Data
    @Builder
    public static class Runs {
        @SerializedName("total")
        @Expose
        private final Integer total;

        @SerializedName("active")
        @Expose
        private final Integer active;
    }

    @Data
    @Builder
    public static class Defects {
        @SerializedName("total")
        @Expose
        private final Integer total;

        @SerializedName("open")
        @Expose
        private final Integer open;
    }
}
