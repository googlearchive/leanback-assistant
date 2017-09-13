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
import android.support.v17.leanback.media.PlaybackTransportControlGlue;
import android.support.v17.leanback.media.PlayerAdapter;
import android.view.KeyEvent;
import android.view.View;

/**
 * Overrides the {@link PlaybackTransportControlGlue} to accept the {@link
 * KeyEvent#KEYCODE_MEDIA_PAUSE} to trigger pausing playback.
 *
 * <p>Workaround for b/65167863.
 */
public class PausePlaybackTransportControlGluePatch<T extends PlayerAdapter>
        extends PlaybackTransportControlGlue<T> {

    public PausePlaybackTransportControlGluePatch(Context context, T adapter) {
        super(context, adapter);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE && isPlaying()) {
            pause();
            return true;
        }
        return super.onKey(v, keyCode, event);
    }
}
