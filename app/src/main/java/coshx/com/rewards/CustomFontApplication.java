package coshx.com.rewards;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by MSMD on 10/21/17.
 */

public class CustomFontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initalize Calligraphy
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("poppins-bold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}