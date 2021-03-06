package com.shishkindenis.locationtracker_parent;

import android.app.Application;

public class MyApplication extends Application {

    public static ApplicationComponent appComponent = DaggerApplicationComponent.create();

}
