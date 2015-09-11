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
package com.github.jorgecastilloprz.mirage.api.foursquare;

import com.github.jorgecastilloprz.mirage.api.foursquare.mapper.FoursquarePlaceMapper;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.NearPlacesFoursquareResponse;
import com.jorgecastilloprz.mirage.datasources.PlacesNetworkDataSource;
import com.jorgecastilloprz.mirage.datasources.exceptions.NetworkMapperException;
import com.jorgecastilloprz.mirage.datasources.exceptions.ObtainPlacesNetworkException;
import com.jorgecastilloprz.mirage.model.Place;
import java.util.List;
import javax.inject.Inject;
import retrofit.RetrofitError;

/**
 * @author Jorge Castillo Pérez
 */
public class PlacesNetworkDataSourceImpl implements PlacesNetworkDataSource {

  private FoursquareRetrofitService service;
  private FoursquarePlaceMapper placeMapper;

  private final int RESULT_COUNT = 50;
  private final int RADIUS = 100000;

  @Inject PlacesNetworkDataSourceImpl(FoursquareRetrofitService service,
      FoursquarePlaceMapper placeMapper) {
    this.service = service;
    this.placeMapper = placeMapper;
  }

  @Override
  public List<Place> obtainPlacesAround(int pageToLoad, double lat, double lng, int radius)
      throws ObtainPlacesNetworkException, NetworkMapperException {

    NearPlacesFoursquareResponse response;
    try {
      response =
          service.obtainPlacesAround(lat + "," + lng, CategoryUtils.getCategories(), RESULT_COUNT,
              RADIUS, 1, "any", "any", pageToLoad * RESULT_COUNT);
    } catch (RetrofitError error) {
      throw new ObtainPlacesNetworkException();
    }

    try {
      return placeMapper.map(response.getResponse().getVenueGroups().get(0).getItems());
    } catch (Exception e) {
      throw new NetworkMapperException();
    }
  }

  @Override public List<Place> obtainHighlightPlacesForCountry(int pageToLoad, String country)
      throws ObtainPlacesNetworkException, NetworkMapperException {
    NearPlacesFoursquareResponse response;
    try {
      response = service.obtainHighlightPlacesForCountry(country, RESULT_COUNT, 1, "any", "any",
          pageToLoad * RESULT_COUNT);
    } catch (RetrofitError error) {
      throw new ObtainPlacesNetworkException();
    }

    try {
      return placeMapper.map(response.getResponse().getVenueGroups().get(0).getItems());
    } catch (Exception e) {
      throw new NetworkMapperException();
    }
  }
}
