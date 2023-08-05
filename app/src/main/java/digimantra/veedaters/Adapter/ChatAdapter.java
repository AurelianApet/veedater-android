package digimantra.veedaters.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.library.bubbleview.BubbleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.Model.ChatDetail;
import digimantra.veedaters.Model.ChatMode;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

/**
 * Created by dmlabs on 10/11/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatDetail.Messages> arrayList;
    private Context context;

    public ChatAdapter(ArrayList<ChatDetail.Messages> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_layout,parent,false);
            return new SenderHolder(view);
        }else if (viewType==1)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_message,parent,false);
            return new ReceiverHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        holder.itemView.setTag(position);
        if (holder instanceof SenderHolder)
        {
            SenderHolder senderHolder=(SenderHolder)holder;
            if (arrayList.get(position).getMessageBody()!=null && !arrayList.get(position).getMessageBody().equalsIgnoreCase("")&& !arrayList.get(position).getMessageBody().isEmpty())
            {
                String myString= "";
                try {
                   // myString=URLDecoder.decode(arrayList.get(position).getMessageBody(),"UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(arrayList.get(position).getMessageBody());
                senderHolder.messageLayout.setVisibility(View.VISIBLE);
                senderHolder.senderMessage.setText(fromServerUnicodeDecoded);
            }else {
                senderHolder.messageLayout.setVisibility(View.GONE);
            }
            if (arrayList.get(position).getMessagePhoto()!=null && arrayList.get(position).getMessagePhoto().getPhoto_path()!=null)
            {
                if (arrayList.get(position).getMessagePhoto().getPhoto_path().startsWith("message"))
                {
                    Glide.with(context).load(ApiClient.IMG_BASE+arrayList.get(position).getMessagePhoto().getPhoto_path()).into(senderHolder.userImage);
                    senderHolder.userImage.setVisibility(View.VISIBLE);
                    senderHolder.messageLayout.setVisibility(View.GONE);
                }

                //senderHolder.userImage.setImageURI(Uri.parse(arrayList.get(position).getMessagePhoto().getPhoto_path()));
            }else {
            }

            if (VeeDatersApp.getMyPic()!=null)
            Picasso.with(context).load(ApiClient.IMG_BASE+VeeDatersApp.getMyPic()).into(senderHolder.senderImage);
        }else if (holder instanceof ReceiverHolder)
        {
            ReceiverHolder receiverHolder=(ReceiverHolder)holder;
            if (arrayList.get(position).getMessageBody()!=null && !arrayList.get(position).getMessageBody().equalsIgnoreCase("") && !arrayList.get(position).getMessageBody().isEmpty())
            {
                receiverHolder.receiveMessage.setVisibility(View.VISIBLE);
                String myString= "";
                try {
                    //myString=URLDecoder.decode(arrayList.get(position).getMessageBody(),"UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(arrayList.get(position).getMessageBody());
                receiverHolder.receiveMessage.setText(fromServerUnicodeDecoded);
            }else {
                receiverHolder.receiveMessage.setVisibility(View.GONE);
            }

            if (arrayList.get(position).getMessagePhoto()!=null && arrayList.get(position).getMessagePhoto().getPhoto_path()!=null)
            {

                if (arrayList.get(position).getMessagePhoto().getPhoto_path().startsWith("message"))
                {
                    Glide.with(context).load(ApiClient.IMG_BASE+arrayList.get(position).getMessagePhoto().getPhoto_path()).into(receiverHolder.userImage);
                    receiverHolder.userImage.setVisibility(View.VISIBLE);
                    receiverHolder.receiveMessage.setVisibility(View.GONE);
                }
               /* else {
                Toast.makeText(context,arrayList.get(position).getMessagePhoto().getPhoto_path()+ "jjj", Toast.LENGTH_LONG).show();
                Glide.with(context).load(Uri.parse(arrayList.get(position).getMessagePhoto().getPhoto_path())).into(receiverHolder.userImage);
                    receiverHolder.userImage.setVisibility(View.VISIBLE);
                    receiverHolder.receiveMessage.setVisibility(View.GONE);
            }*/
                //senderHolder.userImage.setImageURI(Uri.parse(arrayList.get(position).getMessagePhoto().getPhoto_path()));
            }else {
                //receiverHolder.userImage.setVisibility(View.GONE);
            }
            if (VeeDatersApp.getSenderPic()!=null)
                Picasso.with(context).load(ApiClient.IMG_BASE+VeeDatersApp.getSenderPic()).into(receiverHolder.receiverImage);
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        if (arrayList.get(position).getInfo().getSender().getId().equalsIgnoreCase(String.valueOf(PreferenceConnector.getInstance(context).readInt(KeyValue.USER_ID))))
        {
            return 0;
        }else
        {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class SenderHolder extends RecyclerView.ViewHolder
    {
        TextView senderMessage;
        CircleImageView senderImage;
        ImageView userImage;
        LinearLayout messageLayout;
        public SenderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            senderMessage=(TextView)itemView.findViewById(R.id.senderMessage);
            senderImage=(CircleImageView) itemView.findViewById(R.id.senderImage);
            userImage=(ImageView) itemView.findViewById(R.id.userImage);
            messageLayout=(LinearLayout) itemView.findViewById(R.id.messageLayout);
        }
    }
    public class ReceiverHolder extends RecyclerView.ViewHolder
    {
        TextView receiveMessage;
        CircleImageView receiverImage;
        ImageView userImage;
        public ReceiverHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(itemView);
            receiveMessage=(TextView)itemView.findViewById(R.id.receiveMessage);
            receiverImage=(CircleImageView) itemView.findViewById(R.id.receiverImage);
            userImage=(ImageView) itemView.findViewById(R.id.userImage);
        }
    }
}
