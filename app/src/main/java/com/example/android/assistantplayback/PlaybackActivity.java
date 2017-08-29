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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/** Loads {@link PlaybackFragment}. */
public class PlaybackActivity extends FragmentActivity {

    /**
     * Builds an activity intent to play a movie.
     *
     * @param context Used to create an {@link Intent}.
     * @param movieId The id of the movie to play.
     * @return An intent that can be started to play a movie.
     */
    public static Intent createIntent(Context context, int movieId) {
        Intent intent = new Intent(context, PlaybackActivity.class);
        intent.putExtra(PlaybackFragment.EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
