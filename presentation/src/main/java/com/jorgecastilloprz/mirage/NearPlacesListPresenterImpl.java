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
import com.jorgecastilloprz.mirage.helper.AdviceCardHelper;
import com.jorgecastilloprz.mirage.interactor.GetPlacesAround;
import com.jorgecastilloprz.mirage.model.Place;
import com.jorgecastilloprz.mirage.model.PolicyAdvice;
import com.jorgecastilloprz.mirage.model.TutorialAdvice;
import com.jorgecastilloprz.mirage.navigation.ScreenNavigator;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Jorge Castillo Pérez
 */
public class NearPlacesListPresenterImpl extends BasePresenter
    implements NearPlacesListPresenter, GetPlacesAround.Callback {

  private View view;
  private GetPlacesAround getPlacesAround;

  @Inject NearPlacesListPresenterImpl(EventBus bus, ScreenNavigator navigator,
      GetPlacesAround getPlacesAround, AdviceCardHelper adviceCardHelper) {

    super(bus, navigator, adviceCardHelper);
    this.getPlacesAround = getPlacesAround;
  }

  @Override protected TutorialAdvice buildTutorialAdvice() {
    TutorialAdvice tutorialAdvice = new TutorialAdvice();
    tutorialAdvice.setType(TutorialAdvice.Type.NEAR);
    tutorialAdvice.setTitle("Locations around");
    tutorialAdvice.setMessage(
        "You will be able to find near locations here. Use filters to match your "
            + "interests. Locations will be ordered using rating by default, but ordering "
            + "criteria is totally configurable.");
    return tutorialAdvice;
  }

  @Override protected boolean hasToInsertPolicyAdvice() {
    return adviceCardHelper.hasToDisplayPoliciesAdvice();
  }

  @Override protected boolean hasToInsertTutorialAdvice() {
    return adviceCardHelper.hasToDisplayNearPlacesAdvice();
  }

  @Override public void initialize() {
  }

  @Override public void setView(View view) {
    if (view == null) {
      throw new IllegalArgumentException("The view must not be null.");
    }

    this.view = view;
  }

  @Override public void resume() {
    bus.register(this);
  }

  @Override public void pause() {
    bus.unregister(this);
  }

  @Override public void onLoadMoreScrollPositionReached() {
    if (!noMorePlaces) {
      loadNextPage();
    }
  }

  @Override public void onRefreshStarted() {
    getPlacesAround.execute(this, 37.992360, -1.121461, lastLoadedPage);
  }

  private void loadNextPage() {
    getPlacesAround.execute(this, 37.992360, -1.121461, lastLoadedPage++);
  }

  @Override public void onPlacesLoaded(List<Place> places) {
    if (places.size() > 0) {
      if (isUserRefreshing(places)) {
        loadedPlacesUntilNow = places;
      } else {
        loadedPlacesUntilNow.addAll(places);
      }

      view.drawPlaces(loadedPlacesUntilNow);
    } else {
      noMorePlaces = true;
    }
  }

  private boolean isUserRefreshing(List<Place> places) {
    return loadedPlacesUntilNow.size() > 0 && getFirstRealPlaceId(loadedPlacesUntilNow).equals(
        getFirstRealPlaceId(places));
  }

  private String getFirstRealPlaceId(List<Place> places) {
    for (int i = 0; i < places.size(); i++) {
      String currentPlaceId = places.get(i).getId();
      if (currentPlaceId != null && !currentPlaceId.equals("")) {
        return currentPlaceId;
      }
    }

    return "";
  }

  @Override public void onLoadingPlacesError() {
    bus.post(new OnError("Loading places error. Check your connection."));
  }

  @Override public void onPolicyDetailsButtonClick() {
    navigator.goToPolicyScreen();
  }

  @Override public void onUnderstoodButtonClick(int position) {

  }

  @Override public void onPlaceClick(Place place) {

  }
}
