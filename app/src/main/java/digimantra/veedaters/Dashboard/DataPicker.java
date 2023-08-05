package digimantra.veedaters.Dashboard;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Adapter.DataAdapter;
import digimantra.veedaters.Adapter.DividerItemDecoration;
import digimantra.veedaters.Dashboard.Fragment.EditProfile;
import digimantra.veedaters.Dashboard.Fragment.PreferencesFragment;
import digimantra.veedaters.Model.PrefData;
import digimantra.veedaters.R;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class DataPicker extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.done)
    TextView done;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    DataAdapter adapter;
    String dataToPass;
    ArrayList<PrefData> arrayList=new ArrayList<>();
    ArrayList<Integer> integers=new ArrayList<>();
    PrefData prefData;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_picker);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getIntent().hasExtra("DATA"))
        {
            arrayList=getIntent().getParcelableArrayListExtra("DATA");
            int number=getIntent().getIntExtra("TYPE",1);
            String data=getIntent().getStringExtra("PREVIOUS");
            if (arrayList!=null && data!=null)
            {
                for (int i = 0; i <arrayList.size() ; i++) {
                    if (arrayList.get(i).getName().equalsIgnoreCase(data))
                    {
                        arrayList.get(i).setSelected(true);
                        integers.add(0,i);
                    }
                }
            }
            if (number== EditProfile.GENDER)
            {
                title.setText("InterestedIn");

            }
            if (number== EditProfile.SPORT)
            {
                title.setText("Sport");

            }
            if (number== EditProfile.INCOME)
            {
                title.setText("Income");

            }
            if (number== EditProfile.RELIGION)
            {
                title.setText("Religion");

            }
            if (number== EditProfile.STYLE)
            {
                title.setText("Style");

            }
            if (number== EditProfile.NATION)
            {
                title.setText("Nation");

            }
            if (number== EditProfile.STATUS)
            {
                title.setText("Status");

            }

        }
        adapter=new DataAdapter(arrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        adapter.setChooseItem(new DataAdapter.ChooseItem() {
            @Override
            public void onSelect(int position) {
                if (arrayList.get(position).isSelected())
                {
                    if (!integers.isEmpty())
                    {
                        integers.clear();
                    }
                    arrayList.get(position).setSelected(false);
                    adapter.notifyDataSetChanged();
                }else {
                    if (!integers.isEmpty())
                    {
                        arrayList.get(integers.get(0)).setSelected(false);
                        integers.clear();
                        integers.add(0,position);
                    }else {
                        integers.add(0,position);
                    }
                    prefData=arrayList.get(position);
                    dataToPass=arrayList.get(position).getName();
                    arrayList.get(position).setSelected(true);
                    pos=position;
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    @OnClick(R.id.done)
    public void setData()
    {
        result();
    }
    private void result()
    {

        Intent intent=new Intent();
        intent.putExtra("RESULT",dataToPass);
        intent.putExtra("DATA",prefData);
        intent.putExtra("POS",pos);
        setResult(201,intent);
        finish();
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        result();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(DataPicker.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(DataPicker.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(DataPicker.this,PasscodeActivity.class);
                    startActivity(intent);
                }


            }
        }
        myApp.stopActivityTransitionTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((VeeDatersApp)this.getApplication()).startActivityTransitionTimer();
    }
}
