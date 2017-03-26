package com.gwk.timesenseexample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gwk.timesense.TimeSense;
import com.gwk.timesense.configuration.TSConfiguration;
import com.gwk.timesense.listener.TSListener;
import com.gwk.timesense.rule.TSRule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TSListener {

    private static final String CUSTOM_RULE = "CUSTOM_RULE";
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar customCalendar = Calendar.getInstance();
        customCalendar.setTime(new Date());
        customCalendar.set(Calendar.HOUR_OF_DAY, 15);
        customCalendar.set(Calendar.MINUTE, 0);
        customCalendar.set(Calendar.SECOND, 0);
        Date start = customCalendar.getTime();

        customCalendar.set(Calendar.HOUR_OF_DAY, 23);
        Date end = customCalendar.getTime();

        TimeSense.getInstance().setConfiguration(TSConfiguration.defaultConfiguration());
        TimeSense.getInstance().addRule(new TSRule(CUSTOM_RULE, start, end));
        TimeSense.getInstance().addListener(TSRule.TS_RULE_NAME_MORNING, this);
        TimeSense.getInstance().addListener(TSRule.TS_RULE_NAME_AFTERNOON, this);
        TimeSense.getInstance().addListener(TSRule.TS_RULE_NAME_EVENING, this);
        TimeSense.getInstance().addListener(TSRule.TS_RULE_NAME_NIGHT, this);

        this.calendar = Calendar.getInstance();
        updateLabel();
        final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateLabel();
            }
        };

        EditText textDate = (EditText) findViewById(R.id.textDate);
        textDate.setInputType(InputType.TYPE_NULL);
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = MainActivity.this.calendar.get(Calendar.HOUR_OF_DAY);
                int minute = MainActivity.this.calendar.get(Calendar.MINUTE);
                TimePickerDialog picker = new TimePickerDialog(MainActivity.this, timeListener, hour, minute, true);
                picker.setTitle("Select Time");
                picker.show();
            }
        });

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSense.getInstance().addListener(CUSTOM_RULE, MainActivity.this);
            }
        });

        Button buttonRemove = (Button) findViewById(R.id.buttonRemove);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSense.getInstance().removeListener(CUSTOM_RULE);
            }
        });

        Button buttonCheck = (Button) findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset color
                TextView labelMorning = (TextView) findViewById(R.id.labelMorning);
                labelMorning.setTextColor(Color.BLACK);
                TextView labelAfternoon = (TextView) findViewById(R.id.labelAfternoon);
                labelAfternoon.setTextColor(Color.BLACK);
                TextView labelEvening = (TextView) findViewById(R.id.labelEvening);
                labelEvening.setTextColor(Color.BLACK);
                TextView labelNight = (TextView) findViewById(R.id.labelNight);
                labelNight.setTextColor(Color.BLACK);
                TextView labelCustom = (TextView) findViewById(R.id.labelCustom);
                labelCustom.setTextColor(Color.BLACK);

                TimeSense.getInstance().trigger(MainActivity.this.calendar.getTime());
            }
        });
    }

    private void updateLabel() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        EditText textDate = (EditText) findViewById(R.id.textDate);
        textDate.setText(formatter.format(this.calendar.getTime()));
    }

    @Override
    public void timeSenseTriggered() {

    }

    @Override
    public void timeSenseTriggered(String ruleName) {
        TextView labelMorning = (TextView) findViewById(R.id.labelMorning);
        TextView labelAfternoon = (TextView) findViewById(R.id.labelAfternoon);
        TextView labelEvening = (TextView) findViewById(R.id.labelEvening);
        TextView labelNight = (TextView) findViewById(R.id.labelNight);
        TextView labelCustom = (TextView) findViewById(R.id.labelCustom);

        if (ruleName.equals(TSRule.TS_RULE_NAME_MORNING)) {
            labelMorning.setTextColor(Color.GREEN);
        } else if (ruleName.equals(TSRule.TS_RULE_NAME_AFTERNOON)) {
            labelAfternoon.setTextColor(Color.GREEN);
        } else if (ruleName.equals(TSRule.TS_RULE_NAME_EVENING)) {
            labelEvening.setTextColor(Color.GREEN);
        } else if (ruleName.equals(TSRule.TS_RULE_NAME_NIGHT)) {
            labelNight.setTextColor(Color.GREEN);
        } else if (ruleName.equals(CUSTOM_RULE)) {
            labelCustom.setTextColor(Color.GREEN);
            new AlertDialog.Builder(MainActivity.this).setMessage("Custom rule triggered").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }
}
