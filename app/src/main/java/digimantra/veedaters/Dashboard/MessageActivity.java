package digimantra.veedaters.Dashboard;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.squareup.picasso.Picasso;

import net.hockeyapp.android.CrashManager;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Adapter.ChatAdapter;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.ChatDetail;
import digimantra.veedaters.Model.ChatMode;
import digimantra.veedaters.Model.SentMessageResponse;
import digimantra.veedaters.Model.UserLikesResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.userPhoto)
    de.hdodenhof.circleimageview.CircleImageView userPhoto;
    ChatAdapter adapter;
    @BindView(R.id.chatList)
    RecyclerView chatList;
    @BindView(R.id.inputBox)
    EditText inputBox;
    @BindView(R.id.sendButton)
    ImageView sendButton;
    @BindView(R.id.chooseImage)
    ImageView chooseImage;
    @BindView(R.id.backButton)
    ImageView backButton;
    private String receiverId;
    private boolean isClear;
    private Uri imageUrl;
    private BroadcastReceiver broadcastReceiver;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private String message[]={"I believe I have a very good chance.",
            "No rush! I'm around over the weekend too if i can be of any help.",
            "ok :)",
            "Thanks guys!",
            "Basketball was fun! Thank's for coming.",
            "I know who I am. I'm not a mistake"
    };
    private ArrayList<ChatDetail.Messages> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

      /*  View view=getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOption);*/
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title.setText("Message");
        if (getIntent().hasExtra(KeyValue.RECEIVER_ID))
        {
            receiverId=getIntent().getStringExtra(KeyValue.RECEIVER_ID);
        }
        if (getIntent().hasExtra("senderId"))
        {
            receiverId=getIntent().getStringExtra("senderId");
        }
        if (VeeDatersApp.getPhotoPath()!=null)
        {
            Picasso.with(this).load(ApiClient.IMG_BASE+VeeDatersApp.getPhotoPath())
                    .fit().into(userPhoto);
        }
        getMessages();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        chatList.setLayoutManager(manager);
        adapter=new ChatAdapter(arrayList,this);
        chatList.setAdapter(adapter);
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(digimantra.veedaters.FCM.Config.PUSH_NOTIFICATION))
                {
                    String id=null;
                    String msg=intent.getStringExtra("message");
                    String photo=intent.getStringExtra("photopath");
                    id=intent.getStringExtra("senderId");
                    if (receiverId!=null)
                    {
                        if (receiverId.equals(id))
                        {
                            ChatDetail.Messages chatMode=new ChatDetail.Messages();
                            chatMode.setMessageBody(msg);
                            ChatDetail.Messages.UserInfo info=new ChatDetail.Messages.UserInfo();
                            ChatDetail.Messages.UserInfo.MessageSender sender=new ChatDetail.Messages.UserInfo.MessageSender();
                            sender.setId(id);
                            if (!photo.equalsIgnoreCase(""))
                            {
                                ChatDetail.Messages.UserPhoto userPhoto= new ChatDetail.Messages.UserPhoto();
                                userPhoto.setPhoto_path(photo);
                                chatMode.setMessagePhoto(userPhoto);
                            }
                            info.setSender(sender);
                            chatMode.setInfo(info);
                            arrayList.add(chatMode);
                            chatList.smoothScrollToPosition(arrayList.size()+1);
                            adapter.notifyDataSetChanged();
                            chatList.smoothScrollToPosition(arrayList.size()+1);
                        }
                    }

                }
            }
        };
    }

    private void getImage()
    {
        if (checkPermissionREAD_EXTERNAL_STORAGE(this))
        {
            // do your stuff..
            Config config = new Config();
            config.setCameraHeight(R.dimen.app_camera_height);
            config.setToolbarTitleRes(R.string.custom_title);
            config.setSelectionMin(1);
            config.setSelectionLimit(1);
            config.setTabSelectionIndicatorColor(R.color.pink);
            config.setSelectedBottomHeight(R.dimen.bottom_height);
            ImagePickerActivity.setConfig(config);
            Intent intent = new Intent(this, ImagePickerActivity.class);
            startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == Activity.RESULT_OK )
        {
            ArrayList<Uri>  image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            imageUrl=image_uris.get(0);
            File file=new File(imageUrl.getPath());
            compressImage(file);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getImage();
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    @OnClick(R.id.chooseImage)
    public void chooseImage()
    {
        getImage();
    }
    private void clearChat()
    {
        CommonUtility.showProgress(this,"Please wait...");
       ApiClient.getClient().clearChat(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)),receiverId).enqueue(new Callback<UserLikesResponse>() {
           @Override
           public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response) {
               CommonUtility.hideProgress();
               if (response.isSuccessful())
               {
                   isClear=true;
                   arrayList.clear();
                   adapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onFailure(Call<UserLikesResponse> call, Throwable t) {
               CommonUtility.hideProgress();
           }
       });
    }
    private void getMessages()
    {
        CommonUtility.showProgress(this,"Please wait...");
     ApiClient.getClient().messageDetail(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID),receiverId).enqueue(new Callback<ChatDetail>() {
        @Override
        public void onResponse(Call<ChatDetail> call, Response<ChatDetail> response) {
            ChatDetail chatDetail=response.body();
            if (chatDetail!=null && chatDetail.getList()!=null)
            {
                if (!chatDetail.getList().isEmpty())
                {
                    arrayList.addAll(chatDetail.getList());
                    adapter.notifyDataSetChanged();
                }
            }
            CommonUtility.hideProgress();
        }

        @Override
        public void onFailure(Call<ChatDetail> call, Throwable t) {
            CommonUtility.hideProgress();
        }
    });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CrashManager.register(this,getResources().getString(R.string.HockeyAppID));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(digimantra.veedaters.FCM.Config.PUSH_NOTIFICATION));
        VeeDatersApp.activityResumed();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            if (PreferenceConnector.getInstance(MessageActivity.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(MessageActivity.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(MessageActivity.this,PasscodeActivity.class);
                    startActivity(intent);

                }

            } //Do specific came-here-from-background code
        }

        myApp.stopActivityTransitionTimer();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.block_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.block:
                blockUser();
                break;
            case R.id.clear_chat:
                clearChat();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
        VeeDatersApp.activityPaused();
        ((VeeDatersApp)this.getApplication()).startActivityTransitionTimer();
    }

    private void blockUser()
    {
        CommonUtility.showProgress(this,"Please wait...");
        ApiClient.getClient().blockUser(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)),receiverId).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                BlockResponse blockResponse=response.body();
                if (blockResponse.getIsSuccess())
                {
                    CommonUtility.hideProgress();
                    CommonUtility.successToast(MessageActivity.this,"User blocked");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }
    private void compressImage(File file)
    {
        Luban.compress(this, file)
                .putGear(Luban.THIRD_GEAR)      // set the compress mode, default is : THIRD_GEAR
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        sendImage(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    private void sendImage(File file)
    {
        ChatDetail.Messages chatMode=new ChatDetail.Messages();
        chatMode.setMessageBody(inputBox.getText().toString().trim());
        ChatDetail.Messages.UserPhoto photo=new ChatDetail.Messages.UserPhoto();
        photo.setPhoto_path(imageUrl.getPath());
        ChatDetail.Messages.UserInfo info=new ChatDetail.Messages.UserInfo();
        info.setRecipientId(receiverId);
        ChatDetail.Messages.UserInfo.MessageSender sender=new ChatDetail.Messages.UserInfo.MessageSender();
        sender.setId(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)));
        info.setSender(sender);
        chatMode.setInfo(info);
        arrayList.add(chatMode);
        chatList.smoothScrollToPosition(arrayList.size()+1);
        adapter.notifyDataSetChanged();
        chatList.smoothScrollToPosition(arrayList.size()+1);
        final RequestBody reqFile =RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part body= MultipartBody.Part.createFormData("Message[attachment]",file.getName(),reqFile);
        HashMap<String,RequestBody> hashMap=new HashMap<>();
        hashMap.put("Message[recipient_id]", RequestBody.create(MediaType.parse("text/plain"),receiverId));
        hashMap.put("Message[message_body]",RequestBody.create(MediaType.parse("text/plain"),""));
        hashMap.put("Message[message_parent_id]",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(0)));
        ApiClient.getClient().sendImage(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID),hashMap,body).enqueue(new Callback<SentMessageResponse>() {
            @Override
            public void onResponse(Call<SentMessageResponse> call,Response<SentMessageResponse> response) {
                if (response.body()!=null)
                {
                    if (response.isSuccessful())
                    {
                        CommonUtility.successToast(MessageActivity.this,response.message()+"");
                    }
                }
            }
            @Override
            public void onFailure(Call<SentMessageResponse> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.sendButton)
    public void sendMessage()
    {
        chatList.smoothScrollToPosition(arrayList.size()+1);
        if (inputBox.getText().toString().trim().isEmpty())
        {
            return;
        }else {
            ChatDetail.Messages chatMode=new ChatDetail.Messages();
            chatMode.setMessageBody(inputBox.getText().toString().trim());
            ChatDetail.Messages.UserInfo info=new ChatDetail.Messages.UserInfo();
            info.setRecipientId(receiverId);
            ChatDetail.Messages.UserInfo.MessageSender sender=new ChatDetail.Messages.UserInfo.MessageSender();
            sender.setId(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)));
            info.setSender(sender);
            chatMode.setInfo(info);
            arrayList.add(chatMode);

            chatList.smoothScrollToPosition(arrayList.size()+1);
            adapter.notifyDataSetChanged();
            chatList.smoothScrollToPosition(arrayList.size()+1);
           /* HashMap<String,String> map=new HashMap<>();
            map.put("Message[recipient_id]",receiverId);
            map.put("Message[message_body]",inputBox.getText().toString().trim());
            map.put("Message[message_parent_id]",String.valueOf(0));
            Log.e("paramsssss",map.toString());*/
            String enCodedStatusCode = "";
            try {
                enCodedStatusCode = URLEncoder.encode(inputBox.getText().toString(),"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String toServer = inputBox.getText().toString();
            String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(toServer);
            inputBox.setText("");
            Log.e("Paramssssss",toServerUnicodeEncoded+"");
            Log.e("Paramssssss",receiverId+"");
            ApiClient.getClient().sendMessage(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID),receiverId,toServerUnicodeEncoded,String.valueOf(0)).enqueue(new Callback<SentMessageResponse>() {
                @Override
                public void onResponse(Call<SentMessageResponse> call, Response<SentMessageResponse> response) {
                    SentMessageResponse messageResponse=response.body();
                    if (messageResponse!=null && messageResponse.getIsSuccess())
                    {
                        //CommonUtility.successToast(MessageActivity.this,"Message Sent");
                    }else {
                        try {
                         /*  AlertDialog.Builder builder=new AlertDialog.Builder(MessageActivity.this);
                            builder.setMessage(response.errorBody().string());
                            builder.setTitle("Error Title");
                            builder.create().show();
                            Log.e("COnsoleeeeeeee",response.errorBody().string());*/
                           // CommonUtility.errorToast(MessageActivity.this,response.errorBody().string()+"");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<SentMessageResponse> call, Throwable t) {
                        t.printStackTrace();
                }
            });
        }
    }
    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        backClick();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backClick();
    }
    private void backClick()
    {
        if (isClear)
        {
            Intent intent=new Intent();
            intent.putExtra("CLEAR",true);
            setResult(200,intent);
        }else {
            Intent intent=new Intent();
            intent.putExtra("CLEAR",false);
            setResult(200,intent);
        }

        finish();
    }

}
