package com.google.developer.flashcards.data;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Flashcard implements Parcelable {

    public static final String KEY_FLASHCARDS = "flashcards";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";

    private long id;
    private String question;
    private String answer;

    public Flashcard() {
    }

    /**
     * Create a new Flashcard from discrete values
     */
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Create a new Flashcard from a database Cursor
     */
    public Flashcard(Cursor cursor) {
        //TODO: Create a new flashcard from cursor
        this.question = null;
        this.answer = null;
    }

    /**
     * Create a new Flashcard from a data Parcel
     */
    protected Flashcard(Parcel in) {
        this.question = in.readString();
        this.answer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
    }

    public static final Creator<Flashcard> CREATOR = new Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };

}
