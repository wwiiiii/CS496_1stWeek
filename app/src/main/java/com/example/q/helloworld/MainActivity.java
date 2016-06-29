package com.example.q.helloworld;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.GridView;
import android.content.Context;
import android.widget.Toast;

import com.example.q.helloworld.ImageAdapter;
import com.example.q.helloworld.NewAdapter;

public class MainActivity extends AppCompatActivity {
    int asdf = 1234;
    public String[] contacts={"Lee Seungwoo",
                                "Jung Taeyoung",
                                "gallery Gimun"};
    public Integer[] mThumbIds = {
            R.drawable.pic19, R.drawable.pic10,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.pic8, R.drawable.pic3,
            R.drawable.pic10, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.pic7,
            R.drawable.pic8, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.pic5,
            R.drawable.pic8, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
    private ViewPager pager;
    private NewAdapter adapter;
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
//        GridView gridView = (GridView) findViewById(R.id.gridView);
//        gridView.setAdapter(new ImageAdapter(this));
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
 //           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 //               Toast.makeText(MainActivity.this, ""+position, 100).show();
//            }
//        });
        pager = (ViewPager)findViewById(R.id.main_viewPager);
        adapter=new NewAdapter(this.getLayoutInflater(), this);
        pager.setAdapter(adapter);
        tabHost.addTab(spec2);

        TabSpec spec3= tabHost.newTabSpec("Tab C").setContent(R.id.linearLayout3).setIndicator("Tab CC");
        tabHost.addTab(spec3);

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=80;
    }

    //protected void
}