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

public class UserData implements Parcelable {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("user")
    @Expose
    private List<User> user = new ArrayList<>();

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
    public static class User implements Parcelable {

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

        public String getMiles() {
            return miles;
        }

        public void setMiles(String miles) {
            this.miles = miles;
        }

        @SerializedName("miles")
        @Expose
        private String miles;
        @SerializedName("userPhoto")
        @Expose
        private List<UserPhoto> userPhoto = new ArrayList<>();

        public UserVide getUserVide() {
            return userVide;
        }

        public void setUserVide(UserVide userVide) {
            this.userVide = userVide;
        }

        @SerializedName("userVideo")
        @Expose
        private UserVide userVide;
        @SerializedName("userVideoThumb")
        @Expose
        private UserVideoThumb videoThumb;
        @SerializedName("user_meta")
        @Expose
        private UseMeta useMeta;

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

        public UseMeta getUseMeta() {
            return useMeta;
        }

        public void setUseMeta(UseMeta useMeta) {
            this.useMeta = useMeta;
        }
        public static class UserPhoto implements Parcelable {

            @SerializedName("photo_path")
            @Expose
            private String photoPath;
            @SerializedName("photos_id")
            @Expose
            private String photosId;

            public String getPhotoPath() {
                return photoPath;
            }

            public void setPhotoPath(String photoPath) {
                this.photoPath = photoPath;
            }

            public String getPhotosId() {
                return photosId;
            }

            public void setPhotosId(String photosId) {
                this.photosId = photosId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.photoPath);
                dest.writeString(this.photosId);
            }

            public UserPhoto() {
            }

            protected UserPhoto(Parcel in) {
                this.photoPath = in.readString();
                this.photosId = in.readString();
            }

            public static final Creator<UserPhoto> CREATOR = new Creator<UserPhoto>() {
                @Override
                public UserPhoto createFromParcel(Parcel source) {
                    return new UserPhoto(source);
                }

                @Override
                public UserPhoto[] newArray(int size) {
                    return new UserPhoto[size];
                }
            };
        }
        public static class UserVide implements Parcelable
        {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("video_url")
            @Expose
            private String videoUrl;

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.videoUrl);
            }

            public UserVide() {
            }

            protected UserVide(Parcel in) {
                this.id = in.readString();
                this.videoUrl = in.readString();
            }

            public static final Creator<UserVide> CREATOR = new Creator<UserVide>() {
                @Override
                public UserVide createFromParcel(Parcel source) {
                    return new UserVide(source);
                }

                @Override
                public UserVide[] newArray(int size) {
                    return new UserVide[size];
                }
            };
        }
        public static class UserVideoThumb implements Parcelable {
            public String getPhotos_id() {
                return photos_id;
            }

            public void setPhotos_id(String photos_id) {
                this.photos_id = photos_id;
            }

            public String getPhoto_path() {
                return photo_path;
            }

            public void setPhoto_path(String photo_path) {
                this.photo_path = photo_path;
            }

            @SerializedName("photos_id")
            @Expose
            private String photos_id;
            @SerializedName("photo_path")
            @Expose
            private String photo_path;


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.photos_id);
                dest.writeString(this.photo_path);
            }

            public UserVideoThumb() {
            }

            protected UserVideoThumb(Parcel in) {
                this.photos_id = in.readString();
                this.photo_path = in.readString();
            }

            public static final Creator<UserVideoThumb> CREATOR = new Creator<UserVideoThumb>() {
                @Override
                public UserVideoThumb createFromParcel(Parcel source) {
                    return new UserVideoThumb(source);
                }

                @Override
                public UserVideoThumb[] newArray(int size) {
                    return new UserVideoThumb[size];
                }
            };
        }
         public static class UseMeta implements Parcelable{

             public String getAge() {
                 return age;
             }

             public void setAge(String age) {
                 this.age = age;
             }

             @SerializedName("age")
             @Expose
             private String age;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("like")
            @Expose
            private int like;

             public int getLike() {
                 return like;
             }

             public void setLike(int like) {
                 this.like = like;
             }

             public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.age);
                dest.writeString(this.gender);
                dest.writeString(this.dob);
                dest.writeInt(this.like);
            }

            public UseMeta() {
            }

            protected UseMeta(Parcel in) {
                this.age = in.readString();
                this.gender = in.readString();
                this.dob = in.readString();
                this.like = in.readInt();
            }

            public static final Creator<UseMeta> CREATOR = new Creator<UseMeta>() {
                @Override
                public UseMeta createFromParcel(Parcel source) {
                    return new UseMeta(source);
                }

                @Override
                public UseMeta[] newArray(int size) {
                    return new UseMeta[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.username);
            dest.writeString(this.name);
            dest.writeString(this.address);
            dest.writeString(this.email);
            dest.writeString(this.miles);
            dest.writeList(this.userPhoto);
            dest.writeParcelable(this.userVide, flags);
            dest.writeParcelable(this.videoThumb, flags);
            dest.writeParcelable(this.useMeta, flags);
        }

        public User() {
        }

        protected User(Parcel in) {
            this.id = in.readString();
            this.username = in.readString();
            this.name = in.readString();
            this.address = in.readString();
            this.email = in.readString();
            this.miles = in.readString();
            this.userPhoto = new ArrayList<UserPhoto>();
            in.readList(this.userPhoto, UserPhoto.class.getClassLoader());
            this.userVide = in.readParcelable(UserVide.class.getClassLoader());
            this.videoThumb = in.readParcelable(UserVideoThumb.class.getClassLoader());
            this.useMeta = in.readParcelable(UseMeta.class.getClassLoader());
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel source) {
                return new User(source);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isSuccess);
        dest.writeTypedList(this.user);
    }

    public UserData() {
    }

    protected UserData(Parcel in) {
        this.isSuccess = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.user = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
