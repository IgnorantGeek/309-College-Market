package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EditPost extends AppCompatActivity {


    String objectToEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);


        Intent intent = getIntent();
        objectToEdit = intent.getStringExtra("ExtraMessage");

//        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textview_edit_post);
        textView.setText(objectToEdit);
    }
}
