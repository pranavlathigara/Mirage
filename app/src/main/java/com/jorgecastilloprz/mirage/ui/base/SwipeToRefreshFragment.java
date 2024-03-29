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
package com.jorgecastilloprz.mirage.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import butterknife.InjectView;
import com.jorgecastilloprz.mirage.R;
import com.jorgecastilloprz.mirage.bus.EventBus;
import javax.inject.Inject;

/**
 * @author Jorge Castillo Pérez
 */
public abstract class SwipeToRefreshFragment extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener {

  @InjectView(R.id.swipeContainer) SwipeRefreshLayout swipeLayout;
  @Inject EventBus bus;

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    swipeLayout.setOnRefreshListener(this);
    swipeLayout.setColorSchemeResources(R.color.primary);

    swipeLayout.post(new Runnable() {
      @Override public void run() {
        swipeLayout.setRefreshing(true);
        onRefresh();
      }
    });
  }

  @Override public abstract void onRefresh();

  protected void stopRefreshAnimation() {
    swipeLayout.setRefreshing(false);
  }
}
