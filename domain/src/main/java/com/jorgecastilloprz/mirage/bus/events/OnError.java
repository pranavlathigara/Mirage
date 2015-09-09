package com.jorgecastilloprz.mirage.bus.events;

/**
 * @author jorge
 * @since 10/09/15
 */
public class OnError {

  private String errorMessage;

  public OnError(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
