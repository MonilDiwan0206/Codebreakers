package com.example.monil0206.kyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mAuth = FirebaseAuth.getInstance();
        final ProfileFragment profileFragment = new ProfileFragment();
        final IdentityFragment identityFragment = new IdentityFragment();
        final DetailsFragment detailsFragment = new DetailsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.mainframe,profileFragment).commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,profileFragment).commit();
                        return true;

                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,detailsFragment).commit();
                        return true;

                    case R.id.navigation_notifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,identityFragment).commit();
                        return true;

                    default: return false;

                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(MainActivity.this,LoginScreen.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
