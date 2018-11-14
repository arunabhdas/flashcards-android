package com.google.developer.flashcards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.developer.flashcards.R;
import com.google.developer.flashcards.data.CardRecyclerAdapter;
import com.google.developer.flashcards.data.Flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coder on 2/6/17.
 */

public class MyCardRecyclerAdapter extends CardRecyclerAdapter {
    private List<Flashcard> mDataset = new ArrayList<>();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
            // mTextView = (TextView) v.findViewById(R.id.appwidget_text);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyCardRecyclerAdapter(List<Flashcard> flashcards) {
        mDataset = flashcards;
    }
    @Override
    public Flashcard getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyCardRecyclerAdapter.ViewHolder vh = new MyCardRecyclerAdapter.ViewHolder(v);
        vh.mTextView = (TextView) v.findViewById(R.id.appwidget_text);
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // holder.mTextView.setText(mDataset.get(position).getQuestion());
        TextView textView = (TextView) holder.itemView.findViewById(R.id.appwidget_text);
        textView.setText(mDataset.get(position).getQuestion());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
