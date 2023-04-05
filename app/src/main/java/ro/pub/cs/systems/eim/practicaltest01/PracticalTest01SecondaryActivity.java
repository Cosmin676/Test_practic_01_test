package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    TextView sum;
    Button ok_button;
    Button cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        sum = findViewById(R.id.total_clicks);
        ok_button = findViewById(R.id.ok_button);
        cancel_button = findViewById(R.id.cancel_button);

        ok_button.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        cancel_button.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                sum.setText("0");
            } else {
                Integer left = Integer.parseInt(String.valueOf(extras.getInt(Constants.LEFT_TEXT)));
                Integer right = Integer.parseInt(String.valueOf(extras.getInt(Constants.RIGHT_TEXT)));

                sum.setText(String.valueOf(left + right));
            }
        } else {
            sum.setText(savedInstanceState.getString("resultText"));
        }
    }
}