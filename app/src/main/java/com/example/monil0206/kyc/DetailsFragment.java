package com.example.monil0206.kyc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SettingsAdapter settingsAdapter;
    private Toolbar detailsToolbar;
    private List<Settings> settingsList = new ArrayList<>();

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        detailsToolbar = view.findViewById(R.id.detailsToolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(detailsToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Details");

        settingsAdapter = new SettingsAdapter(settingsList,getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(settingsAdapter);
        prepareSettingsData();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        settingsList = new ArrayList<>();

    }

    private void prepareSettingsData(){
        Settings settings = new Settings("Add your Details");
        settingsList.add(settings);

        settings = new Settings("Upload your Documents");
        settingsList.add(settings);

    }

}
