# HECPrototype
######Alternative to PayPal's Headless Express Checkout flow

Headless Express Checkout is PayPal's current checkout flow for trusted merchants on set-top boxes without traditional browsers. It is being phased out by the end of 2015 and needs to be replaced. One of the proposed solutions is a notification system, where the merchant send the express checkout link to the user via SMS or email. This Android TV app is a prototype proof-of-concept for an HEC replacement involving notifications; it mocks a Netflix flow on a set-top box.

An alternative flow to replace Headless Express Checkout with an activation code based system can be found [here] (https://github.com/apmadhani/HECActivation).

##Getting Started

```bash
    # clone the repo
    $ git clone git@github.com:apmadhani/HECPrototype.git
    $ cd HECPrototype

    # set up configurations
    $ cp app/src/main/res/values/credentials.xml.example app/src/main/res/values/credentials.xml
    $ vim app/src/main/res/values/credentials.xml
```

At this point, you'll have to replace url:port in credentials.xml with the address of a mock merchant server. You can download Android Studio and Android emulator [here] (https://developer.android.com/sdk/index.html) in order to modify/run the app.

##Mock Server Information

This app will communicate with the merchant server, which will communicate with the PayPal servers to get express checkout links and send them to the users by email/SMS. The merchant server must have the following endpoints:

| Endpoint                              | Description                                           |
| ------------------------------------- | ----------------------------------------------------- |
| [/cart/submit](#cartsubmit)           | Submit the item(s) in the user's cart.                |
| [/payment/send](#paymentsend)         | Send the PayPal EC link to the user's phone or email. |
| [/payment/status](#paymentstatus)     | Check on the status of a payment.                     |

The endpoints should have the following API's:

### /cart/submit

Submit the item(s) in the user's cart.

Request `data`

```json
{
    "email": ...,
    "password": ...
    "cart": [
        ...
    ]
}
```

Response `data`

```json
{
    "message": "Your cart has been successfully submitted.",
    "token": ...
}
```

### /payment/send

Send the PayPal EC link to the user's phone or email.

Request `data`

```json
{
    "email": ...,
    "password": ...,
    "ec_token": ...,
    "payment": {
        "method": ...,
        "destination": ...
    }
}
```

The `method` field can be "email" or "phone", and `destination` should be a corresponding phone number or email address.

Response `data`

```json
{
    "message": ...
}
```

The `message` will be "The PayPal Express Checkout link has been delivered." if using a method of "email" or "phone".

### /payment/status

Check on the status of a payment.

This endpoint uses long polling. It will not respond until the user has either completed or cancelled their payment.

Requests `data`

```json
{
    "email": ...,
    "password": ...,
    "ec_token": ...
}
```

Response `data`

```json
{
    "status": ...
}
```
