
package com.proyecto.urudatamovil.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.utils.Constants;


public class LoginOutsourcerActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */

    private static boolean isQuit = false;
// Metodos de Android, se sobrescriben,

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_outsourcer);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_main_action_close) {
            olvidarPass();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
            super.onResume();
            if (isQuit) {
                olvidarPass();
                finish();
            }
            String modo = recuperarModo();
            switch (modo) {
                case "RecordarTodo":
                    setUser(recuperarUser());
                    setPass(recuperarPass());
                    break;
                case "OlvidarTodo":
                    setUser("");
                    setPass("");
                    break;
                case "RecordarUser":
                    setUser(recuperarUser());
                    setPass("");
                    break;
                default:
                    securityMode("OlvidarTodo");
                    break;
        }
    }

    public void showPopup(MenuItem item){
            View v = (View) findViewById(R.id.menu_main_opciones);
            PopupMenu popupSecurityMenu = new PopupMenu(this,v);
            popupSecurityMenu.getMenuInflater().inflate(R.menu.main_security_menu, popupSecurityMenu.getMenu());
            popupSecurityMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    Context context = getApplicationContext();
                    CharSequence text = "";
                    int duration = Toast.LENGTH_LONG;
                    switch (item.getItemId()) {
                        case R.id.security_recordar_todo:
                            text = "Se almacenaran el usuario y la password";
                            securityMode("RecordarTodo");
                            break;
                        case R.id.security_menu_olvidar:
                            text = "No se almacenara el usuario y la password.";
                            securityMode("OlvidarTodo");
                            setPass("");
                            setUser("");
                            break;
                        case R.id.security_menu_recordar_usuario:
                            text = "Se almacenara el usuario, pero no la password";
                            securityMode("RecordarUser");
                            setPass("");
                            break;
                        default:
                            break;
                    }
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return true;
                }
            });
            popupSecurityMenu.show();
    }

// Metodos propios de la app

    public static void setQuit(boolean flag) {
        isQuit = flag;
    }

    public void loginOutsoucer(View v) {
        String user = getUser(v);
        String pass = getPass(v);
        if (!(user.equals("") || pass.equals(""))) {
            Intent loginIntent = new Intent(this, LoginConnectActivity.class);
            loginIntent.putExtra("user", user);
            loginIntent.putExtra("pass", pass);
            startActivityForResult(loginIntent, Constants.LOGIN_REQUEST, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.LOGIN_FAILED) {
            loginError();
        } else{
           // saveViewStatus()
           // INICIAR MAIN ACITIVITY OUTSOURCER (USER, PASS, COOKEI)
        }
    }

    public void loginError() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Usuario o clave Invalidos");
        alertDialogBuilder
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginOutsourcerActivity.this.finish();
                    }
                })
                .setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.show();
    }

    // Metodos auxiliares

    private boolean verificaDatos(View view) {
        String user = getUser(view);
        String pass = getPass(view);
        if ((!user.equals("")) && (!pass.equals(""))) {
            almacenarDatos(user,pass );
            return true;
        }
        return false;
    }

    private Intent saveViewStatus(View view, int actionCode) {
        Intent intent = new Intent(this, LoginOutsourcerActivity.class);
        intent = loadIntent(intent, view);
        if (intent != null) {
            intent.putExtra("action", actionCode);
            intent.putExtra("user", getUser(view));
            intent.putExtra("pass", getPass(view));
            this.setIntent(intent);
        }
        return intent;

    }

//----------------------------------------------------
// Datos de sesion -
//----------------------------------------------------

    private String getUser(View v) {
        EditText editTextName = (EditText) findViewById(R.id.id_outsourcer);
        return editTextName.getText().toString();
    }

    private String getPass(View v) {
        EditText editTextPass = (EditText) findViewById(R.id.pass_outsourcer);
        return editTextPass.getText().toString();
    }

    public void setUser(String user) {
        EditText editTextUser = (EditText) findViewById(R.id.id_outsourcer);
        editTextUser.setText(user);
    }

    private void setPass(String pass) {
        EditText editTextPass = (EditText) findViewById(R.id.pass_outsourcer);
        editTextPass.setText(pass);
    }

    private Intent loadIntent(Intent intent, View view) {
        String name = getUser(view);
        String pass = getPass(view);
        if (name.equals("") || pass.equals("")) {
            intent = null;
        } else {
            intent.putExtra("user", name);
            intent.putExtra("pass", pass);
        }
        return intent;
    }

//--------------------------------------------------------
// Recordar usuario y pass - Persiste entre sesiones
//--------------------------------------------------------

    private void olvidarPass() {
        SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor ed = userData.edit();
        ed.putString("pass", "");
        ed.putString("modo","RecordarUser");
        ed.commit();
    }
     private void olvidarTodo(){
         SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
         SharedPreferences.Editor ed = userData.edit();
         ed.putString("pass", "");
         ed.putString("user", "");
         ed.putString("modo","OlvidarTodo");
         ed.commit();
     }
     private void securityMode(String modo) {
         SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
         SharedPreferences.Editor ed = userData.edit();
         ed.putString("modo", modo);
         ed.commit();
         olvidarDatos();
     }

    private void almacenaUser(String user){
        SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor ed = userData.edit();
        ed.putString("user", user);
        ed.commit();
    }

    private void almacenaPass(String pass) {
        SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor ed = userData.edit();
        ed.putString("pass", pass);
        ed.commit();
    }
    private String recuperarModo() {
        SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
        return userData.getString("modo","OlvidarTodo");
    }
    private String recuperarUser() {
        SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
        return userData.getString("user","");
    }

    private String recuperarPass() {
        SharedPreferences userData = getSharedPreferences("userdetails", MODE_PRIVATE);
        return userData.getString("pass", "");
    }
    private void olvidarDatos(){
        String modo=recuperarModo();
        switch (modo) {
            case "RecordarTodo":
                break;
            case "OlvidarTodo":
                olvidarTodo();
                break;
            case "RecordarUser":
                olvidarPass();
                break;
            default:
                olvidarTodo();
                securityMode("OlvidarTodo");
                break;
        }
    }
    private void almacenarDatos(String user, String pass){
        String modo=recuperarModo();
        switch (modo) {
            case "RecordarTodo":
                almacenaUser(user);
                almacenaPass(pass);
                break;
            case "OlvidarTodo":
                olvidarTodo();
                break;
            case "RecordarUser":
                almacenaUser(user);
                break;
            default:
                securityMode("OlvidarTodo");
                break;
        }
     }


// Fragmento de pantalla

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login_outsourcer, container, false);
            return rootView;

        }
    }
}