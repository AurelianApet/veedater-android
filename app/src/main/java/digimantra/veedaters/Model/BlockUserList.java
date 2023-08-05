package digimantra.veedaters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmlabs on 11/12/17.
 */

public class BlockUserList  {
    @SerializedName("is_success")
    @Expose
    private Boolean isSuccess;
    @SerializedName("blocked_users")
    @Expose
    private List<BlockedUser> blockedUsers = new ArrayList<>();

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<BlockedUser> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(List<BlockedUser> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

  public static class BlockedUser
  {
      @SerializedName("photo_path")
      @Expose
      private String photoPath;
      @SerializedName("gender")
      @Expose
      private String gender;
      @SerializedName("dob")
      @Expose
      private String dob;
      @SerializedName("id")
      @Expose
      private String id;
      @SerializedName("name")
      @Expose
      private String name;
      @SerializedName("username")
      @Expose
      private String username;

      public boolean isSelecte() {
          return isSelecte;
      }

      public void setSelecte(boolean selecte) {
          isSelecte = selecte;
      }

      private boolean isSelecte;

      public String getPhotoPath() {
          return photoPath;
      }

      public void setPhotoPath(String photoPath) {
          this.photoPath = photoPath;
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

      public String getId() {
          return id;
      }

      public void setId(String id) {
          this.id = id;
      }

      public String getName() {
          return name;
      }

      public void setName(String name) {
          this.name = name;
      }

      public String getUsername() {
          return username;
      }

      public void setUsername(String username) {
          this.username = username;
      }
  }
}
