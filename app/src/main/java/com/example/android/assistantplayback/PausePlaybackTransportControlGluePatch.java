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
