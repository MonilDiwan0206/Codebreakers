package com.example.monil0206.kyc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class AdminActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolBar;
    private Button scanCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mToolBar = findViewById(R.id.mToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Admin Account");

        scanCode = findViewById(R.id.scanCode);

        scanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent codeScanner = new Intent(AdminActivity.this,CodeScanner.class);
                startActivity(codeScanner);
            }
        });

    }
}
