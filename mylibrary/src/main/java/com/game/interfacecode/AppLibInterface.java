package com.game.interfacecode;

import android.content.Context;

import com.game.tool.InstallReferrerUtil;


public interface AppLibInterface {
    void initApp(Context base);

    void setDebug(Context base,String key);

    void setup(Context context, InstallReferrerUtil.InstallReferrerCallback callback);
}
