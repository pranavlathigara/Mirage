package com.jorgecastilloprz.mirage.helper;

/**
 * @author jorge
 * @since 13/09/15
 */
public interface AdviceCardHelper {

  boolean hasToDisplayPoliciesAdvice();

  boolean hasToDisplayNearPlacesAdvice();

  boolean hasToDisplayHighlightPlacesAdvice();

  void markPoliciesAdviceAsRead();

  void markNearPlacesAdviceAsRead();

  void markHighlightPlacesAdviceAsRead();
}
