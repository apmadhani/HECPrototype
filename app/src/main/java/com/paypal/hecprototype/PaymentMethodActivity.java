package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethodActivity extends Activity {
//    Bitmap paypal;
    private String username;
    public static final String CART_TOKEN = "com.paypal.hecprototype.cartToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        username = getIntent().getStringExtra(LoginActivity.USERNAME);
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

    public void sendNotificationPage(View view) {
        Intent intent = new Intent(this, SendNotificationActivity.class);
        intent.putExtra(LoginActivity.USERNAME, username);
        String cartToken = createCart();
        if(cartToken.equals("error")){
            Context context = getApplicationContext();
            CharSequence text = "ERROR";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else{
            intent.putExtra(CART_TOKEN, cartToken);
            startActivity(intent);
        }
    }

    private String createCart() {
        JSONObject json = new JSONObject();
        JSONObject cartObj = new JSONObject();

        try {
            JSONArray cart = new JSONArray(new String[]{"standard"});
            cartObj.put("cart", cart);
            json.put("clientID", 1);
            json.put("clientKey", getResources().getString(R.string.client_key));
            json.put("data", cartObj);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, getResources().getString(R.string.submit_cart_url));

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                return (String) ((JSONObject) jsonResponse.get("data")).get("token");
            } else {
                return "error";
            }
        }catch(JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void doNothing(View view) {
    }
}
