package com.aldoapps.affandi.sample;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class SampleApplication extends Application {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isInDebugMode()) {
            initLeakCanary();
        }
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static void watchReference(Context context) {
        RefWatcher refWatcher = ((SampleApplication) context.getApplicationContext()).refWatcher;
        if (refWatcher != null) {
            refWatcher.watch(context);
        }
    }

    private boolean isInDebugMode() {
        return (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
