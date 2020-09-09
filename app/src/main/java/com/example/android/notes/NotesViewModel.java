package com.example.android.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private LiveData<List<Notes>> allNotes;
    private NotesRepository notesRepository;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        allNotes = notesRepository.getAllNotes();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }

    public void insert(Notes notes) {
        notesRepository.insert(notes);
    }

    public void deleteAll() {
        notesRepository.deleteAll();
    }

    public void deleteNote(Notes notes) {
        notesRepository.deleteNote(notes);
    }
}
