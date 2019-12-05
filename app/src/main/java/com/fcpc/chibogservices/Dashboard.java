package com.fcpc.chibogservices;

import android.content.Intent;
import android.os.Bundle;

import com.fcpc.chibogservices.core.Globals;
import com.fcpc.chibogservices.core.SharedPreferenceHelper;
import com.fcpc.chibogservices.objects.UserAccountInfo;
import com.fcpc.chibogservices.objects.WalletValue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Activity for containing data into the dashboard.
 */
public class Dashboard extends AppCompatActivity {

    private Double CURRENT_BALANCE = 0d;

    /*
        HTML Client
     */
    private final OkHttpClient client = new OkHttpClient();

    private Handler h;

    private TextView tvBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dashboard);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tvBal = findViewById(R.id.tvBalance);
        h = new Handler(this.getMainLooper());

        initFAB();
        updateBalance();
    }

    private void updateBalance(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final RequestBody formBody = new FormBody.Builder()
                            .add("acc_id", SharedPreferenceHelper.getSharedPreferenceString(
                                    getApplicationContext(),"user_id","none"))
                            .build();

                    Log.e("ID",SharedPreferenceHelper.getSharedPreferenceString(
                            getApplicationContext(),"user_id","none"));

                    final Request request = new Request.Builder()
                            .url(Globals.BALANCE_URL)
                            .post(formBody)
                            .build();

                    Call call = client.newCall(request);
                    final Response response = call.execute();

                    final String responseData = response.body().string().trim();

                   if(responseData.length() > 3){

                       Gson gson = new Gson();

                       Type userType = new TypeToken<ArrayList<WalletValue>>(){}.getType();

                       final List<WalletValue> wList = gson.fromJson(responseData, userType);

                       CURRENT_BALANCE = Double.parseDouble(wList.get(0).wval);

                       tvBal.setText(CURRENT_BALANCE.toString() + " PHP");
                   }
                }
                catch(IOException ioex){
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Dashboard.this,
                                    "Error encountered while loading information",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }).start();
    }

    private void loadInformation(){

    }

    private void initFAB(){
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if(CURRENT_BALANCE >= 2){

                    startActivity(new Intent().setClass(getApplicationContext(),FoodOrderList.class));

                }
                else{
                    Snackbar.make(fab,"You don't have enough balance " +
                            "to perform transactions.",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
