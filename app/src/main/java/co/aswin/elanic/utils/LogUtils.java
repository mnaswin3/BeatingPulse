package co.aswin.elanic.utils;

import android.util.Log;

import co.aswin.elanic.BuildConfig;

/**
 * Utility class for logging
 */
public class LogUtils {

    public static void printInfoLog(String TAG, String message){
        if(BuildConfig.DEBUG) Log.i(TAG,message);
    }

    public static void printInfoLog(String TAG, String tail, String message){
        if(BuildConfig.DEBUG) Log.i(TAG+" "+tail,message);
    }

    public static void printErrorLog(String TAG, String message){
        if(BuildConfig.DEBUG) Log.e(TAG,message);
    }

    public static void printErrorLog(String TAG, String tail, String message){
        if(BuildConfig.DEBUG) Log.e(TAG+" "+tail,message);
    }
}
