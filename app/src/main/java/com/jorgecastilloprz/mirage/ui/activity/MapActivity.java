package com.jorgecastilloprz.mirage.ui.activity;

import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.InjectView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jorgecastilloprz.mirage.MirageApp;
import com.jorgecastilloprz.mirage.R;
import com.jorgecastilloprz.mirage.di.component.DaggerMapActivityComponent;
import com.jorgecastilloprz.mirage.di.component.MapActivityComponent;
import com.jorgecastilloprz.mirage.di.modules.ActivityModule;
import com.jorgecastilloprz.mirage.ui.animator.AppearAnimator;
import com.jorgecastilloprz.mirage.ui.animator.AppearAnimatorListener;
import com.jorgecastilloprz.mirage.ui.base.BaseActivity;
import com.jorgecastilloprz.mirage.utils.DisplayUtils;
import javax.inject.Inject;

/**
 * @author jorge
 * @since 11/09/15
 */
public class MapActivity extends BaseActivity
    implements AppearAnimatorListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

  @Inject AppearAnimator appearAnimator;
  @InjectView(R.id.rootView) FrameLayout rootView;

  private MapActivityComponent component;
  private int fabCenterX, fabCenterY;
  private GoogleApiClient googleApiClient;
  private Location mLastLocation;

  public MapActivityComponent component() {
    if (component == null) {
      component = DaggerMapActivityComponent.builder()
          .applicationComponent(((MirageApp) getApplication()).component())
          .activityModule(new ActivityModule(this))
          .build();
    }
    return component;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    injectStuff();
    calculateFabStats();
    appearAnimator.attachListener(this);

    getGoogleMap().moveCamera(CameraUpdateFactory.zoomTo(15));
    rootView.post(new Runnable() {
      @Override public void run() {
        playAppearAnimation();
      }
    });
  }

  private void injectStuff() {
    injectViews();
    component().inject(this);
  }

  private void calculateFabStats() {
    int fabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
    int fabMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);
    fabCenterX = DisplayUtils.getDisplayMetrics(this).widthPixels - fabSize / 2 - fabMargin;
    fabCenterY = DisplayUtils.getDisplayMetrics(this).heightPixels - fabSize - fabMargin;
  }

  private void playAppearAnimation() {
    appearAnimator.playAppearAnimation(fabCenterX, fabCenterY, rootView.getHeight(), rootView);
  }

  @Override public void onBackPressed() {
    playDisappearAnimation();
  }

  private void playDisappearAnimation() {
    appearAnimator.playDisappearAnimation(fabCenterX, fabCenterY, rootView.getHeight(), rootView);
  }

  @Override public void onAppearAnimationComplete() {
    buildGoogleApiClient();
    googleApiClient.connect();
  }

  @Override public void onDisappearAnimationComplete() {
    finish();
    overridePendingTransition(0, 0);
  }

  private void buildGoogleApiClient() {
    googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
  }

  @Override public void onConnected(Bundle bundle) {
    updateLastLocation();
    zoomToCurrentLocation();
  }

  @Override public void onConnectionSuspended(int i) {
  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {
  }

  private void updateLastLocation() {
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
  }

  private void zoomToCurrentLocation() {
    GoogleMap map = getGoogleMap();
    if (mLastLocation != null) {
      map.moveCamera(CameraUpdateFactory.newLatLng(
          new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
    }
  }

  private GoogleMap getGoogleMap() {
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    return mapFragment.getMap();
  }
}
