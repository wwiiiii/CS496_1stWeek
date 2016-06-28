package com.example.q.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    int asdf = 1234;
    public String[] contacts={"Lee Seungwoo",
                                "Jung Taeyoung",
                                "Lee Gimun"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost=(TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabSpec spec1= tabHost.newTabSpec("Tab A").setContent(R.id.linearLayout).setIndicator("Tab AA");
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(Adapter);
//        list.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        tabHost.addTab(spec1);

        TabSpec spec2= tabHost.newTabSpec("Tab B").setContent(R.id.linearLayout2).setIndicator("Tab BB");
        tabHost.addTab(spec2);

        TabSpec spec3= tabHost.newTabSpec("Tab C").setContent(R.id.linearLayout3).setIndicator("Tab CC");
        tabHost.addTab(spec3);

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=80;
    }

    //protected void
}
