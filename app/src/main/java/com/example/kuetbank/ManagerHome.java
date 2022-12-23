package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //LinearLayout home,profile,managerlist,notification;


    ImageView home1,profile1,managerlist1,notification1;
    TextView home2,profile2,managerlist2,notification2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);


        home1=findViewById(R.id.home1);
        home2=findViewById(R.id.home2);
        profile1=findViewById(R.id.profile1);
        profile2=findViewById(R.id.profile2);
        managerlist1=findViewById(R.id.elist1);
        managerlist2=findViewById(R.id.elist2);
        notification1=findViewById(R.id.notification1);
        notification2=findViewById(R.id.notification2);

        //String email=getIntent().getStringExtra("email");
        FirebaseUser emp = FirebaseAuth.getInstance().getCurrentUser();
        String email=emp.getUid();

        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ManagerHome.this, "You are in Home", Toast.LENGTH_SHORT).show();
            }
        });
        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ManagerHome.this, "You are in Home", Toast.LENGTH_SHORT).show();
            }
        });
        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerHome.this,ManagerProfile.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerHome.this,ManagerProfile.class));
            }
        });
        managerlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerHome.this,EmployeeList.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        managerlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerHome.this,EmployeeList.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        notification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerHome.this,EmployeeApplicants.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        notification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerHome.this,EmployeeApplicants.class);
                intent.putExtra("email",email);
                startActivity(intent);
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
        startActivity(new Intent(ManagerHome.this,ManagerHome.class));
    }

    private void signout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ManagerHome.this,ManagerLogin.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                startActivity(new Intent(ManagerHome.this,ManagerProfile.class));
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ManagerHome.this,ManagerLogin.class));
                break;
            case R.id.nav_customerlist:
                startActivity(new Intent(ManagerHome.this,EmployeeList.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}