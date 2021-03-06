package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import skycom.cableit.Classes.AddressType;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ContactType;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactNewDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;

    EditText editContactName;
    EditText editEmailAddress;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new_detail);

        database = AppDatabase.getDatabase(getApplicationContext());


        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);

        editContactName = (EditText) findViewById(R.id.txtNewContactName);
        editEmailAddress = (EditText) findViewById(R.id.txtNewEmail);

        mySpinner = (Spinner) findViewById(R.id.spnContactType);
        mySpinner.setAdapter(new ArrayAdapter<ContactType>(this, android.R.layout.simple_spinner_item, ContactType.values()));


        Button btnNewContact = findViewById(R.id.btnSaveNewContact);
        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tempContact = editContactName.getText().toString();
                String tempEmail = editEmailAddress.getText().toString();

                ContactType tempValue = (ContactType) mySpinner.getSelectedItem();
                int tempContactType = tempValue.getValue();


                if (tempContact.length() >= 1 && !tempContact.isEmpty()) {

                    database.contactDAO().addContact(new Contact(companyID, tempContactType, tempContact, tempEmail, Boolean.TRUE));


                    Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                    //intent.putExtra("COMPANY_ID", companyID - 1 + "");
                    startActivity(intent);

                }
            }
        });
    }
}
