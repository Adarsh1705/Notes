package com.example.android.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTES_REQUEST_CODE = 1;
    Toolbar toolbar;
    RecyclerView recyclerView;
    NotesViewModel notesViewModel;
    MyRecyclerAdapter myRecyclerAdapter;
    ExtendedFloatingActionButton addNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotes = findViewById(R.id.addNotes);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        myRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this);
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                this.getApplication()).create(NotesViewModel.class);

        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                myRecyclerAdapter.setNotes(notes);
            }
        });

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteActivity = new Intent(MainActivity.this, AddNotes.class);
                startActivityForResult(addNoteActivity, ADD_NOTES_REQUEST_CODE);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Notes NoteToDelete = myRecyclerAdapter.getNotePosition(position);
                notesViewModel.deleteNote(NoteToDelete);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            notesViewModel.deleteAll();
            Toast.makeText(this, "Deleting All Notes...", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTES_REQUEST_CODE && resultCode == RESULT_OK) {
            Notes note = new Notes(data.getStringExtra("NOTE"));
            notesViewModel.insert(note);
        }
    }
}