package com.example.campusmarket;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Activity that represents the page after a user logs in / signs up
 */
public class UserActivity extends Activity implements OnClickListener {
    private Button btnDashboard, btnNewPost, btnProfile;
    public static String loggedInUsername;
    protected static String sessionID = "";

    /**
     * Creates this instance of UserActivity.
     * Display's "Welcome, Username" where username is from previous activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Stores the  user's username
        Intent intent = getIntent();
        loggedInUsername = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String sessMessage =  intent.getStringExtra("sessionID");
        if (sessMessage != null)
        {
            sessionID = intent.getStringExtra("sessionID");
        }
        Log.d("This is the sessionID: ", sessionID);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.usernameWelcome);
        textView.setText(loggedInUsername);

        btnDashboard = (Button) findViewById(R.id.btnGoToDashboard);
        btnDashboard.setOnClickListener(this);
        btnNewPost = (Button) findViewById(R.id.btnNewPost);
        btnNewPost.setOnClickListener(this);
        btnProfile = (Button) findViewById(R.id.btnGoToProfile);
        btnProfile.setOnClickListener(this);
        findViewById(R.id.btnNotifcation).setOnClickListener(this);
    }

    public void notifyMe()
    {
        //testing: create a notification here.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.d("Notifcaitons", "inside the if statement");

            Intent intent = new Intent(this, WebSockets.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

//            Bitmap myBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.campus_market_logo);
            int color = 0xddb4ed;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.DIRECT_MESSAGE_CHANNEL_ID)
                    .setSmallIcon(R.drawable.shopping_cart_notification)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(myBitmap))
                    .setContentTitle("Campus Market Message")
                    .setContentText("You have a direct message from xyz")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setColor(color)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(MainActivity.notificationId++, builder.build());
        }
    }

    /**
     * Sees which button the user is going to click.
     * Almost acts as a navbar
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToDashboard:
                startActivity(new Intent(UserActivity.this,
                        DashboardActivity.class));
                break;
            case R.id.btnNewPost:
                startActivity(new Intent(UserActivity.this,
                        NewPostActivity.class));
                break;
            case R.id.btnGoToProfile:
                startActivity(new Intent(UserActivity.this,
                        ProfileActivity.class));
                break;
            case R.id.btnNotifcation:
                notifyMe();
                break;
            default:
                break;
        }
    }
}
