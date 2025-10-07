package models.project.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Дополнительный простой класс-результат (поддержка совместимости структур).
 */
@Data
public class SimpleResult {
    @SerializedName("code")
    @Expose
    public String code;
}
