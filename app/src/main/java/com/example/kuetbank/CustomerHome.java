package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference ref;
    private TextView t1,t2;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String Balance;
    ImageView home,profile,transfer,payment,withdraw,loan,notification;
    TextView home2,profile2,transfer2,payment2,withdraw2,loan2,notification2,balance,check;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view2);
        toolbar=findViewById(R.id.toolbar);
        check=findViewById(R.id.check);
        balance=findViewById(R.id.balance);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ref= FirebaseDatabase.getInstance().getReference().child("Customer");

        String ACC=getIntent().getStringExtra("accountno");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){
                    //String getName=snapshot.child(ACC).child("Name").getValue(String.class);
                    String getName=snapshot.child(ACC).child("name").getValue().toString();
                    Balance=snapshot.child(ACC).child("balance").getValue().toString();
                    //t1.setText(getName);
                }
                else{
                    Toast.makeText(CustomerHome.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profile=findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerHome.this,CustomerProfile.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
        transfer=findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerHome.this,CustomerTransfer.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count%2==0) {
                    balance.setText(Balance+" ৳");
                    check.setText("Hide Balance");
                    count++;
                }
                else{
                    balance.setText("৳");
                    check.setText("Show Balance");
                    count++;
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        startActivity(new Intent(CustomerHome.this,CustomerHome.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        String ACC=getIntent().getStringExtra("accountno");
        switch(item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                intent=new Intent(CustomerHome.this,CustomerProfile.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
                break;
            case R.id.nav_transfer:
                intent=new Intent(CustomerHome.this,CustomerTransfer.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CustomerHome.this,MainActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}