package com.app.handyman.mender.misc;

import android.app.Application;
import android.graphics.Typeface;

import com.app.handyman.mender.common.utils.FontsOverride;
import com.app.handyman.mender.utils.TypeFactory;

/**
 * Created by apple on 04/12/17.
 */

public class MainApplication extends Application {

    private TypeFactory mFontFactory;
    @Override
    public void onCreate() {
        super.onCreate();

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Quicksand Light.otf");

    }
    public Typeface getTypeFace(int type) {
        if (mFontFactory == null)
            mFontFactory = new TypeFactory(this);

        switch (type) {
            case Constants.Quicksand_Book:
                return mFontFactory.getQuicksand_Book();
            default:
                return mFontFactory.getQuicksand_Book();
        }
    }

    public interface Constants {
        int Quicksand_Book = 1;
    }

}
