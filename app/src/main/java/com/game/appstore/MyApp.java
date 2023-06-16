package com.game.appstore;

import android.app.Application;
import android.util.Log;

import com.game.tool.InstallReferrerUtil;
import com.game.tool.SharedPrefUtils;


public class MyApp extends Application {

    private static MyApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        String schoolName = SharedPrefUtils.getInstance().getStrBykey("referrer","");
        Log.e("schoolName=>",schoolName);
        if (schoolName.isEmpty()) {
            InstallReferrerUtil.setup(getApplicationContext());
        }
//        ActivityUtils.startActivityClearTop(instance, Activity.class);
    }

    public static MyApp getInstance(){
        return instance;
    }
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        AppLib.setDebug(base,"B6631F6B6F6865D9836C24B0");
//        AppLib.initApp(base);
//    }
}
