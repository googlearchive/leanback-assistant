package com.example.android.assistantplayback.playlist;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;

import com.example.android.assistantplayback.model.Movie;

import java.util.List;

/**
 * Converts {@link Movie}s into {@link MediaDescriptionCompat} for the {@link ListPlaylistAdapter}.
 */
public class MovieListPlaylistAdapter extends ListPlaylistAdapter<Movie> {

    public MovieListPlaylistAdapter(List<Movie> movies, int activeIndex) {
        super(movies, activeIndex);
    }

    @Override
    @NonNull
    protected MediaDescriptionCompat map(Movie movie) {
        Bundle imageExtras = new Bundle();
        imageExtras.putString(EXTRA_BACKGROUND_ART, movie.getBackgroundImage());

        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
        return builder.setTitle(movie.getTitle())
                .setDescription(movie.getDescription())
                .setMediaUri(Uri.parse(movie.getVideoUrl()))
                .setIconUri(Uri.parse(movie.getCardImage()))
                .setMediaId(String.valueOf(movie.getId()))
                .setExtras(imageExtras)
                .build();
    }
}
