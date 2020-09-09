package com.example.android.notes;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddNotes extends AppCompatActivity {

    public static final int SPEECH_REQUEST = 5;

    ImageButton recognizeSpeech;
    private EditText typeNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        typeNote = findViewById(R.id.editTextNote);
        Button addNoteButton = findViewById(R.id.addNoteButton);
        recognizeSpeech = findViewById(R.id.imageButtonSpeech);

        List<ResolveInfo> info = getPackageManager().queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(AddNotes.this, MainActivity.class);
                if (TextUtils.isEmpty(typeNote.getText())) {
                    Toast.makeText(AddNotes.this, "No note to add", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, noteIntent);
                } else {
                    String note = typeNote.getText().toString();
                    noteIntent.putExtra("NOTE", note);
                    setResult(RESULT_OK, noteIntent);
                }
                finish();
            }
        });

        if (info.size() > 0) {
            recognizeSpeech.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "You can't you the speech to text functionality!", Toast.LENGTH_SHORT).show();
            recognizeSpeech.setVisibility(View.GONE);
        }
    }

    public void RecognizeSpeech(View view) {
        Intent recognizeSpeechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizeSpeechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
        recognizeSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizeSpeechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

        startActivityForResult(recognizeSpeechIntent, SPEECH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            ArrayList<String> speechWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (speechWords != null) {
                String userWords = speechWords.get(0);
                userWords = userWords.substring(0, 1).toUpperCase() + userWords.substring(1).toLowerCase();
                typeNote.setText(userWords);
            }
        }
    }

}