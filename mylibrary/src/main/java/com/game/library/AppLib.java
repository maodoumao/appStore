package com.game.library;

import android.content.Context;
import com.game.interfacecode.AppLibInterface;
import com.game.interfacefactory.InterfaceFactory;


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
}
