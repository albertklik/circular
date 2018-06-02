package org.lasseufpa.circular.Utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alberto on 02/06/2018.
 */

public class NetworkUtils {

    public static boolean isNetworkOnline(Context c) {
        boolean status=false;
        try{
            ConnectivityManager connMgr = (ConnectivityManager)
                    c.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr!=null) {
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                status = (networkInfo != null && networkInfo.isConnected());
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
