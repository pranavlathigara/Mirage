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
package com.github.jorgecastilloprz.mirage.api.foursquare.mapper;

import com.github.jorgecastilloprz.mirage.api.foursquare.model.FeaturedPhoto;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.FoursquareCategory;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.FoursquareVenuePhotoItem;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.Location;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.Tip;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.Venue;
import com.github.jorgecastilloprz.mirage.api.foursquare.model.VenueItem;
import com.jorgecastilloprz.mirage.datasources.exceptions.NetworkMapperException;
import com.jorgecastilloprz.mirage.model.Category;
import com.jorgecastilloprz.mirage.model.ContactInfo;
import com.jorgecastilloprz.mirage.model.LocationInfo;
import com.jorgecastilloprz.mirage.model.Photo;
import com.jorgecastilloprz.mirage.model.Place;
import com.jorgecastilloprz.mirage.model.RatingInfo;
import com.jorgecastilloprz.mirage.model.UserData;
import com.jorgecastilloprz.mirage.model.UserTip;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class FoursquarePlaceMapperImpl implements FoursquarePlaceMapper {

  @Inject FoursquarePlaceMapperImpl() {
  }

  @Override public List<Place> map(List<VenueItem> venueItems) throws NetworkMapperException {
    List<Place> places = new ArrayList<>();
    for (VenueItem venueItem : venueItems) {
      places.add(map(venueItem));
    }

    return places;
  }

  @Override public Place map(VenueItem venueItem) throws NetworkMapperException {
    Venue venue = venueItem.getVenue();
    return new Place(venue.getId(), venue.getName(), venue.getUrl(), mapContactInfo(venue),
        mapLocation(venue), mapCategories(venue), mapRating(venue), mapPhotos(venue),
        mapUserTips(venueItem));
  }

  private ContactInfo mapContactInfo(Venue venue) {
    return new ContactInfo(venue.getContact().getTwitter(), venue.getContact().getFacebook(),
        venue.getContact().getFacebookUsername(), venue.getContact().getFacebookName());
  }

  private LocationInfo mapLocation(Venue venue) {
    Location location = venue.getLocation();
    return new LocationInfo(location.getCrossStreet(), location.getLat(), location.getLng(),
        formatDistance(location.getDistance() != null ? location.getDistance() : 0),
        location.getPostalCode(), location.getCc(), location.getCity(), location.getState(),
        location.getCountry(), location.getAddress());
  }

  private double formatDistance(Integer distance) {
    double distanceInKm;
    if (distance >= 1000) {
      distanceInKm = (double) Math.round((distance / 1000d) * 10) / 10;
    } else {
      distanceInKm = distance;
    }

    return distanceInKm;
  }

  private List<Category> mapCategories(Venue venue) {
    List<Category> categories = new ArrayList<>();

    for (FoursquareCategory category : venue.getCategories()) {
      categories.add(
          new Category(category.getIcon().getPrefix() + "original" + category.getIcon().getSuffix(),
              category.getName(), category.getId()));
    }
    return categories;
  }

  private List<Photo> mapPhotos(Venue venue) {
    List<Photo> photos = new ArrayList<>();
    if (venue.hasFeaturedPhotos()) {
      for (FeaturedPhoto featuredPhoto : venue.getFeaturedPhotos().getItems()) {
        Photo photo = new Photo(featuredPhoto.getId(), featuredPhoto.getCreatedAt(),
            featuredPhoto.getPrefix() + "original" + featuredPhoto.getSuffix(),
            featuredPhoto.getWidth(), featuredPhoto.getHeight(),
            new UserData(featuredPhoto.getUser().getId(),
                featuredPhoto.getUser().getFirstName() + " " + featuredPhoto.getUser()
                    .getLastName(), featuredPhoto.getUser().getPhoto().getPrefix() + "original"
                + featuredPhoto.getUser().getPhoto().getSuffix()));

        photos.add(photo);
      }
    }

    if (venue.hasPhotos()) {
      for (FoursquareVenuePhotoItem photoItem : venue.getPhotos().getGroups().get(0).getItems()) {
        Photo photo = new Photo(photoItem.getId(), photoItem.getCreatedAt(),
            photoItem.getPrefix() + "original" + photoItem.getSuffix(), photoItem.getWidth(),
            photoItem.getHeight(), new UserData(photoItem.getUser().getId(),
            photoItem.getUser().getFirstName() + " " + photoItem.getUser().getLastName(),
            photoItem.getUser().getPhoto().getPrefix() + "original" + photoItem.getUser()
                .getPhoto()
                .getSuffix()));

        photos.add(photo);
      }
    }

    return photos;
  }

  private List<UserTip> mapUserTips(VenueItem venueItem) {
    List<UserTip> userTips = new ArrayList<>();
    if (venueItem.getTips() != null && venueItem.getTips().size() > 0) {
      for (Tip restTip : venueItem.getTips()) {
        Timestamp stamp = new Timestamp(restTip.getCreatedAt());
        Date date = new Date(stamp.getTime());
        userTips.add(new UserTip(restTip.getId(), date.toString(), restTip.getText(),
            new UserData(restTip.getUser().getId(),
                restTip.getUser().getFirstName() + " " + restTip.getUser().getLastName(),
                restTip.getUser().getPhoto().getPrefix() + "original" + restTip.getUser()
                    .getPhoto()
                    .getSuffix())));
      }
    }

    return userTips;
  }

  private RatingInfo mapRating(Venue venue) {
    RatingInfo venueRatingInfo = null;
    if (venue.getRating() != null) {
      venueRatingInfo =
          new RatingInfo(venue.getRating(), venue.getRatingColor(), venue.getRatingSignals());
    }

    return venueRatingInfo;
  }
}
