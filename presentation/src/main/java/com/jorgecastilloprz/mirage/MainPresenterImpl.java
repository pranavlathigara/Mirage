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
package com.jorgecastilloprz.mirage;

import com.jorgecastilloprz.mirage.bus.EventBus;
import com.jorgecastilloprz.mirage.bus.events.OnError;
import com.jorgecastilloprz.mirage.navigation.ScreenNavigator;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

/**
 * @author Jorge Castillo Pérez
 */
public class MainPresenterImpl implements MainPresenter {

  private View view;

  private EventBus bus;
  private ScreenNavigator navigator;

  @Inject MainPresenterImpl(EventBus bus, ScreenNavigator navigator) {
    this.bus = bus;
    this.navigator = navigator;
  }

  @Override public void setView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("The view must not be null.");
    }

    this.view = view;
  }

  @Override public void initialize() {

  }

  @Override public void resume() {
    bus.register(this);
  }

  @Override public void pause() {
    bus.unregister(this);
  }

  @Override public void onSignOutButtonClick() {
    view.signOutAccount();
    view.storeUserLogedOutInPreferences();
    view.exitToSignInActivity();
  }

  @Override public void onMapButtonClick() {
    navigator.goToMapScreen();
  }

  @Subscribe public void onErrorOcurred(OnError event) {
    view.displayError(event.getErrorMessage());
  }
}
