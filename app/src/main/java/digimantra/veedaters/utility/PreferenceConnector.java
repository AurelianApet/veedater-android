package digimantra.veedaters.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import digimantra.veedaters.Model.LoginResponse;


/**
 * Created by dmlabs on 28/8/17.
 */

public class PreferenceConnector {

    private static PreferenceConnector data;

    public SharedPreferences getPreferences() {
        return preferences;
    }

    private SharedPreferences preferences;

    public PreferenceConnector(Context context) {
        preferences=context.getSharedPreferences(KeyValue.VEEDATERS,0);
    }

    public void storeLogUser(LoginResponse response)
    {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        getEditor().putString(KeyValue.USER_PROFILE,json).commit();
    }
    public LoginResponse getLogUser()
    {
        Gson gson = new Gson();
        String json = getPreferences().getString(KeyValue.USER_PROFILE, "");
        LoginResponse obj = gson.fromJson(json, LoginResponse.class);
        return obj;
    }
    public static PreferenceConnector getInstance(Context context)
    {
        if (data==null)
        {
            data = new PreferenceConnector(context);
        }
        return data;
    }
    public void writeMap(HashMap<String,String> dataList)
    {

    }
    public <T>void writeObject(T object)
    {

    }
   /* public void storeUserInfo(UserModel.User user)
    {
        SharedPreferences.Editor editor=getEditor();
        editor.putString(KeyValue.EMAIL,user.getEmail());
        editor.putString(KeyValue.USERNAME,user.getUsername());
        editor.putString(KeyValue.USERNAME,user.getUsername());
        editor.putString(KeyValue.PHONE,user.getPhone());
        editor.putInt(KeyValue.CHIBHA_USER_HEADERTOKEN,user.getId());
        editor.commit();
    }*/
    public void writeString(String key,String value)
    {
        SharedPreferences.Editor editor=getEditor();
        editor.putString(key,value);
        editor.commit();
    }
    public String readString(String key)
    {
        return preferences.getString(key,"");
    }

    public void writeBoolean(String key,Boolean value)
    {
        SharedPreferences.Editor editor=getEditor();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public boolean readBoolean(String key)
    {
        return preferences.getBoolean(key,false);
    }
    public void writeInt(String key,int value)
    {
        SharedPreferences.Editor editor=getEditor();
        editor.putInt(key,value);
        editor.commit();
    }
    public void isShopRegister(boolean flag)
    {
       // getEditor().putBoolean(KeyValue.IS_SHOP_REGISTER,flag).commit();
    }
    public int readInt(String key)
    {
         return preferences.getInt(key,0);
    }

    public void checkPrefe()
    {

    }
    public void logout()
    {
        preferences.edit().clear().commit();
    }
    private SharedPreferences.Editor getEditor()
    {
        return preferences.edit();
    }
}
