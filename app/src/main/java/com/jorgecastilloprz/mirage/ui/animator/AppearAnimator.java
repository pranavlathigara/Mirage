package com.jorgecastilloprz.mirage.ui.animator;

import android.view.View;

/**
 * @author jorge
 * @since 12/09/15
 */
public interface AppearAnimator {

  void playAppearAnimation(int centerX, int centerY, int finalRadius, View rootView);

  void playDisappearAnimation(int animCenterX, int animCenterY, int initialRadius,
      final View calendarView);

  void attachListener(AppearAnimatorListener listener);
}
