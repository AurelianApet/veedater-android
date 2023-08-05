package digimantra.veedaters.networking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.BlockUserList;
import digimantra.veedaters.Model.ChatDetail;
import digimantra.veedaters.Model.ChatMode;
import digimantra.veedaters.Model.GoogleAddressResponse;
import digimantra.veedaters.Model.LoginResponse;
import digimantra.veedaters.Model.PreferenceModel;
import digimantra.veedaters.Model.SentMessageResponse;
import digimantra.veedaters.Model.StripeResponse;
import digimantra.veedaters.Model.User;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.Model.UserLikesResponse;
import digimantra.veedaters.Model.UserProfile;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dmlabs on 28/8/17.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("user/signup")
    Call<User> register(@FieldMap Map<String, String> fields
    );
    @FormUrlEncoded
    @POST("user/social-signup")
    Call<User> signUpWithSocialMedial(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/social-login")
    Call<User> loginWithSocialMedial(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("user/forgotpassword")
    Call<User> forgotPassword(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> logIn(@FieldMap Map<String, String> fields);

    @Multipart
    @POST("user/profile-update")
    Call<User> upDateUserProfile(@Header("veedater-header-token")String id,
                                 @PartMap Map<String,RequestBody> map,
                                 @Part List<MultipartBody.Part> partMap );
    @GET("user/get-profile")
    Call<UserProfile> getProfileData(@Header("veedater-header-token")String id);
    @GET("user/user-detail")
    Call<UserProfile> getUserData(@Header("veedater-header-token") String token, @Query("id") String id);
    @Multipart
    @POST("user/profile-update")
    Call<User> upDateUserProfileWithoutImage(@Header("veedater-header-token")String id,
                                 @PartMap Map<String, RequestBody> map
                                );
    @FormUrlEncoded
    @POST("user/change-password")
    Call<BlockResponse> changePassword(@Header("veedater-header-token")String id,@FieldMap Map<String,String> map);
    @POST("message/create")
    @FormUrlEncoded
    Call<SentMessageResponse> sendMessage(@Header("veedater-header-token")int id,@Field(value = "Message[recipient_id]",encoded = false) String receiverId,@Field(value = "Message[message_body]",encoded = false) String message,@Field("Message[message_parent_id]")String parentId );
    @Multipart
    @POST("message/create")
    Call<SentMessageResponse> sendImage(@Header("veedater-header-token") int id,@PartMap Map<String,RequestBody> map,@Part MultipartBody.Part part);
    @GET("message/list")
    Call<ChatMode> getMessage(@Header("veedater-header-token")int id);
    @GET("user/list")
    Call<UserData> userList(@Header("veedater-header-token")String id,@Query("nearby") String type);
    @GET("http://maps.googleapis.com/maps/api/geocode/json?")
    Call<GoogleAddressResponse> getGoogleAddress(@Query("address") String addressId);
    @GET("user/list")
    Call<UserData> nearBy(@Header("veedater-header-token")String id, @QueryMap Map<String,String> map);
    @POST("user/logout")
    @FormUrlEncoded
    Call<BlockResponse> logout(@Header("veedater-header-token") String id,@Field("User[device_token]") String token,@Field("User[device_type]") String type);
    @POST("user/review")
    @FormUrlEncoded
    Call<UserLikesResponse> likeUser(@Header("veedater-header-token")int id, @Field("User[user_id]") int userId,@Field("User[review]") int flag);
    @GET("user/favlist")
    Call<UserData> favouriteUser(@Header("veedater-header-token")String id);
    @GET("user/blocklist")
    Call<BlockUserList> getBlockUser(@Header("veedater-header-token")String id);
    @POST("user/subscription")
    @FormUrlEncoded
    Call<StripeResponse> submitToken(@Header("veedater-header-token")String id, @Field("User[token]") String userToken, @Field("User[months]") String month);
    @POST("user/block")
    @FormUrlEncoded
    Call<BlockResponse> blockUser(@Header("veedater-header-token")String id, @Field("User[user_id]") String userID);
    @POST("user/unblock")
    @FormUrlEncoded
    Call<BlockResponse> unBlockUser(@Header("veedater-header-token")String id, @FieldMap Map<String,String>  userID);
    @POST("message/detail")
    @FormUrlEncoded
    Call<ChatDetail> messageDetail(@Header("veedater-header-token")int id, @Field("Message[user_id]") String userID);
    @POST("video/upload")
    @Multipart
    Call<UserLikesResponse> uploadVideo(@Header("veedater-header-token") int id,@Part MultipartBody.Part part,@Part MultipartBody.Part thumb);
    @FormUrlEncoded
    @POST("user/settings")
    Call<BlockResponse> deleteAccount(@Header("veedater-header-token") int id,@Field("User[action]") String action);
   @FormUrlEncoded
   @POST("user/support")
   Call<BlockResponse> feedBack(@Header("veedater-header-token") int id,@Field("User[message]") String action);
    @Multipart
    @POST("shop/create")
    Call<User> createShop(@Header("chibha-header-token") String id,
                               @PartMap Map<String, String> map,
                               @Part MultipartBody.Part image);

    @POST("message/delete-thread")
    @FormUrlEncoded
    Call<UserLikesResponse> clearChat(@Header("veedater-header-token") String id, @Field("Message[user_id]") String userID);
    @POST("user/preferences")
    @FormUrlEncoded
    Call<BlockResponse> setPreference(@Header("veedater-header-token") String id, @FieldMap HashMap<String,String> map);
    @GET("user/preferences")
    Call<PreferenceModel> getPrefernce(@Header("veedater-header-token") String id);
    @FormUrlEncoded
    @POST("user/notification")
    Call<User> setNotification(@Header("chibha-header-token") String id, @Field("User[user_message]") String message,
                                    @Field("User[user_order]") String order);

    @Multipart
    @POST("user/profile-update")
    Call<User> upDateProfile(@Header("chibha-header-token") String id,
                                  @PartMap Map<String, String> map,
                                  @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("user/profile-update")
    Call<User> upDateAccount(@Header("chibha-header-token") String id,
                                  @Field("User[phone]") String phone,
                                  @Field("User[email]") String email);
    @GET("user/get-profile")
    Call<User> getUserProfile(@Header("chibha-header-token") String id);




   }
