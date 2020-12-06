package com.hencesimplified.praveenassignment.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AudioFragment extends Fragment {

    private PlayerView playerView;
    private ProgressBar progressBar;
    private SimpleExoPlayer simpleExoPlayer;
    private Button nextButton;
    private List<String> newList;
    int FLAG_NEXT;
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseDatabase firebaseDatabase = firebaseHelper.databaseInstantiate();
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_audio, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.audio_player);
        progressBar = view.findViewById(R.id.audio_progress_bar);
        nextButton = view.findViewById(R.id.nextAudioButton);
        newList = new ArrayList<>();
        FLAG_NEXT = 0;

        newList.add("https://firebasestorage.googleapis.com/v0/b/praveenassignment.appspot.com/o/SoundHelix-Song-1%20(1).mp3?alt=media&token=13f2942d-29ad-4fd1-98b5-3b03d411048e");
        newList.add("https://firebasestorage.googleapis.com/v0/b/praveenassignment.appspot.com/o/file_example_MP3_1MG.mp3?alt=media&token=e6f38334-2ae1-428f-a75f-6e6cf9a491db");

        audioManager(newList.get(FLAG_NEXT));

        nextButton.setOnClickListener(v -> {
            if (FLAG_NEXT + 1 < newList.size()) {
                FLAG_NEXT += 1;
                simpleExoPlayer.stop();
                progressBar.setVisibility(View.VISIBLE);
                audioManager(newList.get(FLAG_NEXT));

            }
        });
    }

    void audioManager(String newUrl) {

        Uri audioUrl = Uri.parse(newUrl);

        LoadControl loadControl = new DefaultLoadControl();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource mediaSource = new ExtractorMediaSource(audioUrl, factory, extractorsFactory, null, null);

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