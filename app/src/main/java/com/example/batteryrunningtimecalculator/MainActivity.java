package com.example.batteryrunningtimecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText chargePercentEditText;
    EditText dischargeRateEditText;
    Button calculateButton;
    TextView timeRemainingTextView;

    EditText chargeAmpHoursEditText;
    EditText dischargeAmpsEditText;
    Button calculateDischargeButton;
    TextView dischargeTimeRemainingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chargePercentEditText = findViewById(R.id.charge_percent_edittext);
        dischargeRateEditText = findViewById(R.id.discharge_rate_edittext);
        calculateButton = findViewById(R.id.calculate_button);
        timeRemainingTextView = findViewById(R.id.time_remaining_textview);

        //
        // charge_amphours_edittext
        // discharge_amps_edittext
        // calculate_discharge_button
        // discharge_time_remaining_textview
        chargeAmpHoursEditText = findViewById(R.id.charge_amphours_edittext);
        dischargeAmpsEditText = findViewById(R.id.discharge_amps_edittext);
        calculateDischargeButton = findViewById(R.id.calculate_discharge_button);
        dischargeTimeRemainingTextView = findViewById(R.id.discharge_time_remaining_textview);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double chargePercent = Double.parseDouble(chargePercentEditText.getText().toString());
                double dischargeRate = Double.parseDouble(dischargeRateEditText.getText().toString());

                double remainingEnergy = (chargePercent / 100) * 9.8 * 1000; //in watt
                double targetEnergy = 0.05 * 9.8 * 1000; //in watt
                double timeRemaining = ((remainingEnergy - targetEnergy) / dischargeRate) / 1; //in hours

                // Get the current time
                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();
                // Add time remaining in hours to the current time
                calendar.add(Calendar.HOUR_OF_DAY, (int) timeRemaining);
                Date newTime = calendar.getTime();
                // Format the time as desired (e.g., "hh:mm a")
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String formattedNewTime = sdf.format(newTime);

                // display time remaining and expected time
                timeRemainingTextView.setText("Time remaining: " + String.format("%.2f", timeRemaining) + " hours, empty at: " + formattedNewTime + " tomorrow");
            }
        });

        calculateDischargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double chargeAmpHours = Double.parseDouble(chargeAmpHoursEditText.getText().toString());
                double dischargeAmps = Double.parseDouble(dischargeAmpsEditText.getText().toString());

                double MiniumEnergy = 0.05 * 200; // in Amp Hours
                double MinutesRemaining = (chargeAmpHours - MiniumEnergy) / dischargeAmps; //in hours
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();
                // Add time remaining in hours to the current time
                calendar.add(Calendar.HOUR_OF_DAY, (int) MinutesRemaining);
                Date newTime = calendar.getTime();
                // Format the time as desired (e.g., "hh:mm a")
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String formattedNewTime = sdf.format(newTime);

                // display time remaining and expected time
                dischargeTimeRemainingTextView.setText("Time remaining: " + String.format("%.2f",MinutesRemaining) + " hours, empty at " + formattedNewTime + " tomorrow");
            }
        });
    }
}
