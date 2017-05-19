/*
 * Copyright 2017 eneim@Eneim Labs, nam@ene.im
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

package im.ene.toro;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by eneim on 1/20/17.
 *
 * A base implementation of {@link PlayerManager}, ready to use.
 *
 * @since 2.2.0
 */

public abstract class BaseAdapter<VH extends ToroAdapter.ViewHolder> extends ToroAdapter<VH>
    implements PlayerManager {

  private final PlayerManager delegate;

  public BaseAdapter() {
    super();
    this.delegate = PlayerManager.Factory.getInstance();
  }

  @Override public void remove() throws Exception {
    this.delegate.remove();
  }

  @Nullable @Override public ToroPlayer getPlayer() {
    return delegate.getPlayer();
  }

  @Override public void setPlayer(ToroPlayer player) {
    delegate.setPlayer(player);
  }

  @Override public void onRegistered() {
    delegate.onRegistered();
  }

  @Override public void onUnregistered() {
    delegate.onUnregistered();
  }

  @Override public void startPlayback() {
    delegate.startPlayback();
  }

  @Override public void pausePlayback() {
    delegate.pausePlayback();
  }

  @Override public void stopPlayback() {
    delegate.stopPlayback();
  }

  @Deprecated @Override
  public void saveVideoState(String videoId, @Nullable Long position, long duration) {
    delegate.saveVideoState(videoId, position, duration);
  }

  @Override public void savePlaybackState(String mediaId, @Nullable Long position, long duration) {
    delegate.savePlaybackState(mediaId, position, duration);
  }

  @Override public void restorePlaybackState(String mediaId) {
    delegate.restorePlaybackState(mediaId);
  }

  @Nullable @Override public PlaybackState getPlaybackState(String mediaId) {
    return delegate.getPlaybackState(mediaId);
  }

  @NonNull @Override public ArrayList<PlaybackState> getPlaybackStates() {
    return delegate.getPlaybackStates();
  }

  @Deprecated @Override public void restoreVideoState(String videoId) {
    delegate.restorePlaybackState(videoId);
  }

  @Deprecated @Override public PlaybackState getSavedState(String videoId) {
    return delegate.getPlaybackState(videoId);
  }

  @Override public String toString() {
    return getClass().getSimpleName() + "@" + hashCode();
  }
}
