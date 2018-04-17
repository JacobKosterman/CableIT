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
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.AddressType;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ContactType;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactEditDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;
    int contactID;
    Contact contact;

    String tempName;
    String tempEmail;
    Spinner mySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);
        contactID = prefs.getInt("MY_CONTACT", 0);

        List<Contact> tempContact = database.contactDAO().getContact(contactID);

        final EditText txtName = (EditText)findViewById(R.id.textContactName);
        final EditText txtEmail = (EditText)findViewById(R.id.textContactEmail);
        mySpinner = (Spinner) findViewById(R.id.spnContactType);

        tempName = txtName.getText().toString();
        tempEmail = txtEmail.getText().toString();
        mySpinner.setAdapter(new ArrayAdapter<ContactType>(this, android.R.layout.simple_spinner_item, ContactType.values()));



        if (!tempContact.isEmpty()) {
            txtName.setText(String.valueOf(tempContact.get(0).contactName), TextView.BufferType.EDITABLE);
            txtEmail.setText(String.valueOf(tempContact.get(0).emailAddress), TextView.BufferType.EDITABLE);

            contactID = tempContact.get(0).id;
            contact = tempContact.get(0);
        }
        Button btnSaveContact = findViewById(R.id.btnSaveContactChanges);
        btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tempName = txtName.getText().toString();
                tempEmail = txtEmail.getText().toString();

                ContactType tempValue = (ContactType) mySpinner.getSelectedItem();
                int tempContactType = tempValue.getValue();

                contact.contactName = tempName;
                contact.emailAddress = tempEmail;
                contact.contactType = tempContactType;

                database.contactDAO().updateContact(contact);

                Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        startActivity(intent);
    }

}