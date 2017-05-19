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

package im.ene.toro.sample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import im.ene.toro.sample.R;

/**
 * Created by eneim on 3/11/16.
 */
public class SampleItemButton extends LinearLayoutCompat {

  public SampleItemButton(Context context) {
    this(context, null);
  }

  public SampleItemButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  @BindView(R.id.title) TextView titleTextView;
  @BindView(R.id.subtitle) TextView subtitleTextView;
  @BindView(R.id.sample_gif) ImageView demoImageView;

  public SampleItemButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    LayoutInflater.from(context).inflate(R.layout.sample_item_button, this, true);
    ButterKnife.bind(this, this);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SampleItemButton, defStyle, 0);
    String title = a.getString(R.styleable.SampleItemButton_btn_title);
    String subTitle = a.getString(R.styleable.SampleItemButton_btn_subtitle);

    final int imageResource = a.getResourceId(R.styleable.SampleItemButton_btn_image, 0);

    a.recycle();

    if (!TextUtils.isEmpty(title)) {
      titleTextView.setText(title);
    }

    if (subTitle == null || TextUtils.isEmpty(subTitle)) {
      subtitleTextView.setVisibility(GONE);
    } else {
      subtitleTextView.setVisibility(VISIBLE);
      //noinspection deprecation
      subtitleTextView.setText(Html.fromHtml(subTitle));
    }

    if (imageResource != 0) {
      demoImageView.setVisibility(VISIBLE);
      GlideDrawableImageViewTarget imageViewTarget =
          new GlideDrawableImageViewTarget(demoImageView);
      Glide.with(context)
          .load(imageResource)
          .diskCacheStrategy(DiskCacheStrategy.RESULT)
          .skipMemoryCache(true)
          .crossFade()
          .into(imageViewTarget);
    } else {
      demoImageView.setVisibility(GONE);
    }
  }

  public void setTitle(CharSequence title) {
    this.titleTextView.setText(title);
  }

  public void setSubTitle(CharSequence subTitle) {
    //noinspection deprecation
    this.subtitleTextView.setText(Html.fromHtml(subTitle.toString()));
    this.subtitleTextView.setVisibility(VISIBLE);
  }

  public void setImageResource(@DrawableRes int imageResource) {
    if (imageResource != 0) {
      demoImageView.setVisibility(VISIBLE);
      GlideDrawableImageViewTarget imageViewTarget =
          new GlideDrawableImageViewTarget(demoImageView);
      Glide.with(getContext())
          .load(imageResource)
          .diskCacheStrategy(DiskCacheStrategy.RESULT)
          .skipMemoryCache(true)
          .crossFade()
          .into(imageViewTarget);
    } else {
      demoImageView.setVisibility(GONE);
    }
  }
}
