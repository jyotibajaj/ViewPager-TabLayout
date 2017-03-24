package com.example.android.miwok;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        //creating the array for numbers
        final ArrayList<Words> colorNames = new ArrayList<Words>();
        colorNames.add(new Words("white", "safed", R.drawable.color_white, R.raw.color_white));
        colorNames.add(new Words("red", "laal", R.drawable.color_red, R.raw.color_red));
        colorNames.add(new Words("green", "hara", R.drawable.color_green, R.raw.color_green));
        colorNames.add(new Words("brown", "bhoora", R.drawable.color_brown, R.raw.color_brown));
        colorNames.add(new Words("black", "kaala", R.drawable.color_black, R.raw.color_black));
        colorNames.add(new Words("yellow", "peela", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorNames.add(new Words("gray", "dusty kaala", R.drawable.color_gray, R.raw.color_gray));
        colorNames.add(new Words("mustard_yellow", "sarso_peela", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        //arrayadapter to represent how each text view in a list should look like. It act as a bridge between data and

        WordsAdapter itemsAdapter = new WordsAdapter(getActivity(), colorNames, R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected items
                Words wordData = colorNames.get(position);
                int audioResourceId = wordData.getAudioResourceId();
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create(getActivity(), audioResourceId);
                mediaPlayer.start();
                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mediaPlayer.setOnCompletionListener(mCompletionListener);


            }
        });
        return rootView;

    }

    //fragment onStop()

    public void onStop() {
        super.onStop();
        // when the activity is stopped, release the media player resources
        // because we wont be playing any more sounds. when user moves from the activity: onPause and onStop is called.
        // hence in either of this method we should release resources.
        releaseMediaPlayer();

    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }
}


