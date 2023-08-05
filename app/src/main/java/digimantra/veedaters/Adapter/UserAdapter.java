package digimantra.veedaters.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import digimantra.veedaters.Dashboard.Fragment.DiscoveryFragment;
import digimantra.veedaters.Model.User;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;

/**
 * Created by dmlabs on 6/11/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<UserData.User> arrayList;
    FragmentActivity fragment;
    Context  context;
    CustomFilter filter;
    public void setOnItemClick(UserAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    OnItemClick onItemClick;
    public UserAdapter(List<UserData.User> arrayList,FragmentActivity fragment,Context context,DiscoveryFragment discoveryFragment)
    {
        this.arrayList=arrayList;
        this.fragment=fragment;
        this.context=context;
        filter=new CustomFilter(this,discoveryFragment);
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //holder.userImage.setImageResource(arrayList.get(position));
        bindData(arrayList.get(position),holder);
        animate(holder);
    }
    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
      //  final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
    private void bindData(UserData.User user, final ViewHolder holder) {
        holder.userName.setText(user.getUsername()!=null? user.getUsername(): "NA");
        if (user.getUseMeta()!=null)
        {
            if (user.getUseMeta().getGender()!=null && !user.getUseMeta().getGender().equals(""))
            {
                if (user.getUseMeta().getGender().equalsIgnoreCase("Men"))
                {
                    holder.genderImage.setImageResource(R.drawable.masculine);
                }else if (user.getUseMeta().getGender().equalsIgnoreCase("Women"))
                {
                    holder.genderImage.setImageResource(R.drawable.femenine);
                }
            }
            if (user.getUseMeta().getLike()==1)
            {
                holder.likeImage.setImageResource(R.drawable.heart_fill);
            }else {
                holder.likeImage.setImageResource(R.drawable.like);
            }
            if (user.getUseMeta().getAge()!=null && !user.getUseMeta().getAge().isEmpty())
            {
                holder.userAge.setText(user.getUseMeta().getAge()+"");
            }
        }

        if (user.getUserPhoto()!=null && ! user.getUserPhoto().isEmpty())
        {
            Glide.with(context).load(ApiClient.IMG_BASE + user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.userImage);

            Glide.with(context).load(ApiClient.IMG_BASE + user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            })
                    .into(holder.userSmallImage);

        }
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }


    private void updateList(List<UserData.User> arrayList)
    {
        this.arrayList=arrayList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.userSmallImage)
        CircleImageView userSmallImage;
       @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.genderImage)
        ImageView genderImage;
        @BindView(R.id.likeImage)
        ImageView likeImage;
        @BindView(R.id.userAge)
        TextView userAge;
        @BindView(R.id.topLayout)
        RelativeLayout topLayout;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            setHeight(topLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClick(getLayoutPosition());
                }
            });
            likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onLikeclick(getLayoutPosition());
                }
            });
        }

        private void setHeight(RelativeLayout topLayout)
        {
        topLayout.getLayoutParams().height=getDeviceWidth()/2;
        }
    }
    private int getDeviceWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        fragment.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }
    public interface OnItemClick
    {
        void onClick(int position);
        void onLikeclick(int position);
    }
    public class CustomFilter extends Filter
    {
        UserAdapter dashboardAdapter;
        DiscoveryFragment fragment;
        public CustomFilter(UserAdapter dashboardAdapter, DiscoveryFragment fragment) {
            this.dashboardAdapter=dashboardAdapter;
            this.fragment=fragment;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            fragment.filterList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length()==0)
            {
                fragment.filterList.addAll(fragment.arrayList);

            }else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final UserData.User mWords : fragment.arrayList) {
                    if (mWords.getUsername().toLowerCase().startsWith(filterPattern)) {
                        fragment.filterList.add(mWords);
                    }
                }
            }
            results.values = fragment.filterList;
            results.count = fragment.filterList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            updateList(fragment.filterList);
            this.dashboardAdapter.notifyDataSetChanged();
        }
    }
}
