package com.jorgecastilloprz.mirage.di.modules;

import com.jorgecastilloprz.mirage.ui.animator.AppearAnimator;
import com.jorgecastilloprz.mirage.ui.animator.CompatAppearAnimator;
import com.jorgecastilloprz.mirage.ui.animator.LollipopAppearAnimator;
import com.jorgecastilloprz.mirage.utils.BuildUtils;
import dagger.Module;
import dagger.Provides;

/**
 * @author jorge
 * @since 12/09/15
 */
@Module public class AnimationModule {

  @Provides AppearAnimator provideAppearAnimator() {
    return BuildUtils.isVersionLollipopOrNewer() ? new LollipopAppearAnimator()
        : new CompatAppearAnimator();
  }
}
