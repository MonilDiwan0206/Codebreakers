package com.example.monil0206.kyc;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private CircleImageView profileImage;
    private TextView displayName;
    private TextView displayEmail;
    private Toolbar profileToolbar;
    private Button signOut;
    private FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profileImage);
        profileToolbar = view.findViewById(R.id.profileToolbar);
        displayName = view.findViewById(R.id.displayName);
        displayEmail = view.findViewById(R.id.displayEmail);
        mAuth = FirebaseAuth.getInstance();
        signOut = view.findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loginIntent = new Intent(getActivity(),LoginScreen.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(profileToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct != null){
            RequestOptions options = new RequestOptions().error(R.drawable.defprofile).centerCrop();
            String name = acct.getDisplayName();
            String email = acct.getEmail();
            Uri image = acct.getPhotoUrl();
            image = Uri.parse(image.toString().replace("/s96-c/","/s500-c/"));
            displayName.setText(name);
            displayEmail.setText(email + getEmojiByUnicode(0x1F60A));
            Glide.with(this).load(image).apply(options).transition(DrawableTransitionOptions.withCrossFade()).into(profileImage);
//            mRef.child(user_id).child("Display Name").setValue(name);
//            mRef.child(user_id).child("EmailId").setValue(email);
//            mProgress.dismiss();
//            mRef.keepSynced(true);
        }

        return view;
    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
