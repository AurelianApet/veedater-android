package digimantra.veedaters.Dashboard.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.github.bassaer.chatmessageview.model.IChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.github.bassaer.chatmessageview.view.MessageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Dashboard.MyMessageStatusFormatter;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.Model.ChatDetail;
import digimantra.veedaters.Model.MessageList;
import digimantra.veedaters.Model.MessageUser;
import digimantra.veedaters.Model.SentMessageResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageNewActivity extends AppCompatActivity {

   // private ArrayList<User> mUsers;
   @VisibleForTesting
   protected static final int RIGHT_BUBBLE_COLOR = R.color.pink;
    @VisibleForTesting
    protected static final int LEFT_BUBBLE_COLOR = R.color.gray300;
    @VisibleForTesting
    protected static final int BACKGROUND_COLOR = R.color.white;
    @VisibleForTesting
    protected static final int SEND_BUTTON_COLOR = R.color.pink_fade;
    @VisibleForTesting
    protected static final int SEND_ICON = R.drawable.send_new;
    @VisibleForTesting
    protected static final int OPTION_BUTTON_COLOR = R.color.teal500;
    @VisibleForTesting
    protected static final int RIGHT_MESSAGE_TEXT_COLOR = Color.WHITE;
    @VisibleForTesting
    protected static final int LEFT_MESSAGE_TEXT_COLOR = Color.BLACK;
    @VisibleForTesting
    protected static final int USERNAME_TEXT_COLOR = R.color.gray_new;
    @VisibleForTesting
    protected static final int SEND_TIME_TEXT_COLOR = R.color.gray_new;
    @VisibleForTesting
    protected static final int DATA_SEPARATOR_COLOR = R.color.gray_new;
    @VisibleForTesting
    protected static final int MESSAGE_STATUS_TEXT_COLOR = R.color.gray_new;
    @VisibleForTesting
    protected static final String INPUT_TEXT_HINT = "New message";
    @VisibleForTesting
    protected static final int MESSAGE_MARGIN = 5;
    @BindView(R.id.chat_view)
   public ChatView mChatView;
    private ArrayList<MessageUser> mUsers;
    private MessageList mMessageList;
    private String receiverId;
    Message sendMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_new);
        ButterKnife.bind(this);
        initUsers();
        loadMessages();
        mChatView.setRightBubbleColor(ContextCompat.getColor(this,RIGHT_BUBBLE_COLOR));
        mChatView.setLeftBubbleColor(ContextCompat.getColor(this, LEFT_BUBBLE_COLOR));
        mChatView.setBackgroundColor(ContextCompat.getColor(this, BACKGROUND_COLOR));

        mChatView.setSendIcon(SEND_ICON);
        mChatView.setOptionButtonColor(OPTION_BUTTON_COLOR);
        mChatView.setRightMessageTextColor(RIGHT_MESSAGE_TEXT_COLOR);
        mChatView.setLeftMessageTextColor(LEFT_MESSAGE_TEXT_COLOR);
        mChatView.setUsernameTextColor(USERNAME_TEXT_COLOR);
        mChatView.setSendTimeTextColor(SEND_TIME_TEXT_COLOR);
        mChatView.setDateSeparatorColor(DATA_SEPARATOR_COLOR);
        mChatView.setMessageStatusTextColor(MESSAGE_STATUS_TEXT_COLOR);
        mChatView.setInputTextHint(INPUT_TEXT_HINT);
        mChatView.setMessageMarginTop(MESSAGE_MARGIN);
        mChatView.setMessageMarginBottom(MESSAGE_MARGIN);
        mChatView.setMaxInputLine(5);
        mChatView.setUsernameFontSize(getResources().getDimension(R.dimen.font_small));
        mChatView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mChatView.setInputTextColor(ContextCompat.getColor(this, R.color.black));
        mChatView.setInputTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUsers();
                 sendMessage = new Message.Builder()
                        .setUser(mUsers.get(0))
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(false)
                        .setStatusIconFormatter(new MyMessageStatusFormatter(MessageNewActivity.this))
                        .setStatusTextFormatter(new MyMessageStatusFormatter(MessageNewActivity.this))
                        .setMessageStatusType(Message.Companion.getMESSAGE_STATUS_ICON())
                        .setStatus(MyMessageStatusFormatter.STATUS_DELIVERING)
                        .build();

                //Set random status(Delivering, delivered, seen or fail)
                int messageStatus = new Random().nextInt(4);
                sendMessage.setStatus(MyMessageStatusFormatter.STATUS_DELIVERED);
                mChatView.send(sendMessage);
                mMessageList.add(sendMessage);


                ApiClient.getClient().sendMessage(PreferenceConnector.getInstance(MessageNewActivity.this).readInt(KeyValue.USER_ID),receiverId,mChatView.getInputText(),String.valueOf(0)).enqueue(new Callback<SentMessageResponse>() {
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
                //Reset edit text
                mChatView.setInputText("");
            }
        });
    }
   /* private void sendImage()
    {
        Uri uri = data.getData();
        try {
            Bitmap picture = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Message message = new Message.Builder()
                    .setRightMessage(true)
                    .setMessageText(Message.Type.PICTURE.name())
                    .setUser(mUsers.get(0))
                    .hideIcon(true)
                    .setPicture(picture)
                    .setType(Message.Type.PICTURE)
                    .setStatusIconFormatter(new MyMessageStatusFormatter(MessengerActivity.this))
                    .setMessageStatusType(Message.Companion.getMESSAGE_STATUS_ICON())
                    .setStatus(MyMessageStatusFormatter.STATUS_DELIVERED)
                    .build();
            mChatView.send(message);
            //Add message list
            mMessageList.add(message);
    }
    */
    private void initUsers() {
        mUsers = new ArrayList<>();
        //User id
        if (getIntent().hasExtra(KeyValue.RECEIVER_ID))
        {
            receiverId=getIntent().getStringExtra(KeyValue.RECEIVER_ID);
        }
        if (getIntent().hasExtra("senderId"))
        {
            receiverId=getIntent().getStringExtra("senderId");
        }
        int myId = PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID);
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = VeeDatersApp.getMyName();

        int yourId = Integer.parseInt(receiverId);
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = VeeDatersApp.getYourName();

        final MessageUser me = new MessageUser(myId, myName, myIcon);
        final MessageUser you = new MessageUser(yourId, yourName, yourIcon);

        mUsers.add(me);
        mUsers.add(you);
    }
    private void loadMessages() {
        if (mMessageList == null) {
            mMessageList = new MessageList();
        }
        getMessages();
    }

    private void getMessages()
    {
        CommonUtility.showProgress(this,"Please wait...");
        Log.e("MYIDDDDDDDDDDD",PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)+"");
        Log.e("dujiiiiiiiIDDDDDD",receiverId+"");
        ApiClient.getClient().messageDetail(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID),receiverId).enqueue(new Callback<ChatDetail>() {
            @Override
            public void onResponse(Call<ChatDetail> call, Response<ChatDetail> response) {
                ChatDetail chatDetail=response.body();
                if (chatDetail!=null && chatDetail.getList()!=null)
                {
                    if (!chatDetail.getList().isEmpty())
                    {
                       setDataToMessageList(chatDetail.getList());
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

    private void setDataToMessageList(List<ChatDetail.Messages> list) {
        List<Message> messagesList = new ArrayList<>();
        for (int i = 0; i <list.size() ;i++) {
            ChatDetail.Messages messages=list.get(i);
            if (messages.getInfo()!=null)
            {
                Message message;
                if (messages.getInfo().getSender().getId().equalsIgnoreCase(receiverId))
                {
                    message    = new Message.Builder()
                            .setUser(mUsers.get(1))
                            .setRightMessage(false)
                            .setMessageText(messages.getMessageBody())
                            .hideIcon(false)
                            .setStatusIconFormatter(new MyMessageStatusFormatter(MessageNewActivity.this))
                            .setStatusTextFormatter(new MyMessageStatusFormatter(MessageNewActivity.this))
                            .setMessageStatusType(Message.Companion.getMESSAGE_STATUS_ICON())
                            .setStatus(MyMessageStatusFormatter.STATUS_DELIVERED)
                            .build();
                }else {
                     message = new Message.Builder()
                            .setUser(mUsers.get(0))
                            .setRightMessage(true)
                            .setMessageText(messages.getMessageBody())
                            .hideIcon(false)
                            .setStatusIconFormatter(new MyMessageStatusFormatter(MessageNewActivity.this))
                            .setStatusTextFormatter(new MyMessageStatusFormatter(MessageNewActivity.this))
                            .setMessageStatusType(Message.Companion.getMESSAGE_STATUS_ICON())
                            .setStatus(MyMessageStatusFormatter.STATUS_DELIVERED)
                            .build();
                    //Set to chat view

                    //Add message list
                }
                messagesList.add(message);
            }
        }
        MessageView messageView = mChatView.getMessageView();
        messageView.init(messagesList);
        messageView.setSelection(messageView.getCount() - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
