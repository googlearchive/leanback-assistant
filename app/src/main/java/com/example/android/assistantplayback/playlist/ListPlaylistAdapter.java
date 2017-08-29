package com.example.android.assistantplayback.playlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import java.util.List;

/**
 * Converts a playlist of &lt;T&gt; into a playlist of {@link
 * android.support.v4.media.MediaDescriptionCompat}.
 */
public abstract class ListPlaylistAdapter<T> implements PlaylistAdapter {

    protected static final String EXTRA_BACKGROUND_ART = "extra_background_art";

    private final List<T> items;
    private int activeIndex;

    /**
     * Creates a {@link ListPlaylistAdapter} of items that will be converted into a playlist of
     * {@link MediaDescriptionCompat}.
     *
     * @param items The playlist items.
     * @param activeIndex The index of the active item in the playlist.
     */
    protected ListPlaylistAdapter(List<T> items, int activeIndex) {
        this.items = items;
        this.activeIndex = activeIndex;
    }

    /**
     * Returns the current item in the playlist.
     *
     * @return Current item in playlist as a {@link MediaDescriptionCompat}.
     */
    @Override
    public final MediaDescriptionCompat getCurrentItem() {
        return map(items.get(activeIndex));
    }

    /**
     * Moves to the next item in the playlist. If there are no more items, then will return the
     * current item.
     *
     * @return Next item in the playlist.
     */
    @Override
    @NonNull
    public final MediaDescriptionCompat skipToNextItem() {
        if (activeIndex < items.size() - 1) {
            this.activeIndex++;
        }
        return getCurrentItem();
    }

    /**
     * Moves to the previous item in the playlist. If already at the beginning of the playlist, then
     * returns the current item.
     *
     * @return Previous item in the playlist.
     */
    @Override
    @NonNull
    public final MediaDescriptionCompat skipToPreviousItem() {
        if (activeIndex > 0) {
            this.activeIndex--;
        }
        return getCurrentItem();
    }

    /**
     * Converts a {@link MediaDescriptionCompat} into {@link MediaMetadataCompat}. Only a title,
     * description, and media id will be converted. A subclass should override this method if their
     * {@link #map(Object)} method stores more data in the mapped MediaDescriptionCompat.
     *
     * @param item to be converted.
     * @return A converted MediaMetadataCompact from MediaDescriptionCompat.
     */
    @NonNull
    public MediaMetadataCompat mapToMetadata(MediaDescriptionCompat item) {
        MediaMetadataCompat.Builder builder =
                new MediaMetadataCompat.Builder()
                        .putText(MediaMetadataCompat.METADATA_KEY_TITLE, item.getTitle())
                        .putText(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, item.getTitle())
                        .putText(
                                MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION,
                                item.getDescription())
                        .putBitmap(
                                MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, item.getIconBitmap())
                        .putText(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, item.getMediaId());

        if (item.getIconUri() != null) {
            builder.putString(
                    MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
                    item.getIconUri().toString());
        }

        Bundle extras = item.getExtras();
        if (extras != null) {
            String backgroundArtUri = extras.getString(EXTRA_BACKGROUND_ART);
            if (backgroundArtUri != null) {
                builder.putString(MediaMetadataCompat.METADATA_KEY_ART_URI, backgroundArtUri);
            }
        }

        return builder.build();
    }

    /**
     * Convert a playlist item of T to a MediaDescriptionCompat instance. The returned object is
     * expected to have a title, description, icon, and a media id. If there is more customization,
     * then {@link #mapToMetadata(MediaDescriptionCompat)} will need to be overridden as well to
     * reflect the customization.
     *
     * @param t An object to convert into a MediaDescriptionCompat instance.
     * @return A MediaDescriptionCompat instance with the minimum fields populated.
     */
    protected abstract MediaDescriptionCompat map(T t);
}
