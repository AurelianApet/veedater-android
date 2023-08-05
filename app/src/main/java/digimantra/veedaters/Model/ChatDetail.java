package digimantra.veedaters.Model;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmlabs on 14/12/17.
 */

public class ChatDetail {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;

    public List<Messages> getList() {
        return list;
    }

    public void setList(List<Messages> list) {
        this.list = list;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    @SerializedName("list")
    @Expose
    private java.util.List<Messages> list = null;
    public static class Messages
    {
        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public Object getMessageSubject() {
            return messageSubject;
        }

        public void setMessageSubject(Object messageSubject) {
            this.messageSubject = messageSubject;
        }

        public String getMessageCreatorId() {
            return messageCreatorId;
        }

        public void setMessageCreatorId(String messageCreatorId) {
            this.messageCreatorId = messageCreatorId;
        }

        public String getMessageBody() {
            return messageBody;
        }

        public void setMessageBody(String messageBody) {
            this.messageBody = messageBody;
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

        public UserInfo getInfo() {
            return info;
        }

        public void setInfo(UserInfo info) {
            this.info = info;
        }

        public UserPhoto getMessagePhoto() {
            return messagePhoto;
        }

        public void setMessagePhoto(UserPhoto messagePhoto) {
            this.messagePhoto = messagePhoto;
        }

        @SerializedName("message_id")
        @Expose
        private String messageId;
        @SerializedName("message_subject")
        @Expose
        private Object messageSubject;
        @SerializedName("message_creator_id")
        @Expose
        private String messageCreatorId;
        @SerializedName("message_body")
        @Expose
        private String messageBody;
        @SerializedName("createdby")
        @Expose
        private String createdby;
        @SerializedName("createddate")
        @Expose
        private String createddate;
        @SerializedName("info")
        @Expose
        private UserInfo info;
        @SerializedName("messagePhoto")
        @Expose
        private UserPhoto messagePhoto;

        public Uri getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(Uri imageUrl) {
            this.imageUrl = imageUrl;
        }

        private Uri imageUrl=null;
        public boolean isContainImage() {
            return containImage;
        }

        public void setContainImage(boolean containImage) {
            this.containImage = containImage;
        }

        private boolean containImage=false;
        public static class UserPhoto
        {
            public String getPhoto_path() {
                return photo_path;
            }

            public void setPhoto_path(String photo_path) {
                this.photo_path = photo_path;
            }

            @SerializedName("photo_path")
            @Expose
            String photo_path;
        }
    public static class UserInfo
    {
        @SerializedName("message_recipient_id")
        @Expose
        private String messageRecipientId;
        @SerializedName("message_id")
        @Expose
        private String messageId;
        @SerializedName("recipient_id")
        @Expose
        private String recipientId;
        @SerializedName("first_message_key")
        @Expose
        private String firstMessageKey;
        @SerializedName("createdby")
        @Expose
        private String createdby;
        @SerializedName("createddate")
        @Expose
        private String createddate;
        @SerializedName("is_read")
        @Expose
        private String isRead;
        @SerializedName("reciever")
        @Expose
        private MessageReciever reciever;

        public MessageSender getSender() {
            return sender;
        }

        public void setSender(MessageSender sender) {
            this.sender = sender;
        }

        public MessageReciever getReciever() {
            return reciever;
        }

        public void setReciever(MessageReciever reciever) {
            this.reciever = reciever;
        }

        @SerializedName("sender")
        @Expose

        private MessageSender sender;

        public String getMessageRecipientId() {
            return messageRecipientId;
        }

        public void setMessageRecipientId(String messageRecipientId) {
            this.messageRecipientId = messageRecipientId;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getRecipientId() {
            return recipientId;
        }

        public void setRecipientId(String recipientId) {
            this.recipientId = recipientId;
        }

        public String getFirstMessageKey() {
            return firstMessageKey;
        }

        public void setFirstMessageKey(String firstMessageKey) {
            this.firstMessageKey = firstMessageKey;
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

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }
    public static class MessageReciever
    {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        private String username;

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

    }
        public static class MessageSender
        {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("username")
            @Expose
            private String username;

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
        }


    }
    }
}
