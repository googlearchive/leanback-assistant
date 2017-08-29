package com.example.android.assistantplayback;

import android.content.Context;
import android.support.v17.leanback.media.PlaybackTransportControlGlue;
import android.support.v17.leanback.media.PlayerAdapter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v4.media.session.MediaControllerCompat;

import java.util.concurrent.TimeUnit;

/**
 * Manages customizing the actions in the {@link PlaybackControlsRow}. Adds the following action to
 * the primary controls:
 *
 * <ul>
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.SkipPreviousAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.SkipNextAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.FastForwardAction}
 *   <li>{@link android.support.v17.leanback.widget.PlaybackControlsRow.RewindAction}
 * </ul>
 *
 * Note that the superclass, {@link PlaybackTransportControlGlue}, manages the playback controls
 * row.
 */
public class PrimaryPlaybackControlsGlue<T extends PlayerAdapter>
        extends PausePlaybackTransportControlGluePatch<T> {

    private static final long TEN_SECONDS = TimeUnit.SECONDS.toMillis(10);

    private final PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private final PlaybackControlsRow.SkipNextAction mSkipNextAction;
    private final PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private final PlaybackControlsRow.RewindAction mRewindAction;

    private final MediaControllerCompat.TransportControls mMediaSessionTransportControls;

    public PrimaryPlaybackControlsGlue(
            Context context, T adapter, MediaControllerCompat mediaController) {
        super(context, adapter);
        mMediaSessionTransportControls = mediaController.getTransportControls();

        mSkipPreviousAction = new PlaybackControlsRow.SkipPreviousAction(context);
        mSkipNextAction = new PlaybackControlsRow.SkipNextAction(context);
        mFastForwardAction = new PlaybackControlsRow.FastForwardAction(context);
        mRewindAction = new PlaybackControlsRow.RewindAction(context);
    }

    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        // Order matters, super.onCreatePrimaryActions() will create the play / pause action.
        // Will display as follows:
        // play/pause, previous, rewind, fast forward, next
        //   > /||      |<        <<        >>         >|
        super.onCreatePrimaryActions(primaryActionsAdapter);
        primaryActionsAdapter.add(mSkipPreviousAction);
        primaryActionsAdapter.add(mRewindAction);
        primaryActionsAdapter.add(mFastForwardAction);
        primaryActionsAdapter.add(mSkipNextAction);
    }

    @Override
    public void onActionClicked(Action action) {
        if (action == mRewindAction) {
            rewind();
        } else if (action == mFastForwardAction) {
            fastForward();
        } else {
            // Super class handles play/pause and delegates to abstract methods next()/previous().
            super.onActionClicked(action);
        }
    }

    @Override
    public void next() {
        mMediaSessionTransportControls.skipToNext();
    }

    @Override
    public void previous() {
        mMediaSessionTransportControls.skipToPrevious();
    }

    /** Skips backwards 10 seconds. */
    private void rewind() {
        long newPosition = getCurrentPosition() - TEN_SECONDS;
        newPosition = (newPosition < 0) ? 0 : newPosition;
        // The MediaSession callback in PlaybackFragment will be triggered which will sync the glue
        // (this) with media session.
        mMediaSessionTransportControls.seekTo(newPosition);
    }

    /** Skips forward 10 seconds. */
    private void fastForward() {
        if (getDuration() > -1) {
            long newPosition = getCurrentPosition() + TEN_SECONDS;
            newPosition = (newPosition > getDuration()) ? getDuration() : newPosition;
            // The MediaSession callback in PlaybackFragment will be triggered which will sync the
            // glue (this) with media session.
            mMediaSessionTransportControls.seekTo(newPosition);
        }
    }
}
