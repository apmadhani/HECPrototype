package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;


public class LoadingActivity extends Activity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        email = getIntent().getStringExtra(LoginActivity.USERNAME);
        getStatus(getIntent().getStringExtra(PaymentMethodActivity.CART_TOKEN));
    }

    private String getStatus(String cartID) {
        JSONObject json = new JSONObject();
        JSONObject cartToken = new JSONObject();

        try {
            cartToken.put("ec_token", cartID);
            json.put("clientID", 1);
            json.put("clientKey", getResources().getString(R.string.client_key));
            json.put("data", cartToken);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, getResources().getString(R.string.payment_status_url));

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                String status = (String) ((JSONObject) jsonResponse.get("data")).get("status");
                if(status == "success") {
                    goToNetflix();
                } else {
                    cancel();
                }
                return status;
            } else {
                return "error";
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return "error";
        }
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

    public void cancel(View view) {
        cancel();
    }

    public void cancel() {
        Intent intent=new Intent(this, SubscriptionTypeActivity.class);
        intent.putExtra(LoginActivity.USERNAME, email);
        startActivity(intent);
    }

    private void goToNetflix() {
        Intent intent=new Intent(this, TVActivity.class);
        startActivity(intent);
    }
 }
