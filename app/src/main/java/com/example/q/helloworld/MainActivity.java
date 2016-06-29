package com.example.q.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

   // String[] contacts ={"1","2","3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost=(TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabSpec spec1= tabHost.newTabSpec("Tab A").setContent(R.id.linearLayout).setIndicator("Contacts");

        /*
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
        */
        contactList = jsonStringToList(loadJsonData());

        ListAdapter Adapter = new SimpleAdapter(
                MainActivity.this, contactList, R.layout.my_item_view,//list_item,
                new String[]    { TAG_NAME , TAG_EMAIL, TAG_PHONE_MOBILE  },
                new int[]       { R.id.name, R.id.email, R.id.mobile        }
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

    protected ArrayList<HashMap<String,String>> jsonStringToList(String jsonStr)
    {
        ArrayList<HashMap<String,String>> res = new ArrayList<HashMap<String,String>>();
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

                    // tmp hashmap for single contact
                    HashMap<String, String> contact = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    contact.put(TAG_ID, id);
                    contact.put(TAG_NAME, name);
                    contact.put(TAG_EMAIL, email);
                    contact.put(TAG_PHONE_MOBILE, mobile);

                    // adding contact to contact list
                    res.add(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    public void buttonPushed(View view)
    {
        /*TextView tv = (TextView)findViewById(R.id.name);
        String str = tv.toString();
        Log.w("tag",str);*/
        GridLayout parent = (GridLayout)view.getParent();
        TextView mobile = (TextView)parent.getChildAt(2);
        Toast.makeText(getApplicationContext(), mobile.getText().toString(), Toast.LENGTH_SHORT).show();
      //  Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("01044257107"));
      //  this.startActivity(intent);
    }
}
