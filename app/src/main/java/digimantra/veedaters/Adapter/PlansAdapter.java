package digimantra.veedaters.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Model.PlansModel;
import digimantra.veedaters.R;

/**
 * Created by dmlabs on 4/1/18.
 */

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.Holder> {
    Context context;

    public void setListener(ChoosePlanListener listener) {
        this.listener = listener;
    }

    ChoosePlanListener listener;
    public PlansAdapter(Context context, ArrayList<PlansModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<PlansModel> arrayList;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.cell_for_payment,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        holder.price.setText("$"+arrayList.get(position).getPrice()+"/mo");
        holder.time_duration.setText(arrayList.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return arrayList!=null? arrayList.size():0;
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.time_duration)
        TextView time_duration;
        public Holder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.buyThisPlan(getLayoutPosition());
                }
            });

        }
    }
    public interface ChoosePlanListener
    {
        void buyThisPlan(int position);
    }
}
