package digimantra.veedaters.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Model.ChatMode;
import digimantra.veedaters.Model.UserModel;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;



/**
 * Created by dmlabs on 9/11/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>
{

    ArrayList<ChatMode.UserMessage> arrayList;
    Context context;

    public UserListAdapter(ArrayList<ChatMode.UserMessage> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setListener(OnitemClickListener listener) {
        this.listener = listener;
    }

    OnitemClickListener listener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_update,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (String.valueOf(PreferenceConnector.getInstance(context).readInt(KeyValue.USER_ID)).equalsIgnoreCase(arrayList.get(position).getInfo().getSender().getId()))
        {

            String myString= "";
            holder.time.setText(setDate(arrayList.get(position).getInfo().getCreateddate()));
            try {
               // myString= URLDecoder.decode(arrayList.get(position).getMessageBody(),"UTF-8");
               // holder.lastMessage.setText(myString);
                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(arrayList.get(position).getMessageBody());
                holder.lastMessage.setText(fromServerUnicodeDecoded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (arrayList.get(position).getInfo().getReciever()!=null)
            holder.userName.setText(arrayList.get(position).getInfo().getReciever().getUsername()+"");
            //  holder.userImage.setImageResource(arrayList.get(position).getImage());
            if (arrayList.get(position).getInfo().getSender()!=null && arrayList.get(position).getInfo().getReciever().getUserPhoto()!=null)
            {

                        Picasso.with(context).load(ApiClient.IMG_BASE+arrayList.get(position).getInfo().getReciever().getUserPhoto().getPhotoPath())
                                .fit().into(holder.userImage);

            }
        }else {
          //  holder.lastMessage.setText(arrayList.get(position).getMessageBody()+"");
            String myString= "";
            try {
              //  myString= URLDecoder.decode(arrayList.get(position).getMessageBody(),"UTF-8");
                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(arrayList.get(position).getMessageBody());
                holder.lastMessage.setText(fromServerUnicodeDecoded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.time.setText(setDate(arrayList.get(position).getInfo().getCreateddate()));
            if (arrayList.get(position).getInfo().getSender()!=null)
                holder.userName.setText(arrayList.get(position).getInfo().getSender().getUsername()+"");
            //  holder.userImage.setImageResource(arrayList.get(position).getImage());
            if (arrayList.get(position).getInfo().getSender()!=null && arrayList.get(position).getInfo().getSender().getSuserPhoto()!=null)
            {
                if (arrayList.get(position).getInfo().getSender().getSuserPhoto().getPhotoPath()!=null)
                    Picasso.with(context).load(ApiClient.IMG_BASE+arrayList.get(position).getInfo().getSender().getSuserPhoto().getPhotoPath())
                            .fit().into(holder.userImage);
            }
        }

    }
    private String setDate(String messageTime)
    {
        //2018-01-30 07:22:01
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("IST"));
        if (messageTime.isEmpty())
        {
            return "";
        }
        Date messagedate=null;
        Date crrentDate=null;

        try {
            messagedate = format.parse(messageTime);
            Calendar cal = Calendar.getInstance();
            String crrentDateString = format.format(cal.getTime());
            crrentDate = format.parse(crrentDateString);
            long diff = crrentDate.getTime() - messagedate.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            int diffInDays = (int) ((crrentDate.getTime() - messagedate.getTime()) / (1000 * 60 * 60 * 24));

            if (diffInDays > 1) {
                return String.valueOf(diffInDays)+" day ago";
            } else if (diffHours > 24) {
                int hour= (int) (diffHours/24);
                return String.valueOf(hour)+" day ago";
            } else if (diffHours == 24) {

                return String.valueOf(1)+" day ago";
            }else if (diffHours <24) {

                return String.valueOf(diffHours)+" hour ago";
            }
            else if (diffMinutes>60)
            {
                int min= (int) (diffMinutes/60);
                return String.valueOf(min)+" min ago";
            }else if (diffMinutes<60)
            {
                return String.valueOf(diffMinutes)+" min ago";
            }else if (diffSeconds>60)
            {
                int sec= (int) (diffSeconds/60);
                return String.valueOf(sec)+" min ago";
            }else {
                return "just now";
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
            return "";
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.deleteButton)
        ImageView deleteButton;
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.lastMessage)
        TextView lastMessage;
        @BindView(R.id.swipeLayout)
        public SwipeLayout swipeLayout;
       /* @BindView(R.id.left_Layout)
        LinearLayout left_Layout;*/
        @BindView(R.id.right_Layout)
        LinearLayout right_Layout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            /*swipeLayout.addDrag(SwipeLayout.DragEdge.Left, left_Layout);*/
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, right_Layout);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(getLayoutPosition());
                }
            });
          itemView.setOnTouchListener(new View.OnTouchListener() {
              float x1,x2,y1,y2,t1,t2;
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  switch (event.getAction()) {

                      case MotionEvent.ACTION_DOWN:
                          x1 = event.getX();
                          y1 = event.getY();
                          t1 = System.currentTimeMillis();
                          return true;
                      case MotionEvent.ACTION_UP:
                          x2 = event.getX();
                          y2 = event.getY();
                          t2 = System.currentTimeMillis();

                          if (x1 == x2 && y1 == y2 && (t2 - t1) < 200) {
                              listener.onClick(getLayoutPosition());
                          } else if ((t2 - t1) >= 400) {

                          } else if (x1 > x2) {

                          } else if (x2 > x1) {

                          }


                          return true;
                  }

                  return false;
              }
          });
           /* itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });*/

        }
    }
    public interface OnitemClickListener
    {
        void onClick(int position);
        void onDelete(int position);

    }
}
