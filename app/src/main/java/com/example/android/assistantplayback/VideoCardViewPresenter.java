/*
 * Copyright (c) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.assistantplayback;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.assistantplayback.model.Movie;

/** Renders movies onto cards. */
public class VideoCardViewPresenter extends Presenter {

    private final Context mContext;

    public VideoCardViewPresenter(Context context) {
        mContext = new ContextThemeWrapper(context, R.style.DefaultCardTheme);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageCardView imageCardView = new ImageCardView(mContext);
        return new ViewHolder(imageCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;
        ImageCardView view = (ImageCardView) viewHolder.view;

        view.setTitleText(movie.getTitle());
        view.setContentText(movie.getDescription());

        Glide.with(mContext).load(movie.getCardImage()).asBitmap().into(view.getMainImageView());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {}
}
