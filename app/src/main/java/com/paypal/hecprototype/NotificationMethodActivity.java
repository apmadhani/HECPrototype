package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class NotificationMethodActivity extends Activity {

    public static final String NOTIFICATION_METHOD = "com.paypal.hecprototype.notification_method";

    private String username;
    private String subscription;
    private String cart_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_method);
        Intent intent = getIntent();
        username = intent.getStringExtra(LoginActivity.USERNAME);
        subscription = intent.getStringExtra(SubscriptionTypeActivity.SUBSCRIPTION);
        cart_token = intent.getStringExtra(PaymentMethodActivity.CART_TOKEN);
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

    public void goToSendNotification(Intent intent) {
        intent.putExtra(LoginActivity.USERNAME, username);
        intent.putExtra(SubscriptionTypeActivity.SUBSCRIPTION, subscription);
        intent.putExtra(PaymentMethodActivity.CART_TOKEN, cart_token);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(this, SendNotificationActivity.class);
        intent.putExtra(NOTIFICATION_METHOD, getResources().getString(R.string.email));
        goToSendNotification(intent);
    }

    public void sendSMS(View view) {
        Intent intent = new Intent(this, SendNotificationActivity.class);
        intent.putExtra(NOTIFICATION_METHOD, getResources().getString(R.string.sms));
        goToSendNotification(intent);
    }
}
