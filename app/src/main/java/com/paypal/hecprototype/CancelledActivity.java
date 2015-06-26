package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CancelledActivity extends Activity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findViewById(R.id.loading_info).setVisibility(View.INVISIBLE);
        email = getIntent().getStringExtra(LoginActivity.USERNAME);
        ((TextView) findViewById(R.id.loading_title)).setText(getString(R.string.payment_cancelled));
        ((TextView) findViewById(R.id.prompt_continue)).setText(getString(R.string.cancelled_extra_info));
        ((Button) findViewById(R.id.cancel_button)).setText(R.string.goto_subscription);
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

    public void cancel(View v) {
        Intent intent=new Intent(this, SubscriptionTypeActivity.class);
        intent.putExtra(LoginActivity.USERNAME, email);
        startActivity(intent);
    }
}
