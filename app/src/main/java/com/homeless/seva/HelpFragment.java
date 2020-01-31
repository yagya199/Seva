package com.homeless.seva;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    DatabaseReference HomelessData;
    EditText et_work, et_contact, et_location;
    ImageView imageview;
    TextView textView;
    LocationManager locationManager;
    LocationListener locationListener;
    Button btn_submit, photoButton, location_btn;
    String imageEncoded;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference databaseReference;


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomelessData = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("homeless");

        et_work = view.findViewById(R.id.work);
        et_contact = view.findViewById(R.id.contact);
        et_location = view.findViewById(R.id.set_location);
        imageview = view.findViewById(R.id.image);
        location_btn = view.findViewById(R.id.location);
        textView = view.findViewById(R.id.loc);

        btn_submit = view.findViewById(R.id.submit);
        photoButton = view.findViewById(R.id.take_photo);


        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        String address = "";
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (listAddress != null && listAddress.size() > 0) {
                                if (listAddress.get(0).getSubThoroughfare() != null) {
                                    address = listAddress.get(0).getSubThoroughfare() + "\n";

                                }
                                if (listAddress.get(0).getThoroughfare() != null) {
                                    address += listAddress.get(0).getThoroughfare() + "\n";
                                }
                                if (listAddress.get(0).getPostalCode() != null) {
                                    address += listAddress.get(0).getPostalCode() + "\n";


                                }
                                if (listAddress.get(0).getCountryName() != null) {
                                    address += listAddress.get(0).getCountryName();

                                }

                            }
                            et_location.setText(address);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }


            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String work = et_work.getText().toString();
                String contact = et_contact.getText().toString();
                String location = et_location.getText().toString();
                String image = getImageEncoded();


                newHomeless(work, contact, location, image);
            }


        });


    }


    void newHomeless(String work, String contact, String location, String image) {
        HomelessData homelessData = new HomelessData(work, contact, location, image);

        String homelessID = HomelessData.child("homeless").push().getKey();
        HomelessData.child("homeless").child(homelessID).setValue(homelessData);


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(photo);

            encodeBitmapAndSaveToFirebase(photo);


        }
    }


    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

    }

    public String getImageEncoded() {
        return imageEncoded;
    }
}
