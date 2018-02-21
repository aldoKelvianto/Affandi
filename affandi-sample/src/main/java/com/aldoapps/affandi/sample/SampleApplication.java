package com.aldoapps.affandi.sample;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class SampleApplication extends Application {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static void watchReference(Context context) {
        RefWatcher refWatcher = ((SampleApplication) context.getApplicationContext()).refWatcher;
        refWatcher.watch(context);
    }
}
