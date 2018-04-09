package skycom.cableit.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
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

import skycom.cableit.Classes.Adapters.ContactAdapter;
import skycom.cableit.Classes.Address;
import skycom.cableit.Classes.Contact;
import skycom.cableit.Classes.ExpandableListAdapter;
import skycom.cableit.Classes.Company;
import skycom.cableit.Classes.Quote;
import skycom.cableit.Dao.CompanyDAO;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class CompanyDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    String companyIDString = "";
    private SharedPreferences existingCompanyPrefs;
    int companyID = 0;
    int newCompanyID = 0;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ListView tstListView;
    private static ContactAdapter adapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


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

            Bundle bundle = getIntent().getExtras();
            if (bundle != null )
                newCompanyID = bundle.getInt("NEW_COMPANY_ID");

            List<Company> companyOne = database.companyDAO().getCompany(newCompanyID);
            txtName.setText(companyOne.get(0).name);
            txtDescription.setText(companyOne.get(0).description);
        }


        //Adding the current company ID to sharedPrefs
        existingCompanyPrefs = getSharedPreferences("COMPANY_ID_TEST", MODE_PRIVATE);
        SharedPreferences.Editor editor = existingCompanyPrefs.edit();
        editor.putInt("MY_COMPANY", companyID);
        editor.apply();

        //BEGINS SYDNEY TEST
        tstListView=(ListView)findViewById(R.id.lstTest);
        ArrayList<Contact> temp = new ArrayList<>();
        temp.addAll(database.contactDAO().getContactsFromCompany(companyID));

        adapter= new ContactAdapter(this,temp);

        tstListView.setAdapter(adapter);
        tstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //this is clack manager
                Contact c = (Contact) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        String.valueOf(id) + c.contactName + c.emailAddress,
                        Toast.LENGTH_SHORT).show();
            }
        });
        //END SYDNEY TEST


        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Contacts");
        listDataHeader.add("Addresses");
        listDataHeader.add("Quotes");

        // Adding child data
        List<String> contactsStringList = new ArrayList<String>();
        List<Contact> contacts =  database.contactDAO().getContactsFromCompany(companyID);
        for(int i = 0; i < contacts.size(); i++){
            String thisString = contacts.get(i).contactName.toString();
            if(!thisString.equals(null)){
                contactsStringList.add(thisString);
            }
        }

        List<String> addressesStringList = new ArrayList<String>();
        List<Address> addresses =  database.addressDAO().getAddressFromCompany(companyID);
        for(int i = 0; i < addresses.size(); i++){
            String thisString = addresses.get(i).StreetAddress1.toString();
            if(!thisString.equals(null)){
                addressesStringList.add(thisString);
            }
        }

        List<String> quotesStringList = new ArrayList<String>();
        List<Quote> quotes =  database.quoteDAO().getQuotesForCompany(companyID);
        for(int i = 0; i < quotes.size(); i++){
            String thisString = quotes.get(i).quoteNumber.toString() + "\n" + quotes.get(i).dateUpdated.toString();
            if(!thisString.equals(null)){
                quotesStringList.add(thisString);
            }
        }

        listDataChild.put(listDataHeader.get(0), contactsStringList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), addressesStringList);
        listDataChild.put(listDataHeader.get(2), quotesStringList);
    }

}
