package com.github.jorgecastilloprz.mirage.api.foursquare.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo") public class Ne {

  @Expose private Double lat;
  @Expose private Double lng;

  /**
   * @return The lat
   */
  public Double getLat() {
    return lat;
  }

  /**
   * @param lat The lat
   */
  public void setLat(Double lat) {
    this.lat = lat;
  }

  /**
   * @return The lng
   */
  public Double getLng() {
    return lng;
  }

  /**
   * @param lng The lng
   */
  public void setLng(Double lng) {
    this.lng = lng;
  }
}
