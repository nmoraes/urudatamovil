package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.tasks.WSLoginTask;
import com.proyecto.urudatamovil.utils.Constants;

public class LoginConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        String pass = i.getStringExtra("pass");
        String action = i.getStringExtra("action");
        new WSLoginTask(this).execute(user, pass, action);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void loginResult(String cookie) {
        if (cookie == null) {
            setResult(Constants.LOGIN_FAILED,null);
        } else {
            Intent cookieIntent=new Intent();
            cookieIntent.putExtra("cookie",cookie);
            setResult(Constants.LOGIN_OK,cookieIntent);
        }
        finish();
    }
}