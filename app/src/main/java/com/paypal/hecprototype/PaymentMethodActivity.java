package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
        intent.putExtra(CART_TOKEN, cartToken);
        startActivity(intent);
    }

    private String createCart() {
        final String[] response = new String[1];

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse httpresponse;
                JSONObject json = new JSONObject();
                JSONObject cartObj = new JSONObject();

                try {
                    HttpPost post = new HttpPost("http://10.0.2.2:80/api/cart/submit");
                    JSONArray cart = new JSONArray(new String[]{"standard"});
                    cartObj.put("cart", cart);
                    json.put("clientID", 1);
                    json.put("clientKey", "cb91d8ecbe332c731db35e5bd018dacd39359a0d4bde5f5d33b6b438a19df9fa");
                    json.put("data", cartObj);
                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    httpresponse = client.execute(post);

            /*Checking response */
                    if(httpresponse!=null){
                        //Get the data in the entity
                        JSONObject jsonResponse = new JSONObject(EntityUtils.toString(httpresponse.getEntity()));
                        response[0] = (String) ((JSONObject) jsonResponse.get("data")).get("token");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    response[0] = e.getMessage();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response[0];
    }

    public void doNothing(View view) {
    }
}
