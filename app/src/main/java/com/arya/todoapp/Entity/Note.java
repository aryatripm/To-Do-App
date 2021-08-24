package com.arya.todoapp.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String _id;
    private String note_title;
    private String note_desc;
    private Boolean completed;

    public Note() {}

    protected Note(Parcel in) {
        _id = in.readString();
        note_title = in.readString();
        note_desc = in.readString();
        byte tmpCompleted = in.readByte();
        completed = tmpCompleted == 0 ? null : tmpCompleted == 1;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNote_title() {
        return note_title;
    }
    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_desc() {
        return note_desc;
    }
    public void setNote_desc(String note_desc) {
        this.note_desc = note_desc;
    }

    public Boolean getCompleted() {
        return completed;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(note_title);
        parcel.writeString(note_desc);
        parcel.writeByte((byte) (completed == null ? 0 : completed ? 1 : 2));
    }
}
