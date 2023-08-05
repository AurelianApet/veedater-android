package digimantra.veedaters.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmlabs on 15/11/17.
 */

public class UserProfile  {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;

    public NewUser getUser() {
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }

    @SerializedName("user")
    @Expose
    private NewUser user;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public static class NewUser
    {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("userPhoto")
        @Expose
        private List<UserPhoto> userPhoto = new ArrayList<>();

        public UserVideo getUserVideo() {
            return userVideo;
        }

        public void setUserVideo(UserVideo userVideo) {
            this.userVideo = userVideo;
        }

        @SerializedName("userVideo")
       @Expose
        private UserVideo userVideo;
        @SerializedName("user_meta")
        @Expose
        private UserMeta userMeta;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<UserPhoto> getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(List<UserPhoto> userPhoto) {
            this.userPhoto = userPhoto;
        }

        public UserMeta getUserMeta() {
            return userMeta;
        }

        public void setUserMeta(UserMeta userMeta) {
            this.userMeta = userMeta;
        }
        public static class UserVideo
        {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("video_url")
            @Expose
            private String videoUrl;
            @SerializedName("video_title")
            @Expose
            private Object videoTitle;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public Object getVideoTitle() {
                return videoTitle;
            }

            public void setVideoTitle(Object videoTitle) {
                this.videoTitle = videoTitle;
            }

        }
        public static class UserMeta {

            @SerializedName("about")
            @Expose
            private String about;
            @SerializedName("alchohol")
            @Expose
            private String alchohol;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("age")
            @Expose
            private String age;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("max_income")
            @Expose
            private String maxIncome;
            @SerializedName("min_income")
            @Expose
            private String minIncome;
            @SerializedName("nation")
            @Expose
            private String nation;
            @SerializedName("religion")
            @Expose
            private String religion;
            @SerializedName("smoke")
            @Expose
            private String smoke;
            @SerializedName("sport")
            @Expose
            private String sport;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("style")
            @Expose
            private String style;
            @SerializedName("tatoo")
            @Expose
            private String tatoo;
            @SerializedName("like")
            @Expose
            private Integer like;

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getAlchohol() {
                return alchohol;
            }

            public void setAlchohol(String alchohol) {
                this.alchohol = alchohol;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getMaxIncome() {
                return maxIncome;
            }

            public void setMaxIncome(String maxIncome) {
                this.maxIncome = maxIncome;
            }

            public String getMinIncome() {
                return minIncome;
            }

            public void setMinIncome(String minIncome) {
                this.minIncome = minIncome;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getReligion() {
                return religion;
            }

            public void setReligion(String religion) {
                this.religion = religion;
            }

            public String getSmoke() {
                return smoke;
            }

            public void setSmoke(String smoke) {
                this.smoke = smoke;
            }

            public String getSport() {
                return sport;
            }

            public void setSport(String sport) {
                this.sport = sport;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getTatoo() {
                return tatoo;
            }

            public void setTatoo(String tatoo) {
                this.tatoo = tatoo;
            }

            public Integer getLike() {
                return like;
            }

            public void setLike(Integer like) {
                this.like = like;
            }

        }
        public static class UserPhoto {

            @SerializedName("photos_id")
            @Expose
            private String photosId;
            @SerializedName("photo_type")
            @Expose
            private String photoType;
            @SerializedName("photo_title")
            @Expose
            private String photoTitle;
            @SerializedName("photo_path")
            @Expose
            private String photoPath;
            @SerializedName("photo_details")
            @Expose
            private String photoDetails;
            @SerializedName("isactive")
            @Expose
            private String isactive;
            @SerializedName("createdby")
            @Expose
            private String createdby;
            @SerializedName("createddate")
            @Expose
            private String createddate;
            @SerializedName("updatedby")
            @Expose
            private String updatedby;
            @SerializedName("updateddate")
            @Expose
            private String updateddate;

            public String getPhotosId() {
                return photosId;
            }

            public void setPhotosId(String photosId) {
                this.photosId = photosId;
            }

            public String getPhotoType() {
                return photoType;
            }

            public void setPhotoType(String photoType) {
                this.photoType = photoType;
            }

            public String getPhotoTitle() {
                return photoTitle;
            }

            public void setPhotoTitle(String photoTitle) {
                this.photoTitle = photoTitle;
            }

            public String getPhotoPath() {
                return photoPath;
            }

            public void setPhotoPath(String photoPath) {
                this.photoPath = photoPath;
            }

            public String getPhotoDetails() {
                return photoDetails;
            }

            public void setPhotoDetails(String photoDetails) {
                this.photoDetails = photoDetails;
            }

            public String getIsactive() {
                return isactive;
            }

            public void setIsactive(String isactive) {
                this.isactive = isactive;
            }

            public String getCreatedby() {
                return createdby;
            }

            public void setCreatedby(String createdby) {
                this.createdby = createdby;
            }

            public String getCreateddate() {
                return createddate;
            }

            public void setCreateddate(String createddate) {
                this.createddate = createddate;
            }

            public String getUpdatedby() {
                return updatedby;
            }

            public void setUpdatedby(String updatedby) {
                this.updatedby = updatedby;
            }

            public String getUpdateddate() {
                return updateddate;
            }

            public void setUpdateddate(String updateddate) {
                this.updateddate = updateddate;
            }

        }

    }
}
