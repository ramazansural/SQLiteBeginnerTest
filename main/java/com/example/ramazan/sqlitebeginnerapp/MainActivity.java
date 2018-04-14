package com.example.ramazan.sqlitebeginnerapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();

        countRecords();
        readRecords();
    }

    public void countRecords(){
        int recordCount = new TableControllerStudent(this).count();

        TextView textViewRecordsCount = (TextView)findViewById(R.id.MAINACTIVITY_TEXTVIEW_BUTTONCOUNT);
        textViewRecordsCount.setText(recordCount + " records found");
    }

    public void readRecords(){
        LinearLayout linearLayoutRecords = findViewById(R.id.MAINACTIVITY_LINEARLAYOUT_RECORDS);
        linearLayoutRecords.removeAllViews();

        List<ObjectStudent> studentList = new TableControllerStudent(this).read();

        if(studentList.size() > 0)
        {
            for(ObjectStudent obj : studentList)
            {
                int id = obj.id;
                String studentFirstName  = obj.firstName;
                String studentEmail = obj.email;

                String textViewCOntents = studentFirstName + " - " + studentEmail;

                TextView txtStudentItem = new TextView(this);
                txtStudentItem.setPadding(0,10,0,10);
                txtStudentItem.setText(textViewCOntents);
                txtStudentItem.setTag(Integer.toString(id));       //TODO!!! TAG ? WHY?

                linearLayoutRecords.addView(txtStudentItem);
            }
        }
        else
        {
            TextView txtlocationItem = new TextView(this);
            txtlocationItem.setPadding(8,8,8,8);
            txtlocationItem.setText("No Records yet");

            linearLayoutRecords.addView(txtlocationItem);
        }

    }


    public void onCLick_ButtonCreateStudent(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        //ÖNEMLİ: findViewById() metodu burada "formElementsView.findViewById(...)" şeklinde çağrılmalıdır.
        final EditText editTextStudentFirstname= (EditText)formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail= (EditText)formElementsView.findViewById(R.id.editTextStudentEmail);

        //show alertdialog with "formElementsView" inflated from xml
        new AlertDialog.Builder(this)
                .setView(formElementsView)
                .setTitle("Create Student")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //save student here...
                                String name = editTextStudentFirstname.getText().toString();
                                String mail = editTextStudentEmail.getText().toString();

                                ObjectStudent student = new ObjectStudent();
                                student.email = mail;
                                student.firstName = name;

                                boolean createSuccessful = new TableControllerStudent(MainActivity.this).create(student);

                                if(createSuccessful){
                                    Toast.makeText(MainActivity.this, "Student information was saved.", Toast.LENGTH_SHORT).show();
                                    countRecords();

                                }else{
                                    Toast.makeText(MainActivity.this, "Unable to save student information.", Toast.LENGTH_SHORT).show();
                                }
                                MainActivity.this.readRecords();
                                dialog.cancel();
                            }
                        }).show();
    }
}
