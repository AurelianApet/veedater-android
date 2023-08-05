package digimantra.veedaters.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmlabs on 4/12/17.
 */

public class LoginResponse implements Parcelable
{
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("error")
    @Expose
    private String error;

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    @SerializedName("user")
    @Expose
    private LogedUser user;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public LogedUser getUser() {
        return user;
    }

    public void setUser(LogedUser user) {
        this.user = user;
    }
    public static class LogedUser implements Parcelable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("auth_key")
        @Expose
        private String authKey;
        @SerializedName("password_reset_token")
        @Expose
        private String passwordResetToken;

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

        public String getAuthKey() {
            return authKey;
        }

        public void setAuthKey(String authKey) {
            this.authKey = authKey;
        }

        public String getPasswordResetToken() {
            return passwordResetToken;
        }

        public void setPasswordResetToken(String passwordResetToken) {
            this.passwordResetToken = passwordResetToken;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getSocialMediaType() {
            return socialMediaType;
        }

        public void setSocialMediaType(String socialMediaType) {
            this.socialMediaType = socialMediaType;
        }

        public String getVerificationcode() {
            return verificationcode;
        }

        public void setVerificationcode(String verificationcode) {
            this.verificationcode = verificationcode;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
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

        public LoggedUserMeta getUserMeta() {
            return userMeta;
        }

        public void setUserMeta(LoggedUserMeta userMeta) {
            this.userMeta = userMeta;
        }

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("social_media_type")
        @Expose
        private String socialMediaType;
        @SerializedName("verificationcode")
        @Expose
        private String verificationcode;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("is_active")
        @Expose
        private String isActive;
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
        @SerializedName("user_meta")
        @Expose
        private LoggedUserMeta userMeta;

        public Subscription getSubscription() {
            return subscription;
        }

        public void setSubscription(Subscription subscription) {
            this.subscription = subscription;
        }

        @SerializedName("subscription")
        @Expose

        private  Subscription subscription;


        public static class LoggedUserMeta implements Parcelable {
            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getBeer() {
                return beer;
            }

            public void setBeer(String beer) {
                this.beer = beer;
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

            public String getIncome() {
                return income;
            }

            public void setIncome(String income) {
                this.income = income;
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

            public String getTravel() {
                return travel;
            }

            public void setTravel(String travel) {
                this.travel = travel;
            }

            @SerializedName("about")
            @Expose
            private String about;
            @SerializedName("beer")
            @Expose
            private String beer;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("age")
            @Expose
            private String age;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("income")
            @Expose
            private String income;
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
            @SerializedName("travel")
            @Expose
            private String travel;


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.about);
                dest.writeString(this.beer);
                dest.writeString(this.dob);
                dest.writeString(this.age);
                dest.writeString(this.gender);
                dest.writeString(this.income);
                dest.writeString(this.nation);
                dest.writeString(this.religion);
                dest.writeString(this.smoke);
                dest.writeString(this.sport);
                dest.writeString(this.status);
                dest.writeString(this.style);
                dest.writeString(this.travel);
            }

            public LoggedUserMeta() {
            }

            protected LoggedUserMeta(Parcel in) {
                this.about = in.readString();
                this.beer = in.readString();
                this.dob = in.readString();
                this.age = in.readString();
                this.gender = in.readString();
                this.income = in.readString();
                this.nation = in.readString();
                this.religion = in.readString();
                this.smoke = in.readString();
                this.sport = in.readString();
                this.status = in.readString();
                this.style = in.readString();
                this.travel = in.readString();
            }

            public static final Creator<LoggedUserMeta> CREATOR = new Creator<LoggedUserMeta>() {
                @Override
                public LoggedUserMeta createFromParcel(Parcel source) {
                    return new LoggedUserMeta(source);
                }

                @Override
                public LoggedUserMeta[] newArray(int size) {
                    return new LoggedUserMeta[size];
                }
            };
        }
        public static class Subscription implements Parcelable {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("charge_id")
            @Expose
            private String chargeId;
            @SerializedName("transaction_id")
            @Expose
            private String transactionId;
            @SerializedName("plan")
            @Expose
            private String plan;

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

            public String getChargeId() {
                return chargeId;
            }

            public void setChargeId(String chargeId) {
                this.chargeId = chargeId;
            }

            public String getTransactionId() {
                return transactionId;
            }

            public void setTransactionId(String transactionId) {
                this.transactionId = transactionId;
            }

            public String getPlan() {
                return plan;
            }

            public void setPlan(String plan) {
                this.plan = plan;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getMonths() {
                return months;
            }

            public void setMonths(String months) {
                this.months = months;
            }

            public String getExpiresOn() {
                return expiresOn;
            }

            public void setExpiresOn(String expiresOn) {
                this.expiresOn = expiresOn;
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

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
            }

            public String getSubscriptionId() {
                return subscriptionId;
            }

            public void setSubscriptionId(String subscriptionId) {
                this.subscriptionId = subscriptionId;
            }

            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("months")
            @Expose
            private String months;
            @SerializedName("expires_on")
            @Expose
            private String expiresOn;
            @SerializedName("createddate")
            @Expose
            private String createddate;
            @SerializedName("updateddate")
            @Expose
            private String updateddate;
            @SerializedName("customer_id")
            @Expose
            private String customerId;
            @SerializedName("subscription_id")
            @Expose
            private String subscriptionId;


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.userId);
                dest.writeString(this.chargeId);
                dest.writeString(this.transactionId);
                dest.writeString(this.plan);
                dest.writeString(this.amount);
                dest.writeString(this.months);
                dest.writeString(this.expiresOn);
                dest.writeString(this.createddate);
                dest.writeString(this.updateddate);
                dest.writeString(this.customerId);
                dest.writeString(this.subscriptionId);
            }

            public Subscription() {
            }

            protected Subscription(Parcel in) {
                this.id = in.readString();
                this.userId = in.readString();
                this.chargeId = in.readString();
                this.transactionId = in.readString();
                this.plan = in.readString();
                this.amount = in.readString();
                this.months = in.readString();
                this.expiresOn = in.readString();
                this.createddate = in.readString();
                this.updateddate = in.readString();
                this.customerId = in.readString();
                this.subscriptionId = in.readString();
            }

            public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
                @Override
                public Subscription createFromParcel(Parcel source) {
                    return new Subscription(source);
                }

                @Override
                public Subscription[] newArray(int size) {
                    return new Subscription[size];
                }
            };
        }
        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.username);
            dest.writeString(this.authKey);
            dest.writeString(this.passwordResetToken);
            dest.writeString(this.email);
            dest.writeString(this.status);
            dest.writeString(this.address);
            dest.writeString(this.name);
            dest.writeString(this.socialId);
            dest.writeString(this.socialMediaType);
            dest.writeString(this.verificationcode);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
            dest.writeString(this.isActive);
            dest.writeString(this.createdby);
            dest.writeString(this.createddate);
            dest.writeString(this.updatedby);
            dest.writeString(this.updateddate);
            dest.writeParcelable(this.userMeta, flags);
            dest.writeParcelable(this.subscription,flags);
        }

        public LogedUser() {
        }

        protected LogedUser(Parcel in)
        {
            this.id = in.readString();
            this.username = in.readString();
            this.authKey = in.readString();
            this.passwordResetToken = in.readString();
            this.email = in.readString();
            this.status = in.readString();
            this.address = in.readString();
            this.name = in.readString();
            this.socialId = in.readString();
            this.socialMediaType = in.readString();
            this.verificationcode = in.readString();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
            this.isActive = in.readString();
            this.createdby = in.readString();
            this.createddate = in.readString();
            this.updatedby = in.readString();
            this.updateddate = in.readString();
            this.userMeta = in.readParcelable(LoggedUserMeta.class.getClassLoader());
            this.subscription = in.readParcelable(Subscription.class.getClassLoader());
        }

        public static final Creator<LogedUser> CREATOR = new Creator<LogedUser>() {
            @Override
            public LogedUser createFromParcel(Parcel source) {
                return new LogedUser(source);
            }

            @Override
            public LogedUser[] newArray(int size) {
                return new LogedUser[size];
            }
        };
    }



    public LoginResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isSuccess);
        dest.writeString(this.error);
        dest.writeParcelable(this.user, flags);
    }

    protected LoginResponse(Parcel in) {
        this.isSuccess = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.error = in.readString();
        this.user = in.readParcelable(LogedUser.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
