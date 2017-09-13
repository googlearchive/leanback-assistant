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

package com.example.android.assistantplayback.playlist;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;

/** Manages a playlist of MediaDescriptionCompat items. */
public interface PlaylistAdapter {

    /**
     * Returns the current item in the playlist.
     *
     * @return The current item in the playlist.
     */
    MediaDescriptionCompat getCurrentItem();

    /**
     * Moves to the next item in the playlist. If there are no more items, then will return the
     * current item.
     *
     * @return Next item in the playlist.
     */
    @NonNull
    MediaDescriptionCompat skipToNextItem();

    /**
     * Moves to the previous item in the playlist. If already at the beginning of the playlist, then
     * returns the current item.
     *
     * @return Previous item in the playlist.
     */
    @NonNull
    MediaDescriptionCompat skipToPreviousItem();
}
