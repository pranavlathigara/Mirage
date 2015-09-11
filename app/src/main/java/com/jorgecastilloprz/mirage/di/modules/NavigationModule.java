package com.jorgecastilloprz.mirage.di.modules;

import com.jorgecastilloprz.mirage.navigation.ScreenNavigator;
import com.jorgecastilloprz.mirage.navitation.ScreenNavigatorImpl;
import dagger.Module;
import dagger.Provides;

/**
 * @author jorge
 * @since 11/09/15
 */
@Module public class NavigationModule {

  @Provides ScreenNavigator provideScreenNavigator(ScreenNavigatorImpl navigator) {
    return navigator;
  }
}
