package com.app.dportshipper.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager {

    public static final String DPORTSHIPPER = "dportshipper";

    public static final String TOKEN = "token";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "passsword";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(DPORTSHIPPER, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }



    public String getEmail(){
        return sp.getString(EMAIL, "");
    }

    /*public String getToken(){
        return sp.getString(TOKEN, "");
    }*/

    public String getPassword(){
        return sp.getString(PASSWORD, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
