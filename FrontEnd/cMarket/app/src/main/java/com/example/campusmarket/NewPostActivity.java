package com.example.campusmarket;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
 * Activity that represents a page to post a new item.
 */
public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_GALLERY = 1;
    private ImageView imageUpload;
    private TextView tvUpload;
    private EditText etName, etPrice, etCondition, etCategory;
    private String TAG = NewPostActivity.class.getSimpleName();
    private String imageString;
    private Uri uriImage;
    private ProgressDialog pDialog;

    /**
     * Creates instance of NewPostActivity
     * @param savedInstanceState the Saved Instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button btnSubmitPost = findViewById(R.id.btnSubmitPost);
        btnSubmitPost.setOnClickListener(this);

        // to make a new post the fields must be editable:
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCondition = findViewById(R.id.etCondition);
        etCategory = findViewById(R.id.etCategory);
        Button btnUpload = findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(this);

        // initialize image and text view
        imageUpload = findViewById(R.id.imgUploadImage);
        tvUpload = findViewById(R.id.tvUploadImage);
        imageString = "";
    }

    /**
     * When the user clicks to submit their post, calls postItem()
     * @param v theView
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitPost:
                // current way of posting item
                postItem();
                //multi-part way of posting an item
                //doRequest(uriImage);

//                startActivity(new Intent(NewPostActivity.this,
//                        DashboardActivity.class));
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

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
                // selected file from gallery
                uriImage = data.getData();
                Bitmap bitmap = getPath(uriImage);
                String filePath = String.valueOf(bitmap);
                Log.d(TAG, filePath);
                String converted = BitMapToString(bitmap);
                Log.d(TAG, converted);
                Bitmap bconverted = StringToBitMap(converted);

                if (filePath.equals("null"))
                {
                    String failure = "Error in uploading picture";
                    tvUpload.setText(failure);
                }
                else
                {
                    String success = "Image uploaded";
                    tvUpload.setText(success);
                    imageUpload.setImageBitmap(bconverted);
                    imageString = converted;
                }
            }
    }

    /**
     * Converts a bitmap to a string
     * @param bitmap the bitmap to be converted
     * @return string representation of the bitmap
     */
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * Converts a string to a bitmap
     * @param encodedString the string that represents a bitmap
     * @return the converted bitmap object related to the string
     */
    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
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
        if (ContextCompat.checkSelfPermission(NewPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted already. Check if need to tell user why we need permission
            boolean rationale = ActivityCompat.shouldShowRequestPermissionRationale(NewPostActivity.this, Manifest.permission.WRITE_CALENDAR);
            if (rationale)
            {
                // then we need to show the rationale
                String message = "Permission to gallery must be given to upload an image";
                tvUpload.setText(message);

            }
            else
            {
                // don't need to show the rationale, just ask for permission
                ActivityCompat.requestPermissions(NewPostActivity.this, new String[]{
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

    @NonNull
    private MultipartBody.Part prepareFile(String partName, Uri fileUri)
    {

        File file = new File(fileUri.getPath());
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void doRequest(Uri fileUri)
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Const.URL_ITEM_NEW)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Client client = retrofit.create(Client.class);
        Call<ResponseBody> call = client.uploadImage(
                createPartFromString("condition"),
                prepareFile("photo", fileUri));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private RequestBody createPartFromString(String condition) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, condition);
    }

    /**
     * Shows progress dialog during request
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Hides progress dialog during request
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
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
            js.put("image", imageString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "CHECKING THE IMAGE:" + imageString);

        showProgressDialog();
        // Make post request for JSONObject using the url:
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
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
                params.put("image", imageString);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

    }

}
