package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.Phone;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class PhoneNewActivity extends AppCompatActivity {

    private AppDatabase database;
    //int phoneID;
    int contactID;

    EditText editNumber;
    EditText editExt;
    EditText editDesc;

    String Number;
    String Ext;
    String Desc;

    Phone phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_new);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        contactID = prefs.getInt("MY_CONTACT", 0);

        editNumber = (EditText) findViewById(R.id.txtNumber);
        editExt = (EditText) findViewById(R.id.txtExt);
        editDesc = (EditText)findViewById(R.id.txtDescription);

        Button btnEditPhone = findViewById(R.id.btnEdit);
        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Number = editNumber.getText().toString();
                Ext = editExt.getText().toString();
                Desc = editDesc.getText().toString();

                phone = new Phone(contactID, Number, Ext, Desc);

                database.phoneDAO().addPhone(phone);

                Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
