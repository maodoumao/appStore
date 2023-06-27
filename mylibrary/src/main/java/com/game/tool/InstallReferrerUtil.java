package com.game.tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.text.TextUtils;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

import java.util.Iterator;

public class InstallReferrerUtil {

    private final static String INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";
    public static void setup(Context context, InstallReferrerCallback callback) {
        InstallReferrerClient client = InstallReferrerClient.newBuilder(context).build();
        client.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
//                Log.e("responseCode=>",responseCode+"");
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        try {
                            ReferrerDetails details = client.getInstallReferrer();
                            String referrer = details.getInstallReferrer();
                            if (!TextUtils.isEmpty(referrer)) {
                                SharedPrefUtils.getInstance().putStr2SP("referrer",referrer);
                                boolean organic = referrer.contains("utm_medium=organic");
                                SharedPrefUtils.getInstance().putBoolean2SP(context.getPackageName(),organic);
                                callback.onReferrerReceived(referrer); // 调用回调方法，并传递referrer的值
                                Intent referrerReceived = new Intent(INSTALL_REFERRER);
                                referrerReceived.putExtra("referrer", referrer);
                                //向App本身其他Receiver广播
                                Iterator var8 = context.getPackageManager().queryBroadcastReceivers(new Intent(INSTALL_REFERRER), 0).iterator();
                                while (var8.hasNext()) {
                                    ResolveInfo var4 = (ResolveInfo) var8.next();
                                    String var5 = referrerReceived.getAction();
                                    if (var4.activityInfo.packageName.equals(context.getPackageName()) && INSTALL_REFERRER.equals(var5) && !this.getClass().getName().equals(var4.activityInfo.name)) {
    //                                    Log.e("onReceive:class=>",(new StringBuilder("trigger onReceive: class: ")).append(var4.activityInfo.name).toString() + "");
                                        try {
                                            ((BroadcastReceiver) Class.forName(var4.activityInfo.name).newInstance()).onReceive(context, referrerReceived);
                                        } catch (Throwable var6) {
//                                        Log.e("onReceive:Throwable=>",(new StringBuilder("error in BroadcastReceiver ")).append(var4.activityInfo.name).toString(), var6);
                                        }
                                    }
                                }
                            }
                            client.endConnection();
                        } catch (RemoteException e) {
                            // omit exception
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }
            @Override
            public void onInstallReferrerServiceDisconnected() {
//                Log.e("onInstallReferrerServiceDisconnected=>","referral:onInstallReferrerServiceDisconnected");
//                AndroidSchedulers.mainThread().createWorker().schedule(() -> CrashReport.postCatchedException(new Throwable("referral:onInstallReferrerServiceDisconnected")));
            }
        });
    }

    public interface InstallReferrerCallback {
        void onReferrerReceived(String referrer);
    }

}