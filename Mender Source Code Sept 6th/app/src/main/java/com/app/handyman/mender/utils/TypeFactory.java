package com.app.handyman.mender.utils;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFactory {

    final String Quicksand_Book = "fonts/Quicksand Book.otf";


    Typeface typefaceQuicksand_Book;


    public TypeFactory(Context context) {
        typefaceQuicksand_Book = Typeface.createFromAsset(context.getAssets(), Quicksand_Book);

    }

    public Typeface getQuicksand_Book() {
        return typefaceQuicksand_Book;
    }



}