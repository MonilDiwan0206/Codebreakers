package com.example.monil0206.kyc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CodeReader extends AppCompatActivity {
    private Button mButton;
    private TextView fName;
    private TextView date;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_reader);
        mButton = (Button) findViewById(R.id.mButton);
        fName = findViewById(R.id.fName);
        date = findViewById(R.id.date);
        address = findViewById(R.id.address);

        final Activity activity = this;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                fName.setText(splitstr[0]);
                date.setText(splitstr[1]);
                address.setText(splitstr[2]);

            }
        } else {
            Toast.makeText(this, "Invalid QRCode", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
