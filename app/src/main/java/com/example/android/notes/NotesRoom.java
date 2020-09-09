package com.example.android.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class NotesRoom extends RoomDatabase {

    private static NotesRoom INSTANCE;

    public static NotesRoom getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesRoom.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, NotesRoom.class, "note_table")
                            .fallbackToDestructiveMigration()
                            //.addCallback(databaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract NoteDao noteDao();


    /*
     * The below code is used as a callback method which is called
     *when the database is created and is used to populate the database before
     * first interacts with the app.
     */


    /*private static NotesRoom.Callback databaseCallback=new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
new populateDbAsync(INSTANCE).execute();
        }
    };

    private static class populateDbAsync extends AsyncTask<Void, Void, Void> {
       private NoteDao noteDaoAsync;

       String[] words={"Hello", "World", "Its a Note"};
        public populateDbAsync(NotesRoom instance) {
            noteDaoAsync=instance.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String word : words) {
                Notes notes = new Notes(word);
                noteDaoAsync.insert(notes);
            }
            return null;
        }
    }*/
}
