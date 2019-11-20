package com.example.campusmarket;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.campusmarket.utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSockets extends AppCompatActivity implements View.OnClickListener {

    private Button btnSend;
    private EditText etMessage;
    private WebSocketClient client;
    private LinearLayout messageLayout;
    private Context parentView;

    /**
     * Creates this instance of the chat
     * @param savedInstanceState the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websockets);

        parentView = getApplicationContext();
        // display the users' names
        Intent intent = getIntent();
        String seller = intent.getStringExtra("seller");
        String buyer = intent.getStringExtra("buyer");
        if (seller != null && buyer != null)
        {
            String namesString  = seller + " & " + buyer;
            TextView names = findViewById(R.id.tvUsersDM);
            names.setText(namesString);
        }

        // initialize variables
        btnSend =  findViewById(R.id.btnSendMessage);
        etMessage =  findViewById(R.id.etMessage);
        btnSend.setOnClickListener(this);
        messageLayout =  findViewById(R.id.message_layout);
        messageLayout.setOrientation(LinearLayout.VERTICAL);

        // connect the user who is logged in (so they don't type in their own username)
        connectUser(UserActivity.loggedInUsername);
    }

    public void notifyMe(String username)
    {
        //testing: create a notification here.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            Intent intent = new Intent(this, WebSockets.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            int color = 0xddb4ed;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.DIRECT_MESSAGE_CHANNEL_ID)
                    .setSmallIcon(R.drawable.shopping_cart_notification)
                    .setContentTitle("Campus Market Message")
                    .setContentText("You have a direct message from " + username)
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
     * Called when a user tries to connect to the websocket.
     */
    private void connectUser(String username) {
        Draft[] drafts = {new Draft_6455()};
        String w = Const.URL_CHAT + "/" + username;
        Log.d("Socket: ", w);
        try {
            Log.d("Socket: ", "Trying socket");
            client = new WebSocketClient(new URI(w), drafts[0]) {

                @SuppressLint("SetTextI18n")
                @Override
                public void onMessage(final String wholeMessage) {
                    Log.d("", " run() returned: " + wholeMessage);
                    String[] parts = wholeMessage.split(":");
                    String username = parts[0];
                    String message = parts[1];
                    if (!username.equals(UserActivity.loggedInUsername))
                    {
                        notifyMe(username);
                    }
                    // add this message to the scrollbox
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // Stuff that updates the UI
                            TextView tv = new TextView(parentView);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                            tv.setText(wholeMessage);
                            messageLayout.addView(tv);
                            //notifyMe(username);
                        }
                    });
                }

                /**
                 * Called when chat is opened to establish connection
                 * @param handshake the server handshake
                 */
                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + " is connecting ");
                }

                /**
                 * Called when chat is closed to finish chat session
                 * @param code the code (int)
                 * @param reason the reason (String)
                 * @param remote whether remote or not (boolean)
                 */
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", " onClose() returned: " + reason);
                }

                /**
                 * Called if there is an error / exception in the chat
                 * @param e the exception
                 */
                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        } catch (URISyntaxException e) {
            if (e.getMessage() != null)
                Log.d("ExceptionSendMessage:", e.getMessage());
            e.printStackTrace();
        }
        client.connect();
    }

    /**
     * Called when the user sends a message.
     */
    private void sendMessage() {
        try {
            client.send(etMessage.getText().toString() + " ");
            etMessage.setText("");
        } catch (Exception e) {
            if (e.getMessage() != null)
                Log.d("ExceptionSendMessage:", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sees which button the user is going to click.
     * Almost acts as a navbar
     * @param view the View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendMessage:
                sendMessage();
                break;
            default:
                break;
        }
    }
}

