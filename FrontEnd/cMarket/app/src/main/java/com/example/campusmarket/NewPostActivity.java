package com.example.campusmarket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity that represents a page to post a new item.
 */
public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmitPost, btnUpload;
    private ImageView imageUpload;
    private TextView tvUpload;
    private EditText etName, etPrice, etCondition, etCategory;
    private String TAG = NewPostActivity.class.getSimpleName();
    private static final int SELECT_PICTURE = 0;

    /**
     * Creates instance of NewPostActivity
     * @param savedInstanceState the Saved Instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnSubmitPost = findViewById(R.id.btnSubmitPost);
        btnSubmitPost.setOnClickListener(this);

        // to make a new post the fields must be editable:
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCondition = findViewById(R.id.etCondition);
        etCategory = findViewById(R.id.etCategory);
        btnUpload = findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(this);

        // initialize image and text view
        imageUpload = findViewById(R.id.imgUploadImage);
        tvUpload = findViewById(R.id.tvUploadImage);
    }

    /**
     * When the user clicks to submit their post, calls postItem()
     * @param v theView
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitPost:
                postItem();
                startActivity(new Intent(NewPostActivity.this,
                        DashboardActivity.class));
                break;
            case R.id.btnUploadImage:
                selectImage();
                 break;
            default:
                break;
        }
    }

    /**
     * Parses the result of the PhotoPickerIntent.
     * Displays the filepath and a preview of the image on the NewPost page
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();

                Bitmap bitmap = getPath(selectedImage);
                String filePath = String.valueOf(bitmap);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                Log.d(TAG, "The File Path:: " + filePath);

                if (filePath.equals("null"))
                {
                    String err = "Error in uploading picture";
                    tvUpload.setText(err);
                }
                else if(file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                    //FINE
                    tvUpload.setText(filePath);
                    imageUpload.setImageBitmap(bitmap);
                } else {
                    //NOT IN REQUIRED FORMAT
                    String message = "Not an image file!";
                    tvUpload.setText(message);
                }
            }
    }

    /**
     * Returns the path to this iamge
     * @param uri The place where the image is from
     * @return the Bitmap of the image
     */
    private Bitmap getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        // cursor.close();
        // Convert file path into bitmap image using below line.
        return BitmapFactory.decodeFile(filePath);
    }


    /**
     * Checks if we have permission to view user's photos.
     * If we do, then starts PhotoPickerIntent (built-in from Android)
     */
    private void selectImage() {
        // Here, we are in the current activity
//        if (ContextCompat.checkSelfPermission(NewPostActivity.this, Manifest.permission.WRITE_CALENDAR)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted already. Check if need to tell user why we need permission
//            boolean rationale = ActivityCompat.shouldShowRequestPermissionRationale(NewPostActivity.this, Manifest.permission.WRITE_CALENDAR);
//            if (rationale)
//            {
//                    // then we need to show the rationale
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            }
//            else
//            {
//                // don't need to show the rationale, just ask for permission
//                ActivityCompat.requestPermissions(NewPostActivity.this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//
//
//                ActivityCompat.requestPermissions(NewPostActivity.this, new String[]{
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
//
//            }
//        }
//        else {
//            // Permission has already been granted, go ahead
//            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//            photoPickerIntent.setType("image/*");
//            startActivityForResult(photoPickerIntent, 1);
//        }

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    /**
     * Posts the new item to the database with the information
     * that the user filled in on the page.
     * Called once they click "Post"
     */
    public void postItem(){
        // make json object
        String url = Const.URL_ITEM_NEW + "?sessid=" + UserActivity.sessionID;
        JSONObject js = new JSONObject();
        try {
            js.put("name", (etName.getText()).toString());
            js.put("price", (etPrice.getText()).toString());
            js.put("condition", (etCondition.getText()).toString());
            js.put("category", (etCategory.getText()).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make post request for JSONObject using the url:
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers in
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", (etName.getText()).toString());
                params.put("price", (etPrice.getText()).toString());
                params.put("condition", (etCondition.getText()).toString());
                params.put("category", (etCategory.getText()).toString());
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

    }

}
