/*
 * Copyright (C) 2015 Jorge Castillo Pérez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jorgecastilloprz.mirage.di;

import com.jorgecastilloprz.mirage.HighlightPlacesListPresenter;
import com.jorgecastilloprz.mirage.HighlightPlacesPresenterImpl;
import com.jorgecastilloprz.mirage.MainPresenter;
import com.jorgecastilloprz.mirage.MainPresenterImpl;
import com.jorgecastilloprz.mirage.NearPlacesListPresenter;
import com.jorgecastilloprz.mirage.NearPlacesListPresenterImpl;
import com.jorgecastilloprz.mirage.di.annotations.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * @author Jorge Castillo Pérez
 */
@Module public class PresentationModule {

  @Provides @PerActivity MainPresenter provideMainPresenter(MainPresenterImpl presenter) {
    return presenter;
  }

  @Provides @PerActivity NearPlacesListPresenter provideNearPlacesListPresenter(
      NearPlacesListPresenterImpl presenter) {
    return presenter;
  }

  @Provides @PerActivity HighlightPlacesListPresenter provideHighlightPlacesPresenter(
      HighlightPlacesPresenterImpl presenter) {
    return presenter;
  }
}
