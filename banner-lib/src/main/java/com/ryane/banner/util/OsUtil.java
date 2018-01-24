package com.ryane.banner.util;

import android.content.res.Resources;

/**
 * @author RyanLee
 * @date 2018/1/24
 */

public class OsUtil {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
