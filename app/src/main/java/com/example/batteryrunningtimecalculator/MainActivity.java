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

    EditText chargePercentEditText, dischargeRateEditText;
    Button calculateButton;
    TextView timeRemainingTextView;

    EditText chargeAmpHoursEditText, totalAmpHoursEditText, dischargeAmpsEditText;
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
        totalAmpHoursEditText = findViewById(R.id.total_amphours_edittext);
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
                Date currentDay = calendar.getTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(currentDay);
                // Add time remaining in hours to the current time
                calendar.add(Calendar.HOUR_OF_DAY, (int) timeRemaining);
                Date newTime = calendar.getTime();
                Date newDay = calendar.getTime();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(newDay);
                // Format the time as desired (e.g., "hh:mm a")
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String formattedNewTime = sdf.format(newTime);
                SimpleDateFormat sdfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedNewDate = sdfd.format(newTime);
                int differenceInDays = calendar2.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR);
                String strtheDay = "unknown";
                if (differenceInDays == 0) {
                    strtheDay = " today";
                } else if (differenceInDays == 1) {
                    strtheDay = " tomorrow";
                } else if (differenceInDays == 2 ) {
                    strtheDay = " next day";
                }

                // display time remaining and expected time
                timeRemainingTextView.setText("Time remaining: " + String.format("%.2f", timeRemaining) + " hours, empty at: " + formattedNewTime + strtheDay);
            }
        });

        calculateDischargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalAmpHours = Double.parseDouble(totalAmpHoursEditText.getText().toString());
                double chargeAmpHours = Double.parseDouble(chargeAmpHoursEditText.getText().toString());
                double dischargeAmps = Double.parseDouble(dischargeAmpsEditText.getText().toString());

                double MinimumEnergy = 0.05 * totalAmpHours; // in Amp Hours
                double MinutesRemaining = (chargeAmpHours - MinimumEnergy) / dischargeAmps; //in hours
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();
                Date currentDay = calendar.getTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(currentDay);
                // Add time remaining in hours to the current time
                calendar.add(Calendar.HOUR_OF_DAY, (int) MinutesRemaining);
                Date newTime = calendar.getTime();
                Date newDay = calendar.getTime();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(newDay);
                // Format the time as desired (e.g., "hh:mm a")
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                SimpleDateFormat sdfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedNewTime = sdf.format(newTime);
                String formattedNewDate = sdfd.format(newTime);
                int differenceInDays = calendar2.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR);
                String strtheDay = "unknown";
                if (differenceInDays == 0) {
                    strtheDay = " today";
                } else if (differenceInDays == 1) {
                    strtheDay = " tomorrow";
                } else if (differenceInDays == 2 ) {
                    strtheDay = " next day";
                    }

                // display time remaining and expected time
                dischargeTimeRemainingTextView.setText("Time remaining: " + String.format("%.2f",MinutesRemaining) + " hours, empty at " + formattedNewTime + strtheDay);
            }
        });
    }
}
