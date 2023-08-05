package digimantra.veedaters.Dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.StripeResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInfo extends AppCompatActivity
{

    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.makePayment)
    TextView makePayment;
    @BindView(R.id.cvvTxt)
    EditText cvvTxt;
    @BindView(R.id.yearTxt)
    EditText yearTxt;
    @BindView(R.id.monthTxt)
    EditText monthTxt;
    @BindView(R.id.cardHolderName)
    EditText cardHolderName;
    @BindView(R.id.cardNumber)
    EditText cardNumber;
    Stripe stripe;
    String plan,price,duration;
    @BindView(R.id.price)
    TextView planPrice;
    @BindView(R.id.time_duration)
    TextView time_duration;
    @BindView(R.id.activity_payment_info)
    LinearLayout activity_payment_info;
    private  String cardnumber,holdername,month,year,cvv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);
        ButterKnife.bind(this);
        plan=getIntent().getStringExtra("MONTH");
        price=getIntent().getStringExtra("PRICE");
        duration=getIntent().getStringExtra("DURATION");
        planPrice.setText("$"+price);
        time_duration.setText(duration);
        stripe=new Stripe(PaymentInfo.this,"pk_test_tZGIOK0ezwLGPerGW5xnKOmP");
    }
    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        finish();
    }
    @OnClick(R.id.makePayment)
    public void makePayment()
    {
        getCardInfo();
    }
    private void getCardInfo() {
        if (!getData(cardNumber))
        {
            cardNumber.setError("Enter card number");
            return;
        }
        if (!getData(cardHolderName))
        {
            cardHolderName.setError("Enter card holder name");
            return;
        }
        if (!getData(monthTxt))
        {
            monthTxt.setError("Enter valid month");
            return;
        }
        if (!getData(yearTxt))
        {
            yearTxt.setError("Enter valid year");
            return;
        }
        if (!getData(cvvTxt))
        {
            cvvTxt.setError("Enter valid cvv number");
            return;
        }
        Card card=new Card(cardNumber.getText().toString(),Integer.parseInt(monthTxt.getText().toString()),Integer.parseInt(yearTxt.getText().toString()) ,cvvTxt.getText().toString());
        card.setCurrency("usd");
        if (!card.validateNumber())
        {
            cardNumber.setError("Enter valid care number");
            return;
        }
        if (!card.validateCVC())
        {
            cvvTxt.setError("Enter valid cvv number");
            return;
        }
        if (!card.validateExpMonth())
        {
            monthTxt.setError("Enter valid month");
            return;
        }
        CommonUtility.showProgress(PaymentInfo.this,"Please wait");
        stripe.createToken(card, new TokenCallback() {
            @Override
            public void onError(Exception error) {
                CommonUtility.hideProgress();
            }

            @Override
            public void onSuccess(Token token) {
                CommonUtility.hideProgress();
                submitTokenToServer(token.getId());
            }
        });
    }
    public void onMonthClick()
    {
        RelativeLayout linearLayout = new RelativeLayout(this);
        final NumberPicker aNumberPicker = new NumberPicker(this);
        aNumberPicker.setMaxValue(12);
        aNumberPicker.setMinValue(1);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker,numPicerParams);
        activity_payment_info.addView(linearLayout);
    }
    private void submitTokenToServer(String id)
    {
        Log.e("userToken",PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)+"");
        CommonUtility.showProgress(PaymentInfo.this,"Please wait");
        ApiClient.getClient().submitToken(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)),id,plan).enqueue(new Callback<StripeResponse>() {
            @Override
            public void onResponse(Call<StripeResponse> call, Response<StripeResponse> response) {
                CommonUtility.hideProgress();
                StripeResponse stripeResponse=response.body();
                if (stripeResponse!=null && stripeResponse.getIsSuccess() && stripeResponse.getSubscription()!=null)
                {
                    cardHolderName.setText("");
                    cardNumber.setText("");
                    cvvTxt.setText("");
                    yearTxt.setText("");
                    monthTxt.setText("");
                    PreferenceConnector.getInstance(PaymentInfo.this).writeBoolean(KeyValue.HAS_SUBSCRIPTION,true);
                    createSuccessDialog();
                }else {
                    PreferenceConnector.getInstance(PaymentInfo.this).writeBoolean(KeyValue.HAS_SUBSCRIPTION,false);
                    CommonUtility.errorToast(PaymentInfo.this,"Something went wrong...");
                }
            }

            @Override
            public void onFailure(Call<StripeResponse> call, Throwable t) {
                PreferenceConnector.getInstance(PaymentInfo.this).writeBoolean(KeyValue.HAS_SUBSCRIPTION,false);
                CommonUtility.errorToast(PaymentInfo.this,"Something went wrong...");
                CommonUtility.hideProgress();
            }
        });
    }
    private void createSuccessDialog()
    {
        final Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.success_layout);
        TextView getMembership= (TextView) builder.findViewById(R.id.makePayment);
        TextView message= (TextView) builder.findViewById(R.id.message);
        ImageView logInImage= (ImageView) builder.findViewById(R.id.logInImage);
        message.setText("Your payment of"+"$"+price+"\\n was successfully completed.");
        getMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getActivity(), PlanListActivity.class);
                startActivity(intent);*/
               builder.dismiss();
                setData();
            }
        });
        builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        builder.show();
    }

    private void setData()
    {
        Intent intent=new Intent();
        intent.putExtra("Result",true);
        setResult(200,intent);
        finish();
    }

    private boolean getData(EditText text)
    {
        if (!TextUtils.isEmpty(text.getText().toString()))
        {
            return true;
        }
        return false;
    }
    private boolean getData(TextView text)
    {
        if (!TextUtils.isEmpty(text.getText().toString()))
        {
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(PaymentInfo.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(PaymentInfo.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(PaymentInfo.this,PasscodeActivity.class);
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
