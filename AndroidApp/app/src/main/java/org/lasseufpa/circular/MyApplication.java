package org.lasseufpa.circular;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by alberto on 08/01/2018.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}