package com.example.melek.getitfree;

import android.graphics.Color;
import android.os.Bundle;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.app.TabActivity;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class menu extends TabActivity {

    TabHost TabHostWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabSpec TabMenu1 = TabHostWindow.newTabSpec("Actuality");
        TabSpec TabMenu2 = TabHostWindow.newTabSpec("Announcements");
        TabSpec TabMenu3 = TabHostWindow.newTabSpec("Settings");

        //TabHostWindow.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);


        TabHostWindow.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < TabHostWindow.getTabWidget().getChildCount(); i++) {
                 //   TabHostWindow.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#A2CF02")); // unselected
                    TextView tv = (TextView) TabHostWindow.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }

               // TabHostWindow.getTabWidget().getChildAt(TabHostWindow.getCurrentTab()).setBackgroundColor(Color.parseColor("#669900")); // selected
                TextView tv = (TextView) TabHostWindow.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#000000"));

            }
        });



        //Setting up tab 1 name.
        TabMenu1.setIndicator("Actuality");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this,tab_actuality.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Announcements");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this,tab_announcements.class));

        //Setting up tab 3 name.
        TabMenu3.setIndicator("Settings");
        //Set tab 3 activity to tab 3 menu.
        TabMenu3.setContent(new Intent(this,tab_settings.class));

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);

    }


}
