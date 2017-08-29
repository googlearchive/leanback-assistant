package com.example.android.assistantplayback.playlist;

import com.example.android.assistantplayback.data.MockDatabase;
import com.example.android.assistantplayback.model.Movie;

import java.util.List;

/** Creates {@link ListPlaylistAdapter}s with mock playlist data. */
public class MockPlaylistAdapterFactory {

    /**
     * Creates a {@link MovieListPlaylistAdapter} with a mock playlist. The movie is the active item
     * in the playlist.
     *
     * @param movieId The id of the active movie in the playlist.
     * @return A {@link MovieListPlaylistAdapter} with a mock playlist seeded with the given movie.
     */
    public static ListPlaylistAdapter<Movie> createMoviePlaylistAdapterWithActiveMovieId(
            int movieId) {
        List<Movie> movies = MockDatabase.getAllMovies();
        int activeIndex = getCurrentMovieIndex(movies, movieId);
        activeIndex = activeIndex >= 0 ? activeIndex : 0;
        return new MovieListPlaylistAdapter(movies, activeIndex);
    }

    private static int getCurrentMovieIndex(List<Movie> movies, int currentMovieId) {
        for (int index = 0; index < movies.size(); index++) {
            if (currentMovieId == movies.get(index).getId()) {
                return index;
            }
        }
        return -1;
    }
}
