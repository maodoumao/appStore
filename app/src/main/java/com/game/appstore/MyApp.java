package com.game.appstore;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {

    private static MyApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
//        AppLib.setup(getApplicationContext(),new InstallReferrerUtil.InstallReferrerCallback() {
//            @Override
//            public void onReferrerReceived(String referrer) {
//                boolean organic = SharedPrefUtils.getInstance().getBooleanByKey(getPackageName(),true);
//                if (organic) {
//                    //自然量
////                    Log.e("自然量",organic+"");
//                } else {
//                    //非自然量
//                }
//            }
//        });
    }


    public static MyApp getInstance(){
        return instance;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        AppLib.setDebug(base,"B6631F6B6F6865D9836C24B0");
//        AppLib.initApp(base);
    }
}
