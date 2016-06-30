package com.example.q.helloworld;

import android.provider.ContactsContract;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private boolean isZoom=false;
    private float sizeX;
    private float sizeY;
    protected class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        ViewPager mPager;
        public ScaleListener(ViewPager pager) {
            mPager=pager;
        }
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            View currentView=mPager.findViewWithTag("pager"+mPager.getCurrentItem());
//            Log.d("pager","box : "+currentBox.getClass().toString());
//            View currentView = ((LinearLayout)currentBox.getChildAt(0)).getChildAt(0);
//            Log.d("pager","view : "+currentView.getClass().toString());
//            mPager.getAdapter().getItem;
//            View currentView=mPager.getFocusedChild();
//            Log.d("pager","current scaleX : "+currentView.getScaleX());
            sizeX=currentView.getScaleX();
//            Log.d("pager","sizeX : "+currentView.getScaleX());
            Log.d("pager","scaleFactor : "+scaleFactor);
            sizeY=currentView.getScaleY();
            currentView.setScaleX(sizeX*scaleFactor);
//            Log.d("pager","sizeX : "+currentView.getScaleX());
            currentView.setScaleY(sizeY*scaleFactor);
//            float sizeX=mPager.getScaleX();
//            float sizeY=mPager.getScaleY();
//            mPager.setScaleX(sizeX*scaleFactor);
//            mPager.setScaleY(sizeY*scaleFactor);
//            mPager.getChildAt(mPager.getCurrentItem()).setScaleY(scaleFactor);
            return true;
        }
    }
    private ScaleGestureDetector scaleGestureDetector;

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
                    float normalizedposition = Math.abs(1 - Math.abs(position));

                    page.setAlpha(normalizedposition);  //View의 투명도 조절
                    page.setScaleX(normalizedposition / 2 + 0.5f); //View의 x축 크기조절
                    page.setScaleY(normalizedposition / 2 + 0.5f); //View의 y축 크기조절
                    page.setRotationY(position * 80);   //View의 Y축(세로축) 회전 각도
                }
            });

            scaleGestureDetector=new ScaleGestureDetector(this, new ScaleListener(pager));
            pager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int act=event.getAction();
                    switch(act&MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_POINTER_DOWN:
                            if(!isZoom)
                                isZoom=true;
                            break;
                        case MotionEvent.ACTION_DOWN :
                            break;
                        case MotionEvent.ACTION_UP:
                            isZoom=false;
                            break;
                        case MotionEvent.ACTION_MOVE:
//                            if(!isZoom) return false;
                    }
                    scaleGestureDetector.onTouchEvent(event);
                    return false;
                }
            });
        }
        tabHost.addTab(spec2);

        TabSpec spec3= tabHost.newTabSpec("Tab C").setContent(R.id.linearLayout3).setIndicator("Tab CC");
        tabHost.addTab(spec3);

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=80;
    }

}