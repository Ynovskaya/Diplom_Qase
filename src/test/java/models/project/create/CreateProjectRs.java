package models.project.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectRs {
    @SerializedName("status")
    @Expose
    public final Boolean status;

    @SerializedName("result")
    @Expose
    public final Result result;

    @Data
    @Builder
    public static class Result {
        @SerializedName("code")
        @Expose
        public final String code;
    }
}
