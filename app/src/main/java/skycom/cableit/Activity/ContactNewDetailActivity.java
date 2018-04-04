package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import skycom.cableit.Classes.Contact;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactNewDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;

    EditText editContactName;
    EditText editEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new_detail);

        database = AppDatabase.getDatabase(getApplicationContext());


        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);

        editContactName = (EditText) findViewById(R.id.txtNewContactName);
        editEmailAddress = (EditText) findViewById(R.id.txtNewEmail);


        Button btnNewContact = findViewById(R.id.btnSaveNewContact);
        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tempContact = editContactName.getText().toString();
                String tempEmail = editEmailAddress.getText().toString();


                if (tempContact.length() >= 1 && !tempContact.isEmpty()) {

                    database.contactDAO().addContact(new Contact(companyID, "Test Type", tempContact, tempEmail, Boolean.TRUE));


                    Intent intent = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                    intent.putExtra("COMPANY_ID", companyID - 1 + "");
                    startActivity(intent);

                }
            }
        });
    }
}
