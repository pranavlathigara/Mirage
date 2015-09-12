package com.jorgecastilloprz.mirage.ui.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * @author jorge
 * @since 12/09/15
 */
public class LollipopAppearAnimator implements AppearAnimator {

  private final int REVERSE_REVEAL_DURATION = 150;
  private AppearAnimatorListener listener;

  @Override public void attachListener(AppearAnimatorListener listener) {
    this.listener = listener;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override
  public void playAppearAnimation(int animCenterX, int animCenterY, int finalRadius,
      View calendarView) {

    Animator anim =
        ViewAnimationUtils.createCircularReveal(calendarView, animCenterX, animCenterY, 0,
            finalRadius);
    calendarView.setVisibility(View.VISIBLE);
    anim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        if (listener != null) {
          listener.onAppearAnimationComplete();
        }
      }
    });

    anim.start();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override
  public void playDisappearAnimation(int animCenterX, int animCenterY, int initialRadius,
      final View calendarView) {

    Animator anim = ViewAnimationUtils.createCircularReveal(calendarView, animCenterX, animCenterY,
        initialRadius, 0);
    anim.setDuration(REVERSE_REVEAL_DURATION);

    anim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        calendarView.setVisibility(View.INVISIBLE);
        if (listener != null) {
          listener.onDisappearAnimationComplete();
        }
      }
    });

    anim.start();
  }
}
