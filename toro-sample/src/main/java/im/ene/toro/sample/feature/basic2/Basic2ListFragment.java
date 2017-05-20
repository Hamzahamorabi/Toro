/*
 * Copyright 2016 eneim@Eneim Labs, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.toro.sample.feature.basic2;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import im.ene.toro.PlayerListView;
import im.ene.toro.PlaylistHelper;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroStrategy;
import im.ene.toro.sample.BaseToroFragment;
import im.ene.toro.sample.R;
import java.util.List;

/**
 * Created by eneim on 6/30/16.
 *
 * Basic sample 2 introduces <b>Ordered</b> playback list. i.e Videos are played from the very
 * first
 * Video instead of the first prepared Video. First Video order/position is provided by Adapter.
 *
 * In this sample, we manipulate our global {@link ToroStrategy} to make it be able to listen to
 * our Playback order. See {@link Basic2ListFragment#onAttach(Context)}
 */
public class Basic2ListFragment extends BaseToroFragment {

  protected PlayerListView recyclerView;
  protected Basic2Adapter adapter;
  private PlaylistHelper playlistHelper;

  public static Basic2ListFragment newInstance() {
    return new Basic2ListFragment();
  }

  // To tell Toro's strategy which is the Video to play first.
  int firstVideoPosition;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.generic_recycler_view, container, false);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = (PlayerListView) view.findViewById(R.id.recycler_view);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

    adapter = new Basic2Adapter();
    // Do the magic.
    firstVideoPosition = adapter.getFirstVideoPosition();

    recyclerView.setAdapter(adapter);
    playlistHelper = new PlaylistHelper(adapter);

    final ToroStrategy baseStrategy = ToroStrategy.FIRST_PLAYABLE_TOP_DOWN;
    ToroStrategy newStrategy = new ToroStrategy() {
      boolean firstVideoObserved = false;

      @Override public String getDescription() {
        return "First video plays first";
      }

      @Override public ToroPlayer findBestPlayer(List<ToroPlayer> candidates) {
        return baseStrategy.findBestPlayer(candidates);
      }

      @Override public boolean allowsToPlay(ToroPlayer player, ViewParent parent) {
        boolean allowToPlay = (firstVideoObserved || player.getPlayOrder() == firstVideoPosition)
            && baseStrategy.allowsToPlay(player, parent);
        // Keep track of first video on top.
        if (player.getPlayOrder() == firstVideoPosition) {
          firstVideoObserved = true;
        }
        return allowToPlay;
      }
    };

    playlistHelper.setStrategy(newStrategy);
  }

  @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    // Trick to force RecyclerView to scroll to first Video position. Note that it will trigger the
    // scroll every time the Fragment resumes, so comment out to disable.
    recyclerView.postDelayed(new Runnable() {
      @Override public void run() {
        if (recyclerView != null) {
          recyclerView.smoothScrollToPosition(firstVideoPosition);
        }
      }
    }, 200);
  }

  @Override protected void dispatchFragmentActive() {
    playlistHelper.registerPlayerListView(recyclerView);
  }

  @Override protected void dispatchFragmentInactive() {
    playlistHelper.registerPlayerListView(null);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    playlistHelper = null;
  }
}
