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
package com.jorgecastilloprz.mirage.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.jorgecastilloprz.mirage.R;

/**
 * @author Jorge Castillo Pérez
 */
public class SignInActivity extends BaseActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    View.OnClickListener {

  private GoogleApiClient mGoogleApiClient;
  private final int RC_SIGN_IN = 193;

  /**
   * True if the sign-in button was clicked.  When true, we know to resolve all
   * issues preventing sign-in without waiting.
   */
  private boolean mSignInClicked;

  /**
   * True if we are in the process of resolving a ConnectionResult
   */
  private boolean mIntentInProgress;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(new Scope("profile"))
        .build();
  }

  @Override public void onClick(View view) {
    if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
      mSignInClicked = true;
      mGoogleApiClient.connect();
    }
  }

  @Override public void onConnected(Bundle connectionHint) {
    mSignInClicked = false;
    Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
  }

  @Override public void onConnectionSuspended(int i) {

  }

  @Override public void onConnectionFailed(ConnectionResult result) {
    if (!mIntentInProgress) {
      if (mSignInClicked && result.hasResolution()) {
        // The user has already clicked 'sign-in' so we attempt to resolve all
        // errors until the user is signed in, or they cancel.
        try {
          result.startResolutionForResult(this, RC_SIGN_IN);
          mIntentInProgress = true;
        } catch (IntentSender.SendIntentException e) {
          // The intent was canceled before it was sent.  Return to the default
          // state and attempt to connect to get an updated ConnectionResult.
          mIntentInProgress = false;
          mGoogleApiClient.connect();
        }
      }
    }
  }

  protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
    if (requestCode == RC_SIGN_IN) {
      if (responseCode != RESULT_OK) {
        mSignInClicked = false;
      }

      mIntentInProgress = false;

      if (!mGoogleApiClient.isConnected()) {
        mGoogleApiClient.reconnect();
      }
    }
  }
}
