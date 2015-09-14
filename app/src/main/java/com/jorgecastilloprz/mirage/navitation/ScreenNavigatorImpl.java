package com.jorgecastilloprz.mirage.navitation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.jorgecastilloprz.mirage.navigation.ScreenNavigator;
import com.jorgecastilloprz.mirage.ui.activity.MapActivity;
import javax.inject.Inject;

/**
 * @author jorge
 * @since 11/09/15
 */
public class ScreenNavigatorImpl implements ScreenNavigator {

  private Activity activity;

  @Inject ScreenNavigatorImpl(Activity activity) {
    this.activity = activity;
  }

  @Override public void goToMapScreen() {
    Intent mapIntent = new Intent(activity, MapActivity.class);
    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    activity.startActivity(mapIntent);
  }

  @Override public void goToPolicyScreen() {
    String url = "http://www.google.com/intl/es/policies/privacy/partners/";
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    activity.startActivity(i);
  }
}
