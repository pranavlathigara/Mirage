package com.jorgecastilloprz.mirage;

import com.jorgecastilloprz.mirage.bus.EventBus;
import com.jorgecastilloprz.mirage.helper.AdviceCardHelper;
import com.jorgecastilloprz.mirage.model.Place;
import com.jorgecastilloprz.mirage.model.PolicyAdvice;
import com.jorgecastilloprz.mirage.model.TutorialAdvice;
import com.jorgecastilloprz.mirage.navigation.ScreenNavigator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jorge
 * @since 14/09/15
 */
public abstract class BasePresenter {

  protected EventBus bus;
  protected boolean noMorePlaces = false;
  protected ScreenNavigator navigator;
  protected int lastLoadedPage;
  protected List<Place> loadedPlacesUntilNow;
  protected AdviceCardHelper adviceCardHelper;

  protected BasePresenter(EventBus bus, ScreenNavigator navigator,
      AdviceCardHelper adviceCardHelper) {

    this.bus = bus;
    this.navigator = navigator;
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
      tutorialAdvice.setType(TutorialAdvice.Type.NEAR);
      tutorialAdvice.setTitle("Locations around");
      tutorialAdvice.setMessage(
          "You will be able to find interesting near locations here. Use filters to match your "
              + "interests. Locations will be ordered using rating by default, but ordering "
              + "criteria is totally configurable.");

      loadedPlacesUntilNow.add(tutorialAdvice);
    }
  }

  protected abstract boolean hasToInsertPolicyAdvice();

  protected abstract boolean hasToInsertTutorialAdvice();
}
