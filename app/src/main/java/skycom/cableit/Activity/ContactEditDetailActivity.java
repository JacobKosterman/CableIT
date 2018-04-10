package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import skycom.cableit.Classes.Contact;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class ContactEditDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    int companyID;
    int contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit_detail);

        database = AppDatabase.getDatabase(getApplicationContext());



        SharedPreferences prefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        companyID = prefs.getInt("MY_COMPANY", 0);

        List<Contact> tempContact = database.contactDAO().getContactsFromCompany(companyID);

        EditText txtName = (EditText)findViewById(R.id.textContactName);
        EditText txtEmail = (EditText)findViewById(R.id.textContactEmail);

        String tempAddOne = txtName.getText().toString();
        String tempAddTwo = txtEmail.getText().toString();


        if (!tempContact.isEmpty()) {
            txtName.setText(String.valueOf(tempContact.get(0).contactName), TextView.BufferType.EDITABLE);
            txtEmail.setText(String.valueOf(tempContact.get(0).emailAddress), TextView.BufferType.EDITABLE);
            contactID = tempContact.get(0).id;
        }
        Button btnEditContact = findViewById(R.id.btnEditContactFromDetail);
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ContactEditDetailActivity.class);
                intent.putExtra("NEW_COMPANY_ID", companyID);
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