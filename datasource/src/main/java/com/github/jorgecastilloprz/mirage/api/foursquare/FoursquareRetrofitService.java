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

import com.github.jorgecastilloprz.mirage.api.foursquare.model.NearPlacesFoursquareResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Jorge Castillo Pérez
 */
public interface FoursquareRetrofitService {

  @GET("/venues/explore") NearPlacesFoursquareResponse obtainPlacesAround(@Query("ll") String ll,
      @Query("categoryId") String categoryId, @Query("limit") int limit,
      @Query("radius") int radius, @Query("venuePhotos") int venuePhotos,
      @Query("v") String apiCompatibilityDate, @Query("client_id") String clientId,
      @Query("client_secret") String clientSecret, @Query("day") String day,
      @Query("time") String time, @Query("offset") int offset);

  @GET("/venues/explore") NearPlacesFoursquareResponse obtainHighlightPlacesForCountry(
      @Query("near") String near, @Query("limit") int limit, @Query("venuePhotos") int venuePhotos,
      @Query("v") String apiCompatibilityDate, @Query("client_id") String clientId,
      @Query("client_secret") String clientSecret, @Query("day") String day,
      @Query("time") String time, @Query("offset") int offset);
}
