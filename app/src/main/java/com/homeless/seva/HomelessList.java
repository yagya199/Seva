package com.homeless.seva;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomelessList extends ArrayAdapter<HomelessData> {

    ImageView imageView;
    TextView work, contact, location;
    byte[] decodedByteArray;
    private Activity context;
    private List<HomelessData> homeless;


    public HomelessList(Activity context, List<HomelessData> homeless) {
        super(context, R.layout.list_layout);
        this.context = context;
        this.homeless = homeless;


    }

    @Override
    public int getCount() {
        return homeless.size();


    }


    @Override
    public HomelessData getItem(int position) {
        return homeless.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        final HomelessData h = homeless.get(position);
        @SuppressLint("ViewHolder") View listviewitem = inflater.inflate(R.layout.list_layout, null, true);

        imageView = listviewitem.findViewById(R.id.title_image);
        work = listviewitem.findViewById(R.id.w1);
        contact = listviewitem.findViewById(R.id.contact_no);
        location = listviewitem.findViewById(R.id.l1);


        try {
            Bitmap imageBitmap = decodeFromFirebaseBase64(h.getImage());
            imageView.setImageBitmap(imageBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        work.setText(h.getWork());
        contact.setText(h.getMobile());
        location.setText(h.getLocation());


        return listviewitem;
    }

    public Bitmap decodeFromFirebaseBase64(String image) throws NullPointerException {
        byte[] decodedByteArray;
        decodedByteArray = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
