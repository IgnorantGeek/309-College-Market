package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class EditPost extends AppCompatActivity {

    EditText etName, etPrice, etCondition, etCategory;
    JSONObject objectToEdit;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);


        // get the item's current information from the last activity
        Intent intent = getIntent();
        String response = intent.getStringExtra("ExtraMessage");

        // create JSON Object from the response String
        try {
            objectToEdit = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialize text box variables
        etName = findViewById(R.id.etEditName);
        etPrice = findViewById(R.id.etEditPrice);
        etCondition = findViewById(R.id.etEditCondition);
        etCategory = findViewById(R.id.etEditCategory);
        initializeEditTextFields(objectToEdit);

    }

    /**
     * Initializes all of the edit text fields with the item's information
     * @param obj  JSON Object representing the item
     */
    private void initializeEditTextFields(JSONObject obj) {
        try {
            etName.setText(obj.getString("name"));
            etPrice.setText(obj.getString("price"));
            etCondition.setText(obj.getString("condition"));
            etCategory.setText(obj.getString("category"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
