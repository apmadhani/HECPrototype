/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.paypal.hecprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*
 * MainActivity class that loads MainFragment
 */
public class LoginActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public static final String USERNAME = "com.paypal.hecprototype.username";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HideKeyboard.setupUI(findViewById(R.id.activity_login_wrapper), this);
    }

    public void choosePaymentMethod(View view) {
        Intent intent = new Intent(this, PaymentMethodActivity.class);
        EditText email = (EditText) findViewById(R.id.username);
        String message = email.getText().toString();
        intent.putExtra(USERNAME, message);
        startActivity(intent);
    }
}