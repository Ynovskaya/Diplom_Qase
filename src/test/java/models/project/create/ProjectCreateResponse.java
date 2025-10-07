package models.project.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Ответ API при создании проекта.
 */
@Data
public class ProjectCreateResponse {
    @SerializedName("status")
    @Expose
    public Boolean status;

    @SerializedName("result")
    @Expose
    public ProjectResult result;
}
