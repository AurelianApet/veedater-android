package digimantra.veedaters.Adapter;

import android.content.Context;
import android.media.ThumbnailUtils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.Fragment.DetailImages;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.Model.UserLikesResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 6/11/17.
 */

public class ImageAdapter extends PagerAdapter {
    private List<UserData.User> arrayList;
   // cn.jzvd.JZVideoPlayerStandard videocontroller1;
    UserData.User user;
    boolean hasSubscriptio;
    public void setOnPageClick(OnPageClick onPageClick) {
        this.onPageClick = onPageClick;
    }
    int finalFlag;
    OnPageClick onPageClick;
    private Context context;
    public ImageAdapter(FragmentManager fm, List<UserData.User> arrayList, Context context,boolean hasSubscriptio)
    {
        this.arrayList=arrayList;
        this.context=context;
        this.hasSubscriptio=hasSubscriptio;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
     LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_layout, container,
                false);
      /*  videocontroller1=(JZVideoPlayerStandard) itemView.findViewById(R.id.videocontroller1);
        videocontroller1.mRetryBtn.setVisibility(View.GONE);
        videocontroller1.batteryLevel.setVisibility(View.GONE);
        videocontroller1.backButton.setVisibility(View.GONE);*/
        final ProgressBar progress=(ProgressBar)itemView.findViewById(R.id.progress);
        ImageView likeImage = (ImageView) itemView.findViewById(R.id.likeImage);
        ImageView mainIMage = (ImageView) itemView.findViewById(R.id.mainIMage);
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
                if (user.getUseMeta().getGender().equalsIgnoreCase("Men"))
                {
                    genderImage.setImageResource(R.drawable.masculine);
                }else if (user.getUseMeta().getGender().equalsIgnoreCase("Women")){
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
            Glide.with(context).load(ApiClient.IMG_BASE+user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    progress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progress.setVisibility(View.GONE);
                    return false;
                }
            }).into(mainIMage);
        }

        if (user.getUserVide()!=null )
        {
           /* videocontroller1.setState(JZVideoPlayerStandard.CURRENT_STATE_NORMAL);

            videocontroller1.setUp(ApiClient.IMG_BASE+user.getUserVide().getVideoUrl(),JZVideoPlayerStandard.SCREEN_WINDOW_LIST,"Veedaters");*/
            //videocontroller1.ivThumb.setImageDrawable(context.getDrawable(R.drawable.place_holder));
          /*  Picasso.with(context)
                    .load(R.drawable.place_holder)
                    .into(videocontroller1.thumbImageView);*/
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getUserPhoto()!=null)
                {
                    onPageClick.onClick(position);

                }

            }
        });
        userSmallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        likeImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onPageClick.makePayment(position);
                //setLike(position,user.getId());
            }
        });
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
        return itemView;
    }
    private void setLike(final int pos, String id)
    {

    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface OnPageClick
    {
        void onClick(int pos);
        void makePayment(int pos);
    }
}
