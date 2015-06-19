package com.paypal.hecprototype;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by amadhani on 6/18/15.
 */
public class SendNotificationActivityUnitTest extends ActivityUnitTestCase<SendNotificationActivity> {

    private Intent mLaunchIntent;
    private String testUser;

    public SendNotificationActivityUnitTest() {
        super(SendNotificationActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), SendNotificationActivity.class);
        mLaunchIntent.putExtra(LoginActivity.USERNAME, testUser);
    }
}