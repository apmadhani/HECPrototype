package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class SendNotificationActivity extends Activity {

    private EditText inputBox;
    private String newEmail;
    private String phoneNumber;

    private AdapterView.OnItemSelectedListener changeNotification = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // your code here
            if(position==1) {
                newEmail = inputBox.getText().toString();
                inputBox.setHint(getResources().getString(R.string.phone));
                inputBox.setRawInputType(InputType.TYPE_CLASS_PHONE);
                inputBox.setText(phoneNumber);
            } else {
                phoneNumber = inputBox.getText().toString();
                inputBox.setHint(getResources().getString(R.string.email));
                inputBox.setRawInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
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
        String email = intent.getStringExtra(LoginActivity.USERNAME);
        Button sendEmail = (Button) findViewById(R.id.send_saved_email);
        sendEmail.setText(getResources().getString(R.string.send_email)+"\n"+email);

        Spinner spinner = (Spinner) findViewById(R.id.notification_method);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.notification_methods, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this.changeNotification);
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
        sendNotification(view);
    }

    public void sendNotification(View view) {
        Intent intent=new Intent(this, TVActivity.class);
        startActivity(intent);
    }
}
