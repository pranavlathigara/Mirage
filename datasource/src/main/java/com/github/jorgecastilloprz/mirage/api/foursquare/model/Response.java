package com.github.jorgecastilloprz.mirage.api.foursquare.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo") public class Response {

  @Expose private String headerLocation;
  @Expose private String headerFullLocation;
  @Expose private String headerLocationGranularity;
  @Expose private Integer totalResults;
  @Expose private List<VenueItemGroup> groups = new ArrayList<VenueItemGroup>();

  /**
   * @return The headerLocation
   */
  public String getHeaderLocation() {
    return headerLocation;
  }

  /**
   * @param headerLocation The headerLocation
   */
  public void setHeaderLocation(String headerLocation) {
    this.headerLocation = headerLocation;
  }

  /**
   * @return The headerFullLocation
   */
  public String getHeaderFullLocation() {
    return headerFullLocation;
  }

  /**
   * @param headerFullLocation The headerFullLocation
   */
  public void setHeaderFullLocation(String headerFullLocation) {
    this.headerFullLocation = headerFullLocation;
  }

  /**
   * @return The headerLocationGranularity
   */
  public String getHeaderLocationGranularity() {
    return headerLocationGranularity;
  }

  /**
   * @param headerLocationGranularity The headerLocationGranularity
   */
  public void setHeaderLocationGranularity(String headerLocationGranularity) {
    this.headerLocationGranularity = headerLocationGranularity;
  }

  /**
   * @return The totalResults
   */
  public Integer getTotalResults() {
    return totalResults;
  }

  /**
   * @param totalResults The totalResults
   */
  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  /**
   * @return The venueGroups
   */
  public List<VenueItemGroup> getVenueGroups() {
    return groups;
  }

  /**
   * @param venueGroups The venueGroups
   */
  public void setVenueGroups(List<VenueItemGroup> venueGroups) {
    this.groups = venueGroups;
  }
}
