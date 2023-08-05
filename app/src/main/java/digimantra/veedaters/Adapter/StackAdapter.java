package digimantra.veedaters.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import digimantra.veedaters.Dashboard.ViewImages;
import digimantra.veedaters.R;

/**
 * Created by dmlabs on 9/1/18.
 */

public class StackAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private Context context;
    public StackAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.items = objects;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.detail_adapter_layout, null);
        }

        final ProgressBar progress=(ProgressBar)itemView.findViewById(R.id.progress);
        ImageView image = (ImageView) itemView.findViewById(R.id.image);
        ImageView backButton = (ImageView) itemView.findViewById(R.id.backButton);
        TextView imageCount = (TextView) itemView.findViewById(R.id.imageCount);
        Glide.with(context).load(items.get(position)) .diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewImages)context).finish();
            }
        });
        imageCount.setText(""+(position+1)+"/"+items.size());
        return itemView;
    }
}
