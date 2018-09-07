package com.app.handyman.mender.utils;

/**
 * Created by Naveen pc on 8/08/2018.
 */
public class Validation {

    /**
     * Method used to check empty string
     */
    public static boolean isEmpty(String strValue) {
        if (strValue != null && strValue.length() > 0)
            return true;
        else
            return false;
    }

    /**
     * Method used to check valid password
     */
    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    /**
     * Method used to check valid email
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
