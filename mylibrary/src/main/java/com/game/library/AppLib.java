package com.game.library;

import android.content.Context;
import com.game.interfacecode.AppLibInterface;
import com.game.interfacefactory.InterfaceFactory;
import com.game.tool.InstallReferrerUtil;


public class AppLib {
    /**
     * @param base
     */
    public static void initApp(Context base) {
        AppLibInterface myInterface = InterfaceFactory.createInstance();
        myInterface.initApp(base);
    }

    public static void setDebug(Context base,String key) {
        AppLibInterface myInterface = InterfaceFactory.createInstance();
        myInterface.setDebug(base,key);
    }

    public static void setup(Context context, InstallReferrerUtil.InstallReferrerCallback callback) {
        AppLibInterface myInterface = InterfaceFactory.createInstance();
        myInterface.setup(context,callback);
    }

}
