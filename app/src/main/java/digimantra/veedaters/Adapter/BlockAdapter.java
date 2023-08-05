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

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Model.BlockUserList;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;

/**
 * Created by dmlabs on 1/12/17.
 */

public class BlockAdapter extends  RecyclerView.Adapter<BlockAdapter.ViewHolder> {
    ArrayList<BlockUserList.BlockedUser> arrayList;
FragmentActivity fragment;
    Context context;
    public BlockAdapter(ArrayList<BlockUserList.BlockedUser> arrayList, FragmentActivity fragment, Context context) {
        this.arrayList = arrayList;
        this.fragment = fragment;
       this.context=context;
    }

    public void setOnItemClick(BlockAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    BlockAdapter.OnItemClick onItemClick;
    @Override
    public BlockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.block_layout,parent,false);
        return new BlockAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BlockAdapter.ViewHolder holder, int position) {
        bindData(holder,arrayList.get(position));
    }
    private void bindData(ViewHolder holder, BlockUserList.BlockedUser blockedUser)
    {
        if (blockedUser!=null)
        {
            holder.blockUserName.setText(blockedUser.getUsername()!=null? blockedUser.getUsername() :"NA");
            if (blockedUser.getPhotoPath()!=null)
            {
                Glide.with(fragment).load(ApiClient.IMG_BASE + blockedUser.getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.userImage);

                Glide.with(fragment).load(ApiClient.IMG_BASE + blockedUser.getPhotoPath()) .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.blockUserImageSmall);
            }
            if (blockedUser.getGender()!=null)
            {
                if (blockedUser.getGender().equalsIgnoreCase("Man"))
                {
                    holder.blockUserGender.setImageResource(R.drawable.masculine);
                }else if (blockedUser.getGender().equalsIgnoreCase("Woman")){
                    holder.blockUserGender.setImageResource(R.drawable.femenine);
                }
            }
            if (blockedUser.getDob()!=null && !blockedUser.getDob().isEmpty())
            {
                holder.blockUserAge.setText(blockedUser.getDob()+"");
            }
        }
        animate(holder);
    }
    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.blockImage)
        ImageView blockImage;
        @BindView(R.id.blockUserName)
        TextView blockUserName;
        @BindView(R.id.blockUserAge)
        TextView blockUserAge;
        @BindView(R.id.blockUserGender)
        ImageView blockUserGender;
        @BindView(R.id.blockUserImageSmall)
        ImageView blockUserImageSmall;
        @BindView(R.id.rootLayout)
        RelativeLayout rootLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            setHeight(rootLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClick(v,getLayoutPosition());
                }
            });
        }

        private void setHeight(RelativeLayout rootLayout) {
            rootLayout.getLayoutParams().height=getDeviceWidth()/2;
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
        void onClick(View view,int position);
    }
}
