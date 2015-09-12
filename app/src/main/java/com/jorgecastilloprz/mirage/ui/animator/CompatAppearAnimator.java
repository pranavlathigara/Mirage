package com.jorgecastilloprz.mirage.ui.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author jorge
 * @since 12/09/15
 */
public class CompatAppearAnimator implements AppearAnimator {

  private final int APPEAR_COMPAT_DURATION = 200;
  private AppearAnimatorListener listener;

  @Override public void attachListener(AppearAnimatorListener listener) {
    this.listener = listener;
  }

  @Override public void playAppearAnimation(int animCenterX, int animCenterY, int finalRadius,
      final View calendarView) {

    calendarView.setPivotX(animCenterX);
    calendarView.setPivotY(animCenterY);

    ValueAnimator alphaAnim = ObjectAnimator.ofFloat(calendarView, "alpha", 1);
    ValueAnimator scaleAnimX = ObjectAnimator.ofFloat(calendarView, "scaleX", 0, 1);
    ValueAnimator scaleAnimY = ObjectAnimator.ofFloat(calendarView, "scaleY", 0, 1);

    AnimatorSet set = new AnimatorSet();
    set.playTogether(alphaAnim, scaleAnimX, scaleAnimY);
    set.setDuration(APPEAR_COMPAT_DURATION);
    set.setInterpolator(new AccelerateDecelerateInterpolator());
    set.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        calendarView.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        if (listener != null) {
          listener.onAppearAnimationComplete();
        }
      }
    });
    set.start();
  }

  @Override public void playDisappearAnimation(int animCenterX, int animCenterY, int initialRadius,
      final View calendarView) {

    calendarView.setPivotX(animCenterX);
    calendarView.setPivotY(animCenterY);

    ValueAnimator alphaAnim = ObjectAnimator.ofFloat(calendarView, "alpha", 0);
    ValueAnimator scaleAnimX = ObjectAnimator.ofFloat(calendarView, "scaleX", 1, 0);
    ValueAnimator scaleAnimY = ObjectAnimator.ofFloat(calendarView, "scaleY", 1, 0);

    AnimatorSet set = new AnimatorSet();
    set.playTogether(alphaAnim, scaleAnimX, scaleAnimY);
    set.setDuration(APPEAR_COMPAT_DURATION);
    set.setInterpolator(new AccelerateDecelerateInterpolator());
    set.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        calendarView.setVisibility(View.INVISIBLE);
        if (listener != null) {
          listener.onDisappearAnimationComplete();
        }
      }
    });
    set.start();
  }
}
