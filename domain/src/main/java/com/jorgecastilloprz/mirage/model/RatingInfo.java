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
package com.jorgecastilloprz.mirage.model;

/**
 * @author Jorge Castillo Pérez
 */
public class RatingInfo {

  private int rating;
  private String ratingColor;
  private int ratingSignals;

  public RatingInfo(int rating, String ratingColor, int ratingSignals) {
    this.rating = rating;
    this.ratingColor = ratingColor;
    this.ratingSignals = ratingSignals;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getRatingColor() {
    return ratingColor;
  }

  public void setRatingColor(String ratingColor) {
    this.ratingColor = ratingColor;
  }

  public int getRatingSignals() {
    return ratingSignals;
  }

  public void setRatingSignals(int ratingSignals) {
    this.ratingSignals = ratingSignals;
  }
}
