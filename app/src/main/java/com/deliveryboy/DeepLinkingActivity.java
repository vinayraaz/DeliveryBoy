package com.deliveryboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DeepLinkingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_linking);

        Intent intent=getIntent();

        if(intent!=null&&intent.getData()!=null){
            ((TextView)findViewById(R.id.deepLinkText)).setText(intent.getData().toString());
        }
    }
}
