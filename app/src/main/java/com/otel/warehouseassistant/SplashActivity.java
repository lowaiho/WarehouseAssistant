package com.otel.warehouseassistant;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.otel.warehouseassistant.tool.DialogUtils;

import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    TextView versionNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        versionNo = (TextView) findViewById(R.id.versionNo);

        init();
    }

    private void init() {
        try {
            final PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionNo.setText("v" + packageInfo.versionName);

            if (isConnected()) {

                WaitTask task = new WaitTask();
                task.execute((Void) null);

            } else {
                DialogUtils.ShowErrorDialog(SplashActivity.this, getString(R.string.error), getString(R.string.noNetwork), true);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        boolean connected = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            /*
            for (NetworkInfo ni : netInfo) {
                if ((ni.getTypeName().equalsIgnoreCase("WIFI")
                        || ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        && ni.isConnected() && ni.isAvailable()) {
                    connected = true;
                }

            }
            */
            if (netInfo != null) { //connected to the internet
                connected = true;
            }
        }

        return connected;
    }

    public class WaitTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
