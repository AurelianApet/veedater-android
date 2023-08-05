package digimantra.veedaters.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by dmlabs on 28/8/17.
 */

public class CommonUtility {

    private static ProgressDialog progressDialog;
    public static void showProgress(Context context,String message)
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    public static void hideProgress()
    {
        if (progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

    public static void errorToast(Context context,String message)
    {
        TastyToast.makeText(context, message, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }
    public static void warningToast(Context context,String message)
    {
        TastyToast.makeText(context, message, TastyToast.LENGTH_LONG, TastyToast.WARNING);
    }
    public static void confusingToast(Context context,String message)
    {
        TastyToast.makeText(context, message, TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
    }
    public static void inFoToast(Context context,String message)
    {
        TastyToast.makeText(context, message, TastyToast.LENGTH_LONG, TastyToast.INFO);
    }
    public static void successToast(Context context,String message)
    {
        TastyToast.makeText(context, message, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }
}
