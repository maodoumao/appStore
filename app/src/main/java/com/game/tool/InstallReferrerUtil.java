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
    public static void setup(Context context) {

        InstallReferrerClient client = InstallReferrerClient.newBuilder(context)
                .build();
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
//                            Log.e("referrer=>",referrer);
                            boolean organic = referrer.contains("utm_medium=organic");
                            if (organic) {
                                //自然量
//                                Log.e("自然量=>",referrer);
                            } else {
                                //非自然量
//                              Log.e("非自然量=>",referrer);
                            }
                            if (!TextUtils.isEmpty(referrer)) {
                                SharedPrefUtils.getInstance().putStr2SP("referrer",referrer);
                                Intent referrerReceived = new Intent(INSTALL_REFERRER);
                                referrerReceived.putExtra("referrer", referrer);
                                //向App本身其他Receiver广播
                                Iterator var8 = context.getPackageManager().queryBroadcastReceivers(new Intent(INSTALL_REFERRER), 0).iterator();
                                while (var8.hasNext()) {
                                    ResolveInfo var4 = (ResolveInfo) var8.next();
                                    String var5 = referrerReceived.getAction();
                                    if (var4.activityInfo.packageName.equals(context.getPackageName()) && INSTALL_REFERRER.equals(var5) && !this.getClass().getName().equals(var4.activityInfo.name)) {
//                                    BuglyLog.e("onReceive:class:", (new StringBuilder("trigger onReceive: class: ")).append(var4.activityInfo.name).toString() + "");
//                                    Log.e("onReceive:class=>",(new StringBuilder("trigger onReceive: class: ")).append(var4.activityInfo.name).toString() + "");
                                        try {
                                            ((BroadcastReceiver) Class.forName(var4.activityInfo.name).newInstance()).onReceive(context, referrerReceived);
                                        } catch (Throwable var6) {
//                                        BuglyLog.e("onReceive:class:", (new StringBuilder("error in BroadcastReceiver ")).append(var4.activityInfo.name).toString(), var6);
//                                        Log.e("onReceive:Throwable=>",(new StringBuilder("error in BroadcastReceiver ")).append(var4.activityInfo.name).toString(), var6);
                                        }
                                    }
                                }
                            }
//                        AndroidSchedulers.mainThread().createWorker().schedule(() -> CrashReport.postCatchedException(new Throwable("referral:" + referrer)));
                        } catch (RemoteException e) {
                            // omit exception
//                        AndroidSchedulers.mainThread().createWorker().schedule(() -> CrashReport.postCatchedException(new Throwable("RemoteException:" + e.getMessage())));
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
//                        Log.e("FEATURE_NOT_SUPPORTED=>",responseCode+"");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
//                        Log.e("SERVICE_UNAVAILABLE=>",responseCode+"");
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
}