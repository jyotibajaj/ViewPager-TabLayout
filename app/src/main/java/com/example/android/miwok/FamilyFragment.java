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



public class FamilyFragment extends Fragment {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);



    //creating the array for numbers
        final ArrayList<Words> familyNames = new ArrayList<>();
        familyNames.add(new Words("white", "safed", R.drawable.family_daughter, R.raw.family_daughter));
        familyNames.add(new Words("blue", "neela", R.drawable.family_father, R.raw.family_father));
        familyNames.add(new Words("red", "laal", R.drawable.family_grandfather, R.raw.family_grandfather));
        familyNames.add(new Words("green", "hara", R.drawable.family_grandmother, R.raw.family_grandmother));
        familyNames.add(new Words("yellow", "peela", R.drawable.family_mother, R.raw.family_mother));
        familyNames.add(new Words("brown", "bhoora", R.drawable.family_older_brother, R.raw.family_older_brother));
        familyNames.add(new Words("black", "kaala", R.drawable.family_son, R.raw.family_son));
        familyNames.add(new Words("pink", "gualbi", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyNames.add(new Words("purple", "jaamni", R.drawable.family_older_sister, R.raw.family_older_sister));


        //arrayadapter to represent how each text view in a list should look like. It act as a bridge between data and

        WordsAdapter itemsAdapter = new WordsAdapter(getActivity(), familyNames, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected items
                Words wordData = familyNames.get(position);
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





