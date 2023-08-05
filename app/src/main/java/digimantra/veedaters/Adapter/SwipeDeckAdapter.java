package digimantra.veedaters.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;

/**
 * Created by dmlabs on 28/12/17.
 */

public class SwipeDeckAdapter extends BaseAdapter {
    private List<UserData.User> arrayList;
    UserData.User user;
    public SwipeDeckAdapter(List<UserData.User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    private Context context;
    @Override
    public int getCount() {
        return arrayList.isEmpty() ? 0 : arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.adapter_layout, parent, false);
        }
      /*  cn.jzvd.JZVideoPlayerStandard videocontroller1=(JZVideoPlayerStandard) itemView.findViewById(R.id.videocontroller1);
        videocontroller1.mRetryBtn.setVisibility(View.GONE);
        videocontroller1.batteryLevel.setVisibility(View.GONE);
        videocontroller1.backButton.setVisibility(View.GONE);*/

        ImageView likeImage = (ImageView) itemView.findViewById(R.id.likeImage);
        ImageView userSmallImage = (ImageView) itemView.findViewById(R.id.userSmallImage);
        ImageView genderImage = (ImageView) itemView.findViewById(R.id.genderImage);
        TextView distance=(TextView)itemView.findViewById(R.id.distance);
        TextView userAge=(TextView)itemView.findViewById(R.id.userAge);
        TextView userName=(TextView)itemView.findViewById(R.id.userName);
        user =arrayList.get(position);
        userName.setText(user.getUsername()!=null? user.getUsername(): "NA");
        if (user!=null)
        {
            distance.setText(user.getMiles()!=null ? user.getMiles()+" mi" :"");
        }
        if (user.getUseMeta()!=null)
        {
            if (user.getUseMeta().getGender()!=null && !user.getUseMeta().getGender().equals(""))
            {
                if (user.getUseMeta().getGender().equalsIgnoreCase("Male"))
                {
                    genderImage.setImageResource(R.drawable.masculine);
                }else {
                    genderImage.setImageResource(R.drawable.femenine);
                }
            }
            if (user.getUseMeta().getLike()==1)
            {
                likeImage.setImageResource(R.drawable.heart_fill);
            }else {
                likeImage.setImageResource(R.drawable.like);
            }
            if (user.getUseMeta().getAge()!=null && !user.getUseMeta().getAge().isEmpty())
            {
                userAge.setText(user.getUseMeta().getAge()+"");
            }
        }
        if (user.getUserPhoto()!=null && ! user.getUserPhoto().isEmpty())
        {
            Glide.with(context).load(ApiClient.IMG_BASE + user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(userSmallImage);
        }
        if (user.getUserVide()!=null )
        {
           /* videocontroller1.setState(JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            videocontroller1.setUp(ApiClient.IMG_BASE+user.getUserVide().getVideoUrl(),JZVideoPlayerStandard.SCREEN_WINDOW_LIST,"Veedaters");
            //videocontroller1.ivThumb.setImageDrawable(context.getDrawable(R.drawable.place_holder));
            Picasso.with(context)
                    .load(R.drawable.place_holder)
                    .into(videocontroller1.thumbImageView);*/
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getUserPhoto()!=null)
                {
                   // onPageClick.onClick(position,videocontroller1);

                }

            }
        });
        return itemView;
    }
}
