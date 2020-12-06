package com.hencesimplified.praveenassignment.view;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.hencesimplified.praveenassignment.R;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {

    private PlayerView playerView;
    private ProgressBar progressBar;
    private ImageView btFullScreen;
    private SimpleExoPlayer simpleExoPlayer;
    private Button videoNextButton;
    private List<String> newList;
    private int FLAG_VIDEO_NEXT;
    boolean flag = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_video, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.player_view);
        progressBar = view.findViewById(R.id.progress_bar);
        btFullScreen = view.findViewById(R.id.bt_fullscreen);
        videoNextButton = view.findViewById(R.id.videoNextButton);
        newList = new ArrayList<>();

        newList.add("https://firebasestorage.googleapis.com/v0/b/praveenassignment.appspot.com/o/big_buck_bunny_720p_1mb.mp4?alt=media&token=b5bbdfd9-8134-4449-9b71-af7683031e51");
        newList.add("https://firebasestorage.googleapis.com/v0/b/praveenassignment.appspot.com/o/sample.mp4?alt=media&token=85c12d49-a867-465c-85f3-cded4c2201a0");

        videoManager(newList.get(FLAG_VIDEO_NEXT));

        videoNextButton.setOnClickListener(v -> {
            if (FLAG_VIDEO_NEXT + 1 < newList.size()) {
                FLAG_VIDEO_NEXT += 1;
                simpleExoPlayer.stop();
                progressBar.setVisibility(View.VISIBLE);
                videoManager(newList.get(FLAG_VIDEO_NEXT));
            }
        });
    }

    void videoManager(String url) {

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Uri videoUrl = Uri.parse(url);

        LoadControl loadControl = new DefaultLoadControl();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);

        playerView.setPlayer(simpleExoPlayer);

        playerView.setKeepScreenOn(true);

        simpleExoPlayer.prepare(mediaSource);

        simpleExoPlayer.setPlayWhenReady(true);

        simpleExoPlayer.addListener(new Player.EventListener() {
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
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
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

        btFullScreen.setOnClickListener(v -> {
            if (flag) {
                btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.fullscreen_button));

                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                flag = false;
            } else {
                btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.exit_fullscreen_button));

                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                flag = true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        simpleExoPlayer.setPlayWhenReady(false);

        simpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onResume() {
        super.onResume();

        simpleExoPlayer.setPlayWhenReady(true);

        simpleExoPlayer.getPlaybackState();
    }

}