package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;
    int contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        database = AppDatabase.getDatabase(getApplicationContext());

        TextView txtName = (TextView)findViewById(R.id.textContactName);
        TextView txtEmail = (TextView)findViewById(R.id.textContactEmail);

        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);
        //contactID = prefs.getInt("CONTACT_ID", 0);



        List<Contact> tempContact = database.contactDAO().getContactsFromCompany(companyID);
        if (tempContact.size() >= 1 ){
            txtName.setText(tempContact.get(0).contactName);
            txtEmail.setText(tempContact.get(0).emailAddress);
        }


        Button btnEditContact = findViewById(R.id.btnEditContactFromDetail);
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ContactEditDetailActivity.class);
                intent.putExtra("NEW_COMPANY_ID", companyID);
                intent.putExtra("CONTACT_ID", companyID);
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