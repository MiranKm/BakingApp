package com.example.miranpc.bakingapp3.ActivitiesAndFragments;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miranpc.bakingapp3.Steps;
import com.example.miranpc.bakingapp3.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;


public class RecipeDetailFragment extends Fragment {

    public static final String STEPS = "steps";
    public static final String POSITION = "pos";
    private static final String CURRENT_TIME_POSITION = "time";
    private static final String PLAY_BACK_STATE = "play";


    private ArrayList<Steps> steps;
    private int currentStepPosition;
    private SimpleExoPlayer player;
    private String videoUrl;
    private long currentVideoPosition;
    private boolean playBackState;

    FloatingActionButton fabNext;
    FloatingActionButton fabPreview;
    RelativeLayout videoContainer;
    TextView currentPage, stepDescription;
    PlayerView stepVideo;
    ImageView noVideo;
    ProgressBar videoProgress;

    public RecipeDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(STEPS)) {
            steps = getArguments().getParcelableArrayList(STEPS);
            currentStepPosition = getArguments().getInt(POSITION);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recipe_details, container, false);

        fabNext = view.findViewById(R.id.fab_next);
        noVideo = view.findViewById(R.id.no_video);
        fabPreview = view.findViewById(R.id.fab_prev);
        videoContainer = view.findViewById(R.id.video_container);
        videoProgress = view.findViewById(R.id.video_progress);
        stepDescription = view.findViewById(R.id.step_description);
        currentPage = view.findViewById(R.id.current_page);
        stepVideo = view.findViewById(R.id.step_video);

        if (savedInstanceState != null) {
            currentVideoPosition = savedInstanceState.getLong(CURRENT_TIME_POSITION, 0);
            steps = savedInstanceState.getParcelableArrayList(STEPS);
            playBackState = savedInstanceState.getBoolean(PLAY_BACK_STATE, true);
            currentStepPosition = savedInstanceState.getInt(POSITION, 0);
        }
        setListeners();
        populateDate();
        return view;

    }

    private void setListeners() {
        fabNext.setOnClickListener(v -> {
            currentStepPosition++;
            populateDate();
        });
        fabPreview.setOnClickListener(v -> {
            currentStepPosition--;
            populateDate();
        });
    }

    private void populateDate() {

        Steps step = steps.get(currentStepPosition);
        videoUrl = step.getVideoURL();
        currentPage.setText(getString(R.string.page_indicator, currentStepPosition + 1, steps.size()));
        stepDescription.setText(step.getDescription());


        CheckNavigation();
        checkVideoAvailability();

    }

    private void checkVideoAvailability() {

        if (TextUtils.isEmpty(videoUrl)) {
            videoContainer.setVisibility(View.GONE);
        } else {
            videoContainer.setVisibility(View.VISIBLE);
            setVideoMediaSource();
        }
    }

    @SuppressLint("RestrictedApi")
    private void CheckNavigation() {

        if (currentStepPosition == 0) {
            fabPreview.setVisibility(View.INVISIBLE);
            fabNext.setVisibility(View.VISIBLE);
        } else if (currentStepPosition == steps.size() - 1) {

            fabPreview.setVisibility(View.VISIBLE);
            fabNext.setVisibility(View.INVISIBLE);
        } else {
            fabPreview.setVisibility(View.VISIBLE);
            fabNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24)
            initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null)
            initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVideoPosition = player.getCurrentPosition();
        playBackState = player.getPlayWhenReady();
        if (Util.SDK_INT <= 23)
            releasePlayer();
    }

    private void releasePlayer() {
        player.release();
        stepVideo.setPlayer(null);
        player = null;
    }

    private void initializePlayer() {

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter()));

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        stepVideo.setPlayer(player);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playbackState == Player.STATE_BUFFERING) {
                    videoProgress.setVisibility(View.VISIBLE);

                } else {
                    videoProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        setVideoMediaSource();
    }

    private void setVideoMediaSource() {

        if (player != null) {
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));
            Uri videoUri = Uri.parse(videoUrl);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            player.prepare(videoSource);
            player.seekTo(currentVideoPosition);
            player.setPlayWhenReady(playBackState);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24)
            releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_TIME_POSITION, currentVideoPosition);
        outState.putParcelableArrayList(STEPS, steps);
        outState.putBoolean(PLAY_BACK_STATE, playBackState);
        outState.putInt(POSITION, currentStepPosition);
    }
}
