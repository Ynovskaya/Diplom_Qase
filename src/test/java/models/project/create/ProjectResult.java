package models.project.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Результат создания проекта (внутренний объект ответа).
 */
@Data
public class ProjectResult {
    @SerializedName("code")
    @Expose
    public String code;
}
