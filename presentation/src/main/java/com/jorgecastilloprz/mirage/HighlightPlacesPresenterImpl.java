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
import com.jorgecastilloprz.mirage.interactor.GetHighlightedPlacesForCountry;
import com.jorgecastilloprz.mirage.model.Place;
import com.jorgecastilloprz.mirage.model.TutorialAdvice;
import com.jorgecastilloprz.mirage.navigation.ScreenNavigator;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Jorge Castillo Pérez
 */
public class HighlightPlacesPresenterImpl extends BasePresenter
    implements HighlightPlacesListPresenter, GetHighlightedPlacesForCountry.Callback {

  private View view;
  private GetHighlightedPlacesForCountry getHighlightedPlacesForCountry;

  @Inject HighlightPlacesPresenterImpl(EventBus bus, ScreenNavigator navigator,
      GetHighlightedPlacesForCountry getHighlightedPlacesForCountry,
      AdviceCardHelper adviceCardHelper) {

    super(bus, navigator, adviceCardHelper);
    this.getHighlightedPlacesForCountry = getHighlightedPlacesForCountry;
  }

  @Override protected TutorialAdvice buildTutorialAdvice() {
    TutorialAdvice tutorialAdvice = new TutorialAdvice();
    tutorialAdvice.setType(TutorialAdvice.Type.HIGHLIGHT);
    tutorialAdvice.setTitle("Highlight locations");
    tutorialAdvice.setMessage(
        "Here you have plenty of interesting places of your country that you never visited. Take a "
            + "look around and find highlighted locations to plan your next vacations!");
    return tutorialAdvice;
  }

  @Override protected boolean hasToInsertPolicyAdvice() {
    return false;
  }

  @Override protected boolean hasToInsertTutorialAdvice() {
    return adviceCardHelper.hasToDisplayNearPlacesAdvice();
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

  @Override public void onLoadMoreScrollPositionReached() {
    if (!noMorePlaces) {
      loadNextPage();
    }
  }

  @Override public void onRefreshStarted() {
    getHighlightedPlacesForCountry.execute(this, "spain", lastLoadedPage);
  }

  private void loadNextPage() {
    getHighlightedPlacesForCountry.execute(this, "spain", lastLoadedPage++);
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
