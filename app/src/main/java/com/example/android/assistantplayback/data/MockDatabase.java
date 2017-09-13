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

package com.example.android.assistantplayback.data;

import android.app.SearchManager;
import android.media.Rating;

import com.example.android.assistantplayback.model.Movie;
import com.example.android.assistantplayback.model.MovieBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** Mock data to provide content for search results. */
public class MockDatabase {

    // The columns we'll include in the video database table
    public static final String KEY_NAME = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String KEY_DESCRIPTION = SearchManager.SUGGEST_COLUMN_TEXT_2;
    public static final String KEY_ICON = SearchManager.SUGGEST_COLUMN_RESULT_CARD_IMAGE;
    public static final String KEY_DATA_TYPE = SearchManager.SUGGEST_COLUMN_CONTENT_TYPE;
    public static final String KEY_IS_LIVE = SearchManager.SUGGEST_COLUMN_IS_LIVE;
    public static final String KEY_VIDEO_WIDTH = SearchManager.SUGGEST_COLUMN_VIDEO_WIDTH;
    public static final String KEY_VIDEO_HEIGHT = SearchManager.SUGGEST_COLUMN_VIDEO_HEIGHT;
    public static final String KEY_AUDIO_CHANNEL_CONFIG =
            SearchManager.SUGGEST_COLUMN_AUDIO_CHANNEL_CONFIG;
    public static final String KEY_PURCHASE_PRICE = SearchManager.SUGGEST_COLUMN_PURCHASE_PRICE;
    public static final String KEY_RENTAL_PRICE = SearchManager.SUGGEST_COLUMN_RENTAL_PRICE;
    public static final String KEY_RATING_STYLE = SearchManager.SUGGEST_COLUMN_RATING_STYLE;
    public static final String KEY_RATING_SCORE = SearchManager.SUGGEST_COLUMN_RATING_SCORE;
    public static final String KEY_PRODUCTION_YEAR = SearchManager.SUGGEST_COLUMN_PRODUCTION_YEAR;
    public static final String KEY_COLUMN_DURATION = SearchManager.SUGGEST_COLUMN_DURATION;
    public static final String KEY_ACTION = SearchManager.SUGGEST_COLUMN_INTENT_ACTION;

    private static List<Movie> movies;

    /**
     * Returns a list of all of the movies in the database.
     *
     * @return All of the movies.
     */
    public static List<Movie> getAllMovies() {
        if (movies == null) {
            movies =
                    Arrays.asList(
                            elephantsDream(), bigBuckBunny(), jurassicPark(), theIncredibles());
        }
        return movies;
    }

    /**
     * Searches for a movie whose title or description can match against the query parameter.
     *
     * @param query Search string.
     * @return A list of movies that match the query string.
     */
    public List<Movie> search(String query) {
        query = query.toLowerCase();
        List<Movie> results = new ArrayList<>();
        for (Movie movie : getAllMovies()) {
            if (movie.getTitle().toLowerCase().contains(query)
                    || movie.getDescription().toLowerCase().contains(query)) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Finds a particular movie with the given id.
     *
     * @param id movie's id.
     * @return A movie with the id.
     */
    public static Movie findMovieWithId(int id) {
        for (Movie movie : getAllMovies()) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        throw new IllegalArgumentException("Cannot find movie with id: " + id);
    }

    private static Movie elephantsDream() {
        MovieBuilder builder = new MovieBuilder();
        builder.setId(1)
                .setTitle("Elephant's Dream")
                .setDescription(
                        "The story of two strange characters exploring a capricious and seemingly infinite machine. The elder, Proog, acts as a tour-guide and protector, happily showing off the sights and dangers of the machine to his initially curious but increasingly skeptical protege Emo. As their journey unfolds we discover signs that the machine is not all Proog thinks it is, and his guiding takes on a more desperate aspect.")
                .setCardImage(
                        "https://orange.blender.org/wp-content/themes/orange/images/common/ed_header.jpg?x53801")
                .setBackgroundImage(
                        "https://orange.blender.org/wp-content/themes/orange/images/common/ed_header.jpg?x53801")
                .setVideoUrl("https://archive.org/download/ElephantsDream/ed_1024_512kb.mp4")
                .setContentType("video/mp4")
                .setWidth(1280)
                .setHeight(720)
                .setAudioChannelConfig("2.0")
                .setPurchasePrice("$8.99")
                .setRentalPrice("$1.99")
                .setRatingStyle(Rating.RATING_5_STARS)
                .setRatingScore(3.5f)
                .setProductionYear(2006)
                .setDuration((int) (TimeUnit.MINUTES.toMillis(10) + TimeUnit.SECONDS.toMillis(53)));
        return builder.createMovie();
    }

    private static Movie bigBuckBunny() {
        MovieBuilder builder = new MovieBuilder();
        builder.setId(2)
                .setTitle("Big Buck Bunny")
                .setDescription(
                        "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.")
                .setCardImage(
                        "https://peach.blender.org/wp-content/uploads/poster_bunny_big.jpg?x11217")
                .setBackgroundImage(
                        "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217")
                .setVideoUrl(
                        "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4")
                .setContentType("video/mp4")
                .setWidth(1280)
                .setHeight(720)
                .setAudioChannelConfig("2.0")
                .setPurchasePrice("$12.99")
                .setRentalPrice("$1.99")
                .setRatingStyle(Rating.RATING_5_STARS)
                .setRatingScore(2.75f)
                .setProductionYear(2008)
                .setDuration((int) (TimeUnit.MINUTES.toMillis(9) + TimeUnit.SECONDS.toMillis(56)));
        return builder.createMovie();
    }

    private static Movie jurassicPark() {
        MovieBuilder builder = new MovieBuilder();
        builder.setId(3)
                .setTitle("Jurassic Park")
                .setDescription(
                        "During a preview tour, a theme park suffers a major power breakdown that allows its cloned dinosaur exhibits to run amok.")
                .setCardImage(
                        "https://orange.blender.org/wp-content/themes/orange/images/common/ed_header.jpg?x53801")
                .setBackgroundImage(
                        "https://ackorange.blender.org/wp-content/themes/orange/images/common/ed_header.jpg?x53801")
                .setVideoUrl("https://archive.org/download/ElephantsDream/ed_1024_512kb.mp4")
                .setContentType("video/mp4")
                .setWidth(1280)
                .setHeight(720)
                .setAudioChannelConfig("2.0")
                .setPurchasePrice("$19.99")
                .setRentalPrice("$9.99")
                .setRatingStyle(Rating.RATING_5_STARS)
                .setRatingScore(4.05f)
                .setProductionYear(1993)
                .setDuration(
                        (int)
                                (TimeUnit.HOURS.toMillis(2)
                                        + TimeUnit.MINUTES.toMillis(7)
                                        + TimeUnit.SECONDS.toMillis(15)));
        return builder.createMovie();
    }

    private static Movie theIncredibles() {
        MovieBuilder builder = new MovieBuilder();
        builder.setId(4)
                .setTitle("The Incredibles")
                .setDescription(
                        "In this lauded Pixar animated film, married superheroes Mr. Incredible (Craig T. Nelson) and Elastigirl (Holly Hunter) are forced to assume mundane lives as Bob and Helen Parr after all super-powered activities have been banned by the government. While Mr. Incredible loves his wife and kids, he longs to return to a life of adventure, and he gets a chance when summoned to an island to battle an out-of-control robot. Soon, Mr. Incredible is in trouble, and it's up to his family to save him.")
                .setCardImage(
                        "https://peach.blender.org/wp-content/uploads/poster_bunny_big.jpg?x11217")
                .setBackgroundImage(
                        "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217")
                .setVideoUrl(
                        "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4")
                .setContentType("video/mp4")
                .setWidth(1280)
                .setHeight(720)
                .setAudioChannelConfig("2.0")
                .setPurchasePrice("$5.99")
                .setRentalPrice("$4.99")
                .setRatingStyle(Rating.RATING_5_STARS)
                .setRatingScore(4f)
                .setProductionYear(2004)
                .setDuration(
                        (int)
                                (TimeUnit.HOURS.toMillis(1)
                                        + TimeUnit.MINUTES.toMillis(56)
                                        + TimeUnit.SECONDS.toMillis(21)));
        return builder.createMovie();
    }
}
