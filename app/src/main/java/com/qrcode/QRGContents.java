package com.qrcode;

import android.provider.ContactsContract;

public class QRGContents {

    public static final class ImageType {
         static int IMAGE_PNG = 0;
        public static int IMAGE_JPEG = 1;
    }

    public static final class Type {

        // Plain text. Use Intent.putExtra(DATA, string). This can be used for URLs too, but string
        // must include "http://" or "https://".
        public static final String TEXT = "TEXT_TYPE";

        // An email type. Use Intent.putExtra(DATA, string) where string is the email address.
         static final String EMAIL = "EMAIL_TYPE";

        // Use Intent.putExtra(DATA, string) where string is the phone number to call.
         static final String PHONE = "PHONE_TYPE";

        // An SMS type. Use Intent.putExtra(DATA, string) where string is the number to SMS.
         static final String SMS = "SMS_TYPE";

         static final String CONTACT = "CONTACT_TYPE";

         static final String LOCATION = "LOCATION_TYPE";
    }

     static final String URL_KEY = "URL_KEY";

     static final String NOTE_KEY = "NOTE_KEY";

    // When using Type.CONTACT, these arrays provide the keys for adding or retrieving multiple
    // phone numbers and addresses.
     static final String[] PHONE_KEYS = {
            ContactsContract.Intents.Insert.PHONE, ContactsContract.Intents.Insert.SECONDARY_PHONE,
            ContactsContract.Intents.Insert.TERTIARY_PHONE
    };

   /* public static final String[] PHONE_TYPE_KEYS = {
            ContactsContract.Intents.Insert.PHONE_TYPE,
            ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE,
            ContactsContract.Intents.Insert.TERTIARY_PHONE_TYPE
    };*/

     static final String[] EMAIL_KEYS = {
            ContactsContract.Intents.Insert.EMAIL, ContactsContract.Intents.Insert.SECONDARY_EMAIL,
            ContactsContract.Intents.Insert.TERTIARY_EMAIL
    };

 /*   public static final String[] EMAIL_TYPE_KEYS = {
            ContactsContract.Intents.Insert.EMAIL_TYPE,
            ContactsContract.Intents.Insert.SECONDARY_EMAIL_TYPE,
            ContactsContract.Intents.Insert.TERTIARY_EMAIL_TYPE
    };*/
}
