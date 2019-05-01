package com.devpoint.firebasedb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;



/**
 * Created by Rahul on 23-11-2017.
 */

public class PreferenceUtils {

    public static final String PREF_DEVICE_GCM_ID = "pref_device_gcm_id";
    public static final String PREF_ID = "id";
    public static final String PREF_NAME = "name";
    public static final String PREF_EMAIL = "email";
    public static final String PREF_CONTACT_NO = "contact_no";
    public static final String PREF_ADDRESS = "address";
    public static final String PREF_CITY = "city";
    public static final String PREF_STATE = "state";
    public static final String PREF_ZIP = "zip";
    public static final String PREF_COUNTRY = "country";
    public static final String PREF_ROLE_ID = "role_id";
    public static final String PREF_PROFILE_3X_PIC = "profile_3x_pic";
    public static final String PREF_PROFILE_2X_PIC = "profile_2x_pic";
    private static final String PREF_LOGIN_DATA = "PREF_LOGIN_DATA";
    private static final String PREF_IS_LOGIN = "is_login";
    private static final String USER_PREFS_NAME = "com.arka.fahmni.pre.user";
    private static final String APP_PREFS_NAME = "com.arka.fahmni.pre.app";
    private static final String PREF_START_TIME = "PREF_START_TIME";
    private static final String PREF_START_TIME_SESSION_ID = "PREF_START_TIME_SESSION_ID";
    private static final String PREF_LANGUAGE = "PREF_LANGUAGE";


    private SharedPreferences sharedPreferences1;

    public static boolean getIsLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_IS_LOGIN, false);
    }

    public static void setIsLogin(Context context, boolean isLogin) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREF_IS_LOGIN, isLogin);
        editor.apply();
    }


    public static boolean isFirstTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_IS_LOGIN, true);
    }

    public static void setFirstTime(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREF_IS_LOGIN, false);
        editor.apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getString(PREF_LANGUAGE, "ar");
    }

    public static void setLanguage(Context context,String language) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_LANGUAGE, language);
        editor.apply();
    }

    public static void setUserData(Context context, String data) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_LOGIN_DATA, data);
        editor.apply();
    }

    public static void setStartTime(Context context, long data, int id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(PREF_START_TIME, data);
        editor.putInt(PREF_START_TIME_SESSION_ID, id);
        editor.apply();
    }


    public static @Nullable long getStartTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getLong(PREF_START_TIME, 0);
//        String userRow = sp.getString(PREF_START_TIME_SESSION_ID, 0);
    }

    public static @Nullable int getStartTimeSesionId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getInt(PREF_START_TIME_SESSION_ID, 0);
//        String userRow = sp.getString(PREF_START_TIME_SESSION_ID, 0);
    }


    public static void getDeleteTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(PREF_START_TIME).remove(PREF_START_TIME_SESSION_ID).apply();
    }

    public void saveData(String key, String value, Context context) {
        sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPreferences1.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key, Context context) {
        sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences1 != null) {
            return sharedPreferences1.getString(key, "");
        }
        return "";
    }

    public class Key {
        public static final String ROLE_TUTOR = "TUTOR";
        public static final String ROLE_STUDENT = "STUDENT";
    }


}
