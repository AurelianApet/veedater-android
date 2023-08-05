package digimantra.veedaters.network;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmlabs on 24/1/18.
 */

public class CallService extends AsyncTask<String,Void,String> {
    private int requestcode;
    HttpURLConnection httpURLConnection;
    CallResponse callback;
    Context context;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    HashMap<String, String> postparams;
    String result = "";
    String  token = "";
    public AlertDialog alertDialog;
    private boolean value=false;

    public CallService(Context context) {
        this.context = context;
    }

    public CallService(CallResponse callback, Context context, int requestcode) {
        this.callback = callback;
        this.context = context;
        this.requestcode = requestcode;
    }

    public CallService(CallResponse callback,String token, Context context,HashMap<String, String> postparams) {
        this.callback=callback;
        this.context = context;
        this.token=token;
        this.postparams = postparams;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        final String url = params[0];
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Authorization", token);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(12000);
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            os.write(getPostData(postparams).getBytes());
            bufferedWriter.flush();
            bufferedWriter.close();

            os.close();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line + "\n");
                    line = bufferedReader.readLine();
                }
                Log.e("builder contains", builder.toString());
                is.close();
                result = builder.toString();
                return result;
            } else {
                result = "";
                //Utilities.alertDialog(ctx,result);
                return result;
            }

        } catch (MalformedURLException mfe) {
            mfe.printStackTrace();
            result = "";
            //Utilities.alertDialog(ctx,result);
            return result;
        } catch (IOException ioe) {
            result = "";
            // Utilities.alertDialog(ctx,result);
            ioe.printStackTrace();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = "";
            //  Utilities.alertDialog(ctx,result);
            return result="";
        } finally {
            httpURLConnection.disconnect();


        }    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            callback.response(result,requestcode);

        } catch (Exception e) {
            e.printStackTrace();
            //  Utilities.alertDialog(ctx,result);

        } finally {

        }
    }

    private String getPostData(HashMap<String, String> postparams) {
        StringBuilder builder = new StringBuilder();
        if (postparams != null) {
            for (Map.Entry<String, String> entry : postparams.entrySet()) {
                try {
                    builder.append("&");
                    builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    builder.append("=");
                    builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return builder.toString();
        } else {
            return "";
        }

    }

    public static boolean checkInternetConnection(Context ctx) {



        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }
}
