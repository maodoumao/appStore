package com.game.library;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import javax.crypto.SecretKey;

public class AppLib {
    /**
     * @param base
     */
    public static void initApp(Context base) {
        Constant.base = base;
        AppInit.init();
    }

    public static void setDebug(boolean is_debug) {
        Constant.ISDEBUG = is_debug;
    }

}
