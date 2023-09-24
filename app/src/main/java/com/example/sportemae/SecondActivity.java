package com.example.sportemae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ViewPager2 pager = findViewById(R.id.pager);
        FragmentStateAdapter pageAdapter = new MyAdapter(this);
        pager.setAdapter(pageAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy(){

            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                Map<Integer, String> days = new HashMap<Integer, String>();
                days.put(1, "Пн");
                days.put(2, "Вт");
                days.put(3, "Ср");
                days.put(4, "Чт");
                days.put(5, "Пт");
                days.put(6, "Сб");
                days.put(7, "Вс");

                tab.setText("" + (days.get(position + 1)));
            }
        });
        tabLayoutMediator.attach();
    }
}
