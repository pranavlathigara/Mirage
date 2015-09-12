package com.jorgecastilloprz.mirage.navitation;

import android.app.Activity;
import android.content.Intent;
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
}
