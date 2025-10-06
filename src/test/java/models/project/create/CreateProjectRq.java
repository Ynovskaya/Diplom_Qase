package models.project.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectRq {
    @SerializedName("title")
    @Expose
    private final String title;

    @SerializedName("code")
    @Expose
    private final String code;

    @SerializedName("description")
    @Expose
    private final String description;

    @SerializedName("access")
    @Expose
    private final String access;

    @SerializedName("group")
    @Expose
    private final String group;
}
