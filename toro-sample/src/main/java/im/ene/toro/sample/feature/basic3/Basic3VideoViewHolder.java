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

package im.ene.toro.sample.feature.basic3;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import im.ene.toro.exoplayer2.ExoPlayerHelper;
import im.ene.toro.exoplayer2.ExoPlayerView;
import im.ene.toro.extended.ExtPlayerViewHolder;
import im.ene.toro.sample.R;
import im.ene.toro.sample.data.SimpleVideoObject;

/**
 * Created by eneim on 6/29/16.
 *
 * This sample use {@link ExoPlayerView} API to play medias. So by default, Video ViewHolder
 * requires an implemented Component of that interface.
 */
public class Basic3VideoViewHolder extends ExtPlayerViewHolder {

  // vh_toro_video_basic_3 is the updated version for vh_toro_video_basic, which has an extra
  // TextView to show the selective click event handling.
  public static final int LAYOUT_RES = R.layout.vh_toro_video_basic_3;

  private SimpleVideoObject video;
  private MediaSource mediaSource;
  final TextView dummyView;

  public Basic3VideoViewHolder(View itemView) {
    super(itemView);
    dummyView = (TextView) itemView.findViewById(R.id.text);
  }

  @Override protected void onBind(RecyclerView.Adapter adapter, @Nullable Object item) {
    if (!(item instanceof SimpleVideoObject)) {
      throw new IllegalArgumentException("Invalid Object: " + item);
    }

    this.video = (SimpleVideoObject) item;
    // prepare mediaSource
    this.mediaSource = ExoPlayerHelper.buildMediaSource(itemView.getContext(), //
        Uri.parse(this.video.video), new DefaultDataSourceFactory(itemView.getContext(),
            Util.getUserAgent(itemView.getContext(), "Toro-Sample")), itemView.getHandler(), null);
  }

  @Override protected ExoPlayerView findVideoView(View itemView) {
    return (ExoPlayerView) itemView.findViewById(R.id.video);
  }

  @NonNull @Override protected MediaSource getMediaSource() {
    return mediaSource;
  }

  // MEMO: Unique or null
  @Nullable @Override public String getMediaId() {
    return this.video != null ? this.video.video + "@" + getAdapterPosition() : null;
  }

  /* END: ToroPlayer callbacks (partly) */

  // Interaction setup
  @Override public void setOnItemClickListener(View.OnClickListener listener) {
    super.setOnItemClickListener(listener);
    playerView.setOnClickListener(listener);
    // HINT: Un-comment this to enable click event on this TextView.
    // dummyView.setOnClickListener(listener);
  }

  @Override public void setOnItemLongClickListener(final View.OnLongClickListener listener) {
    super.setOnItemLongClickListener(listener);
    playerView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        return listener.onLongClick(v) || helper.onLongClick(v);
      }
    });
  }

  @Override public Target getNextTarget() {
    return Target.THIS_PLAYER;
  }
}
