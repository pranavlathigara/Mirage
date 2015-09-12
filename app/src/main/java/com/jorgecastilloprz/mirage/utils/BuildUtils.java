package com.jorgecastilloprz.mirage.utils;

import android.os.Build;

/**
 * @author jorge
 * @since 12/09/15
 */
public class BuildUtils {

  public static boolean isVersionLollipopOrNewer() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }
}
