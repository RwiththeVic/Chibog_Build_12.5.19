package com.fcpc.chibogservices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fcpc.chibogservices.core.Globals;
import com.fcpc.chibogservices.core.SharedPreferenceHelper;
import com.fcpc.chibogservices.objects.UserAccountInfo;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity<publlc> extends AppCompatActivity {

    ViewPager viewPager;

    /**
     * View objects
     */
    private Button btnRegister;
    private EditText Name;
    private EditText Password;
    private Button Login;

    /*
        HTML Client
     */
    private final OkHttpClient client = new OkHttpClient();

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h = new Handler(this.getMainLooper());

        viewPager = (ViewPager) findViewById(R.id.viewPager2);

        ViewPagerLogin viewPagerLogin = new ViewPagerLogin(this);

        viewPager.setAdapter(viewPagerLogin);

        Timer timer = new Timer();;
        timer.scheduleAtFixedRate(new MainActivity.MytimerTask(), 2000, 4000);

        YoYo.with(Techniques.Tada)
                .duration(2000)
                .repeat(2)
                .playOn(findViewById(R.id.txtChibog2));

        btnRegister = (Button) findViewById(R.id.btnRegister);
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationActivity();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(getApplicationContext(),Dashboard.class));
                finish();
                //doLogin(Name.getText().toString(),Password.getText().toString());
                //validate(Name.getText().toString(), Password.getText().toString());
            }
        });

    }

    private void doLogin(final String username, final String password){

        if(username.length() >= 3 && password.length() >= 3){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final RequestBody formBody = new FormBody.Builder()
                                .add("acc_user", username)
                                .add("acc_pass", password)
                                .build();

                        final Request request = new Request.Builder()
                                .url(Globals.LOGIN_URL)
                                .post(formBody)
                                .build();

                        Call call = client.newCall(request);
                        final Response response = call.execute();

                        final String responseData = response.body().string().trim();

                        Log.e("Data",responseData);

                        if(responseData.length() > 1){
                            if(!responseData.contains("0 results")){
                                Gson gson = new Gson();

                                Type userType = new TypeToken<ArrayList<UserAccountInfo>>(){}.getType();

                                final List<UserAccountInfo> accList = gson.fromJson(responseData, userType);
                                
                                SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),
                                        "user_id",accList.get(0).acc_info_id);

                                h.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Welcome, "
                                                + accList.get(0).acc_username, Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent().setClass(getApplicationContext(),Dashboard.class));
                                        finish();
                                    }
                                });

                            }
                            else{
                                h.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Incorrect login information.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else{
                            h.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Incorrect login information.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                    catch(final IOException ioex){
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("ERR",ioex.getMessage());
                                Snackbar.make(Login,"There was an error in connection." + ioex.getMessage()
                                        ,Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }).start();
        }
        else{
            Toast.makeText(this, "Username and password " +
                    "must be at least 3 characters.", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveUserID(String userid){
        SharedPreferenceHelper.setSharedPreferenceString(
                MainActivity.this,"user_id",userid);
    }

    public void showErrorDialog(){

        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("");
    }

    public void openRegistrationActivity() {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
    }

    private void validate(String userName, String userPassword) {

        if ((userName.equals("Arvic Cruz")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Ron dela Merced")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }

        if ((userName.equals("Josiah Buhayan")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Neil Rico")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Geoffrey Reyes")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Adrian dela Cruz")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Christian Griño")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Iñigo Balmadrid")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Adrian Cueto")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Vinz Dacanay")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Amiel Divinagracia")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("David Manalo")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("Cryz Dexter Santos")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        if ((userName.equals("1")) && (userPassword.equals("1"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    }
    public class MytimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }
}




