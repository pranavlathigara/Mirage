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
package com.jorgecastilloprz.mirage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.InjectView;
import com.jorgecastilloprz.mirage.MainPresenter;
import com.jorgecastilloprz.mirage.MirageApp;
import com.jorgecastilloprz.mirage.R;
import com.jorgecastilloprz.mirage.di.component.DaggerMainActivityComponent;
import com.jorgecastilloprz.mirage.di.component.MainActivityComponent;
import com.jorgecastilloprz.mirage.di.modules.ActivityModule;
import com.jorgecastilloprz.mirage.log.Logger;
import com.jorgecastilloprz.mirage.ui.base.SignInActivity;
import com.jorgecastilloprz.mirage.ui.fragment.HighlightPlacesListFragment;
import com.jorgecastilloprz.mirage.ui.fragment.NearPlacesListFragment;
import com.jorgecastilloprz.mirage.ui.fragment.adapter.MainSectionPagerAdapter;
import javax.inject.Inject;

/**
 * @author Jorge Castillo Pérez
 */
public class MainActivity extends SignInActivity implements MainPresenter.View {

  @InjectView(R.id.toolbar) Toolbar toolbar;
  @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
  @InjectView(R.id.viewpager) ViewPager viewPager;
  @InjectView(R.id.nav_view) NavigationView navigationView;
  @InjectView(R.id.tabs) TabLayout tabLayout;
  @InjectView(R.id.fab) FloatingActionButton fab;

  @Inject MainPresenter presenter;
  @Inject Logger logger;

  private MainActivityComponent component;

  public MainActivityComponent component() {
    if (component == null) {
      component = DaggerMainActivityComponent.builder()
          .applicationComponent(((MirageApp) getApplication()).component())
          .activityModule(new ActivityModule(this))
          .build();
    }
    return component;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    injectStuff();

    setupActionBar();

    navigationView = (NavigationView) findViewById(R.id.nav_view);
    setupDrawerContent(navigationView);

    setupViewPager(viewPager);
    setupTabs();
    initPresenter();
  }

  private void injectStuff() {
    injectViews();
    component().inject(this);
  }

  private void setupActionBar() {
    setSupportActionBar(toolbar);
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override protected void onConnectionComplete() {

  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
          }
        });
  }

  private void setupViewPager(ViewPager viewPager) {
    MainSectionPagerAdapter adapter = new MainSectionPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(NearPlacesListFragment.newInstance(), "Place list");
    adapter.addFragment(HighlightPlacesListFragment.newInstance(), "Highlighted places");
    viewPager.setAdapter(adapter);
  }

  private void setupTabs() {
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.getTabAt(0).setIcon(R.drawable.ic_view_day_white_24dp);
    tabLayout.getTabAt(0).setText("");
    tabLayout.getTabAt(1).setIcon(R.drawable.ic_pages_white_24dp);
    tabLayout.getTabAt(1).setText("");
    tabLayout.getTabAt(1).getIcon().mutate().setAlpha(125);

    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override public void onTabSelected(TabLayout.Tab tab) {
        tab.getIcon().mutate().setAlpha(255);
        viewPager.setCurrentItem(tab.getPosition(), true);
      }

      @Override public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().mutate().setAlpha(125);
      }

      @Override public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  private void initPresenter() {
    presenter.setView(this);
    presenter.initialize();
  }

  @Override protected void onStart() {
    super.onStart();
    mGoogleApiClient.connect();
  }

  @Override protected void onStop() {
    mGoogleApiClient.disconnect();
    super.onStop();
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override public void exitToSignInActivity() {
    Intent signUpIntent = new Intent(this, MirageSignInActivity.class);
    startActivity(signUpIntent);
    finish();
  }

  @Override public void displayError(String message) {
    Snackbar.make(fab, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;

      case R.id.action_settings:
        return true;

      case R.id.action_signout:
        presenter.onSignOutButtonClick();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }
}