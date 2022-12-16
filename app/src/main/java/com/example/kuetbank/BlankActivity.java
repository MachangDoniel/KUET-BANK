package com.example.kuetbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BlankActivity extends AppCompatActivity {

    Button button;
    TextView Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);


        //Phone.findViewById(R.id.phone);
        //String phone=getIntent().getStringExtra("phone");
        //Phone.setText("1893097217");

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BlankActivity.this,CustomerOTPsend.class));
            }
        });
    }
}