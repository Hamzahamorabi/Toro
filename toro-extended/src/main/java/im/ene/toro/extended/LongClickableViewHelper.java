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

package im.ene.toro.extended;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnLongClickListener;
import im.ene.toro.PlayerManager;
import im.ene.toro.Toro;
import im.ene.toro.ToroPlayer;
import im.ene.toro.exoplayer2.ExoPlayerViewHelper;

/**
 * Created by eneim on 10/4/16.
 */

public class LongClickableViewHelper extends ExoPlayerViewHelper implements OnLongClickListener {

  public LongClickableViewHelper(@NonNull ToroPlayer player, @NonNull View itemView) {
    super(player, itemView);
  }

  @Override public boolean onLongClick(View v) {
    PlayerManager manager = getPlayerManager(itemView.getParent());
    // Important components are missing, return
    if (manager == null) {
      return false;
    }

    // Being pressed player is not be able to play, return
    if (!player.wantsToPlay() || !Toro.getStrategy().allowsToPlay(player, itemView.getParent())) {
      return false;
    }

    ToroPlayer currentPlayer = manager.getPlayer();
    if (player != currentPlayer) {
      // Being pressed player is a new one
      // All conditions to switch players has passed, process the switching
      // Manually save Video state
      // Not the current player, and new player wants to play, so switch players
      if (currentPlayer != null) {
        if (currentPlayer.isPlaying()) {
          manager.savePlaybackState(currentPlayer.getMediaId(), currentPlayer.getCurrentPosition(),
              currentPlayer.getDuration());
        }
        // Force pause
        manager.pausePlayback();
      }

      manager.setPlayer(null);
      // Trigger new player
      if (!player.isPrepared()) {
        player.preparePlayer(false);
      } else {
        manager.setPlayer(player);
        manager.restorePlaybackState(player.getMediaId());
        manager.startPlayback();
      }
    } else {
      // Pressing current player, pause it if it is playing
      if (currentPlayer.isPlaying()) {
        manager.savePlaybackState(currentPlayer.getMediaId(), currentPlayer.getCurrentPosition(),
            currentPlayer.getDuration());
        manager.pausePlayback();
      } else {
        // It's paused, so we resume it
        if (!currentPlayer.isPrepared()) {
          currentPlayer.preparePlayer(false);
        } else {
          manager.restorePlaybackState(currentPlayer.getMediaId());
          manager.startPlayback();
        }
      }
    }

    return true;
  }
}
