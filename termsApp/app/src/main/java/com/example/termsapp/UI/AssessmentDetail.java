package com.example.termsapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termsapp.Database.Repository;
import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {

    int aID;
    String aName;
    String aType;
    String aStart;
    String aEnd;
    int cID;
    EditText aNameEdit;
    Spinner aTypeSpinner;
    EditText aStartEdit;
    EditText aEndEdit;
    Repository repository;
    Button saveAssessmentButton;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
        aID = getIntent().getIntExtra("aID", -1);

        aNameEdit = findViewById(R.id.assessmentTitleEditText);
        aName = getIntent().getStringExtra("aName");
        aNameEdit.setText(aName);

        aType = getIntent().getStringExtra("aType");
        aTypeSpinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        aTypeSpinner.setAdapter(typeAdapter);
        aTypeSpinner.setSelection(getTypeInt());

        // testing for datePicker below
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        // testing or datePicker above

        aStartEdit = findViewById(R.id.assessmentStartEditText);
        aStart = getIntent().getStringExtra("aStart");
        if (aStart == null) {
            aStartEdit.setText(sdf.format(new Date()));
        }
        else {
            aStartEdit.setText(aStart);
        }

        aEndEdit = findViewById(R.id.assessmentEndEditText);
        aEnd = getIntent().getStringExtra("aEnd");
        if (aEnd == null) {
            aEndEdit.setText(sdf.format(new Date()));
        } else
            aEndEdit.setText(aEnd);



        aStartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateToString = aStartEdit.getText().toString();
                try {
                    startCalendar.setTime(sdf.parse(dateToString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startDate, startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();
            }
        };

        aEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateToString = aStartEdit.getText().toString();
                try {
                    endCalendar.setTime(sdf.parse(dateToString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endDate, endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        cID = getIntent().getIntExtra("cID", -1);

        saveAssessmentButton = findViewById(R.id.saveAssessmentButton);

        saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assessment assessment;
                repository = new Repository(getApplication());
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(aStartEdit.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2 = sdf.parse(aEndEdit.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (d2.before(d1)) {
                    Toast.makeText(AssessmentDetail.this, getString(R.string.dateError), Toast.LENGTH_LONG).show();
                } else  {
                    if (aID == -1) {
                        if (repository.getAllAssessments().size() == 0) {
                            aID = 1;
                        } else {
                            aID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
                        }
                        assessment = new Assessment(aID, aNameEdit.getText().toString(), aTypeSpinner.getSelectedItem().toString(), aStartEdit.getText().toString(), aEndEdit.getText().toString(), cID);
                        repository.insert(assessment);
                        Toast.makeText(AssessmentDetail.this, "Assessment Saved Successfully", Toast.LENGTH_LONG).show();
                    }
                    else {
                        assessment = new Assessment(aID, aNameEdit.getText().toString(), aTypeSpinner.getSelectedItem().toString(), aStartEdit.getText().toString(), aEndEdit.getText().toString(), cID);
                        repository.update(assessment);
                        Toast.makeText(AssessmentDetail.this, "Assessment Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    AssessmentDetail.this.finish();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveAssessment:
                Assessment assessment;
                repository = new Repository(getApplication());
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(aStartEdit.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2 = sdf.parse(aEndEdit.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (d2.before(d1)) {
                    Toast.makeText(AssessmentDetail.this, getString(R.string.dateError), Toast.LENGTH_LONG).show();
                }
                else    {
                    if (aID == -1) {
                        if (repository.getAllAssessments().size() == 0) {
                            aID = 1;
                            assessment = new Assessment(aID, aNameEdit.getText().toString(), aTypeSpinner.getSelectedItem().toString(), aStartEdit.getText().toString(), aEndEdit.getText().toString(), cID);
                            repository.insert(assessment);
                            Toast.makeText(this, "Assessment Saved Successfully", Toast.LENGTH_LONG).show();
                        }
                        else {
                            aID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
                            assessment = new Assessment(aID, aNameEdit.getText().toString(), aTypeSpinner.getSelectedItem().toString(), aStartEdit.getText().toString(), aEndEdit.getText().toString(), cID);
                            repository.update(assessment);
                            Toast.makeText(this, "Assessment Updated Successfully", Toast.LENGTH_LONG).show();
                        }
                        this.finish();
                    }
                }
                return true;

            case R.id.deleteAssessment:
                for (Assessment a : repository.getAllAssessments()) {
                    if (a.getAssessmentID() == aID) {
                        repository.delete(a);
                        Toast.makeText(AssessmentDetail.this, a.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();
                    }
                }
                this.finish();
                return true;

            case R.id.notify_start_assessment:
                String startDate = aStartEdit.getText().toString();
                String startName = aNameEdit.getText().toString();
                String format = "MM/dd/yy";
                sdf = new SimpleDateFormat(format, Locale.US);
                Date myStartDate = null;
                try {
                    myStartDate = sdf.parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long triggerStart = myStartDate.getTime();
                Intent intent3 = new Intent(AssessmentDetail.this, MyReceiver.class);
                intent3.putExtra("notification", startDate + " " + startName + " begins");
                PendingIntent sender1 = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, intent3, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, triggerStart, sender1);
                return true;

            case R.id.notify_end_assessment:
                String endDate = aEndEdit.getText().toString();
                String endName = aNameEdit.getText().toString();
                format = "MM/dd/yy";
                sdf = new SimpleDateFormat(format, Locale.US);
                Date myEndDate = null;
                try {
                    myEndDate = sdf.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long endTrigger = myEndDate.getTime();
                Intent intent4 = new Intent(AssessmentDetail.this, MyReceiver.class);
                intent4.putExtra("notification", endDate + " " + endName + " ends");
                PendingIntent sender2 = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, intent4, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, endTrigger, sender2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        aStartEdit.setText(sdf.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        aEndEdit.setText(sdf.format(endCalendar.getTime()));
    }

    private int getTypeInt() {
        int typeInt = 0;
        for (Assessment a : repository.getAllAssessments()) {
            if (a.getAssessmentID() == aID) {
                if (a.getAssessmentType().contains("Perf")) {
                    typeInt = 1;
                }
            }
            else typeInt = 0;
            break;
        }
        return typeInt;
    }

}