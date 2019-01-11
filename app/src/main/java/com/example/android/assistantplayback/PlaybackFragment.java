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

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.VideoSupportFragment;
import android.support.v17.leanback.app.VideoSupportFragmentGlueHost;
import android.support.v17.leanback.media.MediaPlayerAdapter;
import android.support.v17.leanback.media.PlaybackGlue;
import android.support.v17.leanback.media.PlaybackTransportControlGlue;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.example.android.assistantplayback.playlist.ListPlaylistAdapter;
import com.example.android.assistantplayback.playlist.MockPlaylistAdapterFactory;

/**
 * Plays back a video. Syncs media session and playback glue during video playback. <br>
 * <br>
 * A movie's id is required to be passed in by id via the activity's extras.
 */
public class PlaybackFragment extends VideoSupportFragment {

    private static final String TAG = "PlaybackFragment";

    public static final String EXTRA_MOVIE_ID =
            "com.example.android.assistantplayback.extra.MOVIE_ID";

    public static final long AVAILABLE_MEDIA_ACTIONS =
            PlaybackStateCompat.ACTION_PLAY
                    | PlaybackStateCompat.ACTION_PAUSE
                    | PlaybackStateCompat.ACTION_PLAY_PAUSE
                    | PlaybackStateCompat.ACTION_STOP
                    | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    | PlaybackStateCompat.ACTION_SEEK_TO;

    private MediaSessionCompat mSession;
    private MediaSessionCallback mMediaSessionCallback;
    private PlaybackTransportControlGlue<MediaPlayerAdapter> mPlayerGlue;
    private ListPlaylistAdapter<?> mPlaylistAdapter;
    private final PlaybackGlue.PlayerCallback playWhenReadyPlayerCallback =
            new PlayWhenReadyPlayerCallback();

    private final PlaybackGlue.PlayerCallback playPausePlayerCallback =
            new PlaybackGlue.PlayerCallback() {
                @Override
                public void onPlayStateChanged(PlaybackGlue glue) {
                    super.onPlayStateChanged(glue);
                    Log.d(TAG, "PlayerCallback: onPlayStateChanged()");
                    int state =
                            glue.isPlaying()
                                    ? PlaybackStateCompat.STATE_PLAYING
                                    : PlaybackStateCompat.STATE_PAUSED;
                    mMediaSessionCallback.updatePlaybackState(
                            state,
                            mPlayerGlue.getCurrentPosition(),
                            mPlaylistAdapter.getCurrentItem());
                    Log.d(TAG, "PlayerCallback: playback state: " + state);
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int movieId = getActivity().getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        if (movieId == -1) {
            Log.w(TAG, "Invalid movieId, cannot playback.");
            throw new IllegalArgumentException("Invalid movieId " + movieId);
        }

        mPlaylistAdapter =
                MockPlaylistAdapterFactory.createMoviePlaylistAdapterWithActiveMovieId(movieId);

        mSession = new MediaSessionCompat(getContext(), TAG);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mSession.setActive(true);
        MediaControllerCompat.setMediaController((Activity) getContext(), mSession.getController());

        mPlayerGlue =
                new PrimaryPlaybackControlsGlue<>(
                        getContext(),
                        new MediaPlayerAdapter(getContext()),
                        mSession.getController());
        mPlayerGlue.setHost(new VideoSupportFragmentGlueHost(this));
        mPlayerGlue.addPlayerCallback(playWhenReadyPlayerCallback);
        mPlayerGlue.addPlayerCallback(playPausePlayerCallback);

        mMediaSessionCallback = new MediaSessionCallback(mPlayerGlue);
        mSession.setCallback(mMediaSessionCallback);

        playMedia(mPlaylistAdapter.getCurrentItem());
    }

    private void playMedia(MediaDescriptionCompat media) {
        mPlayerGlue.setTitle(media.getTitle());
        mPlayerGlue.setSubtitle(media.getDescription());
        mPlayerGlue.getPlayerAdapter().setDataSource(media.getMediaUri());
    }

    /** Checks if the glue is prepared before telling the glue to start playback. */
    private static class PlayWhenReadyPlayerCallback extends PlaybackGlue.PlayerCallback {
        @Override
        public void onPreparedStateChanged(PlaybackGlue glue) {
            super.onPreparedStateChanged(glue);
            if (glue.isPrepared()) {
                glue.play();
            }
        }
    }

    /**
     * Delegates media commands sent from the assistant to the glue. <br>
     * Note that Play/Pause are handled via key code input, not MediaSession.
     */
    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        private final PlaybackTransportControlGlue<?> mGlue;

        public MediaSessionCallback(PlaybackTransportControlGlue<?> glue) {
            mGlue = glue;
        }

        @Override
        public void onPlay() {
            Log.d(TAG, "MediaSessionCallback: onPlay()");
            mGlue.play();
            updatePlaybackStateToPlaying(
                    mGlue.getCurrentPosition(), mPlaylistAdapter.getCurrentItem());
        }

        @Override
        public void onPause() {
            Log.d(TAG, "MediaSessionCallback: onPause()");
            mGlue.pause();
            updatePlaybackState(
                    PlaybackStateCompat.STATE_PAUSED,
                    mGlue.getCurrentPosition(),
                    mPlaylistAdapter.getCurrentItem());
        }

        @Override
        public void onSeekTo(long position) {
            Log.d(TAG, "MediaSessionCallback: onSeekTo()");
            mGlue.seekTo(position);
            updatePlaybackStateToPlaying(position, mPlaylistAdapter.getCurrentItem());
        }

        /*
         * User wishes to stop playback. Finish activity and go back to where the user came
         * from, which might not be your app. For example, Assistant's search results.
         *
         * Note that if the user started playback of a TV series or a playlist, then we would
         * not want to destroy the media session. Instead we would release the player and not
         * destroy the fragment in case the user wants to continue on in the playlist/TV series.
         * Since this sample only demonstrates movies, we will destroy the session and the fragment.
         */
        @Override
        public void onStop() {
            Log.d(TAG, "MediaSessionCallback: onStop()");
            // In onDestroy(), the player will be released. If you are using ExoPlayer, you will
            // need to manually release the player.
            getActivity().finish();
        }

        @Override
        public void onSkipToNext() {
            Log.d(TAG, "MediaSessionCallback: onSkipToNext()");
            playAndUpdateMediaSession(mPlaylistAdapter.skipToNextItem());
        }

        @Override
        public void onSkipToPrevious() {
            Log.d(TAG, "MediaSessionCallback: onSkipToPrevious()");
            playAndUpdateMediaSession(mPlaylistAdapter.skipToPreviousItem());
        }

        private void playAndUpdateMediaSession(MediaDescriptionCompat media) {
            playMedia(media);
            mSession.setMetadata(mPlaylistAdapter.mapToMetadata(media));
            updatePlaybackStateToPlaying(0L, media);
        }

        private void updatePlaybackStateToPlaying(
                long position, MediaDescriptionCompat description) {
            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING, position, description);
        }

        private void updatePlaybackState(
                @PlaybackStateCompat.State int state,
                long position,
                MediaDescriptionCompat description) {
            PlaybackStateCompat.Builder builder =
                    new PlaybackStateCompat.Builder()
                            .setActions(AVAILABLE_MEDIA_ACTIONS)
                            .setActiveQueueItemId(Long.parseLong(description.getMediaId()))
                            .setState(state, position, 1.0f);
            mSession.setPlaybackState(builder.build());
        }
    }
}
