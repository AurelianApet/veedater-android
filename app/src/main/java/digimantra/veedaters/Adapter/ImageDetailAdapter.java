package digimantra.veedaters.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.List;

import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.Fragment.DetailImages;
import digimantra.veedaters.Dashboard.ViewImages;
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
 * Created by dmlabs on 8/11/17.
 */





    public class ImageDetailAdapter extends PagerAdapter {

         UserData.User user;
        private Context context;
    ArrayList<String> arrayList;
    int lastposition;
        public ImageDetailAdapter(FragmentManager fm,  ArrayList<String> arrayList, Context context) {
            this.arrayList=arrayList;
            this.context=context;

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
        public Object instantiateItem(final ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.detail_adapter_layout, container,
                    false);
            itemView.setTag(position);
            final ProgressBar progress=(ProgressBar)itemView.findViewById(R.id.progress);
            ImageView image = (ImageView) itemView.findViewById(R.id.image);
            ImageView backButton = (ImageView) itemView.findViewById(R.id.backButton);
            TextView imageCount = (TextView) itemView.findViewById(R.id.imageCount);
           /* ImageView likeButton = (ImageView) itemView.findViewById(R.id.likeButton);
            ImageView userSmallImage = (ImageView) itemView.findViewById(R.id.userSmallImage);
            ImageView genderImage = (ImageView) itemView.findViewById(R.id.genderImage);

            TextView userName = (TextView) itemView.findViewById(R.id.userName);
            TextView userAge = (TextView) itemView.findViewById(R.id.userAge);
            TextView distence = (TextView) itemView.findViewById(R.id.distence);*/
            // Capture position and set to the ImageView
            //image.setImageResource(arrayList.get(position));
           /* if (user!=null)
            {
                distence.setText(user.getMiles()!=null ? user.getMiles()+" mi" :"" +
                        "");
            }*/

                Glide.with(context).load(arrayList.get(position)) .diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
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
                }).into(image);
          /*  if (user.getUserPhoto()!=null && ! user.getUserPhoto().isEmpty())
            {
                Glide.with(context).load(ApiClient.IMG_BASE + user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(userSmallImage);
            }*/
           /* if (user.getUsername()!=null && !user.getUsername().isEmpty())
            {
                userName.setText(user.getUsername()+"");
            }*/
         /*   if (user.getUseMeta().getAge()!=null && !user.getUseMeta().getAge().isEmpty())
            {
                userAge.setText(user.getUseMeta().getAge()+"");
            }*/
          /*  if (user.getUseMeta().getGender()!=null && !user.getUseMeta().getGender().isEmpty())
            {
                if (user.getUseMeta().getGender().equalsIgnoreCase("male"))
                {
                    genderImage.setImageResource(R.drawable.masculine);
                }else {
                    genderImage.setImageResource(R.drawable.femenine);
                }
            }
            if (user.getUseMeta().getLike()==1)
            {
                likeButton.setImageResource(R.drawable.heart_fill);
            }else {
                likeButton.setImageResource(R.drawable.like);
            }
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLike(lastposition,user.getId());
                }
            });*/
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewImages)context).finish();
                }
            });
                imageCount.setText(""+(position+1)+"/"+arrayList.size());
            // Add viewpager_item.xml to ViewPager
            ((ViewPager) container).addView(itemView);
            return itemView;
        }

        @Override
        public int getCount() {
            return arrayList!=null? arrayList.size(): 0;
        }
   private void setLike(int pos, String id)
   {
       CommonUtility.showProgress(context,"Plese wait...");
       int flag=0;
       if (user.getUseMeta().getLike()==1) {
       flag=0;
       }else {
           flag=1;
       }
       final int finalFlag = flag;
       ApiClient.getClient().likeUser(PreferenceConnector.getInstance(context).readInt(KeyValue.USER_ID), Integer.parseInt(id),flag).enqueue(new Callback<UserLikesResponse>() {
           @Override
           public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response) {
               CommonUtility.hideProgress();
               if (response.body().getIsSuccess())
               {
                   user.getUseMeta().setLike(finalFlag);
                  // arrayList.get(position).getUseMeta().setLike(value);
                   notifyDataSetChanged();
               }else {

               }
           }

           @Override
           public void onFailure(Call<UserLikesResponse> call, Throwable t) {
               CommonUtility.hideProgress();
           }
       });
   }

    @Override
    public int getItemPosition(Object object) {

            return POSITION_NONE;


    }
}


