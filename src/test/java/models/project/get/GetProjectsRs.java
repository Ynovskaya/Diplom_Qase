package models.project.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetProjectsRs {

    @SerializedName("status")
    @Expose
    public final Boolean status;

    @SerializedName("projects")
    @Expose
    public final List<Project> projects;

    @SerializedName("total")
    @Expose
    public final Integer total;

    @Data
    @Builder
    public static class Project {
        @SerializedName("code")
        @Expose
        public final String code;

        @SerializedName("title")
        @Expose
        public final String title;

        @SerializedName("description")
        @Expose
        public final String description;

        @SerializedName("is_available")
        @Expose
        public final Boolean isAvailable;

        @SerializedName("is_archived")
        @Expose
        public final Boolean isArchived;

        @SerializedName("is_private")
        @Expose
        public final Boolean isPrivate;

        @SerializedName("avatar_url")
        @Expose
        public final String avatarUrl;

        @SerializedName("case_count")
        @Expose
        public final Integer caseCount;

        @SerializedName("suite_count")
        @Expose
        public final Integer suiteCount;

        @SerializedName("milestone_count")
        @Expose
        public final Integer milestoneCount;

        @SerializedName("run_total")
        @Expose
        public final Integer runTotal;

        @SerializedName("run_active")
        @Expose
        public final Integer runActive;

        @SerializedName("defect_total")
        @Expose
        public final Integer defectTotal;

        @SerializedName("defect_unresolved")
        @Expose
        public final Integer defectUnresolved;

        @SerializedName("is_creator")
        @Expose
        public final Boolean isCreator;

        @SerializedName("members")
        @Expose
        public final List<Member> members;

        @SerializedName("favorite")
        @Expose
        public final Boolean favorite;
    }

    @Data
    @Builder
    public static class Member {
        @SerializedName("internal_id")
        @Expose
        public final Integer internalId;

        @SerializedName("name")
        @Expose
        public final String name;

        @SerializedName("role_title")
        @Expose
        public final String roleTitle;

        @SerializedName("user")
        @Expose
        public final User user;

        @SerializedName("role")
        @Expose
        public final Role role;

        @SerializedName("type")
        @Expose
        public final Integer type;

        @SerializedName("status")
        @Expose
        public final Integer status;
    }

    @Data
    @Builder
    public static class Role {
        @SerializedName("internal_id")
        @Expose
        public final Integer internalId;

        @SerializedName("title")
        @Expose
        public final String title;

        @SerializedName("description")
        @Expose
        public final String description;

        @SerializedName("is_default")
        @Expose
        public final Boolean isDefault;

        @SerializedName("is_owner")
        @Expose
        public final Boolean isOwner;
    }

    @Data
    @Builder
    public static class User {
        @SerializedName("name")
        @Expose
        public final String name;

        @SerializedName("firstName")
        @Expose
        public final String firstName;

        @SerializedName("lastName")
        @Expose
        public final String lastName;

        @SerializedName("email")
        @Expose
        public final String email;

        @SerializedName("avatar_url")
        @Expose
        public final String avatarUrl;

        @SerializedName("is_online")
        @Expose
        public final Boolean isOnline;

        @SerializedName("last_action")
        @Expose
        public final String lastAction;

        @SerializedName("is_active")
        @Expose
        public final Boolean isActive;
    }
}
