package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Adapters.PhoneAdapter;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ContactType;
import skycom.cableit.Classes.Phone;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;
    int contactID;
    String addressType;


    ContactType contactType;

    ListView lvPhone;
    private static PhoneAdapter adapterPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        lvPhone =(ListView)findViewById(R.id.lstPhones);
        TextView txtName = (TextView)findViewById(R.id.textContactName);
        TextView txtEmail = (TextView)findViewById(R.id.textContactEmail);
        TextView txtContactType = (TextView)findViewById(R.id.textContactType);

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        companyID = prefs.getInt("MY_COMPANY", 0);
        contactID = prefs.getInt("MY_CONTACT", 0);

        List<Contact> tempContact = database.contactDAO().getContact(contactID);
        if (tempContact.size() >= 1 ){
            txtName.setText(tempContact.get(0).contactName);
            txtEmail.setText(tempContact.get(0).emailAddress);

            int tempConTypeID = tempContact.get(0).contactType;
            contactType = ContactType.valueOf(tempConTypeID);

            addressType = contactType.name();
            txtContactType.setText(addressType);

        }

        final ArrayList<Phone> temp = new ArrayList<>();
        temp.addAll(database.phoneDAO().getCompanyPhoneNumbers(contactID));

        adapterPhone = new PhoneAdapter(this,temp);



        lvPhone.setAdapter(adapterPhone);
        lvPhone.setVisibility(View.VISIBLE);
        lvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phone ph = (Phone) parent.getItemAtPosition(position);
                if (ph != null)
                {
                    Intent intent = new Intent(getApplicationContext(), PhoneDetailActivity.class);

                        editor.putInt("MY_PHONE", ph.id);
                        editor.apply();
                        startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btnEditContact = findViewById(R.id.btnEditContactFromDetail);
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ContactEditDetailActivity.class);
                startActivity(intent);
            }
        });

        Button btnAddPhone = findViewById(R.id.btnAddPhone);
        btnAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PhoneNewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyDetailActivity.class);
        intent.putExtra("NEW_COMPANY_ID", companyID);
        startActivity(intent);
    }

}