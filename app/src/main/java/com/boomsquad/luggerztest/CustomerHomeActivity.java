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
    private RadioGroup mRadioGroup;




    private Button btnRequest;

    private String userID;
    private String pStatus = "Open";

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

                Lugs lugs = new Lugs(pDateField.getText().toString(),
                                        pTimeField.getText().toString(),
                                        pItemsField.getText().toString(),
                                        pLocationField.getText().toString(),
                                        itemDestinationField.getText().toString(),
                                        pStatus);

                addLug(lugs);

            }
        });

    }


    private void addLug(Lugs lugs) {
        FirebaseDatabase historyDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = historyDatabase.getReference("history").push();

        myRef.setValue(lugs);


    }
}
