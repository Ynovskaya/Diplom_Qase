package models.create;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCaseRs {

    @SerializedName("status")
    private final Boolean status;

    @SerializedName("result")
    private final Result result;

    @Data
    @Builder
    public static class Result {
        @SerializedName("id")
        private final Integer id;
    }
}
