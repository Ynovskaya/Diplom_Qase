package models.project.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Result {//класс ответов
    @SerializedName("code")
    @Expose
    public String code;
}
