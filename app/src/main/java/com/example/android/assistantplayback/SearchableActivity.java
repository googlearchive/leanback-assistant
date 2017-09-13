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

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.assistantplayback.data.MockDatabase;
import com.example.android.assistantplayback.model.Movie;

import static android.support.v4.content.IntentCompat.EXTRA_START_PLAYBACK;

/**
 * Handles the intent from a global search. <br>
 * The assistant determines if the content should begin immediately, and lets us know with the
 * boolean extra value, {@link android.support.v4.content.IntentCompat#EXTRA_START_PLAYBACK
 * EXTRA_START_PLAYBACK}. If <em>true</em>, launches {@link PlaybackActivity}, otherwise, launches
 * {@link VideoDetailsActivity}.
 */
public class SearchableActivity extends Activity {

    private static final String TAG = "SearchableActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Search data " + getIntent().getData());

        if (getIntent() != null && getIntent().getData() != null) {
            Uri uri = getIntent().getData();
            int id = Integer.valueOf(uri.getLastPathSegment());

            boolean startPlayback = getIntent().getBooleanExtra(EXTRA_START_PLAYBACK, false);
            Log.d(TAG, "Should start playback? " + (startPlayback ? "yes" : "no"));

            if (startPlayback) {
                startActivity(PlaybackActivity.createIntent(this, id));
            } else {
                Movie movie = MockDatabase.findMovieWithId(id);
                startActivity(VideoDetailsActivity.createIntent(this, movie));
            }
        }
        finish();
    }
}
