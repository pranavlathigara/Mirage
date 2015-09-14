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
package com.jorgecastilloprz.mirage.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import com.jorgecastilloprz.mirage.R;
import com.jorgecastilloprz.mirage.model.Place;
import com.jorgecastilloprz.mirage.model.PolicyAdvice;
import com.jorgecastilloprz.mirage.model.TutorialAdvice;
import com.jorgecastilloprz.mirage.ui.components.picassotransform.ColorTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Castillo Pérez
 */
public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

  private List<Place> places;
  private Context context;

  public PlaceListAdapter(Context context) {
    this.context = context;
    places = new ArrayList<>();
  }

  public void setPlaces(List<Place> places) {
    this.places = places;
  }

  @Override public PlaceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;

    switch (viewType) {
      case ViewTypes.POLICY:
      case ViewTypes.TUTORIAL:
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advice, parent, false);
        break;
      default:
        v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_place_list, parent, false);
    }

    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Place place = places.get(position);

    switch (getItemViewType(position)) {
      case ViewTypes.POLICY:
        Picasso.with(holder.image.getContext())
            .load(R.drawable.ic_verified_user_white_48dp)
            .into(holder.image);
        holder.title.setText(R.string.policies_title);
        holder.text.setText(R.string.policies);
        break;
      case ViewTypes.TUTORIAL:
        Picasso.with(holder.image.getContext())
            .load(R.drawable.ic_verified_user_white_48dp)
            .into(holder.image);
        holder.title.setText(((TutorialAdvice) place).getTitle());
        holder.text.setText(((TutorialAdvice) place).getMessage());
        holder.detailsButton.setVisibility(View.GONE);
        break;
      default:
        loadPhoto(place, holder.image);
        holder.title.setText(place.getName());
        holder.subtitle.setText(place.getLocationInfo().getCity());
        setTip(holder.userComment, place);

        holder.distance.setText(place.getLocationInfo().getDistance() > 0 ? place.getLocationInfo()
            .getFormattedDistance() : "");
        setupRating(holder.ratingBgCircle, holder.ratingText, place);
    }
  }

  private void setTip(TextView userComment, Place place) {
    if (place.hasTips()) {
      userComment.setText(place.getTips().get(0).getText());
    }
  }

  private void loadPhoto(Place place, ImageView imageView) {
    String photoUrl = place.getPhotos() != null && place.getPhotos().size() > 0 ? place.getPhotos()
        .get(0)
        .getUrl() : "";

    if (!photoUrl.equals("")) {
      Picasso.with(context).load(photoUrl).into(imageView);
    }
  }

  private void setupRating(ImageView ratingBgCircle, TextView ratingText, Place place) {
    if (place.getRatingInfo() != null) {
      ratingBgCircle.setVisibility(View.VISIBLE);
      ratingText.setVisibility(View.VISIBLE);
      setRatingColor(ratingBgCircle, place.getRatingInfo().getRatingColor());
      ratingText.setText(place.getRatingInfo().getRating() + "");
    } else {
      ratingBgCircle.setVisibility(View.GONE);
      ratingText.setVisibility(View.GONE);
    }
  }

  private void setRatingColor(ImageView bgCircle, String color) {
    Picasso.with(context)
        .load(R.drawable.ic_menu)
        .transform(new ColorTransform(color))
        .memoryPolicy(MemoryPolicy.NO_CACHE)
        .error(android.R.color.darker_gray)
        .into(bgCircle);
  }

  @Override public int getItemCount() {
    return places.size();
  }

  @Override public int getItemViewType(int position) {
    return places.get(position) instanceof PolicyAdvice ? ViewTypes.POLICY
        : places.get(position) instanceof TutorialAdvice ? ViewTypes.TUTORIAL : ViewTypes.DEFAULT;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.card) @Optional CardView card;
    @InjectView(R.id.image) @Optional ImageView image;
    @InjectView(R.id.title) @Optional TextView title;
    @InjectView(R.id.subtitle) @Optional TextView subtitle;
    @InjectView(R.id.userComment) @Optional TextView userComment;
    @InjectView(R.id.distance) @Optional TextView distance;
    @InjectView(R.id.ratingBgCircle) @Optional ImageView ratingBgCircle;
    @InjectView(R.id.ratingText) @Optional TextView ratingText;
    @InjectView(R.id.text) @Optional TextView text;
    @InjectView(R.id.detailsButton) @Optional TextView detailsButton;
    @InjectView(R.id.understoodButton) @Optional TextView understoodButton;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.inject(this, v);
    }
  }

  class ViewTypes {
    public static final int POLICY = 0;
    public static final int TUTORIAL = 1;
    public static final int DEFAULT = 2;
  }
}
