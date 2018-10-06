package com.example.monil0206.kyc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import static android.text.TextUtils.isEmpty;

public class UserDetails extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String TAG = "Dates";
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Button submitButton;
    private EditText fullName;
    private EditText phoneNumber;
    private EditText postalAddress;
    private RadioGroup radioSex;
    private TextView inviText;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        mToolbar = (Toolbar)findViewById(R.id.mToolbar);
        mDisplayDate = findViewById(R.id.mDisplayDate);
        submitButton = findViewById(R.id.submitButton);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        postalAddress = findViewById(R.id.postalAddress);
        radioSex = findViewById(R.id.radioSex);
        inviText = findViewById(R.id.inviText);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Enter your Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fullName.getText().toString();
                String number = phoneNumber.getText().toString();
                String address = postalAddress.getText().toString();
                String date = mDisplayDate.getText().toString();
                int selected = radioSex.getCheckedRadioButtonId();
                RadioButton gender = findViewById(selected);
                String sex = (String) gender.getText();
                if(fname.equals("") || number.equals("") || address.equals("") || date.equals("")){
                    Toast.makeText(UserDetails.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mRef.child(user_id).child("Name").setValue(fname);
                    mRef.child(user_id).child("DOB").setValue(date);
                    mRef.child(user_id).child("Gender").setValue(sex);
                    mRef.child(user_id).child("Contact").setValue(number);
                    mRef.child(user_id).child("Address").setValue(address);
                    mRef.child(user_id).child("Verified").setValue("false");

                }
            }
        });



        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(
                        UserDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        day,month,year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "-" + month + "-" + year;
                mDisplayDate.setText(date);
            }
        };

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    mDisplayDate.setVisibility(View.INVISIBLE);
                    fullName.setVisibility(View.INVISIBLE);
                    phoneNumber.setVisibility(View.INVISIBLE);
                    postalAddress.setVisibility(View.INVISIBLE);
                    radioSex.setVisibility(View.INVISIBLE);
                    submitButton.setVisibility(View.INVISIBLE);
                    inviText.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
