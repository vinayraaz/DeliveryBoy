package com.deliveryboy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deliveryboy.modle.DeliveryBoyListModel;
import com.deliveryboy.modle.OrderAssignModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderDetails extends AppCompatActivity {
    String deliveryBName, deliveryBAdd, deliveryBMobile, deliveryBFbaseId;
    int deliveryBStatus, OrderStatus;
    TextView tvName, tvAdd, tvMobile, tvStatus;
    TextView orderName, orderAdd, orderDetails, orderStatus;
    DatabaseReference updateOrder;
    LinearLayout linearLayout;
    List<OrderAssignModel> orderAssignModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails_layout);
        tvName = (TextView) findViewById(R.id.name);
        tvAdd = (TextView) findViewById(R.id.add);
        tvMobile = (TextView) findViewById(R.id.mobile);
        linearLayout = (LinearLayout) findViewById(R.id.linear1);

        orderDetails = (TextView) findViewById(R.id.orderdetail);
        orderName = (TextView) findViewById(R.id.ordername);
        orderAdd = (TextView) findViewById(R.id.orderadd);
        orderStatus = (TextView) findViewById(R.id.orderstatus);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            deliveryBName = b.getString("NAME");
            deliveryBAdd = b.getString("ADD");
            deliveryBStatus = b.getInt("STATUS");
            deliveryBMobile = b.getString("MOBILE");
            deliveryBFbaseId = b.getString("FBASEID");

        }
        tvName.setText("Delivery Boy :" + deliveryBName);
        tvAdd.setText("Address : " + deliveryBAdd);
        tvMobile.setText("Mobile No :" + deliveryBMobile);
        orderView();

       /* if (deliveryBStatus == 1) {
           // tvStatus.setText("Order");
            orderDeliver();
        }*/
        //tvName.setText(deliveryBName);
    }

    private void orderView() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Order_Assign").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot OrderDetails : dataSnapshot.getChildren()) {
                    OrderAssignModel orderNote = OrderDetails.getValue(OrderAssignModel.class);
                    orderAssignModels.add(orderNote);

                }
                for (int i = 0; i < orderAssignModels.size(); i++) {
                    if (deliveryBFbaseId.equalsIgnoreCase(orderAssignModels.get(i).getDeliveryBid())) {
                        linearLayout.setVisibility(View.VISIBLE);
                        orderDetails.setText("Order Detail : " + orderAssignModels.get(i).getOrderDetails());
                        orderName.setText("Order Name : " + orderAssignModels.get(i).getOrderName());
                        orderAdd.setText("Order Address : " + orderAssignModels.get(i).getOrderAdd());
                        OrderStatus = orderAssignModels.get(i).getOrderStatus();
                        if (OrderStatus == 1) {
                            orderStatus.setText("Order");
                            orderDeliver();
                        }else if (OrderStatus == 2) {
                            orderStatus.setText("Delivered");
                        }

                        System.out.println("Name**** " + orderAssignModels.get(i).getDeliveryBid());
                    }else {
                        linearLayout.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void orderDeliver() {

        int delay = 2 * 60000; // delay for 0 sec.
        int period = 9 * 60000; // repeat every 10 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    updateOrder = FirebaseDatabase.getInstance().getReference("Order_Assign");
                    updateOrder.child(deliveryBFbaseId).child("orderStatus").setValue(2);

                } catch (Exception e) {

                }

            }
        }, delay, period);
    }
}
