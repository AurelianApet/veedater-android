package digimantra.veedaters.Model;

/**
 * Created by dmlabs on 9/11/17.
 */

public class UserModel {
    private String userName;
    private String lastMessage;
    private String userId;
    private int image;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public UserModel(String userName, String lastMessage, String userId, int image) {
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.userId = userId;
        this.image = image;
    }
}
