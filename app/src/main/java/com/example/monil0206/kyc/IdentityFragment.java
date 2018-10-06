package com.example.monil0206.kyc;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdentityFragment extends Fragment {


    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private String text2qr;
    private ImageView mImage;
    private TextView nodata;
    private TextView congo;

    public IdentityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_identity, container, false);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mImage = view.findViewById(R.id.mImage);
        nodata = view.findViewById(R.id.nodata);
        congo = view.findViewById(R.id.congo);



        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String verification = dataSnapshot.child("Verified").getValue().toString();
                if(verification.equalsIgnoreCase("true")){
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String dob = dataSnapshot.child("DOB").getValue().toString();
                    String add = dataSnapshot.child("Address").getValue().toString();
                    String phone = dataSnapshot.child("Contact").getValue().toString();
                    text2qr = name+"%"+dob+"%"+add+"%"+phone;

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE, 200, 200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        mImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } else {
                    mImage.setVisibility(View.INVISIBLE);
                     congo.setVisibility(View.INVISIBLE);
                    nodata.setVisibility(View.VISIBLE);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

}
