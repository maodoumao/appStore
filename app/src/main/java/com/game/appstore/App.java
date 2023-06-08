package com.game.appstore;

import android.app.Application;
import android.content.Context;

import com.game.library.AppLib;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppLib.setDebug(true);
        AppLib.initApp(base);
    }
}
