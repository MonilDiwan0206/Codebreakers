package com.example.monil0206.kyc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    private Button submitBtn;
    private EditText emailText;
    private EditText passText;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailText);
        passText = findViewById(R.id.passwordText);
        submitBtn = findViewById(R.id.submitBtn);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Logging In....");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                String email = emailText.getText().toString();
                String pass = passText.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(AdminLogin.this, "Fields empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mProgress.dismiss();
                                Intent madminIntent = new Intent(AdminLogin.this,CodeScanner.class);
                                startActivity(madminIntent);
                                finish();
                            } else {
                                mProgress.dismiss();
                                Toast.makeText(AdminLogin.this, "Error..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
