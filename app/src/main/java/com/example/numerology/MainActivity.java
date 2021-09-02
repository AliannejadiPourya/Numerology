package com.example.numerology;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "25";//just some random number

    int favoriteNumber, result;
    String BaseString = "Your Spiritual number is ";
    String FinalResults;
    EditText favoriteNumberInput;
    Button submitButton;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


        textView = (TextView) findViewById(R.id.textres);

        favoriteNumberInput = (EditText) findViewById(R.id.favoriteNumberInput);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteNumber = Integer.valueOf(favoriteNumberInput.getText().toString());
                result = sumDigits(favoriteNumber);
                FinalResults = BaseString + result + "!";
                textView.setText(FinalResults);
                showToast(String.valueOf(FinalResults));
                String message = FinalResults;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        MainActivity.this, "25"
                )
                        .setSmallIcon(R.drawable.ic_baseline_notifications)
                        .setContentTitle("Congratulations!")
                        .setContentText(message)
                        .setAutoCancel(true);
                Intent intent = new Intent(MainActivity.this,
                        NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message", message);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this
                        , 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(0, builder.build());

            }
        });


    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public static int sumDigits(int number) {
        if (number < 10) return number;

        int sum = 0;

        while (number > 0) {
            int digit = number % 10;
            sum = sum + digit;

            number = number / 10;
        }

        if (sum >= 10) {//recursive
            sum = sumDigits(sum);
        }

        return sum;
    }
}