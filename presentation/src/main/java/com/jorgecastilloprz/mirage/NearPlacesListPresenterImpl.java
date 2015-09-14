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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Jorge Castillo Pérez
 */
public class NearPlacesListPresenterImpl
    implements NearPlacesListPresenter, GetPlacesAround.Callback {

  private View view;
  private EventBus bus;
  private boolean noMorePlaces = false;
  private GetPlacesAround getPlacesAround;
  private int lastLoadedPage;
  private List<Place> loadedPlacesUntilNow;

  private AdviceCardHelper adviceCardHelper;

  @Inject NearPlacesListPresenterImpl(EventBus bus, GetPlacesAround getPlacesAround,
      AdviceCardHelper adviceCardHelper) {
    this.bus = bus;
    this.getPlacesAround = getPlacesAround;
    this.lastLoadedPage = 0;
    this.adviceCardHelper = adviceCardHelper;
    this.loadedPlacesUntilNow = new ArrayList<>();

    initAdvices();
  }

  private void initAdvices() {
    if (hasToInsertPolicyAdvice()) {
      PolicyAdvice policyAdvice = new PolicyAdvice();
      loadedPlacesUntilNow.add(policyAdvice);
    }

    if (hasToInsertTutorialAdvice()) {
      TutorialAdvice tutorialAdvice = new TutorialAdvice();
      tutorialAdvice.setTitle("Locations around");
      tutorialAdvice.setMessage(
          "You will be able to find interesting near locations here. Use filters to match your "
              + "interests. Locations will be ordered using rating by default, but ordering "
              + "criteria is totally configurable.");

      loadedPlacesUntilNow.add(tutorialAdvice);
    }
  }

  private boolean hasToInsertPolicyAdvice() {
    return adviceCardHelper.hasToDisplayPoliciesAdvice();
  }

  private boolean hasToInsertTutorialAdvice() {
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
}
