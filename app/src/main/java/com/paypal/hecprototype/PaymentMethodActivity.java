package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PaymentMethodActivity extends Activity {
//    Bitmap paypal;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        username = getIntent().getStringExtra(LoginActivity.USERNAME);
//        ImageView paypalbutton = (ImageView) findViewById(R.id.PayPalButton);
//        paypal = bitmapFromURL(getString(R.string.paypalurl));
//        paypalbutton.setImageBitmap(paypal);
    }

//    public Bitmap bitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (Exception e) {
//            e.printStackTrace();;
//            return null;
//        }
//    }


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

    public void sendNotificationPage(View view) {
        Intent intent = new Intent(this, SendNotificationActivity.class);
        intent.putExtra(LoginActivity.USERNAME, username);
        startActivity(intent);
    }

    public void doNothing(View view) {
    }
}
