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
