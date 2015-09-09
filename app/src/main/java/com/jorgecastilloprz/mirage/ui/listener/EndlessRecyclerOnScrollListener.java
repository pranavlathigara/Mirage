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
package com.jorgecastilloprz.mirage.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Jorge Castillo Pérez
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

  private int sizeBeforeScroll = 0;
  private boolean loading = false;
  private int visibleThreshold = 2;
  int firstVisibleItem, visibleItemCount, totalItemCount;

  private LinearLayoutManager mLinearLayoutManager;

  public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
    this.mLinearLayoutManager = linearLayoutManager;
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    visibleItemCount = recyclerView.getChildCount();
    totalItemCount = mLinearLayoutManager.getItemCount();
    firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

    if (loading) {
      if (totalItemCount > sizeBeforeScroll) {
        loading = false;
        sizeBeforeScroll = totalItemCount;
      }
    } else if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
      endHasBeenReached();
    }
  }

  private void endHasBeenReached() {
    loading = true;
    onLoadMore();
  }

  public abstract void onLoadMore();
}