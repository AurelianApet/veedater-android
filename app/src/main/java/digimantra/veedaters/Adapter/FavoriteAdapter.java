package digimantra.veedaters.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;

/**
 * Created by dmlabs on 7/11/17.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    FragmentActivity fragmentActivity;
    ArrayList<UserData.User> arrayList;
    Context context;


    public FavoriteAdapter(ArrayList<UserData.User> arrayList, FragmentActivity fragmentActivity, Context context) {
        this.arrayList=arrayList;
        this.fragmentActivity=fragmentActivity;
        this.context=context;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    OnItemClick onItemClick;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_layout,parent,false);
        return new FavoriteAdapter.ViewHolder(view);
    }
    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindData(holder,arrayList.get(position));
        animate(holder);
    }

    private void bindData(ViewHolder holder, UserData.User user) {
        holder.userName.setText(user.getUsername()!=null? user.getUsername(): "NA");
        if (user.getUseMeta()!=null)
        {
            if (user.getUseMeta().getGender()!=null && !user.getUseMeta().getGender().equals(""))
            {
                if (user.getUseMeta().getGender().equalsIgnoreCase("Men"))
                {
                    holder.genderImage.setImageResource(R.drawable.masculine);
                }else  if (user.getUseMeta().getGender().equalsIgnoreCase("Women")){
                    holder.genderImage.setImageResource(R.drawable.femenine);
                }
            }
            if (user.getUseMeta().getAge()!=null && !user.getUseMeta().getAge().isEmpty())
            {
             holder.userAge.setText(user.getUseMeta().getAge()+"");
            }
           /* if (user.getUseMeta().getLike()==1)
            {
                holder.likeImage.setImageResource(R.drawable.heart_fill);
            }else {
                holder.likeImage.setImageResource(R.drawable.like);
            }*/
        }
        if (user.getUserPhoto()!=null && ! user.getUserPhoto().isEmpty())
        {
            Glide.with(context).load(ApiClient.IMG_BASE + user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.userImage);

            Glide.with(context).load(ApiClient.IMG_BASE + user.getUserPhoto().get(0).getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.chotiImage);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.chotiImage)
        ImageView chotiImage;
        @BindView(R.id.genderImage)
        ImageView genderImage;
        @BindView(R.id.userAge)
        TextView userAge;
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.topLayout)
        RelativeLayout topLayout;
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
        }

        private void setHeight(RelativeLayout topLayout) {
        topLayout.getLayoutParams().height=getDeviceWidth()/2;
        }

    }
    private int getDeviceWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        fragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }
    public interface OnItemClick
    {
        void onClick(int position);
    }
}
