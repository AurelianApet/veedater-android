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
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Adapter.DividerItemDecoration;
import digimantra.veedaters.Adapter.PlansAdapter;
import digimantra.veedaters.Model.ChatMode;
import digimantra.veedaters.Model.PlansModel;
import digimantra.veedaters.R;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class PlanListActivity extends AppCompatActivity {

    @BindView(R.id.planList)
    RecyclerView planList;
    PlansAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backButton)
    ImageView backButton;
    private String priceArray[]={"24.99","19.99","14.99"};
    private String month[]={"1","3","6"};
    private String timeArray[]={"For one month","For three month","For six month"};
    private ArrayList<PlansModel> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getData();
        adapter=new PlansAdapter(this,arrayList);
        planList.setLayoutManager(new LinearLayoutManager(this));
        planList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        adapter.setListener(new PlansAdapter.ChoosePlanListener() {
            @Override
            public void buyThisPlan(int position) {
                Intent intent=new Intent(PlanListActivity.this,PaymentInfo.class);
                intent.putExtra("MONTH",month[position]);
                intent.putExtra("PRICE",priceArray[position]);
                intent.putExtra("DURATION",timeArray[position]);
                startActivityForResult(intent,101);
            }
        });
        planList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 101:
            {
                if (data!=null)
                {
                    finish();
                }
            }
        }
    }

    @OnClick(R.id.backButton)
    public void onBackClick()
    {
       finish();
    }
    private void getData()
    {
        arrayList.clear();
        for (int i = 0; i <priceArray.length ; i++) {
            PlansModel model=new PlansModel(priceArray[i],timeArray[i]);
            arrayList.add(model);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(PlanListActivity.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(PlanListActivity.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(PlanListActivity.this,PasscodeActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
