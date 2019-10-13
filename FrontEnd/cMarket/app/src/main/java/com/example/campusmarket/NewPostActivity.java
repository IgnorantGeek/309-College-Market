package com.example.campusmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnSubmitPost = findViewById(R.id.btnSubmitPost);
        btnSubmitPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitPost:
                startActivity(new Intent(NewPostActivity.this,
                        DashboardActivity.class));
                break;
            default:
                break;

        }
    }
}

