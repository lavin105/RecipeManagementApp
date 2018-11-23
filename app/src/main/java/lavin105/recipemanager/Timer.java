package lavin105.recipemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Timer extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button setTime, cancelAlarm;
    TextView theTime;
    FloatingActionButton goHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set_layout);

        setTime=findViewById(R.id.set_alarm);
        theTime=findViewById(R.id.time);
        goHome=findViewById(R.id.go_home);
        cancelAlarm=findViewById(R.id.cancel_alarm);
        getSupportActionBar().setTitle("Cook Timer");
        Intent i = getIntent();

        if(i.getStringExtra("theAlarm").equals("")){
            theTime.setText("No Cooking Alarm Set");

        }else{
            theTime.setText(i.getStringExtra("theAlarm"));
        }


        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("alarm",theTime.getText().toString());
                setResult(RESULT_OK,i);
                finish();
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeSelector = new TimeSelector();
                timeSelector.show(getSupportFragmentManager(),"time select");
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theTime.getText().toString().equals("No Cooking Alarm Set")){
                    Toast.makeText(Timer.this,"No Alarm Set",Toast.LENGTH_SHORT).show();

                }else{
                    cancelTheAlarm();

                }
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);

        updateTime(c);
        startAlarm(c);


    }

    private void updateTime(Calendar c){
        String time="Alarm set for: ";
        time+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        theTime.setText(time);
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(Timer.this,AlertResponse.class);
        PendingIntent pi=PendingIntent.getBroadcast(Timer.this,1,i,0);

        if(c.before(Calendar.getInstance())){
            Toast.makeText(Timer.this,"That time is in the past!",Toast.LENGTH_SHORT).show();
        }else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);

        }

    }

    private void cancelTheAlarm(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(Timer.this,AlertResponse.class);
        PendingIntent pi=PendingIntent.getBroadcast(Timer.this,1,i,0);
        alarmManager.cancel(pi);
        theTime.setText("Alarm Canceled");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("alarm",theTime.getText().toString());
        setResult(RESULT_OK,i);
        finish();
    }
}
