package com.homeless.seva;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Donate extends Fragment {

    ListView listView;

    public Donate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        String[] str = {"FOOD", "CLOTHES", "MONEY", "HEALTHCARE", "INDIA"};

        listView = view.findViewById(R.id.donate_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, str);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.foodonfoot.org/"));
                    startActivity(browserIntent);
                }
                if (position == 1) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://clothingthehomeless.azurewebsites.net/"));
                    startActivity(browserIntent);
                }
                if (position == 2) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://handup.org/"));
                    startActivity(browserIntent);
                }
                if (position == 3) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://nhchc.org/"));
                    startActivity(browserIntent);
                }

                if (position == 4) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.udayfoundation.org/"));
                    startActivity(browserIntent);
                }
            }
        });


        return view;
    }

}
