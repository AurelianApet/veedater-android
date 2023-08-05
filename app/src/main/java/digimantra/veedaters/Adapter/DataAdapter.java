package digimantra.veedaters.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Model.PrefData;
import digimantra.veedaters.R;

/**
 * Created by dmlabs on 9/1/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {
    private ArrayList<PrefData> arrayList;

    public void setChooseItem(ChooseItem chooseItem) {
        this.chooseItem = chooseItem;
    }

    ChooseItem chooseItem;
    public DataAdapter(ArrayList<PrefData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    private Context context;
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datacel,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(arrayList.get(position).getName());
        if (arrayList.get(position).isSelected())
        {
            holder.tick.setVisibility(View.VISIBLE);
        }else {
            holder.tick.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList!=null? arrayList.size():0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.tick)
        ImageView tick;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseItem.onSelect(getLayoutPosition());
                }
            });
        }
    }
    public interface ChooseItem
    {
        void onSelect(int position);
    }
}
