package com.example.kuetbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class EmployeeFromAdmin extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_from_admin);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);
        AdminAdapter adminAdapter=new AdminAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adminAdapter.addfragment(new EmployeeListFragment(),"EMPLOYEE LIST");
        adminAdapter.addfragment(new AssignEmployeeFragment(),"ASSIGN EMPLOYEE");
        adminAdapter.addfragment(new Fragment(),"EXTRA");
        viewPager.setAdapter(adminAdapter);
    }
}