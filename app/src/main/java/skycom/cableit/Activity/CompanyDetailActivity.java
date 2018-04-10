package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skycom.cableit.Classes.Adapters.AddressAdapter;
import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ExpandableListAdapter;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String companyIDString = "";
    private SharedPreferences existingCompanyPrefs;
    int companyID = 0;


    ListView lvContact;
    private static ContactAdapter adapterContact;
    ListView lvAddress;
    private static AddressAdapter adapterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        database = AppDatabase.getDatabase(getApplicationContext());
        TextView txtName = (TextView)findViewById(R.id.textName);
        TextView txtDescription = (TextView)findViewById(R.id.textDescription);
        int tempCompanyID;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                companyIDString = null;
            } else {
                companyIDString = extras.getString("COMPANY_ID");
            }
        } else {
            companyIDString = (String) savedInstanceState.getSerializable("COMPANY_ID");
        }

        if (companyIDString != null)
        {


            if (companyIDString != ""){
                tempCompanyID= Integer.parseInt(companyIDString) + 1;
                companyID = tempCompanyID;
                List<Company> companyOne = database.companyDAO().getCompany(tempCompanyID);
                txtName.setText(companyOne.get(0).name);
                txtDescription.setText(companyOne.get(0).description);
            }
            else{
                txtName.setText("No ID found");
            }
        }

        //This is used if creating a new company.
        if (companyIDString == "" || companyIDString == null){
            try{
                Bundle bundle = getIntent().getExtras();
                if (bundle != null )
                    companyID = bundle.getInt("NEW_COMPANY_ID");

                List<Company> companyOne = database.companyDAO().getCompany(companyID);
                txtName.setText(companyOne.get(0).name);
                txtDescription.setText(companyOne.get(0).description);
            }catch(Exception e) {
            }
        }


        //Adding the current company ID to sharedPrefs
        existingCompanyPrefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        SharedPreferences.Editor editor = existingCompanyPrefs.edit();
        editor.putInt("MY_COMPANY", companyID);
        editor.apply();

        //BEGINS SYDNEY TEST
        lvContact =(ListView)findViewById(R.id.lstContact);
        ArrayList<Contact> temp = new ArrayList<>();
        temp.addAll(database.contactDAO().getContactsFromCompany(companyID));

        adapterContact = new ContactAdapter(this,temp);

        lvContact.setAdapter(adapterContact);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact c = (Contact) parent.getItemAtPosition(position);
                if (c != null)
                {
                    Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                    if (companyIDString != ""){
                        //intent.putExtra("COMPANY_ID_TEST", companyID + "");
                        //intent.putExtra("CONTACT_ID", c.id + "");
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //END SYDNEY TEST

        lvAddress =(ListView)findViewById(R.id.lstAddress);
        ArrayList<Address> tempAddress = new ArrayList<>();
        tempAddress.addAll(database.addressDAO().getAddressFromCompany(companyID));



        adapterAddress = new AddressAdapter(this,tempAddress);

        lvAddress.setAdapter(adapterAddress);
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address a = (Address) parent.getItemAtPosition(position);
                if (a != null)
                {
                    Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);
                    if (companyIDString != "") {
                        intent.putExtra("COMPANY_ID_TEST", a.id + "");
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button to navigate to the Company detail Activity
        Button btnEditCompany = findViewById(R.id.btnEditCompany);
        btnEditCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompanyEditDetailActivity.class);
                if (companyIDString != ""){
                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
                    startActivity(intent);
                }
            }
        });

        //Buttong to navaigate to the Address Edit Detail Activity
        Button btnAddAddress = findViewById(R.id.btnNewAddress);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressNewDetailActivity.class);
                if (companyIDString != ""){
                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
                    startActivity(intent);
                }
            }
        });

        //Buttong to navaigate to the Address Edit Detail Activity
        Button btnAddContact = findViewById(R.id.btnNewContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactNewDetailActivity.class);
                if (companyIDString != ""){
                    intent.putExtra("COMPANY_ID_TEST", companyIDString + "");
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, CompanyListActivity.class);
        startActivity(intent);
    }
}
