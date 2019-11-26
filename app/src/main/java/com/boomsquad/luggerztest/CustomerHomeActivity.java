package com.boomsquad.luggerztest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerHomeActivity extends AppCompatActivity {

    private EditText pDateField, pTimeField, pItemsField, pLocationField, itemDestinationField;
    private String pDate, pTime, pItems, pLocation, itemDestination;
    private String mService;
    private RadioGroup mRadioGroup;

    private String requestService;



    private Button btnRequest;

    private String userID;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference historyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            }
        };

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        historyDatabase = FirebaseDatabase.getInstance().getReference().child("history").child(userID);

        getMoveInfo();


        pDateField = (EditText) findViewById(R.id.requestDate);
        pTimeField = (EditText) findViewById(R.id.requestTime);
        pItemsField = (EditText) findViewById(R.id.items);
        pLocationField = (EditText) findViewById(R.id.pickupLocation);
        itemDestinationField = (EditText) findViewById(R.id.destination);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.check(R.id.UberX);

        btnRequest = (Button) findViewById(R.id.request);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
//                final String date = pDateField.getText().toString();
//                final String time = pTimeField.getText().toString();
//                final String items = pItemsField.getText().toString();
//                final String location = pLocationField.getText().toString();
//                final String destination = itemDestinationField.getText().toString();
//
//                int selectId = mRadioGroup.getCheckedRadioButtonId();
//
//                final RadioButton radioButton = (RadioButton) findViewById(selectId);
//
//                if (radioButton.getText() == null){
//                    return;
//                }
//
//                requestService = radioButton.getText().toString();
            }
        });

    }

    private void getMoveInfo() {
        historyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("pDate")!=null){
//                        pDate = map.get("pDate").toString();
                        pDateField.setText(pDate);
                    }
                    if(map.get("pTime")!=null){
//                        pTime = map.get("pTime").toString();
                        pTimeField.setText(pTime);
                    }
                    if(map.get("pItems")!=null){
//                        pItems = map.get("pItems").toString();
                        pItemsField.setText(pItems);
                    }
                    if(map.get("pLocation")!=null){
//                        pLocation = map.get("pLocation").toString();
                        pLocationField.setText(pItems);
                    }
                    if(map.get("pDestination")!=null){
//                        itemDestination = map.get("pDestination").toString();
                        itemDestinationField.setText(itemDestination);
                    }
                    if(map.get("service")!=null){
                        mService = map.get("service").toString();
                        switch (mService){
                            case"UberX":
                                mRadioGroup.check(R.id.UberX);
                                break;
                            case"UberBlack":
                                mRadioGroup.check(R.id.UberBlack);
                                break;
                            case"UberXl":
                                mRadioGroup.check(R.id.UberXl);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveUserInformation() {
        pDate = pDateField.getText().toString();
        pTime = pTimeField.getText().toString();
        pItems = pItemsField.getText().toString();
        pLocation = pLocationField.getText().toString();
        itemDestination = itemDestinationField.getText().toString();


        int selectId = mRadioGroup.getCheckedRadioButtonId();

        final RadioButton radioButton = (RadioButton) findViewById(selectId);

        if (radioButton.getText() == null){
            return;
        }

        mService = radioButton.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("pDate", pDate);
        userInfo.put("pTime", pTime);
        userInfo.put("pItems", pItems);
        userInfo.put("pLocation", pLocation);
        userInfo.put("pDestination", itemDestination);


        userInfo.put("service", mService);
        historyDatabase.setValue(userInfo);



    }
}
