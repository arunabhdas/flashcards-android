package com.google.developer.flashcards.data;

import android.support.v7.widget.RecyclerView;

/**
 * Base class for the RecyclerView adapter to display flashcards.
 */
public abstract class CardRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    /**
     * Return a {@link Flashcard} represented by this item in the adapter.
     *
     * @param position Adapter item position
     *
     * @return A new {@link Flashcard} filled with this position's attributes.
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public abstract Flashcard getItem(int position);

}
