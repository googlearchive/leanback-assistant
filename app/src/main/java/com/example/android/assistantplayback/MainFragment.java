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
 *
 */

package com.example.android.assistantplayback;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;

import com.example.android.assistantplayback.data.MockDatabase;
import com.example.android.assistantplayback.model.Movie;

/** Displays videos in a vertical grid. */
public final class MainFragment extends android.support.v17.leanback.app.VerticalGridFragment {

    private static final int NUM_COLUMNS = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayObjectAdapter mRowAdapter =
                new ArrayObjectAdapter(new VideoCardViewPresenter(getContext()));
        setAdapter(mRowAdapter);

        setTitle(getString(R.string.assistant_playback_videos));
        setBadgeDrawable(
                getActivity().getResources().getDrawable(R.drawable.assistant_tv_banner, null));

        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        for (Movie movie : MockDatabase.getAllMovies()) {
            mRowAdapter.add(movie);
        }

        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            Movie movie = (Movie) item;

            startActivity(VideoDetailsActivity.createIntent(getContext(), movie));
        }
    }
}
