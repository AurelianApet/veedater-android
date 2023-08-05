package digimantra.veedaters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmlabs on 9/1/18.
 */

public class PreferenceModel {
    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public UserPrefer getUserPrefer() {
        return userPrefer;
    }

    public void setUserPrefer(UserPrefer userPrefer) {
        this.userPrefer = userPrefer;
    }

    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("user")
    @Expose
    private UserPrefer userPrefer;
    public static class UserPrefer
    {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("min_age")
        @Expose
        private String minAge;
        @SerializedName("max_age")
        @Expose
        private String maxAge;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("religion")
        @Expose
        private String religion;
        @SerializedName("sports")
        @Expose
        private String sports;
        @SerializedName("min_income")
        @Expose
        private String minIncome;
        @SerializedName("max_income")
        @Expose
        private String maxIncome;
        @SerializedName("style")
        @Expose
        private String style;
        @SerializedName("alchohol")
        @Expose
        private String alchohol;
        @SerializedName("smoke")
        @Expose
        private String smoke;
        @SerializedName("tatoo")
        @Expose
        private String tatoo;
        @SerializedName("createddate")
        @Expose
        private String createddate;
        @SerializedName("updateddate")
        @Expose
        private String updateddate;
        @SerializedName("createdby")
        @Expose
        private String createdby;
        @SerializedName("updatedby")
        @Expose
        private String updatedby;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMinAge() {
            return minAge;
        }

        public void setMinAge(String minAge) {
            this.minAge = minAge;
        }

        public String getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(String maxAge) {
            this.maxAge = maxAge;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getSports() {
            return sports;
        }

        public void setSports(String sports) {
            this.sports = sports;
        }

        public String getMinIncome() {
            return minIncome;
        }

        public void setMinIncome(String minIncome) {
            this.minIncome = minIncome;
        }

        public String getMaxIncome() {
            return maxIncome;
        }

        public void setMaxIncome(String maxIncome) {
            this.maxIncome = maxIncome;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getAlchohol() {
            return alchohol;
        }

        public void setAlchohol(String alchohol) {
            this.alchohol = alchohol;
        }

        public String getSmoke() {
            return smoke;
        }

        public void setSmoke(String smoke) {
            this.smoke = smoke;
        }

        public String getTatoo() {
            return tatoo;
        }

        public void setTatoo(String tatoo) {
            this.tatoo = tatoo;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getUpdateddate() {
            return updateddate;
        }

        public void setUpdateddate(String updateddate) {
            this.updateddate = updateddate;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(String updatedby) {
            this.updatedby = updatedby;
        }
    }
}
