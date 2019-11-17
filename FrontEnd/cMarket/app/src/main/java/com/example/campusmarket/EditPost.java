package com.example.campusmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.app.Client;
import com.example.campusmarket.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity for editing a user's item post
 */
public class EditPost extends AppCompatActivity implements View.OnClickListener {

    private String TAG = EditPost.class.getSimpleName();
    EditText etName, etPrice, etCondition, etCategory;
    JSONObject objectToEdit;
    Button btnSubmit, btnDelete, btnImage;
    private ProgressDialog pDialog;
    private ImageView ivImage;
    private Bitmap bmImage;
    private TextView tvUpload;
    private static final int PICK_FROM_GALLERY = 1;
    private Uri uriImage;

    /**
     * Creates this instance of EditPost.
     * @param savedInstanceState the instance of that class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        // get the item's current information from the last activity
        Intent intent = getIntent();
        String response = intent.getStringExtra("ExtraMessage");

        // create JSON Object from the response String
        try {
            assert response != null;
            objectToEdit = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialize image
        tvUpload = findViewById(R.id.tvUploadImageEdit);
        ivImage = findViewById(R.id.imgUploadImageEdit);
        try {
            String imageString = objectToEdit.getString("image");
            bmImage = NewPostActivity.StringToBitMap(imageString);
            ivImage.setImageBitmap(bmImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialize text box variables
        etName = findViewById(R.id.etEditName);
        etPrice = findViewById(R.id.etEditPrice);
        etCondition = findViewById(R.id.etEditCondition);
        etCategory = findViewById(R.id.etEditCategory);
        initializeEditTextFields(objectToEdit);

        // make buttons clickable
        btnSubmit = findViewById(R.id.btnEditSubmit);
        btnSubmit.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnEditDelete);
        btnDelete.setOnClickListener(this);
        btnImage = findViewById(R.id.btnUploadImageEdit);
        btnImage.setOnClickListener(this);

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

    /**
     * Called when the submit button is clicked.
     * Posts the json item back to the database with the new name, price, category, and condition.
     * Uses the old json's user and refnum fields.
     * @param oldObject JSON object representing the item with OLD information
     */
    public void updateItemInformation(JSONObject oldObject) {
        // create the new json object
        JSONObject toAdd = new JSONObject();
        try {
            toAdd.put("refnum", oldObject.getString("refnum"));
            toAdd.put("name", (etName.getText()).toString());
            toAdd.put("price", (etPrice.getText()).toString());
            toAdd.put("category", (etCategory.getText()).toString());
            toAdd.put("user", oldObject.getJSONObject("user"));
            toAdd.put("condition", (etCondition.getText()).toString());
            toAdd.put("image", NewPostActivity.BitMapToString(bmImage));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // post the new json object
        make_update_request(toAdd);
    }

    /**
     * Shows the progress dialog while it's loading
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Hides the progress dialog
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Puts the item to the new database
     * @param js JSON Object representing the item to post
     */
    private void make_update_request(final JSONObject js)
    {
        String url = Const.URL_ITEM_UPDATE + "/";
        try {
            url += js.getString("refnum");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url+= ("?sessid=" + UserActivity.sessionID);
        showProgressDialog();
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " posted");
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("refnum", js.getString("refnum"));
                    params.put("name", js.getString("name"));
                    params.put("price", js.getString("price"));
                    params.put("category", js.getString("category"));
                    params.put("user", js.getString("user"));
                    params.put("condition", js.getString("condition"));
                    params.put("image", NewPostActivity.BitMapToString(bmImage));
                }  catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");
    }

    /**
     * Deletes the item that is currently being displayed
     */
    private void deletePost()
    {
        // declaring urls from api
        String url = Const.URL_ITEM_DELETE + "/";

        try {
            String refnum = objectToEdit.getString("refnum");
            url += refnum;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        url+= ("?sessid=" + UserActivity.sessionID);

        showProgressDialog();
        // Make request for JSONObject - delete req
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " deleted");
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");
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

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            // selected file from gallery
            uriImage = data.getData();
            bmImage = getPath(uriImage);
            String filePath = String.valueOf(bmImage);

            if (filePath.equals("null"))
            {
                String failure = "Error in uploading picture";
                tvUpload.setText(failure);
            }
            else
            {
                String success = "Image uploaded";
                tvUpload.setText(success);
                ivImage.setImageBitmap(bmImage);
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
        // Check if the user already granted us permission
        if (ContextCompat.checkSelfPermission(EditPost.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted already. Check if need to tell user why we need permission
            boolean rationale = ActivityCompat.shouldShowRequestPermissionRationale(EditPost.this, Manifest.permission.WRITE_CALENDAR);
            if (rationale)
            {
                // then we need to show the rationale
                String message = "Permission to gallery must be given to upload an image";
                tvUpload.setText(message);

            }
            else
            {
                // don't need to show the rationale, just ask for permission
                ActivityCompat.requestPermissions(EditPost.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            }
        }
        else {
            // Permission has already been granted, go ahead
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);
        }
    }

    /**
     * After we request permission, use what the user responded with.
     * @param requestCode the request code
     * @param permissions the permission array
     * @param grantResults the result of the permission grant
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == PICK_FROM_GALLERY) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);
            } else {
                // user did not grant permission
                String message = "Permission to gallery must be given to upload an image";
                tvUpload.setText(message);
            }
        }
    }

    /**
     * Waits for the user to click a button on the screen.
     * If the button is "Submit," it updates that item's info
     * If the button is "Delete," it deletes that item
     * @param view Current view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEditSubmit:
                updateItemInformation(objectToEdit);
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.btnEditDelete:
                deletePost();
                Intent intent2 = new Intent(this, ProfileActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnUploadImageEdit:
                selectImage();
                break;
            default:
                break;
        }
    }
}
