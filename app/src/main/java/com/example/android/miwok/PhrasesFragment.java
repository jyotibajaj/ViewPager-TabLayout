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
public class PhrasesFragment extends Fragment {
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


    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        //creating the array for numbers
        final ArrayList<Words> phrases = new ArrayList<>();
        phrases.add(new Words("white", "safed", R.raw.phrase_my_name_is));
        phrases.add(new Words("blue", "neela", R.raw.phrase_what_is_your_name));
        phrases.add(new Words("red", "laal", R.raw.phrase_are_you_coming));
        phrases.add(new Words("green", "hara", R.raw.phrase_come_here));
        phrases.add(new Words("yellow", "peela", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Words("brown", "bhoora", R.raw.phrase_im_feeling_good));
        phrases.add(new Words("black", "kaala", R.raw.phrase_where_are_you_going));
        phrases.add(new Words("pink", "gualbi", R.raw.phrase_lets_go));
        phrases.add(new Words("purple", "jaamni", R.raw.phrase_yes_im_coming));
        phrases.add(new Words("orange", "santri", R.raw.phrase_are_you_coming));


        //arrayadapter to represent how each text view in a list should look like. It act as a bridge between data and

        WordsAdapter itemsAdapter = new WordsAdapter(getActivity(), phrases, R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words wordData = phrases.get(position);
                int audioResourceId = wordData.getAudioResourceId();
                // Release the media player if it currently exists because we are about to
                // play a different sound file
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
    //activity onStop()
    public void onStop(){
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



