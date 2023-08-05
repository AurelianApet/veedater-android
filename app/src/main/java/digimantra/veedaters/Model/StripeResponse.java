package digimantra.veedaters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmlabs on 4/1/18.
 */

public class StripeResponse {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subscription")
    @Expose
    private Subscription subscription;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
    public static class Subscription
    {
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("subscription_id")
        @Expose
        private String subscriptionId;
        @SerializedName("months")
        @Expose
        private String months;
        @SerializedName("id")
        @Expose
        private Integer id;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
