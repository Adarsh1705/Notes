package com.example.android.notes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Notes {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Note")
    private String Note;

    public Notes(@NonNull String Note) {
        this.Note = Note;
    }

    @NonNull
    public String getNote() {
        return Note;
    }


}
