package com.github.jorgecastilloprz.mirage.api.foursquare.model;

import com.google.gson.annotations.Expose;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo") public class NearPlacesFoursquareResponse {

  @Expose private Meta meta;
  @Expose private Response response;

  /**
   * @return The meta
   */
  public Meta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  /**
   * @return The response
   */
  public Response getResponse() {
    return response;
  }

  /**
   * @param response The response
   */
  public void setResponse(Response response) {
    this.response = response;
  }
}
