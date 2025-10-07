package models.create;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ API при создании кейса.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseCreateResponse {

    @SerializedName("status")
    private Boolean status;

    @SerializedName("result")
    private Result result;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        @SerializedName("id")
        private Integer id;
    }
}
