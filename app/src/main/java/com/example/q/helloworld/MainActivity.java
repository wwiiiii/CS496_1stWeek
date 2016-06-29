package com.example.q.helloworld;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import android.widget.ViewSwitcher;
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
    private ViewSwitcher viewSwitcher;

    public void BackToGrid(View v){
        viewSwitcher.showNext();
    }
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
        viewSwitcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
        Animation inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left); inAnimation.setDuration(500);
           Animation outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right); outAnimation.setDuration(500);
           viewSwitcher.setOutAnimation(outAnimation);
           viewSwitcher.setInAnimation(inAnimation);
        {
            GridView gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new ImageAdapter(this));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    pager.setCurrentItem(position, true);
                    viewSwitcher.showNext();
                }
            });
//            System.out.println("============================");
//            viewSwitcher.addView(gridView);
        }
        {
            pager = (ViewPager) findViewById(R.id.main_viewPager);
            adapter = new NewAdapter(this.getLayoutInflater(), this);
            pager.setAdapter(adapter);
            pager.setPageTransformer(false, new ViewPager.PageTransformer() {
                        @Override
                        public void transformPage(View page, float position) {
                      float normalizedposition = Math.abs( 1 - Math.abs(position) );

                page.setAlpha(normalizedposition);  //View의 투명도 조절
                page.setScaleX(normalizedposition/2 + 0.5f); //View의 x축 크기조절
                page.setScaleY(normalizedposition/2 + 0.5f); //View의 y축 크기조절
                page.setRotationY(position * 80);   //View의 Y축(세로축) 회전 각도
            }
        });
//            System.out.println("============================");
//            viewSwitcher.addView(pager);
        }

        tabHost.addTab(spec2);

        TabSpec spec3= tabHost.newTabSpec("Tab C").setContent(R.id.linearLayout3).setIndicator("Tab CC");
        tabHost.addTab(spec3);

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=80;
    }

}