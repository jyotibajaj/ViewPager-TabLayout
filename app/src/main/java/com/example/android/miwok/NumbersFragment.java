package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class NumbersFragment extends Fragment {

    private final static String TAG = "AudioFocus";
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
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.i(TAG, "AUDIOFOCUS_GAIN");
                    //restart/resume your sound
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.e(TAG, "AUDIOFOCUS_LOSS");
                    //Loss of audio focus for a long time
                    //Stop playing the sound
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                    //pause
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    //Loss of audio focus for a short time
                    //Pause playing the sound
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    //Loss of audio focus for a short time.
                    //But one can duck. Lower the volume of playing the sound
                    break;

                default:
                    //
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        //creating the array for numbers
        final ArrayList<Words> words = new ArrayList<>();
        words.add(new Words("one", "ek", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("two", "dou", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("three", "teen", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("four", "chaar", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("five", "paach", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("six", "che", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("seven", "saat", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("eight", "aath", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("nine", "no", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("ten", "dus", R.drawable.number_ten, R.raw.number_ten));


        //arrayadapter to represent how each text view in a list should look like. It act as a bridge between data and

        WordsAdapter itemsAdapter = new WordsAdapter(getActivity(), words, R.color.category_numbers);

        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        final boolean gotFocus = requestAudioFocusForMyApp(getActivity());

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();
                //get selected items
                Words wordData = words.get(position);
                int audioResourceId = wordData.getAudioResourceId();

                if (gotFocus) {
                    mediaPlayer = MediaPlayer.create(getActivity(), audioResourceId);
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                    releaseAudioFocusForMyApp(getActivity());


                }
            }
        });
        return rootView;
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


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private boolean requestAudioFocusForMyApp(final Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus for playback
        int result = am.requestAudioFocus(mOnAudioFocusChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.d("AudioFocus", "Audio focus received");
            return true;
        } else {
            Log.d("AudioFocus", "Audio focus NOT received");
            return false;
        }
    }

    private void releaseAudioFocusForMyApp(final Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}

