package com.ptit.filmdictionary.ui.movie_detail;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.ptit.filmdictionary.BuildConfig;

public class YoutubeFragment extends YouTubePlayerFragment
        implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayer mYouTubePlayer;
    private String mTrailerId;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initialize(BuildConfig.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean restored) {
        mYouTubePlayer = youTubePlayer;
        if (!restored && mTrailerId != null) {
            mYouTubePlayer.setFullscreenControlFlags(
                    YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
            mYouTubePlayer.cueVideo(mTrailerId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        mYouTubePlayer = null;
    }

    @Override
    public void onDestroy() {
        if (mYouTubePlayer != null) mYouTubePlayer.release();
        super.onDestroy();
    }

    public void setTrailerId(String trailerId) {
        mTrailerId = trailerId;
        if (mTrailerId != null && mYouTubePlayer != null) mYouTubePlayer.cueVideo(mTrailerId);
    }

    public void playTrailer() {
        if (mYouTubePlayer != null) mYouTubePlayer.play();
    }

    public void setFullScreen(boolean isFullScreen) {
        mYouTubePlayer.setFullscreen(isFullScreen);
    }
}
