package dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * Представление тест-сьюта (UI).
 */
@Data
@Builder
public class TestSuiteDto {
    private String title;
    private String description;
    private String preconditions;

    @SerializedName("parent_id")
    private int parentId;
}
