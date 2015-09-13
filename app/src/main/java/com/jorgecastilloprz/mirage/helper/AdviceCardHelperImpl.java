package com.jorgecastilloprz.mirage.helper;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import javax.inject.Inject;

/**
 * @author jorge
 * @since 13/09/15
 */
public class AdviceCardHelperImpl implements AdviceCardHelper {

  private SharedPreferences preferences;

  @Inject AdviceCardHelperImpl(Application application) {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(application);
  }

  @Override public boolean hasToDisplayPoliciesAdvice() {
    return preferences.getBoolean("displayPoliciesAdvice", true);
  }

  @Override public boolean hasToDisplayNearPlacesAdvice() {
    return preferences.getBoolean("displayNearPlacesAdvice", true);
  }

  @Override public boolean hasToDisplayHighlightPlacesAdvice() {
    return preferences.getBoolean("displayHighlightPlacesAdvice", true);
  }

  @Override public void markPoliciesAdviceAsRead() {
    preferences.edit().putBoolean("displayPoliciesAdvice", false).apply();
  }

  @Override public void markNearPlacesAdviceAsRead() {
    preferences.edit().putBoolean("displayNearPlacesAdvice", false).apply();
  }

  @Override public void markHighlightPlacesAdviceAsRead() {
    preferences.edit().putBoolean("displayHighlightPlacesAdvice", false).apply();
  }
}
