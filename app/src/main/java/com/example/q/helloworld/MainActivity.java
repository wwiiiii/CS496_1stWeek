package com.example.q.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PHONE_MOBILE = "mobile";
    private static final String TAG_PHONE_HOME = "home";
    private static final String TAG_PHONE_OFFICE = "office";

    // contacts JSONArray
    JSONArray contacts = null;


    ArrayList<Person> contactList;
    ArrayAdapter Adapter;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        TabHost tabHost=(TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabSpec spec1= tabHost.newTabSpec("Tab A").setContent(R.id.linearLayout).setIndicator("Contacts");


        contactList = jsonStringToList(loadJsonData());


        Adapter = new PersonAdapter(
                this, R.layout.my_item_view, contactList
        );



        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(Adapter);

        tabHost.addTab(spec1);

        TabSpec spec2= tabHost.newTabSpec("Tab B").setContent(R.id.linearLayout2).setIndicator("Tab BB");
        tabHost.addTab(spec2);

        TabSpec spec3= tabHost.newTabSpec("Tab C").setContent(R.id.linearLayout3).setIndicator("Tab CC");
        tabHost.addTab(spec3);

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=80;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
               // startActivity(intent);
                addListContact();
            }
        });
    }


    protected String loadJsonData(){
        InputStream is = getResources().openRawResource(R.raw.mydata);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch(Exception e){
        } finally {
            try{is.close();}
            catch(Exception e){}
        }

        return writer.toString();
    }

    protected ArrayList<Person> jsonStringToList(String jsonStr)
    {
        ArrayList<Person> res = new ArrayList<Person>();
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String email = c.getString(TAG_EMAIL);
                    String address = c.getString(TAG_ADDRESS);
                    String gender = c.getString(TAG_GENDER);

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject(TAG_PHONE);
                    String mobile = phone.getString(TAG_PHONE_MOBILE);
                    String home = phone.getString(TAG_PHONE_HOME);
                    String office = phone.getString(TAG_PHONE_OFFICE);


                    Person contact = new Person(name,email,mobile);

                    // adding contact to contact list
                    res.add(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        res.add(new Person("","",""));
        return res;
    }
    public void buttonPushed(View view)
    {
        /*TextView tv = (TextView)findViewById(R.id.name);
        String str = tv.toString();
        Log.w("tag",str);*/
        vibrator.vibrate(1000);
        GridLayout parent = (GridLayout)view.getParent();
        TextView mobile = (TextView)parent.getChildAt(2);
        String num = mobile.getText().toString();
        Toast.makeText(getApplicationContext(), num, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
        startActivity(intent);
    }
    int dummyn = 0;
    protected void addListContact()
    {
        Intent intent = new Intent(MainActivity.this, addActivity.class);
        startActivityForResult(intent, 1);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                String name = data.getStringExtra("data_name");
                String email = data.getStringExtra("data_email");
                String mobile = data.getStringExtra("data_mobile");
                contactList.add(contactList.size()-1,new Person(name, email, mobile));
                Adapter.notifyDataSetChanged();
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(getApplicationContext(), "Input Canceled!", Toast.LENGTH_SHORT);
        }

    }

    class Person{
        public String name;
        public String email;
        public String mobile;

        public Person(String _name, String _email, String _mobile)
        {
            this.name = _name;
            this.email = _email;
            this.mobile = _mobile;
        }

    }

    private class PersonAdapter extends ArrayAdapter<Person>{

        private ArrayList<Person> items;

        public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.my_item_view, null);
            }
            Person p = items.get(position);
            if (p != null) {
                TextView nameV = (TextView) v.findViewById(R.id.name);
                TextView emailV = (TextView) v.findViewById(R.id.email);
                TextView mobileV = (TextView) v.findViewById(R.id.mobile);
                if (nameV != null) {
                    nameV.setText(p.name);
                }
                if (emailV != null) {
                    emailV.setText(p.email);
                }
                if (mobileV != null) {
                    mobileV.setText(p.mobile);
                }
            }
            return v;
        }
    }
}
