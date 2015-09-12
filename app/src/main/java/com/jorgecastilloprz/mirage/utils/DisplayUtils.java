package com.jorgecastilloprz.mirage.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @author jorge
 * @since 12/09/15
 */
public class DisplayUtils {

  public static DisplayMetrics getDisplayMetrics(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

    return metrics;
  }
}
