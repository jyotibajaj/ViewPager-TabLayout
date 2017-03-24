package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aashi on 12/03/17.
 */

public class WordsAdapter extends ArrayAdapter<Words> {
    int mColorResourceId;

    public WordsAdapter(Context context, ArrayList<Words> words, int mColorResourceId) {
        super(context, 0, words);
        this.mColorResourceId = mColorResourceId;

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_list_layout, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Words wordData = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.english);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        defaultTextView.setText(wordData.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView hindiTextView = (TextView) listItemView.findViewById(R.id.hindi);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        hindiTextView.setText(wordData.getHindiTranslation());


        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        if (wordData.hasImage()) {
            image.setImageResource(wordData.getImageResourceId());
            // Make sure the view is visible
            image.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            image.setVisibility(View.GONE);

        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.linear_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        return listItemView;


    }
}

