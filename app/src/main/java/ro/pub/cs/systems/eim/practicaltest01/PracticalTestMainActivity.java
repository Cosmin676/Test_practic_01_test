package ro.pub.cs.systems.eim.practicaltest01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTestMainActivity extends AppCompatActivity {

    EditText left_edit;
    EditText right_edit;

    Button left_button;
    Button right_button;

    Button swap_button;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    Integer left_clicks = 0;
    Integer right_clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_practical_test01_main);

        left_edit = findViewById(R.id.left_edit_text);
        right_edit = findViewById(R.id.right_edit_text);

        left_button = findViewById(R.id.left_button);
        right_button = findViewById(R.id.right_button);
        swap_button = findViewById(R.id.swap_button);

        left_button.setOnClickListener(it -> {
            left_clicks++;
            startpracticeservice();
            left_edit.setText(String.valueOf(left_clicks));
        });

        right_button.setOnClickListener(it -> {
            right_clicks++;
            startpracticeservice();
            right_edit.setText(String.valueOf(right_clicks));
        });

        swap_button.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra(Constants.LEFT_TEXT, left_clicks);
            intent.putExtra(Constants.RIGHT_TEXT, right_clicks);
            startActivityForResult(intent, Constants.REQUEST_CODE);
        });

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

    }

    private void startpracticeservice() {
        if (left_clicks + right_clicks > 5) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);

            intent.putExtra(Constants.LEFT_TEXT, left_clicks);
            intent.putExtra(Constants.RIGHT_TEXT, right_clicks);

            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        getApplicationContext().stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "resultat okey", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "resultat cancel", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Constants.LEFT_TEXT, left_clicks);
        outState.putInt(Constants.RIGHT_TEXT, right_clicks);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        left_clicks = savedInstanceState.getInt(Constants.LEFT_TEXT);
        right_clicks = savedInstanceState.getInt(Constants.RIGHT_TEXT);

        left_edit.setText(String.valueOf(left_clicks));
        right_edit.setText(String.valueOf(right_clicks));
    }


}