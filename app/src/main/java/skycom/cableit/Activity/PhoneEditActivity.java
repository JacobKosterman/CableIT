package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import skycom.cableit.Classes.Phone;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class PhoneEditActivity extends AppCompatActivity {


    private AppDatabase database;
    int phoneID;
    int contactID;

    EditText tvNumber;
    EditText tvExt;
    EditText tvDesc;

    String Number;
    String Ext;
    String Desc;


    Phone phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_edit);


        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        contactID = prefs.getInt("MY_CONTACT", 0);
        phoneID = prefs.getInt("MY_PHONE", 0);

        phone = database.phoneDAO().getPhone(phoneID);
        Number = phone.phoneNumber;
        Ext = phone.ext;
        Desc = phone.description;

        tvNumber = (EditText) findViewById(R.id.txtNumber);
        tvExt = (EditText) findViewById(R.id.txtExt);
        tvDesc = (EditText)findViewById(R.id.txtDescription);


        Button btnSavePhone = findViewById(R.id.btnSave);
        btnSavePhone.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                phone = database.phoneDAO().getPhone(phoneID);

                Number = tvNumber.getText().toString();
                Ext = tvExt.getText().toString();
                Desc = tvDesc.getText().toString();

                phone.phoneNumber = Number;
                phone.ext = Ext;
                phone.description = Desc;

                database.phoneDAO().updatePhone(phone);

                Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
