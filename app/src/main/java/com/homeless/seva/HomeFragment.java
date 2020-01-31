package com.homeless.seva;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    ListView listView;
    DatabaseReference databaseReference;
    List<HomelessData> HomelessDataList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        HomelessDataList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("homeless");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HomelessData data = dataSnapshot1.getValue(HomelessData.class);


                    HomelessDataList.add(0, data);

                }

                if (getActivity() != null) {
                    HomelessList adapter = new HomelessList(getActivity(), HomelessDataList);
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}





















