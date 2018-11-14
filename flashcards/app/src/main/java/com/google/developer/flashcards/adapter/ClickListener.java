package com.google.developer.flashcards.adapter;

import android.view.View;

/**
 * Created by coder on 2/5/17.
 */


public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}