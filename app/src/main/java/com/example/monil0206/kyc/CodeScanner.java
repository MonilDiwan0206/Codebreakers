package com.example.monil0206.kyc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CodeScanner extends AppCompatActivity {
    private TextView name;
    private TextView dob;
    private TextView address;
    private TextView phoneNumber;
    private Button Scan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);

        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        Scan = findViewById(R.id.scan);
        final Activity activity = this;
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You Cancelled the Scanning", Toast.LENGTH_SHORT).show();
            } else {
                String text = result.getContents().toString();
                String [] splitstr = text.split("%");
                name.setText(splitstr[0]);
                dob.setText(splitstr[1]);
                address.setText(splitstr[2]);
                phoneNumber.setText(splitstr[3]);

            }
        } else {
            Toast.makeText(this, "Invalid QRCode", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
