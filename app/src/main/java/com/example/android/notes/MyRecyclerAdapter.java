package com.example.android.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyRecyclerViewHolder> {
    LayoutInflater layoutInflater;
    private List<Notes> notesList;

    public MyRecyclerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new MyRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        if (notesList != null) {
            Notes currentNote = notesList.get(position);
            holder.noteWord.setText(currentNote.getNote());
        } else {
            holder.noteWord.setText("No Notes");
        }
    }

    @Override
    public int getItemCount() {
        if (notesList != null) {
            return notesList.size();
        } else return 0;
    }

    public void setNotes(List<Notes> notes) {
        notesList = notes;
        notifyDataSetChanged();
    }

    public Notes getNotePosition(int position) {
        return notesList.get(position);
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView noteWord;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            noteWord = itemView.findViewById(R.id.main_note);
        }
    }
}
