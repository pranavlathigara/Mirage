package com.github.jorgecastilloprz.mirage.api.foursquare.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo") public class Reasons {

  @Expose private Integer count;
  @Expose private List<ReasonItem> items = new ArrayList<ReasonItem>();

  /**
   * @return The count
   */
  public Integer getCount() {
    return count;
  }

  /**
   * @param count The count
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  /**
   * @return The items
   */
  public List<ReasonItem> getItems() {
    return items;
  }

  /**
   * @param items The items
   */
  public void setItems(List<ReasonItem> items) {
    this.items = items;
  }
}
