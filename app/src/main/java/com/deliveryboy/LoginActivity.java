package com.deliveryboy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deliveryboy.modle.DeliveryBoyListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference databaseReference;
    List<DeliveryBoyListModel> deliveryBoyListModels = new ArrayList<>();
    EditText edMobile;
    String mobileno;
    Button btnSubmit;
    String deliveryBName ,deliveryBAdd,deliveryBMobile,deliveryBFbaseId;
    int deliveryBStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference = FirebaseDatabase.getInstance().getReference("new_user");
        edMobile = (EditText)findViewById(R.id.mobile);
        btnSubmit = (Button)findViewById(R.id.submit);
        btnSubmit.setOnClickListener(this);



    }
    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                deliveryBoyListModels.clear();
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    DeliveryBoyListModel note = noteSnapshot.getValue(DeliveryBoyListModel.class);
                    deliveryBoyListModels.add(note);
                }
                for (int i=0;i<deliveryBoyListModels.size();i++){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.dismiss();
                Log.d("ERROR", databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        mobileno = edMobile.getText().toString();
        for (int i=0;i<deliveryBoyListModels.size();i++){
            if (mobileno.equals(deliveryBoyListModels.get(i).getMobileValue())) {

                 deliveryBName = deliveryBoyListModels.get(i).getNameValue();
                 deliveryBAdd = deliveryBoyListModels.get(i).getAddValue();
                 deliveryBStatus = deliveryBoyListModels.get(i).getDeliveryBoyStatus();
                 deliveryBMobile = deliveryBoyListModels.get(i).getMobileValue();
                 deliveryBFbaseId = deliveryBoyListModels.get(i).getUserId();


                Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,OrderDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("NAME",deliveryBName);
                intent.putExtra("ADD",deliveryBAdd);
                intent.putExtra("STATUS",deliveryBStatus);
                intent.putExtra("MOBILE",deliveryBMobile);
                intent.putExtra("FBASEID",deliveryBFbaseId);

                startActivity(intent);
            }else {
            }
        }
    }
}
