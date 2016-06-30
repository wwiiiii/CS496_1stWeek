package com.example.q.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class addActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);


    }

    protected void onConfirm(View view)
    {
        Intent intent = getIntent();
        String name = ((TextView)findViewById(R.id.inputName)).getText().toString();
        String email = ((TextView)findViewById(R.id.inputEmail)).getText().toString();
        String mobile = ((TextView)findViewById(R.id.inputMobile)).getText().toString();
        intent.putExtra("data_name",name);
        intent.putExtra("data_email",email);
        intent.putExtra("data_mobile",mobile);
        setResult(RESULT_OK, intent);
        finish();
    }

    protected void onCancel(View view)
    {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}
