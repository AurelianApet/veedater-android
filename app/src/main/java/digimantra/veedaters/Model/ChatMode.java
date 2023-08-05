package digimantra.veedaters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmlabs on 10/11/17.
 */

public class ChatMode {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;

    public List<UserMessage> getList() {
        return list;
    }

    public void setList(List<UserMessage> list) {
        this.list = list;
    }

    @SerializedName("list")
    @Expose
    private java.util.List<UserMessage> list = null;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public static class UserMessage {
        @SerializedName("message_id")
        @Expose
        private String messageId;
        @SerializedName("message_subject")
        @Expose
        private String messageSubject;
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
        private Info info;
        @SerializedName("messagePhoto")
        @Expose
        private Object messagePhoto;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getMessageSubject() {
            return messageSubject;
        }

        public void setMessageSubject(String messageSubject) {
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

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public Object getMessagePhoto() {
            return messagePhoto;
        }
        public void setMessagePhoto(Object messagePhoto) {
            this.messagePhoto = messagePhoto;
        }
        public static class Info
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
            private Reciever reciever;
            @SerializedName("sender")
            @Expose
            private Sender sender;

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

            public Reciever getReciever() {
                return reciever;
            }

            public void setReciever(Reciever reciever) {
                this.reciever = reciever;
            }

            public Sender getSender() {
                return sender;
            }

            public void setSender(Sender sender) {
                this.sender = sender;
            }
            public static class Sender
            {
                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("username")
                @Expose
                private String username;
                @SerializedName("suserPhoto")
                @Expose
                private SuserPhoto suserPhoto;

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

                public SuserPhoto getSuserPhoto() {
                    return suserPhoto;
                }

                public void setSuserPhoto(SuserPhoto suserPhoto) {
                    this.suserPhoto = suserPhoto;
                }
                public static class SuserPhoto
                {
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
            public static class Reciever
            {
                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("username")
                @Expose
                private String username;

                public ReceiverPhoto getUserPhoto() {
                    return userPhoto;
                }

                public void setUserPhoto(ReceiverPhoto userPhoto) {
                    this.userPhoto = userPhoto;
                }

                @SerializedName("ruserPhoto")
                @Expose
                private ReceiverPhoto userPhoto = null;

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


            public static class ReceiverPhoto
            {
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
    }
}
