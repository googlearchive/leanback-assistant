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

package com.example.android.assistantplayback.data;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.assistantplayback.model.Movie;

import java.util.List;

/**
 * Provides global search on the app's movie catalog. The assistant will query this provider for
 * results. <br>
 * Note: If you provide WatchAction feeds to Google, then you do not need this class. You should
 * still handle the playback intent and media controls in your fragment. This class enables <a
 * href="https://developer.android.com/training/tv/discovery/searchable.html">on-device search.</a>.
 */
public class VideoContentProvider extends ContentProvider {

    private static final String TAG = "VideoContentProvider";

    private static final String AUTHORITY = "com.example.android.assistantplayback";

    private MockDatabase mDatabase;

    // UriMatcher constant for search suggestions
    private static final int SEARCH_SUGGEST = 1;

    private UriMatcher mUriMatcher;

    private final String[] queryProjection =
            new String[] {
                BaseColumns._ID,
                MockDatabase.KEY_NAME,
                MockDatabase.KEY_DESCRIPTION,
                MockDatabase.KEY_ICON,
                MockDatabase.KEY_DATA_TYPE,
                MockDatabase.KEY_IS_LIVE,
                MockDatabase.KEY_VIDEO_WIDTH,
                MockDatabase.KEY_VIDEO_HEIGHT,
                MockDatabase.KEY_AUDIO_CHANNEL_CONFIG,
                MockDatabase.KEY_PURCHASE_PRICE,
                MockDatabase.KEY_RENTAL_PRICE,
                MockDatabase.KEY_RATING_STYLE,
                MockDatabase.KEY_RATING_SCORE,
                MockDatabase.KEY_PRODUCTION_YEAR,
                MockDatabase.KEY_COLUMN_DURATION,
                MockDatabase.KEY_ACTION,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
            };

    @Override
    public boolean onCreate() {
        mDatabase = new MockDatabase();
        mUriMatcher = buildUriMatcher();
        return true;
    }

    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(
                AUTHORITY, "/search/" + SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        uriMatcher.addURI(
                AUTHORITY,
                "/search/" + SearchManager.SUGGEST_URI_PATH_QUERY + "/*",
                SEARCH_SUGGEST);
        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {

        Log.d(TAG, uri.toString());

        if (mUriMatcher.match(uri) == SEARCH_SUGGEST) {
            Log.d(TAG, "Search suggestions requested.");

            return search(uri.getLastPathSegment());

        } else {
            Log.d(TAG, "Unknown uri to query: " + uri);
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private Cursor search(String query) {
        List<Movie> results = mDatabase.search(query);

        MatrixCursor matrixCursor = new MatrixCursor(queryProjection);
        if (!results.isEmpty()) {
            for (Movie movie : results) {
                matrixCursor.addRow(convertMovieIntoRow(movie));
            }
        }

        return matrixCursor;
    }

    private Object[] convertMovieIntoRow(Movie movie) {
        return new Object[] {
            movie.getId(),
            movie.getTitle(),
            movie.getDescription(),
            movie.getCardImage(),
            movie.getContentType(),
            movie.isLive(),
            movie.getWidth(),
            movie.getHeight(),
            movie.getAudioChannelConfig(),
            movie.getPurchasePrice(),
            movie.getRentalPrice(),
            movie.getRatingStyle(),
            movie.getRatingScore(),
            movie.getProductionYear(),
            movie.getDuration(),
            "GLOBALSEARCH",
            movie.getId()
        };
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert is not implemented.");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Delete is not implemented.");
    }

    @Override
    public int update(
            @NonNull Uri uri,
            @Nullable ContentValues contentValues,
            @Nullable String s,
            @Nullable String[] strings) {
        throw new UnsupportedOperationException("Update is not implemented.");
    }
}
