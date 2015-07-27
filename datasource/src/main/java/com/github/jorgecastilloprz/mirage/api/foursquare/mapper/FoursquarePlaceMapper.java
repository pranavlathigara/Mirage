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

import com.github.jorgecastilloprz.mirage.api.foursquare.model.VenueItem;
import com.github.jorgecastilloprz.mirage.mapper.Mapper;
import com.jorgecastilloprz.mirage.datasources.exceptions.MappingException;
import com.jorgecastilloprz.mirage.datasources.exceptions.NetworkMapperException;
import com.jorgecastilloprz.mirage.model.Place;
import java.util.List;

/**
 * Restricts generic types to just allow a mapping from a rest {@link VenueItem} to a domain
 * {@link Place}.
 *
 * @author Jorge Castillo Pérez
 */
public interface FoursquarePlaceMapper extends Mapper<VenueItem, Place> {

  @Override List<Place> map(List<VenueItem> venueItems) throws NetworkMapperException;

  @Override Place map(VenueItem venueItem) throws NetworkMapperException;
}
