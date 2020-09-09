package com.example.android.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    private NoteDao noteDao;
    private LiveData<List<Notes>> allNotes;

    public NotesRepository(Application application) {
        NotesRoom notesRoom = NotesRoom.getDatabase(application);
        noteDao = notesRoom.noteDao();
        allNotes = noteDao.getAllNotes();
    }


    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }

    public void insert(Notes notes) {
        new InsertAsyncTask(noteDao).execute(notes);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(noteDao).execute();
    }

    public void deleteNote(Notes notes) {
        new DeleteNoteAsyncTask(noteDao).execute(notes);
    }


    public static class InsertAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NoteDao asyncNoteDao;

        public InsertAsyncTask(NoteDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            asyncNoteDao.insert(notes[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao asyncNoteDao;

        public DeleteAllAsyncTask(NoteDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncNoteDao.deleteAll();
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NoteDao asyncNoteDao;

        public DeleteNoteAsyncTask(NoteDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            asyncNoteDao.deleteNote(notes[0]);
            return null;
        }
    }
}
