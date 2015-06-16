package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class SendNotificationActivity extends Activity {

    private EditText inputBox;
    private String newEmail;
    private String phoneNumber;
    private String oldEmail;
    private String cartID;
    private Spinner methodSpinner;

    private AdapterView.OnItemSelectedListener changeNotification = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // your code here
            if(position==1) {
                newEmail = inputBox.getText().toString();
                inputBox.setHint(getResources().getString(R.string.phone));
                inputBox.setInputType(InputType.TYPE_CLASS_PHONE);
                inputBox.setText(phoneNumber);
            } else {
                phoneNumber = inputBox.getText().toString();
                inputBox.setHint(getResources().getString(R.string.email));
                inputBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                inputBox.setSelection(inputBox.getText().length());
                inputBox.setText(newEmail);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        inputBox = (EditText) findViewById(R.id.contact);
        newEmail="";
        phoneNumber="";
        Intent intent = getIntent();
        oldEmail = intent.getStringExtra(LoginActivity.USERNAME);
        cartID=intent.getStringExtra(PaymentMethodActivity.CART_TOKEN);
        Button sendEmail = (Button) findViewById(R.id.send_saved_email);
        sendEmail.setText(getResources().getString(R.string.send_email)+"\n"+oldEmail);

        methodSpinner = (Spinner) findViewById(R.id.notification_method);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.notification_methods, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        methodSpinner.setAdapter(adapter);
        methodSpinner.setOnItemSelectedListener(this.changeNotification);
        HideKeyboard.setupUI((View) findViewById(R.id.activity_send_notification_wrapper), this);
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

    public void sendCustomNotification(View view) {
        String resp;
        if  (methodSpinner.getSelectedItemPosition() == 1) {
            resp = sendNotification("phone", inputBox.getText().toString());
        } else {
            resp = sendNotification("email", inputBox.getText().toString());
        }
        if(resp.equals("error")) {
           showError();
        } else {
            goToLoading();
        }
    }

    private void showError() {
        Context context = getApplicationContext();
        CharSequence text = "ERROR";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void sendNotification(View view) {
        if(sendNotification("email", oldEmail).equals("error")){
            showError();
        } else {
            goToLoading();
        }
    }

    private String sendNotification(final String method, final String destination) {
        JSONObject json = new JSONObject();
        JSONObject cartToken = new JSONObject();
        JSONObject payment = new JSONObject();

        try {
            payment.put("method", method);
            payment.put("destination", destination);
            cartToken.put("ec_token", cartID);
            cartToken.put("payment", payment);
            json.put("clientID", 1);
            json.put("clientKey", getResources().getString(R.string.client_key));
            json.put("data", cartToken);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, getResources().getString(R.string.payment_send_url));

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                return (String) ((JSONObject) jsonResponse.get("data")).get("message");
            } else {
                return "error";
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }


    public void goToLoading() {
        Intent intent=new Intent(this, LoadingActivity.class);
        intent.putExtra(LoginActivity.USERNAME, oldEmail);
        intent.putExtra(PaymentMethodActivity.CART_TOKEN, cartID);
        startActivity(intent);
    }
}
