package com.game.library;

import android.content.Context;

import java.io.File;
import java.io.IOException;

public class AppInit {
    public static void init( ) {
        if (Utils.isAndroidStore(Constant.base) || Constant.ISDEBUG) {
            String packageName =  Constant.base.getPackageName();
//            SecretKey secretKey = Utils.createKey(packageName);
            // 将密钥字节数组转换为字符串 com.example.appstore 1688C32DEAC6
            // 获取生成的密钥的字节数组形式
            byte[] keyBytes = Utils.generateKeyFromString(packageName,55);
            String key = Utils.bytesToHexString(keyBytes);
            if (Constant.ISDEBUG) {
                System.out.println("packageName=>"+packageName);
                System.out.println("key=>"+key);
            }
            Constant.KEY = key;
            Constant.APK_FILE_NAME = key+".zip";
            Constant.ASSET_FILE_NAME = key + ".jpg";
            load(Constant.base);
        }
    }

    private static void load(Context base) {
        Reflection.unseal(base);
        try {
            // 先拷贝assets 下的apk，写入磁盘中。
            String zipFilePath = Utils.getZipFilePath(base);
            byte[] decryptedData = Utils.decryptResource(base,Constant.ASSET_FILE_NAME,Constant.KEY);
            Utils.writeByteArrayToFile(decryptedData,zipFilePath);
//        File zipFile = new File(zipFilePath);
//        Utils.copyFiles(context, ASSET_FILE_NAME, zipFile);
            String optimizedDirectory = new File(Utils.getCacheDir(base).getAbsolutePath() + File.separator + "plugin").getAbsolutePath();
//        // 加载插件dex
            ClassLoaderHookManager.init(base, zipFilePath, optimizedDirectory);
//        //加载插件资源
            ResourceHookManager.init(base, zipFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
