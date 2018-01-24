package com.ryane.banner.util;

import android.content.res.Resources;

/**
 * Create Time: 2018/1/24
 *
 * @author RyanLee
 */

public class OsUtil {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
