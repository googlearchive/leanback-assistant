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

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsSupportFragment;
import android.support.v17.leanback.app.DetailsSupportFragmentBackgroundController;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.android.assistantplayback.model.Movie;

/**
 * Displays a detailed view and the metadata about a movie such as title description, price, etc.
 */
public class VideoDetailsFragment extends DetailsSupportFragment {

    public static final String EXTRA_MOVIE = "com.example.android.assistantplayback.extra.MOVIE";

    private static final int ACTION_WATCH = 1;
    private static final int ACTION_RENT = 2;
    private static final int ACTION_BUY = 3;

    private Movie mMovie;
    private ArrayObjectAdapter mAdapter;
    private DetailsSupportFragmentBackgroundController mDetailsBackground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovie = getActivity().getIntent().getParcelableExtra(EXTRA_MOVIE);
        mDetailsBackground = new DetailsSupportFragmentBackgroundController(this);

        setupAdapter();
        setupDetailsOverviewRow();
        initializeBackground(mMovie);
    }

    private void setupAdapter() {
        FullWidthDetailsOverviewRowPresenter detailsPresenter =
                new FullWidthDetailsOverviewRowPresenter(
                        new DetailsDescriptionPresenter(), new MovieDetailsOverviewLogoPresenter());

        detailsPresenter.setBackgroundColor(
                ContextCompat.getColor(getContext(), R.color.background_dark));
        detailsPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);

        detailsPresenter.setOnActionClickedListener(
                new OnActionClickedListener() {
                    @Override
                    public void onActionClicked(Action action) {
                        if (action.getId() == ACTION_WATCH) {
                            Intent intent =
                                    PlaybackActivity.createIntent(getContext(), mMovie.getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

        ClassPresenterSelector mPresenterSelector = new ClassPresenterSelector();
        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
        mAdapter = new ArrayObjectAdapter(mPresenterSelector);
        setAdapter(mAdapter);
    }

    private void setupDetailsOverviewRow() {
        final DetailsOverviewRow row = new DetailsOverviewRow(mMovie);

        Glide.with(this)
                .load(mMovie.getCardImage())
                .asBitmap()
                .dontAnimate()
                .error(R.drawable.assistant_tv_banner)
                .into(
                        new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(
                                    final Bitmap resource, GlideAnimation glideAnimation) {
                                row.setImageBitmap(getActivity(), resource);
                            }
                        });

        SparseArrayObjectAdapter adapter = new SparseArrayObjectAdapter();

        adapter.set(
                ACTION_WATCH, new Action(ACTION_WATCH, getResources().getString(R.string.watch)));
        adapter.set(
                ACTION_RENT,
                new Action(
                        ACTION_RENT,
                        getResources().getString(R.string.rent),
                        mMovie.getRentalPrice()));
        adapter.set(
                ACTION_BUY,
                new Action(
                        ACTION_BUY,
                        getResources().getString(R.string.buy),
                        mMovie.getPurchasePrice()));
        row.setActionsAdapter(adapter);

        mAdapter.add(row);
    }

    private void initializeBackground(Movie movie) {
        mDetailsBackground.enableParallax();
        Glide.with(getActivity())
                .load(movie.getBackgroundImage())
                .asBitmap()
                .centerCrop()
                .error(R.drawable.assistant_tv_banner)
                .into(
                        new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(
                                    Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                mDetailsBackground.setCoverBitmap(bitmap);
                                mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                            }
                        });
    }

    static class MovieDetailsOverviewLogoPresenter extends DetailsOverviewLogoPresenter {

        static class ViewHolder extends DetailsOverviewLogoPresenter.ViewHolder {
            public ViewHolder(View view) {
                super(view);
            }

            public FullWidthDetailsOverviewRowPresenter getParentPresenter() {
                return mParentPresenter;
            }

            public FullWidthDetailsOverviewRowPresenter.ViewHolder getParentViewHolder() {
                return mParentViewHolder;
            }
        }

        @Override
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
            ImageView imageView =
                    (ImageView)
                            LayoutInflater.from(parent.getContext())
                                    .inflate(
                                            R.layout.lb_fullwidth_details_overview_logo,
                                            parent,
                                            false);

            Resources res = parent.getResources();
            int width = res.getDimensionPixelSize(R.dimen.detail_thumb_width);
            int height = res.getDimensionPixelSize(R.dimen.detail_thumb_height);
            imageView.setLayoutParams(new ViewGroup.MarginLayoutParams(width, height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            return new ViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
            DetailsOverviewRow row = (DetailsOverviewRow) item;
            ImageView imageView = ((ImageView) viewHolder.view);
            imageView.setImageDrawable(row.getImageDrawable());
            if (isBoundToImage((ViewHolder) viewHolder, row)) {
                MovieDetailsOverviewLogoPresenter.ViewHolder vh =
                        (MovieDetailsOverviewLogoPresenter.ViewHolder) viewHolder;
                vh.getParentPresenter().notifyOnBindLogo(vh.getParentViewHolder());
            }
        }
    }

    static class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

        @Override
        protected void onBindDescription(ViewHolder viewHolder, Object item) {
            Movie movie = (Movie) item;

            if (movie != null) {
                viewHolder.getTitle().setText(movie.getTitle());
                viewHolder.getSubtitle().setText(String.valueOf(movie.getProductionYear()));
                viewHolder.getBody().setText(movie.getDescription());
            }
        }
    }
}
